set JAVAPATH="C:\Program Files\Java\jdk1.6.0_30\bin"

set LIBS_PATH=

set CLASSPATH=.;%LIBS_PATH%

set PATH=%JAVAPATH%;%PATH%

javadoc -public -sourcepath src -d javadocs src\URIidevelopment\URIpolicies.java

pause

