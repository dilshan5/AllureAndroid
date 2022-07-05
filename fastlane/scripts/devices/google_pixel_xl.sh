#!/usr/bin/env bash

export PACKAGE="system-images;android-32;google_apis;arm64-v8a"
export ABI="arm64-v8a"


# Install AVD files
#echo "y" | $ANDROID_HOME/tools/bin/sdkmanager --install $PACKAGE // if JDK is 1.8
# refer error : https://stackoverflow.com/questions/67948840/exception-in-thread-main-java-lang-noclassdeffounderror-javax-xml-bind-annota
echo "y" |$ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager --install $PACKAGE

# Create emulator
# if JDK is 1.8
# echo "no" | $ANDROID_HOME/tools/bin/avdmanager create avd -n $EMULATOR_NAME -d $EMULATOR --abi $ABI --package $PACKAGE --force
echo "no" | $ANDROID_HOME/cmdline-tools/latest/bin/avdmanager create avd -n google_pixel_xl -d pixel_xl --abi $ABI --package $PACKAGE --force

$ANDROID_HOME/emulator/emulator -list-avds

echo "Starting emulator google_pixel_xl_API_32"

# Start emulator in background
nohup $ANDROID_HOME/emulator/emulator -avd google_pixel_xl -no-snapshot -no-audio -no-boot-anim -accel on -wipe-data > /dev/null 2>&1 & sleep 5s

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
