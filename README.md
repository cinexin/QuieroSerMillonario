# QuieroSerMillonario
Proyecto final curso UPV Android

PREREQUISITES
-------------
    · Android API v10 (Android 4.2 or +)
    · Copy $ROOT_FOLDER/data/questions*.xml to your physical or emulated device data folder of the app package
     (ie: /data/data/upv.ejercicios.proyectofinal.quierosermillonario/files/ )


INSTALLATION
------------

With Android SDK (recommended):
    Open the project and deploy it (via gradle) on your favourite device (physical or emulated device)

Installing directly from .apk file:
    Use Android debugger to run it on your emulator (or physical device): "adb install... app-debug.apk"

PENDING TODO's AND IMPROVEMENTS 
-------------------------------
1. Ask user for confirmation when settings are saved / changed
2. Ask user for confirmation when local scores are about to be removed
3. Notify user when he/she answers incorrectly a question (Game Over and score achieved)
4. Remove jokers on the fly from the menu as long as they're being used
5. Disable appropriate answers when 50% joker is requested
6. Change location icons on "Settings" UI (don't match look & feel at all)
7. What happens if user actually WINS the game? 
8. Test for bigger letters on "Play Game" UI
9. Test for more bugs to appear (and MAYBE fix them)

