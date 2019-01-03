@echo off
rem The base for haikuc was nxjc from leJOS

if Not "%CLASSESDIR%" == "" goto :PathIsSet

	set CLASSESDIR=%TEMP%\haikuvm
	if not exist %CLASSESDIR% md %CLASSESDIR%


	rem PATH for AVR cross compiler
    rem set XCOMPILER_AVR=      C:\Arduino-1.8.1\hardware\tools\avr\utils\bin;C:\Arduino-1.8.1\hardware\tools\avr\bin
	set XCOMPILER_AVR=%XCOMPILER_AVR%;%~p0..\hardware\tools\avr\utils\bin;%~p0..\hardware\tools\avr\bin;%~p0..\hardware\tools\avr\etc;C:\HaikuVM\hardware\tools\avr\utils\bin;C:\HaikuVM\hardware\tools\avr\bin;C:\HaikuVM\hardware\tools\avr\etc

	rem PATH for hitachi H8300 cross compiler
	set XCOMPILER_H8300=%~p0..\hardware\tools\h8300\bin;%~p0..\hardware\tools\lejos_rcx;C:\haikuVM\hardware\tools\h8300\bin;C:\haikuVM\hardware\tools\lejos_rcx

	rem this and the relative PATHs are good for HaikuVM
	set PATH=%PATH%;%XCOMPILER_AVR%;%XCOMPILER_H8300%;.;../../..

:PathIsSet

if "%OS%" == "Windows_NT" goto :winnt

:win98
	echo Windows 9x/ME is no longer supported.
	echo Please upgrade to Windows 2000 or later.
	goto :eof

:append_jar
	set "TMP_CP=%TMP_CP%%TMP_JAR%"
	goto :eof

:build_classpath
	if not exist "%~2" (
		echo Internal error. The following directory does not exist:
		echo   "%~2"
		exit /B 1
	)

	set "TMP_CP="
	for /R "%~2" %%i in ("%~3") do (
		set "TMP_JAR=%~4%%i"
		call :append_jar
	)
	set "%~1=%TMP_CP:~1%"
	goto :eof

:search_path
	set "%~1=%~f$PATH:2"
	goto :eof

:find_make
	call :search_path MAKE make.exe
	if "%MAKE%" == "" (
		echo make.exe was not found in the default search path.
		echo Install a WINAVR.
		exit /B 1
	)
	goto :eof

:find_java_and_javac
	call :search_path JAVA java.exe
	call :search_path JAVAC javac.exe
	if "%JAVA%" == "" (
		echo java.exe was not found in the default search path.
		echo Install a JDK and set the variable LEJOS_NXT_JAVA_HOME
		echo to the root directory of the JDK.
		echo Example: set LEJOS_NXT_JAVA_HOME=C:\Program Files\Java\jdk1.8.0_31
		echo .
		exit /B 1
	) else if "%JAVAC%" == "" (
		echo javac.exe was not found in the default search path.
		echo Consider setting the variable LEJOS_NXT_JAVA_HOME to
		echo the root directory of a JDK. Otherwise,
		echo some tools might not work.
		echo Example: set LEJOS_NXT_JAVA_HOME=C:\Program Files\Java\jdk1.8.0_31
		echo .
		exit /B 1
	)
	goto :eof

:set_java_and_javac
	set "JAVA=%~2\bin\java.exe"
	set "JAVAC=%~2\bin\javac.exe"
	if not exist "%JAVA%" (
		echo The variable %~1 does not point to the root directory of
		echo a JRE or JDK. The following executable does not exist:
		echo   "%JAVA%"
		exit /B 1
	) else if not exist "%JAVAC%" (
		echo The variable %~1 seems to point to the root directory of
		echo a JRE. It should point to the root directory of a JDK.
		echo Otherwise, some tools might not work.
	)
	goto :eof

:parse
	IF "%~1"=="" GOTO endparse
	set ARG=%~1
	IF "%~1"=="-classpath" SET HCLASSPATH=%HCLASSPATH%;%~2
	SHIFT
	GOTO parse

:winnt
	setlocal
	set "NXJ_HOME=%~dp0.."
	set PATH=%PATH%;%NXJ_HOME%\hardware\tools\avr\utils\bin

	call :build_classpath HAIKU_KERNELS "%%NXJ_HOME%%\bootstrap\src\main\java" "*MicroKernel*.java" "' '"
	rem Looks odd, but we need to close the last open '
	set HAIKU_KERNELS=%HAIKU_KERNELS%'
        rem call set HAIKU_KERNELS=%%HAIKU_KERNELS:'="%%
	call :build_classpath NXJ_CP_NXT "%%NXJ_HOME%%\lib\nxt"  "*.jar" ";"

	if not "%LEJOS_NXT_JAVA_HOME%" == "" (
		call :set_java_and_javac LEJOS_NXT_JAVA_HOME "%%LEJOS_NXT_JAVA_HOME%%"
	) else if not "%JAVA_HOME%" == "" (
		call :set_java_and_javac JAVA_HOME "%%JAVA_HOME%%"
	) else (
		call :find_java_and_javac
	)

	set error=0
	set "NXJ_COMMAND=%~1"

	if "%NXJ_COMMAND%"=="haikuc" goto :haikuc
	if "%NXJ_COMMAND%"=="haikulink" goto :haikulink
	if "%NXJ_COMMAND%"=="haikuupload" goto :haikuupload

:haiku
	set "NXJ_COMMAND=%~n0"
	call :build_classpath NXJ_CP_PC  "%%NXJ_HOME%%\lib\pc"   "*.jar" ";"

    "%JAVA%" "-Dhaikuvm.home=%NXJ_HOME%" "-DCOMMAND_NAME=%NXJ_COMMAND%" -classpath "%NXJ_CP_PC%" haikuvm.pc.tools.HaikuVM %*
    rem echo Script haiku called for pass "%NXJ_COMMAND%". Error = %errorlevel%
	set error=%errorlevel%
    goto :eof

:haikuc
	shift
    echo #
    echo #
    echo #
    echo #############################################################
    echo # generating class files
    echo #############################################################

    call :find_make

	SET HCLASSPATH=
	goto :parse
    :endparse

    call SET JAVAC=%%JAVAC:\=/%%

    "%MAKE%" -f %NXJ_HOME%/bin/makeclasses.mk all

	set error=%errorlevel%
    if errorlevel 1 goto :error
    echo Done with generating class file(s)
    goto :eof

:haikulink
	shift
    echo #
    echo #
    echo #
    echo #############################################################
    echo # generating c files
    echo #############################################################

    echo "%JAVA%" "-Dhaikuvm.home=%NXJ_HOME%" "-DCOMMAND_NAME=%NXJ_COMMAND%" -classpath "%NXJ_CP_PC%" haikuvm.pc.tools.HaikuVM --bootclasspath "%CLASSESDIR%;%NXJ_CP_NXT%" %*
    "%JAVA%" "-Dhaikuvm.home=%NXJ_HOME%" "-DCOMMAND_NAME=%NXJ_COMMAND%" -classpath "%NXJ_CP_PC%" haikuvm.pc.tools.HaikuVM --bootclasspath "%CLASSESDIR%;%NXJ_CP_NXT%" %*
	set error=%errorlevel%
    if errorlevel 1 goto :error

    echo #############################################################
    echo # Done with generating c files
    echo #############################################################
	call :find_make
    cd target\cross
    "%MAKE%" clean
    "%MAKE%" all
	set error=%errorlevel%
    if errorlevel 1 goto :error2
    goto :eof

:haikuupload
	shift
    echo #
    echo #
    echo #
    echo #############################################################
    echo # uploading file
    echo #############################################################

    if "x%CD:rduino=%"=="x%CD%" goto :notInArduinodirectory
		cd ..
		arduino_debug --upload libraries/HaikuVM/examples/HaikuVM/HaikuVM.ino
    goto :eof

:notInArduinodirectory
	call :find_make
    cd target\cross

    set args=
:ParamLoop
		IF "%~1"=="" GOTO Continue
		set HAIKU_OUTPUT="%~1"
		IF "%~1"=="--Config:Port" set args=%args% HAIKU_PORT="%~2"
		SHIFT
		GOTO ParamLoop
:Continue
    echo "%MAKE%" upload %args% HAIKU_OUTPUT=%HAIKU_OUTPUT%
    "%MAKE%" upload %args% HAIKU_OUTPUT=%HAIKU_OUTPUT%
	set error=%errorlevel%
    if errorlevel 1 goto :error

    goto :eof

:error2
    echo #############################################################
    echo # error %error% while cross compiling
    echo #############################################################
    goto :error_exit

:error_exit
:error
    echo Error %error% during %NXJ_COMMAND%
    exit /B %error%
:eof
