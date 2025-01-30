import 'dart:async';
import 'package:flutter/services.dart';

class NativeShareAndroid {
  static const MethodChannel _channel = MethodChannel('native_share_android');

  /// Открывает почтовый клиент с возможностью прикрепления файлов
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
      print("Ошибка при отправке email: '${e.message}'");
    }
  }

  /// Делится файлом и (опционально) текстом
  static Future<void> shareFile({required String filePath, String? text}) async {
    try {
      await _channel.invokeMethod('shareFile', {
        'filePath': filePath,
        'text': text ?? "",
      });
    } on PlatformException catch (e) {
      print("Ошибка при отправке файла: '${e.message}'");
    }
  }

  /// Делится только текстом
  static Future<void> shareText(String text) async {
    try {
      await _channel.invokeMethod('shareText', {'text': text});
    } on PlatformException catch (e) {
      print("Ошибка при отправке текста: '${e.message}'");
    }
  }
}