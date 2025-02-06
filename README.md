# 📲 native_share_android

A Flutter plugin for **native sharing on Android**, including file sharing, text sharing, and email sending with attachments.

## Support my work 🙂
## 🇺🇦Donate to Ukraine's struggle for freedom and independence🇺🇦
If you like my project, you can support me:

[![Donate via PayPal](https://img.shields.io/badge/Donate-PayPal-blue)](https://www.paypal.com/donate/?hosted_button_id=75ZVP3YU4LRQG)
[![Donate via Monobank](https://img.shields.io/badge/Donate-Monobank-black)](https://send.monobank.ua/jar/9zFEbUM1pN)

## ✨ Features
✔ Share **files** using Android's native share sheet.  
✔ Share **text content** with other applications.  
✔ Send **emails with attachments** directly from your app.

---

## 📦 Installation

Add this dependency to your `pubspec.yaml`:
```yaml
dependencies:
  native_share_android: ^latest_version

Then run:

flutter pub get



🚀 Usage

import 'package:native_share_android/native_share_android.dart';


1️⃣ Share a File

NativeShareAndroid.shareFile(
  filePath: "/path/to/file.pdf",
  text: "Check this out!",
);


2️⃣ Share Text

NativeShareAndroid.shareText("Hello, world!");

3️⃣ Send Email with Attachment

NativeShareAndroid.sendEmail(
  email: "example@gmail.com",
  subject: "Test Email",
  body: "This is a test email.",
  attachment: "/path/to/file.pdf",
);

🔗 License

This project is licensed under the MIT License. See LICENSE for more details.

⚡ Enjoy seamless sharing in your Flutter apps! 🚀