import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'native_share_android_method_channel.dart';

abstract class NativeShareAndroidPlatform extends PlatformInterface {
  /// Constructs a NativeShareAndroidPlatform.
  NativeShareAndroidPlatform() : super(token: _token);

  static final Object _token = Object();

  static NativeShareAndroidPlatform _instance = MethodChannelNativeShareAndroid();

  /// The default instance of [NativeShareAndroidPlatform] to use.
  ///
  /// Defaults to [MethodChannelNativeShareAndroid].
  static NativeShareAndroidPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [NativeShareAndroidPlatform] when
  /// they register themselves.
  static set instance(NativeShareAndroidPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
