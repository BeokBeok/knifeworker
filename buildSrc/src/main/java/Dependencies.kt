object Version {
    const val KOTLIN = "1.5.31"
    const val MATERIAL = "1.4.0"
    const val CORE_KTX = "1.6.0"
    const val CONSTRAINT_LAYOUT = "2.1.0"
    const val JUNIT = "1.1.1"

    const val MATERIAL_DIALOG = "3.1.1"

    const val PLAY_SERVICE_ADS = "20.5.0"

    const val HILT = "2.38.1"
}

object Libraries {
    const val KOTLIN_STDLIB_JDK8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Version.KOTLIN}"
    const val MATERIAL = "com.google.android.material:material:${Version.MATERIAL}"
    const val CORE_KTX = "androidx.core:core-ktx:${Version.CORE_KTX}"
    const val CONSTRAINT_LAYOUT =
        "androidx.constraintlayout:constraintlayout:${Version.CONSTRAINT_LAYOUT}"
    const val JUNIT = "androidx.test.ext:junit:${Version.JUNIT}"
    const val KOTLIN_TEST_JUNIT =
        "org.jetbrains.kotlin:kotlin-test-junit:${Version.KOTLIN}"
    const val JUNIT_KTX = "androidx.test.ext:junit-ktx:1.1.1"
    const val TEST_CORE_KTX = "androidx.test:core-ktx:1.2.0"
    const val ROBOLECTRIC = "org.robolectric:robolectric:4.6.1"
    const val CORE_TESTING = "androidx.arch.core:core-testing:2.0.0"

    const val MATERIAL_DIALOG_CORE =
        "com.afollestad.material-dialogs:core:${Version.MATERIAL_DIALOG}"
    const val MATERIAL_DIALOG_DATETIME =
        "com.afollestad.material-dialogs:datetime:${Version.MATERIAL_DIALOG}"

    const val PLAY_SERVICES_ADS =
        "com.google.android.gms:play-services-ads:${Version.PLAY_SERVICE_ADS}"
    const val PLAY_CORE_KTX = "com.google.android.play:core-ktx:1.8.1"

    const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:1.3.6"
}

object Hilt {
    const val ANDROID = "com.google.dagger:hilt-android:${Version.HILT}"
    const val COMPILER = "com.google.dagger:hilt-compiler:${Version.HILT}"
}

object Root {
    const val GRADLE = "com.android.tools.build:gradle:7.0.2"
    const val KOTLIN_GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.KOTLIN}"
    const val HILT_ANDROID_GRADLE_PLUGIN = "com.google.dagger:hilt-android-gradle-plugin:${Version.HILT}"
}
