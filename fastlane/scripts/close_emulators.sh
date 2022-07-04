#!/usr/bin/env bash
for n in $($ANDROID_HOME/platform-tools/adb devices | egrep -o  'emulator-(\d+)')
do
    echo "Stopping emulator device: $n"
    $ANDROID_HOME/platform-tools/adb -s $n emu kill
    echo "$n killed"
done