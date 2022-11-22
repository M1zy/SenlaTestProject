@ECHO OFF
set CLASSPATH=.
set CLASSPATH=%CLASSPATH%\out\artifacts\SenlaTestProject_jar\SenlaTestProject.jar

%JAVA_HOME%\bin\java -Xms128m -Xmx384m -Xnoclassgc ro.my.class.MyClass