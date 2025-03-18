# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Keep Koin DI classes
-keep class org.koin.** { *; }

# Keep InjectionKt and everything inside the DI package
-keep class com.example.core.di.** { *; }
-keepclassmembers class com.example.core.di.** { *; }

# Keep all classes with @Inject annotation (for DI)
-keepattributes *Annotation*

# Prevent Kotlin metadata from being removed
-keepattributes InnerClasses, EnclosingMethod, Signature

# Keep all classes in the domain model package
-keep class com.example.core.domain.model.** { *; }
-keepclassmembers class com.example.core.domain.model.** { *; }

-dontwarn java.lang.invoke.StringConcatFactory

-keep class com.example.core.databinding.** { *; }
-keep class com.example.core.di.** { *; }
-keep class com.example.core.domain.usecase.**{*;}
-keep class * implements retrofit2.CallAdapter { *; }
-keep class * implements retrofit2.Converter { *; }
-keep class * extends retrofit2.Retrofit$Builder { *; }
-keep,allowobfuscation interface com.example.core.data.remote.ApiService { *; }
-keep class com.example.core.data.remote.** { *; }


