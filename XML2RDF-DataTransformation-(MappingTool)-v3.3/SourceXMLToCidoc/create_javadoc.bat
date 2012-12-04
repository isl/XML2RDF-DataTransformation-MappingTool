set JAVAPATH="C:\Program Files\Java\jdk1.6.0_30\bin"

set LIBS_PATH=lib\aduna-commons-io-2.4.jar;lib\aduna-commons-lang-2.3.jar;lib\aduna-commons-net-2.2.jar;lib\aduna-commons-text-2.2.jar;lib\aduna-commons-xml-2.2.jar;lib\commons-logging.jar;lib\flexigraph.jar;lib\google-collect-snapshot-20090211.jar;lib\JAXP_142.jar;lib\log4j-1.2.14.jar;lib\postgresql-8.1-407.jdbc3.jar;lib\postgresql-8.3-604.jdbc4.jar;lib\sesame-model-2.2.4.jar;lib\sesame-rio-api-2.2.4.jar;lib\sesame-rio-n3-2.2.4.jar;lib\sesame-rio-ntriples-2.2.4.jar;lib\sesame-rio-rdfxml-2.2.4.jar;lib\sesame-rio-trig-2.2.4.jar;lib\sesame-rio-trix-2.2.4.jar;lib\sesame-rio-turtle-2.2.4.jar;lib\slf4j-api-1.5.0.jar;lib\slf4j-jdk14-1.5.0.jar;lib\spring.jar;lib\swkmmodel2.jar;..\URIpolicies\dist\URIpolicies.jar

set CLASSPATH=.;%LIBS_PATH%

set PATH=%JAVAPATH%;%PATH%

javadoc -sourcepath src -d javadocs src\Transformation\*.java

pause

