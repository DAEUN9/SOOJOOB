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
# 소스 파일의 라인을 섞지 않는 옵션 (이거 안해주면 나중에 stacktrace보고 어느 line에서 오류가 난 것인지 확인 불가)
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
# 소스 파일 변수 명 바꾸는 옵션
-renamesourcefileattribute SourceFile


# ====================== [proguard 중요 사항] ========================

# 1. 난독화 적용 시 외부 라이브러리 및 jar 파일 사용 시 패키지 명을 확인해서 난독화 제외 설정 필요 (특정 객체 초기화 부분 클래스에서 패키지명 파악)

# 2. 프로젝트 설정 부분에서 release 모드와 debug 모드 나눠서 설정 가능

# ==================================================================





# ====================== [proguard option] =========================

#-dontwarn 패키지명.** : 지정해서 경고 무시

#-keep class 패키지명.** : 난독화가 필요하지 않은 경우

#-keep interface 패키지명.** : 난독화가 필요하지 않은 경우

#-ignorewarnings : 경고 무시

#-dontoptimize : 최적화 하지 않기

#-dontshrink : 사용하지 않는 메소드 유지

#-keepclassmembers : 특정 클래스 멤버 원상태 유지

#-keepattributes : 내부 클래스 원상태 유지 적용

#-dontshrink : 사용하지 않는 메소드 유지 설정

#-dontoptimize : 최적화하지 않음

#-dontobfuscate : 난독화를 수행 하지 않도록 함

#-verbose : 로그봄 설정

#-ignorewarnings : 경고 무시

#-dontpreverify : 사전 검증기능을 사용하지 않음

#-dontusemixedcaseclassnames : 대소문자가 혼합된 클래스명을 허용하지 않음

# ==================================================================





# ====================== [project settings] ========================

# [디버깅 모드에서 난독화 제외 설정]
-dontshrink
-dontoptimize
-dontobfuscate

# ==================================================================




# ========================== [Firebase] ============================

-keepattributes *Annotation*
-keepattributes Signature


# ==================================================================

# okhttp3
-dontwarn okhttp3.**
-dontwarn okio.**
-dontnote okhttp3.**
# okhttp3


# ====================== [class and google] ========================

-keep class com.google.android.** { *; }
-keep class google.** { *; }
-keep class android.** { *; }
-keep class androidx.** { *; }
-keep class com.android.** { *; }
-keep class com.google.** { *; }
-keep class lambda* { *; }
-keep class com.google.maps.** { *; }
#-keepclassmembers enum * {
#    values(...);
#    valueOf(...);
#}
#-keepclasseswithmembers class * {
#    native <methods>;
#}
#-keep public class * extends android.app.Activity { *; }
#-keep public class * extends android.app.Application { *; }
#-keep public class * extends android.app.Service { *; }
#-keep public class * extends android.content.BroadcastReceiver { *; }
#-keep public class * extends android.content.ContentProvider { *; }
#-keep public class * extends android.app.backup.BackupAgentHelper { *; }
#-keep public class * extends android.preference.Preference { *; }
#-keep public class * extends android.widget.TextView { *; }
#-keep public class * extends android.widget.Button { *; }

# ==================================================================





# ==================== [ok http and retrofit] ======================

#-keep class okhttp3.** { *; }
#-keep class okio.** { *; }
-keep class retrofit2.** { *; }

# ==================================================================

## Retrofit2
## Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
## EnclosingMethod is required to use InnerClasses.
#-keepattributes Signature, InnerClasses, EnclosingMethod
#
## Retrofit does reflection on method and parameter annotations.
#-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
#
## Retain service method parameters when optimizing.
#-keepclassmembers,allowshrinking,allowobfuscation interface * {
#    @retrofit2.http.* <methods>;
#}
#
## Ignore annotation used for build tooling.
#-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
#
## Ignore JSR 305 annotations for embedding nullability information.
#-dontwarn javax.annotation.**
#
## Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
#-dontwarn kotlin.Unit
#
## Top-level functions that can only be used by Kotlin.
#-dontwarn retrofit2.KotlinExtensions.**
#
## With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
## and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
#-if interface * { @retrofit2.http.* <methods>; }
#-keep,allowobfuscation interface <1>
## Retrofit2




# support v4 ,v7
-dontwarn android.support.**
# support v4 ,v7

# ========================== [glide image] =========================

-dontwarn com.bumptech.glide.**
-keep class com.bumptech.glide.** { *; }
-keep interface com.bumptech.glide.** { *; }
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# ==================================================================