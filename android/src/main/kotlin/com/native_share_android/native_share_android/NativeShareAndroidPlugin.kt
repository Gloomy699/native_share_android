package com.native_share_android.native_share_android

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import java.io.File

class NativeShareAndroidPlugin : FlutterPlugin, MethodCallHandler, ActivityAware {

  private lateinit var channel: MethodChannel
  private lateinit var context: android.content.Context
  private var activity: Activity? = null  // Добавляем переменную для хранения Activity

  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    context = flutterPluginBinding.applicationContext
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "native_share_android")
    channel.setMethodCallHandler(this)
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    activity = binding.activity
  }

  override fun onDetachedFromActivityForConfigChanges() {
    activity = null
  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    activity = binding.activity
  }

  override fun onDetachedFromActivity() {
    activity = null
  }

  override fun onMethodCall(call: MethodCall, result: Result) {
    when (call.method) {
      "sendEmail" -> {
        val email = call.argument<String>("email") ?: ""
        val subject = call.argument<String>("subject") ?: ""
        val body = call.argument<String>("body") ?: ""
        val attachment = call.argument<String>("attachment") ?: ""
        sendEmail(email, subject, body, attachment, result)
      }

      "shareFile" -> {
        val filePath = call.argument<String>("filePath")
        val text = call.argument<String>("text")
        if (filePath != null) {
          shareFile(filePath, text, result)
        } else {
          result.error("INVALID_ARGUMENT", "File path is null", null)
        }
      }

      "shareText" -> {
        val text = call.argument<String>("text") ?: ""
        shareText(text, result)
      }

      else -> result.notImplemented()
    }
  }

  private fun sendEmail(
    email: String,
    subject: String,
    body: String,
    attachment: String,
    result: Result
  ) {
    try {
      val intent = Intent(Intent.ACTION_SEND_MULTIPLE).apply {
        type = "message/rfc822"
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)

        val uris = ArrayList<Uri>()
        val file = File(attachment)
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
        uris.add(uri)

        putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
      }

      startActivityWithoutChooser(intent)
      result.success(null)
    } catch (e: ActivityNotFoundException) {
      result.error("NO_EMAIL_CLIENT", "Нет установленного почтового клиента", null)
    }
  }

  private fun shareFile(filePath: String, text: String?, result: Result) {
    try {
      val file = File(filePath)
      val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)

      val intent = Intent(Intent.ACTION_SEND).apply {
        type = "*/*"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        if (!text.isNullOrEmpty()) {
          putExtra(Intent.EXTRA_TEXT, text)
        }
      }

      startActivity(intent)
      result.success(null)
    } catch (e: Exception) {
      result.error("SHARE_ERROR", "Ошибка при отправке файла: ${e.message}", null)
    }
  }

  private fun shareText(text: String, result: Result) {
    try {
      val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
      }

      startActivity(intent)
      result.success(null)
    } catch (e: Exception) {
      result.error("SHARE_ERROR", "Ошибка при отправке текста: ${e.message}", null)
    }
  }

  private fun startActivity(intent: Intent) {
    activity?.startActivity(Intent.createChooser(intent, null))
      ?: run {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(Intent.createChooser(intent, null))
      }
  }

  private fun startActivityWithoutChooser(intent: Intent) {
    activity?.startActivity(intent)
      ?: run {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
      }
  }
}