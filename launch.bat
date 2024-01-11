@echo off
setlocal enabledelayedexpansion

:: This is a simple Batch script to compile all files and run the TerminalInterface file.

:: How to run the program
:: Path: launch.bat

echo Which platform are you on ?
echo (0) - Windows
echo (1) - Linux
set /p platform=Enter platform (0 for Windows, 1 for Linux): 

if not "%platform%"=="0" if not "%platform%"=="1" (
    echo "Error: Invalid argument supplied. Should be 0 (Windows) or 1 (Linux)"
    pause
    exit /b 1
)

echo Starting...
dir /s /b *.java > files.txt
echo Compiling...
javac --release 11 -d src/classFiles @files.txt

if "%platform%"=="0" (
    set "CLASSPATH=.;src/classFiles/;src/classFiles/*;src/classFiles/*/*;src/classFiles/*/*/*;src/classFiles/*/*/*/*"
) else if "%platform%"=="1" (
    set "CLASSPATH=.:src/classFiles/:src/classFiles/*:src/classFiles/*/*:src/classFiles/*/*/*:src/classFiles/*/*/*/*"
) else (
    echo "Please enter 0 (Windows start) or 1 (Linux start)"
    pause
    exit /b 1
)

echo Select a game mode :
echo (0) - Terminal Mode
echo (1) - Visual Mode
set /p gameMode=Enter game mode (0 for Terminal Mode, 1 for Visual Mode): 

if "%gameMode%"=="0" (
    echo Game mode selected: Terminal
    echo Running...
    java -cp !CLASSPATH! view.TerminalInterface
) else if "%gameMode%"=="1" (
    echo Game mode selected: Visual
    echo Running...
    java -cp !CLASSPATH! view.App
) else (
    echo Error: Please enter 0 or 1
    pause
    exit /b 1
)

set DELCLASSPATH="src\classFiles\*"
del /q /s !DELCLASSPATH! >nul
del files.txt >nul
pause
exit /b 0