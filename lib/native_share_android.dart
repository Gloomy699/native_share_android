import 'dart:async';
import 'package:flutter/services.dart';

class NativeShareAndroid {
  static const MethodChannel _channel = MethodChannel('native_share_android');

  /// Opens the email client with the option to attach files
  static Future<void> sendEmail({
    String? email,
    String? subject,
    String? body,
    String? attachment,
  }) async {
    try {
      await _channel.invokeMethod('sendEmail', {
        'email': email ?? "",
        'subject': subject ?? "",
        'body': body ?? "",
        'attachment': attachment ?? "",
      });
    } on PlatformException catch (e) {
      print("Error sending email: '${e.message}'");
    }
  }

  /// Shares a file (optionally with text)
  static Future<void> shareFile({required String filePath, String? text}) async {
    try {
      await _channel.invokeMethod('shareFile', {
        'filePath': filePath,
        'text': text ?? "",
      });
    } on PlatformException catch (e) {
      print("Error sharing file: '${e.message}'");
    }
  }

  /// Shares only text
  static Future<void> shareText(String text) async {
    try {
      await _channel.invokeMethod('shareText', {'text': text});
    } on PlatformException catch (e) {
      print("Error sharing text: '${e.message}'");
    }
  }
}