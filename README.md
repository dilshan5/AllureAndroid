
[build]: https://github.com/allure-framework/allure-kotlin/actions
[build-badge]: https://github.com/allure-framework/allure-kotlin/workflows/Build/badge.svg
[license]: http://www.apache.org/licenses/LICENSE-2.0
[license-badge]: https://img.shields.io/badge/License-Apache%202.0-blue.svg

[![build-badge][]][build] [![License][license-badge]][license]

# Why Allure-Kotlin
`allurе-android` isn't supported anymore. Because of this reason we need to moved to `allure-kotlin`


# Allure Kotlin Integrations
Another reason for the creation of `allure-kotlin` was Android support for unit tests, Robolectric tests, and on-device instrumentation tests.

Why not `allure-java`?

Allure Java targets Java 1.8 which can't be used on Android. Due to having to be backward compatible with older devices, Android developers are limited to Java 1.6/1.7 with some support for Java 1.8 features. Unfortunately, it doesn't include things like Stream, Optional or MethodHandle.invoke. Because of that `allure-java` can't be used there.

The core of this library was ported from `allure-java`. Thanks to that `allure-kotlin` has the same API, features, test coverage and solutions as `allure-java`. On top of the core library support for Kotlin and Android test frameworks were added.

Check out the [Allure Documentation][allure-docs].

### Connection with allure-java
The core of this library was ported from `allure-java` in order to achieve compatibility with Java 1.6 and Android API 21. Thanks to that `allure-kotlin` has the same features, test coverage and solutions as `allure-java`. Following modules have been migrated:

- `allure-mode`l -> `allure-kotlin-model`
- `allure-java-commons` -> `allure-kotlin-commons`
- `allure-java-commons-tes`t -> `allure-kotlin-commons-test`

Following changes have to be made in order to keep the compatibility with Java 1.6:

- java.util.Optional (Java 1.8+) -> Kotlin null type & safe call operators
- java.util.stream.* (Java 1.8+) -> Kotlin collection operators
- java.nio.file.* (Java 1.7+) -> migrating form Path to File
- repeatable annotations (Java 1.8+) -> no alternatives, feature not supported by JVM 1.6

## Supported frameworks

- JUnit4
- Android Robolectric (via AndroidX Test)
- Android Instrumentation (via AndroidX Test)


## Getting started

#### Setting up the dependency
```gradle
repositories {
    mavenCentral()
}

dependencies {
    androidTestImplementation "io.qameta.allure:allure-kotlin-model:$LATEST_VERSION"
    androidTestImplementation "io.qameta.allure:allure-kotlin-commons:$LATEST_VERSION"
    androidTestImplementation "io.qameta.allure:allure-kotlin-junit4:$LATEST_VERSION"
    androidTestImplementation "io.qameta.allure:allure-kotlin-android:$LATEST_VERSION"
}
```

#### Attaching listener

AndroidX Test introduced a new `AndroidJUnit4` class runner that can be used for both **Robolectric** and **on-device instrumentation tests**. The same pattern is used for `AllureAndroidJUnit4` class runner. It attaches the allure listener to current class runner, but under the hood it uses `AndroidJUnit4`. All you need to do is to add `@RunWith(AllureAndroidJUnit4::class)` annotation to your test.

```kotlin
@RunWith(AllureAndroidJUnit4::class)
class MyInstrumentationTest {
    ...
}
```

Using AllureAndroidJUnit4 over class - works for both robolectric and on-device tests.

#### Robolectric tests

Robolectric tests are simple unit tests, hence the API is the same. The report data will be placed in the same place as for unit tests.

#### On-device instrumentation tests

You can also use testInstrumentationRunner for setting up runner.

```
android {
    defaultConfig {
        testInstrumentationRunner "io.qameta.allure.android.runners.AllureAndroidJUnitRunner"
    }
}
```

#### Integration
As on-device instrumentation test run on an actual device, the results have to be saved there as well.
You don't need to add any permissions to manifest: results are saved in an app files directory, e.g.
`/data/data/com.example/files/allure-results`.


After the tests are finished you can move the results to the external storage and pull the files using an **adb** like this one:
```
# Assuming your package is com.example
adb exec-out run-as com.example sh -c 'cd /data/data/com.example/files && tar cf - allure-results' > allure-results.tar

# Or using pull
$ adb shell
$ run-as com.example sh -c 'cd /data/data/com.example/files && tar cf - allure-results' | tar xvf - -C /data/local/tmp
# Ignore the permission errors
$ exit
$ adb pull /data/local/tmp/allure-results
```
Finally, you can generate the report via Allure CLI (see the [Allure Documentation][allure-cli]) or generate report with [allure-gradle][allure-gradle-plugin] plugin.

#### **Orchestrator TestStorage**
When tests clears app data between each tests then saving test results in app storage will not work because old test results will be cleared when app data is cleared.
To save test results directly on sdcard new TestStorage from androidx.test.services can be used.

Enabling test storage for automation tests:
 - add `allure.results.useTestStorage=true` to `allure.properties` in androidTest resources
 - add `androidTestUtil("androidx.test:orchestrator:VERSION}` to your app dependencies (if you do not have it already)

After that allure will use TestStorage to save test results. Test results will be saved by default into `/sdcard/googletest/test_outputfiles/allure-results`
To get those files from device you can use e.g `adb exec-out sh -c 'cd  /sdcard/googletest/test_outputfiles && tar cf - allure-results' | tar xvf - -C /output/dir`

*NOTE: allure-results folder name can be changed using `allure.results.directory` property.*

[allure-gradle-plugin]: https://github.com/allure-framework/allure-gradle
[allure-cli]: https://docs.qameta.io/allure/#_reporting
[gradle-test-listener]: https://discuss.gradle.org/t/how-to-attach-a-runlistener-to-your-junit-4-tests-in-gradle/30788
[allure-docs]: https://docs.qameta.io/allure/
