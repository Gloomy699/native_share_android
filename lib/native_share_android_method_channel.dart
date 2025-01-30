import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'native_share_android_platform_interface.dart';

/// An implementation of [NativeShareAndroidPlatform] that uses method channels.
class MethodChannelNativeShareAndroid extends NativeShareAndroidPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('native_share_android');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
