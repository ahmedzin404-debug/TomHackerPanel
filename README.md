# Tom Freestyle — Android Kotlin

A mobile-only fictional hacker-panel prank/simulation.

## Features
- Login code: `98062`
- Blue Freestyle panel
- Secret code: `TOM`
- Red visual panel with AIM / BOLT / AIM LOOK / USB / SENSI toggles
- Draggable floating Tom bubble using Android overlay permission
- Tap bubble to reopen the main app
- No real game manipulation, aim assistance, USB control, system commands, network actions, or hacking functionality

## Build
Open the project folder in Android Studio, allow Gradle to sync, then use:
Build > Build APK(s)

The floating bubble requires the user to manually allow **Display over other apps** in Android settings.

The app uses an Android foreground service with the `specialUse` type because modern Android versions require a declared foreground-service type for target SDK 34+.

## Supplied image
`app/src/main/res/drawable/tom_avatar.png` is the Tom image supplied by the user.
