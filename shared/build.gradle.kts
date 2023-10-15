plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("io.realm.kotlin") version "1.10.0"
    kotlin("plugin.serialization")
}
kotlin {
    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = "1.0.0"
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }
       // extraSpecAttributes["resources"] = "['src/commonMain/resources/**', 'src/iosMain/resources/**']"
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                api(compose.animation)

                val precomposeVersion = "1.5.0"
                api("moe.tlaster:precompose:$precomposeVersion")
                api("moe.tlaster:precompose-viewmodel:$precomposeVersion")
                api("moe.tlaster:precompose-koin:$precomposeVersion")

                val storeVersion = "5.0.0"
                implementation("org.mobilenativefoundation.store:store5:$storeVersion")

                implementation("io.insert-koin:koin-core:3.4.3")
                implementation ("io.insert-koin:koin-compose:1.0.4")
                api("io.github.qdsfdhvh:image-loader:1.6.3")


                val realmVersion ="1.10.0"
                implementation("io.realm.kotlin:library-base:$realmVersion")
                implementation("io.realm.kotlin:library-sync:$realmVersion")

                val coroutineVersion = "1.7.3"
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")

                val ktorClientVersion ="2.3.3"
                implementation("io.ktor:ktor-client-core:$ktorClientVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorClientVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorClientVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorClientVersion")
                implementation("io.ktor:ktor-client-cio:$ktorClientVersion")
                implementation("io.ktor:ktor-client-logging:$ktorClientVersion")
            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.activity:activity-compose:1.8.0")
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.12.0")
                implementation("io.ktor:ktor-client-android:2.3.3")

            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation("io.ktor:ktor-client-darwin:2.3.3")
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.myapplication.common"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}


