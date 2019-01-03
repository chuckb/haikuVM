@echo off
echo %0 %1 %2 %3 %4 %5 %6 %7 %8 %9

set "NXJ_COMMAND=%~n0"
set "NXJ_HOME=%~dp0\..\..\..\.."
if exist C:\java\win64\JDK-1.8.0 set "JAVA_HOME=C:\java\win64\JDK-1.8.0"

set ARG2=%2
if not "%ARG2:arduinoIDE=%" == "%ARG2%" goto arduino_ide

if not "%2" == "pc" 	goto prepared

cd "\Program Files (x86)\Microsoft Visual Studio 14.0\VC"
call vcvarsall

:prepared
cd %NXJ_HOME%
if not exist junitCProject mkdir junitCProject
cd junitCProject
if exist tutorials rmdir /s /q tutorials
if not exist tutorials mkdir tutorials
copy ..\myCProject\tutorials\* tutorials
goto mkdirs

:arduino_ide
if exist D:\arduino-1.8.1\libraries cd D:\arduino-1.8.1\libraries
if exist C:\arduino-1.8.1\libraries cd C:\arduino-1.8.1\libraries
goto mkdirs

:mkdirs
if exist haikuVM rmdir /s /q haikuVM
if exist haikuJava rmdir /s /q haikuJava
if exist utility rmdir /s /q utility
if exist target rmdir /s /q target
if exist haikuC rmdir /s /q haikuC

set TJAVAFILE=%5
call SET TJAVAFILE=%%TJAVAFILE:.=/%%

if exist %NXJ_HOME%\haikuBench\src\main\java\%TJAVAFILE%.java (
  set JAVAFILE=%NXJ_HOME%\haikuBench\src\main\java\%TJAVAFILE%
) else if exist %NXJ_HOME%\examples\src\main\java\%TJAVAFILE%.java (
  set JAVAFILE=%NXJ_HOME%\examples\src\main\java\%TJAVAFILE%
) else if exist %NXJ_HOME%\rcx_samples\src\main\java\%TJAVAFILE%.java (
  set JAVAFILE=%NXJ_HOME%\rcx_samples\src\main\java\%TJAVAFILE%
) else if exist %NXJ_HOME%\gallerie\src\main\java\%TJAVAFILE%.java (
  set JAVAFILE=%NXJ_HOME%\gallerie\src\main\java\%TJAVAFILE%
) else (
  set JAVAFILE=%TJAVAFILE%
)

set ARGS=
if not "%6" == "" set ARGS=%ARGS% %6
if not "%7" == "" set ARGS=%ARGS% %7
if not "%8" == "" set ARGS=%ARGS% %8
if not "%9" == "" set ARGS=%ARGS% %9


if "%2" == "junit.cygwin" 	set PATH=D:\cygwin\usr\local\bin;D:\cygwin\usr\bin;D:\cygwin\bin;.;%PATH%

goto %1
goto eof

:experiment
call %NXJ_HOME%\bin\haiku --Config %2 --Mode %3 %5
goto eof

:develop_link_upload
call %NXJ_HOME%\bin\haiku --Config %2 --Mode %3 %4 %ARGS% %JAVAFILE%
goto eof

:xdevelop_link_upload
call %NXJ_HOME%\bin\haikulink --Config %2 --Mode %3 -o junitCProject.hex --bootclasspath ../bootstrap/bin;../haikuRT\bin;../lib/nxt/classes.jar --classpath ../haikuBench/bin;../examples/bin;../gattaca/bin %5
call %NXJ_HOME%\bin\haikuupload                      junitCProject.hex
goto eof

:link_upload

rem call %NXJ_HOME%\bin\haikulink -v --Config %2 --Mode %3 -o junitCProject.hex C:\haikuVM\examples\src\main\java\arduino\tutorial\BlinkWithThread
call %NXJ_HOME%\bin\haikulink -v --Config %2 -o junitCProject.hex --bootclasspath D:\Entwicklung\haikuVM\lib\haikuvm\bootstrap.jar;D:\Entwicklung\haikuVM\lib\haikuvm\haikuRT.jar;D:\Entwicklung\haikuVM\lib\nxt\classes.jar --classpath ../haikuBench/bin;../examples/bin;../gattaca/bin %5
call %NXJ_HOME%\bin\haikuupload                      junitCProject.hex
goto eof

:link_upload_PanicSupport
call %NXJ_HOME%\bin\haikuc %JAVAFILE%.java
call %NXJ_HOME%\bin\haikulink -v --Config %2 --Mode %3 --Config:PanicSupport 1 --Config:PanicExceptions "NullPointerException|NoSuchMethodError|OutOfMemoryError|ClassCastException|InternalError|IndexOutOfBoundsException|ArrayIndexOutOfBoundsException|ArrayStoreException|StackOverflowError" %JAVAFILE%
call %NXJ_HOME%\bin\haikuupload
goto eof


:optimize_link_upload

java -jar %NXJ_HOME%\lib\pc\proguard.jar -keep class %5 { public static void main(java.lang.String[]);} @%NXJ_HOME%\lib\pc\myproguard.conf

call %NXJ_HOME%\bin\haikulink -v --Config %2 --Mode %3 -o junitCProject.hex --bootclasspath ..\lib\pc\optimized.jar %5
call %NXJ_HOME%\bin\haikuupload                      junitCProject.hex
goto eof


:eof
exit %errorlevel%
