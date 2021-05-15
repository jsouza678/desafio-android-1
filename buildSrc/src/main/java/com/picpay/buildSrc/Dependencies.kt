package com.picpay.buildSrc

object Dependencies {

    object Core {
        private const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
        private const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
        private const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
        private const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
        private const val gson = "com.google.code.gson:gson:${Versions.gson}"
        private const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
        private const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
        private const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
        private const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        private const val coroutinesX = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
        private const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
        private const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
        private const val room = "androidx.room:room-ktx:${Versions.room}"
        private const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
        const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"

        val list = listOf(
            kotlin,
            kotlinStdLib,
            coreKtx,
            appCompat,
            gson,
            viewModel,
            liveData,
            lifecycleRuntime,
            coroutinesCore,
            coroutinesX,
            navigationFragment,
            navigationUi,
            room,
            roomRuntime
        )
    }

    object Classpath {
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"

        val classPathList = listOf(
            kotlin,
            gradle
        )
    }

    object UI {
        private const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
        private const val material = "com.google.android.material:material:${Versions.material}"
        private const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
        private const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"
        private const val circleImageView = "de.hdodenhof:circleimageview:${Versions.circleImageView}"

        val list = listOf(
            constraintLayout,
            material,
            recyclerView,
            picasso,
            circleImageView
        )
    }

    /**
        https://insert-koin.io/docs/quickstart/kotlin
     */
    object DI {
        private const val koinAndroid = "org.koin:koin-android:${Versions.koin}"
        private const val koinCore = "org.koin:koin-core:${Versions.koin}"
        private const val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"

        val list = listOf(
            koinAndroid,
            koinCore,
            koinViewModel
        )
    }

    object Network {
        private const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        private const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
        private const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
        private const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.okHttp}"

        val list = listOf(
            retrofit,
            gsonConverter,
            okHttp,
            mockWebServer
        )
    }

    object Test {
        private const val jUnit = "junit:junit:${Versions.junit}"
        private const val mockitoCore = "org.mockito:mockito-core:${Versions.mockito}"
        private const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}"
        private const val coreTesting = "androidx.arch.core:core-testing:${Versions.coreTesting}"
        private const val mockitoInline = "org.mockito:mockito-inline:${Versions.mockitoInline}"
        private const val koinTest = "org.koin:koin-test:${Versions.koin}"
        private const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
        private const val testRunner = "androidx.test:runner:${Versions.testRunner}"
        private const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
        private const val coreKtx = "androidx.test:core-ktx:${Versions.coreKtxTest}"

        val testList = listOf(
            jUnit,
            mockitoCore,
            mockitoKotlin,
            coreTesting,
            mockitoInline,
            koinTest,
            coroutinesTest
        )

        val androidTestList = listOf(
            testRunner,
            espressoCore,
            coreKtx
        )
    }
}