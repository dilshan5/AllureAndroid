#!/usr/bin/env bash

# TODO: fix script

# Install AVD files
echo "y" | $ANDROID_HOME/tools/bin/sdkmanager --install 'system-images;android-28;google_apis;x86_64'

# Create emulator
echo "no" | $ANDROID_HOME/tools/bin/avdmanager create avd -n samsung_galaxy_s9 -k 'system-images;android-28;google_apis;x86_64'  -f

$ANDROID_HOME/emulator/emulator -list-avds

echo "Starting emulator samsung_galaxy_s9"

# Start emulator in background
nohup $ANDROID_HOME/emulator/emulator -avd samsung_galaxy_s9 -skin 2960x1440 > /dev/null 2>&1 &