cd \
mkdir myProject
cd \myProject
C:\haikuVM\bin\haiku -v --Config duemilanove.UsingCLIB C:\haikuVM\examples\src\main\java\de\javamagazin\BlinkArduino.java
goto exit

rem done ###########################################

cd C:\haikuVM\myCProject
C:\haikuVM\bin\haiku -v --Config arduino -o Blink.hex C:\haikuVM\examples\src\main\java\arduino\tutorial\Blink.java
goto exit




cd haikuVM\myCProject
call C:\haikuVM\bin\haiku -v --Config arduino --Config:Port \\\.\COM17 -o BlinkWithThread.hex C:\haikuVM\examples\src\main\java\arduino\tutorial\BlinkWithThread.java
goto exit


echo on

cd C:\haikuVM\myCProject

call C:\haikuVM\bin\haikuc                                             C:\haikuVM\examples\src\main\java\avr\tutorial\BlinkSimple.java
echo on
call C:\haikuVM\bin\haikulink -v --Config simple010 -o BlinkSimple.hex C:\haikuVM\examples\src\main\java\avr\tutorial\BlinkSimple
echo on
call C:\haikuVM\bin\haikuupload                        BlinkSimple.hex
goto exit


cd C:\haikuVM\myCProject
call C:\haikuVM\bin\haikuc                                               C:\haikuVM\examples\src\main\java\arduino\tutorial\BlinkWithThread.java
call C:\haikuVM\bin\haikulink -v --Config arduino -o BlinkWithThread.hex C:\haikuVM\examples\src\main\java\arduino\tutorial\BlinkWithThread
call C:\haikuVM\bin\haikuupload                      BlinkWithThread.hex

goto exit


cd C:\haikuVM\myCProject
C:\haikuVM\bin\haiku -v --Config asuro -o Blink.hex           C:\haikuVM\examples\src\main\java\arduino\tutorial\Blink.java
goto exit

cd C:\haikuVM\myCProject
C:\haikuVM\bin\haiku -v --Config asuro -o BlinkWithThread.hex C:\haikuVM\examples\src\main\java\arduino\tutorial\BlinkWithThread.java
goto exit


cd C:\haikuVM\myCProject
call C:\haikuVM\bin\haikuc                                   C:\haikuVM\examples\src\main\java\arduino\tutorial\Blink.java
call C:\haikuVM\bin\haikulink -v --Config asuro -o Blink.hex C:\haikuVM\examples\src\main\java\arduino\tutorial\Blink
call C:\haikuVM\bin\haikuupload                    Blink.hex
goto exit

cd C:\haikuVM\myCProject
call C:\haikuVM\bin\haikuc                                             C:\haikuVM\examples\src\main\java\arduino\tutorial\BlinkWithThread.java
call C:\haikuVM\bin\haikulink -v --Config asuro -o BlinkWithThread.hex C:\haikuVM\examples\src\main\java\arduino\tutorial\BlinkWithThread
call C:\haikuVM\bin\haikuupload                    BlinkWithThread.hex
goto exit


rem javamagazine ##################################

cd \
mkdir myProject
cd \myProject
C:\haikuVM\bin\haiku -v --Config duemilanove C:\haikuVM\examples\src\main\java\de\javamagazin\Blink.java
goto exit

cd \
mkdir myProject
cd \myProject
C:\haikuVM\bin\haiku -v --Config duemilanove C:\haikuVM\examples\src\main\java\de\javamagazin\BlinkWithThread.java
goto exit

cd \
mkdir myProject
cd \myProject
C:\haikuVM\bin\haiku -v --Config duemilanove.UsingCLIB C:\haikuVM\examples\src\main\java\de\javamagazin\BlinkArduino.java
goto exit

cd \
mkdir myProject
cd \myProject
C:\haikuVM\bin\haiku -v --Config duemilanove.ProcessingCLIB C:\haikuVM\examples\src\main\java\de\javamagazin\BlinkProcessing.java
goto exit

rem leonardo ##################################

cd \
mkdir myProject
cd \myProject
C:\haikuVM\bin\haiku -v --Config duemilanove.UsingCLIB C:\haikuVM\examples\src\main\java\arduino\tutorial\HelloWorldJava.java
goto exit

cd \
mkdir myProject
cd \myProject
C:\haikuVM\bin\haiku -v --Config leonardo.UsingCLIB    C:\haikuVM\examples\src\main\java\arduino\tutorial\HelloWorldJava.java
goto exit

:exit