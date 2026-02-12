# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in the Android SDK.

# Keep data classes used with StateFlow
-keep class com.assignment.chatapp.model.** { *; }
-keep class com.assignment.chatapp.ui.uistates.ChatUiState { *; }

# Keep coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
