import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.vanniktech.maven.publish") version "0.28.0"
}

android {
    namespace = "com.aone.permissions"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
}


mavenPublishing {
    coordinates(
        groupId = "io.github.kapact",
        artifactId = "aonepermissions",
        version = "1.0.0"
    )

    // Configure POM metadata for the published artifact
    pom {
        name.set("AOne Permissions")
        description.set("Library to get permissions on android jetpack compose in few lines of code.")
        inceptionYear.set("2024")
        url.set("https://github.com/kapact/aonepermissions")

        licenses {
            license {
                name.set("Apache license 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0")
            }
        }

        developers {
            developer {
                id.set("kapact")
                name.set("Akshay kumar shaw")
                email.set("akshay.8459361606@gmail.com")
            }
        }

        scm {
            url.set("https://github.com/kapact/aonepermissions")
        }

        // Configure publishing to maven central
        publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

        // Enable GPG signing for all publications
        signAllPublications()

//             Command to publish
//            ./gradlew publishAndReleaseToMavenCentral --no-configuration-cache
    }
}