set JAVAPATH="C:\Program Files\Java\jdk1.6.0_30\bin"

set LIBS_PATH=dist\lib\aduna-commons-io-2.4.jar;dist\lib\aduna-commons-lang-2.3.jar;dist\lib\aduna-commons-net-2.2.jar;dist\lib\aduna-commons-text-2.2.jar;dist\lib\aduna-commons-xml-2.2.jar;dist\lib\commons-logging.jar;dist\lib\flexigraph.jar;dist\lib\google-collect-1.0-rc1.jar;dist\lib\JAXP_142.jar;dist\lib\log4j-1.2.14.jar;dist\lib\postgresql-8.1-407.jdbc3.jar;dist\lib\postgresql-8.3-604.jdbc4.jar;dist\lib\sesame-model-2.2.4.jar;dist\lib\sesame-rio-api-2.2.4.jar;dist\lib\sesame-rio-n3-2.2.4.jar;dist\lib\sesame-rio-ntriples-2.2.4.jar;dist\lib\sesame-rio-rdfxml-2.2.4.jar;dist\lib\sesame-rio-trig-2.2.4.jar;dist\lib\sesame-rio-trix-2.2.4.jar;dist\lib\sesame-rio-turtle-2.2.4.jar;dist\lib\slf4j-api-1.5.0.jar;dist\lib\slf4j-jdk14-1.5.0.jar;dist\lib\spring.jar;dist\lib\swkmmodel2.jar;dist\lib\URIpolicies.jar

set CLASSPATH=.;%LIBS_PATH%

set PATH=%JAVAPATH%;%PATH%

copy ..\URIpolicies\URIpolicies.jar .\dist\lib

del Transformation\*.class 

javac -d . src\Transformation\*.java 

pause

jar cvf .\SourceXMLToCidoc.jar Transformation\*.class

pause
