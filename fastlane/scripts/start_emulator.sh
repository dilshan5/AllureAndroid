#!/usr/bin/env bash

if [ $API == 28 ]
then
  export PACKAGE="system-images;android-${API};google_apis;x86_64"
  export ABI="google_apis/x86_64"
else
  export PACKAGE="system-images;android-${API};google_apis;x86"
  export ABI="google_apis/x86"
fi

export EMULATOR_NAME="${EMULATOR}_API${API}"

# Install AVD files
echo "y" | $ANDROID_HOME/tools/bin/sdkmanager --install $PACKAGE

# Create emulator
#echo "no" | $ANDROID_HOME/tools/bin/avdmanager create avd -n $EMULATOR_NAME --device $EMULATOR --abi $ABI --package $PACKAGE --force
#echo "no" | $ANDROID_HOME/tools/bin/avdmanager create avd -n $EMULATOR_NAME -d $EMULATOR --abi $ABI --package $PACKAGE --force
echo "no" | $ANDROID_HOME/tools/bin/avdmanager create avd -n $EMULATOR_NAME -d $EMULATOR --abi $ABI --package $PACKAGE --force

#list available emulators
#echo | $ANDROID_HOME/tools/bin/avdmanager list

$ANDROID_HOME/emulator/emulator -list-avds

echo "Starting emulator ${EMULATOR_NAME}"

# Start emulator in background
nohup $ANDROID_HOME/emulator/emulator -avd $EMULATOR_NAME -no-snapshot -no-window -no-audio -no-boot-anim -accel on -wipe-data > /dev/null 2>&1 & sleep 5s

#workaround for runner performance issues - https://github.com/actions/virtual-environments/issues/3719

echo y | find ~/.android

echo 'config.ini'
        cat ~/.android/avd/$EMULATOR_NAME.avd/config.ini
        echo 'hw.ramSize=4096MB' >> ~/.android/avd/$EMULATOR_NAME.avd/config.ini
        echo '== config.ini modified'
        cat ~/.android/avd/$EMULATOR_NAME.avd/config.ini

EMU_BOOTED=0
        n=0
        echo 1 > /tmp/failed
        while [[ $EMU_BOOTED = 0 ]];do
            echo "Test for current focus"
            #        $ANDROID_HOME/platform-tools/adb shell dumpsys window
            CURRENT_FOCUS=`$ANDROID_HOME/platform-tools/adb shell dumpsys window 2>/dev/null | grep -i mCurrentFocus`
            echo "Current focus: ${CURRENT_FOCUS}"
            case $CURRENT_FOCUS in
            *"Launcher"*)
              echo "Launcher is ready, Android boot completed"
              EMU_BOOTED=1
              rm /tmp/failed
            ;;
            *"Not Responding: com.android.systemui"*)
              echo "Dismiss System UI isn't responding alert"
              # adb shell input keyevent KEYCODE_DPAD_DOWN
              adb shell input keyevent KEYCODE_DPAD_DOWN
              adb shell input keyevent KEYCODE_ENTER
            ;;
            *"Not Responding: com.google.android.gms"*)
              echo "Dismiss GMS isn't responding alert"
              adb shell input keyevent KEYCODE_ENTER
            ;;
            *"Not Responding: system"*)
              echo "Dismiss Process system isn't responding alert"
              adb shell input keyevent KEYCODE_ENTER
            ;;
            *)
              n=$((n + 1))
              echo "Waiting Android to boot 10 sec ($n)..."
              sleep 10
              if [ $n -gt 60 ]; then
                  echo "Android Emulator does not start in 10 minutes"
                  exit 2
              fi
            ;;
            esac
        done
        echo "Android Emulator started."
