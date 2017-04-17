# QuieroSerMillonario
**Proyecto final curso UPV Android**

PREREQUISITES
-------------
    · Android API v10 (Android 4.2 or +)
    · Copy $ROOT_FOLDER/data/questions*.xml to your physical or emulated device data folder of the app package
     (ie: /data/data/upv.ejercicios.proyectofinal.quierosermillonario/files/ )


INSTALLATION
------------

With Android SDK (**recommended**):
    Open the project and deploy it (via gradle) on your favourite device (physical or emulated device)

Installing directly from .apk file:
    Use Android debugger to run it on your emulator (or physical device): "adb install... app-debug.apk"

**PENDING TODO's AND IMPROVEMENTS** 
-------------------------------
1. Ask user for confirmation when settings are saved / changed [DONE]
2. Ask user for confirmation when local scores are about to be removed [DONE]
3. Notify user when he/she answers incorrectly a question (Game Over and score achieved) [DONE]
4. Remove jokers on the fly from the menu as long as they're being used [DONE]
5. Disable appropriate answers when 50% joker is requested [TODO]
6. Change location icons on "Settings" UI (don't match look'n'feel at all) [TODO]
7. What happens if user actually WINS the game? [TODO]
8. Test for bigger letters on "Play Game" UI [TODO]
9. Test for more bugs to appear (and MAYBE fix them) [TODO]
10. Change the icon in the map (Friend's scores tab) to show a cup icon for the best score

