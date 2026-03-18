plugins {
    id("com.android.application")
    // 删掉这一行：id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.liquidglasslsp" // 替换为你的包名
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.liquidglasslsp" // 替换为你的包名
        minSdk = 21
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
}

dependencies {
    // 核心 Compose 依赖
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation(platform("androidx.compose:compose-bom:2024.10.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // 你的玻璃效果库模块
    implementation(project(":backdrop"))

    // Xposed 模块必需依赖
    compileOnly("de.robv.android.xposed:api:82")
    compileOnly("de.robv.android.xposed:api-extras:82")

    // 单元测试依赖
    testImplementation("junit:junit:4.13.2")

    // 安卓仪器化测试依赖
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.10.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    // Debug 工具依赖
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
