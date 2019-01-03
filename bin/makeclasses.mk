all:
	'$(JAVAC)' -target 1.8 -source 1.8 -bootclasspath '$(NXJ_CP_NXT)' -extdirs '' -classpath '$(HCLASSPATH);$(NXJ_HOME)\haikuRT\src\main\java;$(NXJ_HOME)\bootstrap\src\main\java;$(NXJ_HOME)\examples\src\main\java;$(NXJ_HOME)\haikuBench\src\main\java;$(NXJ_HOME)\gallerie\src\main\java;$(NXJ_HOME)\incubator\src\main\java;$(NXJ_HOME)\rcx_samples\src\main\java;$(NXJ_HOME)\rcx_java\src\main\java' -d '$(CLASSESDIR)' $(HAIKU_KERNELS) '$(ARG)'
	
unix:
	"$(JAVAC)" -bootclasspath "$(NXJ_CP_NXT)" -extdirs "" -classpath '$(HCLASSPATH):$(NXJ_HOME)/haikuRT/src/main/java:$(NXJ_HOME)/bootstrap/src/main/java:$(NXJ_HOME)/examples/src/main/java:$(NXJ_HOME)/haikuBench/src/main/java:$(NXJ_HOME)/gallerie/src/main/java:$(NXJ_HOME)/incubator/src/main/java' -d '$(CLASSESDIR)' $(HAIKU_KERNELS) '$(ARG)'
	
	