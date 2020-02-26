echo on>sources.txt
echo off

find ./src/com/ -name "*.java" > sources.txt

javac -encoding ISO-8859-1 -classpath "./lib/jade.jar" -d ./bin @sources.txt
java -cp "./lib/jade.jar":"./bin" jade.Boot -name LauncherAgent -agents Launcher:main.com.univlr.pepper.speechhands.PepperLauncherAgent
