# Welcome to haikuVM

This is a small Java Virtual Machine targeting Arduino compatible microcontrollers. It is based on work from Genom Bob and was pulled from [the haikuVM Sourceforge project](http://haiku-vm.sourceforge.net/). The base version pulled was 1.4.3.

## Bare Metal Java on Raspberry Pi

While, at the time of this writing, the HaikuVM project only supported the VM on the Raspberry Pi running Linux, I have added the capability to run the Haiku Java VM bare metal (no operating system). This has been tested on the Pi Zero, Pi 2B, Pi 3A+, Pi 3B.

You can view a [sample exerciser application for the Pi](examples/src/main/java/rpi/tutorial/PiExerciser.java).

To run this example:

- Clone this repo:
```bash
git clone https://github.com/chuckb/haikuVM.git
```
- Install Java 8 or better
```bash
sudo apt-get install openjdk-8-jdk
```
- Install [ARM cross compiler](https://developer.arm.com/tools-and-software/open-source-software/developer-tools/gnu-toolchain/gnu-rm/downloads)
- Connect USB serial cable to Pi GPIO 14/15. You need to know the port assigned by your host OS. For Mac, my Adafruit serial dongle was assigned to ```/dev/tty.SLAB_USBtoUART```.
- Clone the bootloader repo:
```bash
git clone https://github.com/chuckb/raspbootin.git
```
- Build a bootloader for your Pi:
```bash
cd raspbootin/raspbootin2
make (rpi | rpi2 | rpi3)
```
- Get an SD card, remove all files, copy just built ```kernel.img``` to root of SD card.
- Copy the following [Raspberry Pi firmware files](https://github.com/raspberrypi/firmware/tree/master/boot) to the root of your SD card:
  - ```bootcode.bin```
  - ```fixup.dat```
  - ```start.elf```
- Create a ```config.txt``` on the SD card with the following in it:
```
start_file=start.elf
fixup_file=fixup.dat
kernel_address=0x02000000
```
- Insert SD card into Pi, and power up the Pi.
- Outside of haikuVM directory, make another project directory:
```bash
mkdir myproject
cd myproject
```
- Build and deploy (assume a Pi 3A+, a serial port of ```/dev/tty.SLAB_USBtoUART```, and the location of the haikuVM directory is ```/Users/chuck_benedict/haikuVM```):
```bash
/Users/chuck_benedict/haikuVM/bin/haiku -v --Config rpi3apbp --Config:Port /dev/tty.SLAB_USBtoUART /Users/chuck_benedict/haikuVM/examples/src/main/java/rpi/tutorial/PiExerciser.java
```

At the end of the build process, the app should run, you should note the ACT LED on the Pi flashing, and on the console: 
```
JRaspBootCom Raspberry Pi Boot Loader Version 1.0
http://github.com/chuckb/raspbootin
Copyright (c) 2020, Chuck Benedict
### Listening on /dev/tty.SLAB_USBtoUART

Raspbootin V2
#############
Built for: Raspberry Pi 3
#############
### sending kernel PiExerciser.img [37216 byte]
### finished sending
boot...
Bare Metal Java on Raspberry Pi Exercise Program.
Board Model: 0
Board Revision: 9445600
Firmware Version: 1588356021
Serial Number: 76458c3200000000
Mac Address: b8:27:eb:45:8c:32
Max ARM Clock Rate: 1400000000Hz
Tick: 7444
Tick: 7545
Tick: 7646
Tick: 7747
Tick: 7848
Tick: 7949
Tick: 8050
Tick: 8151
Tick: 8252
Tick: 8353
Tick: 8454
```

## WIP

I have added this project to github because the original project is located in a subversion repo, and that makes it harder to collaborate (for me anyway). Second, I am slowly adding gradle build capabilities to each of the sub-projects. Finally, my eventual goal is to make this work on the Teensyduino (done), Raspberry Pi bare metal (almost done...Pi 4 support pending) and the Cross The Road Electronics Hero board (pending).

Subproject builds are now working for most projects, however haikufier tests are not yet working from gradle. Use ```gradlew build -x :haikufier:test``` to exclude.

A [gradle plugin](https://github.com/chuckb/HaikuVMPlugin) to automate downloading the HaikuVM build system, compiling Java classes using the gradle Java plugin (so that IDEs will work nicely), running the haikulink tools, running the cross compiler, and then running a boot loader to upload your kernel to the target, is done, but needs a bit more TLC. It also automates building a bootable SD card image for the Raspberry Pi with a serial bootloader.
