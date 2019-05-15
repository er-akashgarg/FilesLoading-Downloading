-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose
-ignorewarnings
-printmapping mapping.txt
-printusage usage.txt
-dontskipnonpubliclibraryclasses
-optimizationpasses 5
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-keepattributes *Annotation*,EnclosingMethod,SourceFile,LineNumberTable,Exceptions,Deprecated
-dontwarn android.support.**
-dontwarn com.google.**
-keepattributes -keepattributes Exceptions
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-allowaccessmodification
-renamesourcefileattribute MyApplication
-repackageclasses ''


#GSON-----------------------------------------------------------------------------------------------
-dontwarn sun.misc.**
-keep class com.google.gson.stream.** { *; }


#Picasso Library -----------------------------------------------------------------------------------
-keep class com.squareup.picasso.**{ *; }
-dontwarn com.squareup.picasso.**


#Retrofit2 -----------------------------------------------------------------------------------------
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-dontwarn retrofit2.adapter.rxjava.CompletableHelper$**


-keepattributes EnclosingMethod
-keepclasseswithmembers class * {
    @retrofit2.* <methods>;
}

-keepclasseswithmembers interface * {
    @retrofit2.* <methods>;
}
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}


# okhttp3 ------------------------------------------------------------------------------------------
-dontwarn okio.**
-keep class okhttp3.internal.**{ *; }
-dontwarn okhttp3.internal.**
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-keepclassmembers class * implements javax.net.ssl.SSLSocketFactory {
    private final javax.net.ssl.SSLSocketFactory delegate;
}


#RX-JAVA -------------------------------------------------------------------------------------------
-keep class io.reactivex.** { *; }
-dontwarn io.reactivex.**

-keep class android.widget.Space.** { *; }
-dontwarn android.widget.Space.**

-keep class org.simpleframework.xml.**{ *; }
-dontwarn org.simpleframework.xml.**

-keep class com.marshalchen.ultimaterecyclerview.**{ *; }
-dontwarn com.marshalchen.ultimaterecyclerview.**

-keep class com.squareup.picasso.**{ *; }
-dontwarn com.squareup.picasso.**

-keep class io.jsonwebtoken.**{ *; }
-dontwarn io.jsonwebtoken.**

-keep class com.malinskiy.superrecyclerview.**{ *; }
-dontwarn com.malinskiy.superrecyclerview.**

-keep class com.fasterxml.jackson.**{ *; }
-dontwarn com.fasterxml.jackson.**

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
    }


