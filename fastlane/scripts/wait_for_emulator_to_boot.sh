#!/usr/bin/env bash
sleep 10
echo "list of active devices: $($ANDROID_HOME/platform-tools/adb devices)"
for n in $($ANDROID_HOME/platform-tools/adb devices | egrep -o  'emulator-(\d+)')
do
    echo "waiting for $n to boot"
    $ANDROID_HOME/platform-tools/adb -s $n wait-for-device shell 'while [[ -z $(getprop sys.boot_completed | tr -d '\r') ]]; do sleep 1; done;'
    echo "$n booted"
done