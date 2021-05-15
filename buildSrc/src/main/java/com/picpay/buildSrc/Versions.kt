package com.picpay.buildSrc

object Versions {

    /**
        Build.Gradle configuration
    **/
    const val compileSdk = 30
    const val minSdk = 21
    const val targetSdk = 30

    // Version
    private const val versionMajor = 1
    private const val versionMinor = 0
    private const val versionPatch = 0

    const val versionCode = versionMajor * 10000 + versionMinor * 100 + versionPatch
    const val versionName = "${versionMajor}.${versionMinor}.${versionPatch}"

    const val kotlin = "1.4.10"
    const val gradle = "4.1.3"

    const val appCompat = "1.1.0"
    const val gson = "2.8.6"
    const val coreKtx = "1.2.0"
    const val coreTesting = "2.1.0"
    const val constraintLayout = "1.1.3"
    const val material = "1.1.0"
    const val recyclerView = "1.2.0"
    const val navigation = "2.3.5"
    const val retrofit = "2.9.0"
    const val okHttp = "4.3.1"
    const val picasso = "2.71828"
    const val circleImageView = "3.0.0"
    const val koin = "2.2.0-rc-4"
    const val lifecycle = "2.2.0"
    const val coroutines = "1.3.3"
    const val coreKtxTest = "1.2.0"
    const val room = "2.3.0"

    const val junit = "4.12"
    const val mockito = "2.27.0"
    const val mockitoInline = "2.8.47"
    const val mockitoKotlin = "2.1.0"
    const val testRunner = "1.1.1"
    const val espresso = "3.1.1"
}