# Welcome to haikuVM

This is a small Java Virtual Machine targeting Arduino compatible microcontrollers. It is based on work from Genom Bob and was pulled from [the haikuVM Sourceforge project](http://haiku-vm.sourceforge.net/). The base version pulled was 1.4.3.

## WIP

I have added this project to github because the original project is located in a subversion repo and that makes it harder to collaborate. Second, I am slowly adding gradle build capabilities to each of the sub-projects. My eventual goal is to make this work on the Teensyduino (done) and the Cross The Road Electronics Hero board (pending).

Subproject builds now working for most projects, however haikufier tests are not yet working from gradle. Use ```gradlew build -x :haikufier:test``` to exclude.
