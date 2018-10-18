package me.maxandroid.tieguanyinsimple.compiler.prebuilt

import com.bennyhuo.aptutils.types.ClassType

val CONTEXT = ClassType("android.content.Context")
val INTENT = ClassType("android.content.Intent")
val ACTIVITY = ClassType("android.app.Activity")
val BUNDLE = ClassType("android.os.Bundle")
val BUNDLE_UTILS = ClassType("me.maxandroid.tieguanyinsimple.runtime.utils.BundleUtils")

val ACTIVITY_BUILDER = ClassType("me.maxandroid.tieguanyinsimple.runtime.ActivityBuilder")