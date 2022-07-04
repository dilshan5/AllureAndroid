#!/usr/bin/env bash

# Install AVD files
echo "y" | $ANDROID_HOME/tools/bin/sdkmanager --install 'system-images;android-30;google_apis;x86'

# Create emulator
echo "no" | $ANDROID_HOME/tools/bin/avdmanager create avd -n google_pixel_xl -d pixel_xl -k 'system-images;android-30;google_apis;x86' -f

$ANDROID_HOME/emulator/emulator -list-avds

echo "Starting emulator google_pixel_xl"

# Start emulator in background
nohup $ANDROID_HOME/emulator/emulator -avd google_pixel_xl -no-snapshot -no-window -no-audio -no-boot-anim -memory 4000 > /dev/null 2>&1 & sleep 5s
