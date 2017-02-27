# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/htainlinshwe/Documents/android_sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-dontwarn org.oscim.**
-dontwarn org.slf4j.**

-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-keep class org.oscim.** { *; }
-keep interface org.oscim.** { *; }
-keep class com.loopj.android.** { *; }
-keep interface com.loopj.android.** { *; }
-keep class javax.annotation.** { *; }
-keep interface javax.annotation.** { *; }
-keep class org.slf4j.** { *; }
-keep interface org.slf4j.** { *; }

-keep class java.awt.font.** { *; }
-keep class  javax.imageio.** { *; }
-keep class java.awt.print.** { *; }

# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\android-sdk\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/htainlinshwe/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-dontwarn org.apache.xmlgraphics.**
-dontwarn org.apache.xmlgraphics.image.codec.util.**
-dontwarn java.awt.image.**
-dontwarn  com.sun.management.OperatingSystemMXBean
-keep public class android.support.v7.widget.** { *; }
-keep public class android.support.v7.internal.widget.** { *; }
-keep public class android.support.v7.internal.view.menu.** { *; }

-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}
-keep public class com.google.android.gms.ads.** {
   public *;
}

-keep public class com.google.ads.** {
   public *;
}

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class .R
-keep class **.R$* {
    <fields>;
}

-keep class com.graphhopper.** { *; }
-keep class com.graphhopper.reader.** { *; }
-keep class com.graphhopper.util.** { *; }
-keep class io.paperdb.** { *; }
-keep class com.esotericsoftware.** { *; }
-dontwarn com.esotericsoftware.**
-keep class de.javakaffee.kryoserializers.** { *; }
-dontwarn de.javakaffee.kryoserializers.**
## GSON 2.2.4 specific rules ##
-dontwarn java.lang.invoke**

# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

-keepattributes EnclosingMethod


-printusage unused.txt

-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-printmapping out.map

-dontpreverify

-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable,*Annotation*,Signature,EnclosingMethod,InnerClasses


#General Android
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.content.Context {
    public void *(android.view.View);
    public void *(android.view.MenuItem);
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

-keep class com.graphhopper.** { *; }

#Billing
-keep class com.android.vending.billing.**
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

#Support Library
-keep public class * extends android.preference.PreferenceFragment
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.support.v4.app.FragmentActivity



-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}



-dontwarn android.support.**


# GMS
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**

# MixPanel
-dontwarn com.mixpanel.**

#Flurry
-keep class com.flurry.** { *; }
-dontwarn com.flurry.**
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
 }

# Square
-dontwarn com.squareup.okhttp.**
-dontwarn okio.**

# Retrofit
-keep class com.google.inject.* { *; }
-keep class org.apache.http.* { *; }
-keep class org.apache.james.mime4j.* { *; }
-keep class javax.inject.* { *; }
-keep class retrofit.** { *; }
-dontwarn rx.**
-dontwarn com.google.appengine.api.urlfetch.*
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}
-keep class rx.schedulers.Schedulers {
    public static <methods>;
}
-keep class rx.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class rx.schedulers.TestScheduler {
    public <methods>;
}
-keep class rx.schedulers.Schedulers {
    public static ** test();
}
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    long producerNode;
    long consumerNode;

}
#VTM
-dontwarn java.awt.*
-dontwarn java.awt.geom.*
-dontwarn org.slf4j.impl.*

# Jackson
-keepnames class org.codehaus.jackson.** { *; }
-dontwarn javax.xml.**
-dontwarn javax.xml.stream.events.**
-dontwarn com.fasterxml.jackson.databind.**

## GSON (used by Game Analytics) ##
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.** { *; }

#add models here

#with static references to required inner classes


-keepnames class * implements java.io.Serializable

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

#AsyncHttp
-keep class com.loopj.android.http.** { *; }
-keep interface com.loopj.android.http.** { *; }

#BugSense
-keep class com.bugsense.** { *; }



-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}


-keepclassmembers class * {
public void onEvent_(_);
}
#Facebook API
-keep class com.facebook.** { *; }

## GAME ANALYTICS ##
-keep class com.gameanalytics.android.** { *; }
-dontwarn java.awt.**
-dontskipnonpubliclibraryclasses
-dontwarn javax.imageio.**
-dontwarn org.bouncycastle.*
-dontwarn  org.bouncycastle.cms.**
-keep class com.smk.skpopupview.** { *; }
-dontwarn org.bouncycastle.crypto.**
-dontwarn org.bouncycastle.asn1.**
-dontwarn org.bouncycastle.jcajce.**
-dontwarn org.bouncycastle.cert.**
-dontwarn org.bouncycastle.operator.**
-dontwarn org.bouncycastle.jce.**
-dontwarn org.bouncycastle.cert.**
-dontwarn org.bouncycastle.ocsp.**
-dontwarn org.apache.xml.security.utils.**
-dontwarn com.smk.skpopupview.R$layout
-dontwarn com.smk.skpopupview.R$id
-dontwarn com.smk.skpopupview.R$menu
-dontwarn com.smk.skpopupview.R$menu
-dontwarn java.awt.FontMetrics

-dontwarn com.smk.skpopupview.R
-keep class org.spongycastle.** { *; }
-dontwarn org.spongycastle.**

-dontwarn freemarker.template.**
##CHECK HERE
-keep public class org.apache.xml.security.**{*;}
-dontwarn org.apache.jcp.**
-dontwarn org.bouncycastle.tsp.**

## iNew rules for EventBus 3.0.x ##
# http://greenrobot.org/eventbus/documentation/proguard/
-dontwarn sun.misc.Unsafe
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
#VividSolutions
-dontwarn com.vividsolutions.jts.awt.**
-keep class org.bouncycastle.cms.** { *; }
-keep class org.bouncycastle.asn1.** { *; }
# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-dontwarn okio.**
-keep class org.bouncycastle.** { *; }

-keepattributes Signature
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on RoboVM on iOS. Will not be used at runtime.
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

-dontwarn sun.misc.**

-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}

-dontwarn com.caverock.androidsvg.*

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** {
*;
}

-dontoptimize
-dontobfuscate

-dontwarn sun.misc.Unsafe
-dontwarn com.google.common.collect.MinMaxPriorityQueue

# GWT dev dependencies needed to ignore
-dontwarn java.awt.geom.AffineTransform
-dontwarn java.awt.Graphics2D
-dontwarn java.awt.image.BufferedImage
-dontwarn java.awt.RenderingHints
-dontwarn java.awt.RenderingHints$Key
-dontwarn javax.imageio.ImageIO
-dontwarn javax.imageio.ImageReader
-dontwarn javax.imageio.stream.MemoryCacheImageInputStream
-dontwarn javax.imageio.metadata.IIOMetadata
-dontwarn java.lang.management.ThreadMXBean
-dontwarn java.lang.management.ManagementFactory
-dontwarn java.lang.management.OperatingSystemMXBean
-dontwarn java.lang.management.GarbageCollectorMXBean
-dontwarn org.w3c.dom.Node




# WAsync + AHC dependencies
# TODO Check if Grizzly can be replaced with Apache Http Client
# For WAsync 1.4.3
-dontwarn com.ning.http.client.providers.**
-dontwarn com.ning.http.util.**


# Grizzly
-dontwarn com.ning.http.client.providers.netty.**
-dontwarn com.ning.http.client.providers.jdk.**
-dontwarn org.glassfish.grizzly.http.server.**
-dontwarn org.glassfish.grizzly.servlet.**
-dontwarn org.glassfish.grizzly.websockets.glassfish.**
-dontwarn org.glassfish.grizzly.websockets.**
-dontwarn com.ning.http.util.DefaultHostnameVerifier
-dontwarn com.google.common.util.concurrent.ServiceManager
-dontwarn java.nio.**
-dontwarn java.util.**

-keepclasseswithmembers public class * {
    public static void main(java.lang.String[]);
}

-keep public class java.awt.**
-keep class java.awt.** {*;}
# Guava depends on the annotation and inject packages for its annotations, keep them both
-keep public class javax.annotation**
-keep public class javax.inject**
-dontwarn java.awt.FontMetrics
-dontwarn java.awt.**,java.beans.**
-dontwarn com.itextpdf.awt.PdfGraphics2D

