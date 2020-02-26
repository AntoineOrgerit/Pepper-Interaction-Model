@echo on>sources.txt
@echo off
setlocal enabledelayedexpansion
set "parentfolder=%CD%"
for /r . %%g in (*.java) do (
  set "var=%%g"
  set var=!var:%parentfolder%=!
  echo .!var! >> sources.txt
)
javac -classpath ".\lib\jade.jar" -d .\bin  @sources.txt
java -cp ".\lib\jade.jar;bin" jade.Boot -name LauncherAgent -agents Launcher:main.com.univlr.pepper.speechhands.PepperLauncherAgent
pause
