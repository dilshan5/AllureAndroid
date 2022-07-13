fastlane documentation
----

# Installation

Make sure you have the latest version of the Xcode command line tools installed:

```sh
xcode-select --install
```

For _fastlane_ installation instructions, see [Installing _fastlane_](https://docs.fastlane.tools/#installing-fastlane)

# Available Actions

### sonar_report

```sh
[bundle exec] fastlane sonar_report
```

run a sonar scan

----


## Android

### android unit_tests

```sh
[bundle exec] fastlane android unit_tests
```

Execute unit tests

### android ui_tests

```sh
[bundle exec] fastlane android ui_tests
```

Execute instrumentation test on Emulator

### android pr_to_develop

```sh
[bundle exec] fastlane android pr_to_develop
```

Submit a PR to develop branch

### android compile

```sh
[bundle exec] fastlane android compile
```

Compile debug and test sources

### android beta

```sh
[bundle exec] fastlane android beta
```

Submit a new Beta Build to Crashlytics Beta

### android deploy

```sh
[bundle exec] fastlane android deploy
```

Deploy a new version to the Google Play

----

This README.md is auto-generated and will be re-generated every time [_fastlane_](https://fastlane.tools) is run.

More information about _fastlane_ can be found on [fastlane.tools](https://fastlane.tools).

The documentation of _fastlane_ can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
