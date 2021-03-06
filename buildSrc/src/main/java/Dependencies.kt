object Version {
    const val KOTLIN = "1.3.70"
    const val MATERIAL = "1.2.0-alpha03"
    const val CORE_KTX = "1.1.0"
    const val CONSTRAINT_LAYOUT = "1.1.3"
    const val JUNIT = "1.1.1"
    const val ESPRESSO = "3.2.0"
    const val LIFECYCLE_EXT = "2.2.0-rc03"

    const val MATERIAL_DIALOG = "3.1.1"

    const val PLAY_SERVICE_ADS = "18.3.0"
}

object Libraries {
    const val KOTLIN_STDLIB_JDK7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Version.KOTLIN}"
    const val MATERIAL = "com.google.android.material:material:${Version.MATERIAL}"
    const val CORE_KTX = "androidx.core:core-ktx:${Version.CORE_KTX}"
    const val CONSTRAINT_LAYOUT =
        "androidx.constraintlayout:constraintlayout:${Version.CONSTRAINT_LAYOUT}"
    const val JUNIT = "androidx.test.ext:junit:${Version.JUNIT}"
    const val ESPRESSO = "androidx.test.espresso:espresso-core:${Version.ESPRESSO}"
    const val KOTLIN_TEST_JUNIT =
        "org.jetbrains.kotlin:kotlin-test-junit:${Version.KOTLIN}"
    const val JUNIT_KTX = "androidx.test.ext:junit-ktx:1.1.1"
    const val TEST_CORE_KTX = "androidx.test:core-ktx:1.2.0"
    const val ROBOLECTRIC = "org.robolectric:robolectric:4.3.1"
    const val CORE_TESTING = "androidx.arch.core:core-testing:2.0.0"

    const val LIFECYCLE_EXT = "androidx.lifecycle:lifecycle-extensions:${Version.LIFECYCLE_EXT}"

    const val MATERIAL_DIALOG_CORE =
        "com.afollestad.material-dialogs:core:${Version.MATERIAL_DIALOG}"
    const val MATERIAL_DIALOG_DATETIME =
        "com.afollestad.material-dialogs:datetime:${Version.MATERIAL_DIALOG}"

    const val PLAY_SERVICES_ADS =
        "com.google.android.gms:play-services-ads:${Version.PLAY_SERVICE_ADS}"
    const val PLAY_CORE_KTX = "com.google.android.play:core-ktx:1.8.1"

    const val PREFERENCE_KTX = "androidx.preference:preference-ktx:1.1.1"
}
