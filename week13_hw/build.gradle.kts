// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // Other plugins
    id("com.android.application") version "7.0.3" apply false
    id("com.google.gms.google-services") version "4.3.10" apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
