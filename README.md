# Training App - SCOUTING LEAD Message

Hello, this app is meant to be both training and a baseplate for applications that run on Windows devices. In scouting, we sometimes need to make apps off the tablets; which is where this setup should help. It doesn't take long to learn JavaFX, but is best to learn with all the fundamentals of Android Studio's Solo training - since this system connects both worlds. 

I'll use this README as a way to explain exporting the app and the training README to direct where to find files. 

When using this app as a baseplate, other than the standard gradle dependencies, you also need to update module-info.java with the imports you use. This is so the exported .exe can actually compile with the dependencies you call, or else everything breaks. Here's the command you run INSIDE THE PROJECT TERMINAL to build the app:

./gradlew jpackageImage

And the result will be found in ..\Training_IntelliJ\build\jpackage

The name, version, etc can all be updated in build.gradle under jlink

Last updated: 07/09/26 - IntelliJ IDEA 2026.2.1 (JDK 17)
