set JAVAPATH="C:\Program Files\Java\jdk1.6.0_30\bin"

set LIBS_PATH=

set CLASSPATH=.;%LIBS_PATH%

set PATH=%JAVAPATH%;%PATH%

del URIidevelopment\*.class 

javac -d . src\URIidevelopment\URIPolicies.java 

pause

jar cvf .\URIpolicies.jar URIidevelopment\*.class

pause
