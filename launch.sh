#!/bin/bash
## This is a simple bash script to compile all files and run the TerminalInterface file.

## How to run the program
# Path: launch.sh
echo "Starting..."
find -name "*.java" > files.txt
echo "Compiling..."
javac -d src/classFiles @files.txt
echo "Running..."

if [ $# -eq 0 ] 
then 
    echo "Error : No arguments supplied. Should be 0 (Windows) or 1 (Linux)"
    exit 1
fi

if [ $1 == "help" ]
then
    echo "Use command as :
        ./launch.sh 0
    if you're on Windows
    or
        ./launch.sh 1
    if you're on Linux"
else

    if [ $1 -eq 0 ] 
    then
        CLASSPATH=".;src/classFiles/;src/classFiles/*;src/classFiles/*/*;src/classFiles/*/*/*;src/classFiles/*/*/*/*"
    elif [ $1 -eq 1 ] 
    then 
        CLASSPATH=".:src/classFiles/:src/classFiles/*:src/classFiles/*/*:src/classFiles/*/*/*:src/classFiles/*/*/*/*"
    else 
        echo "Please enter 0 (Windows start) or 1 (Linux start)"
        exit 1
    fi

    echo Select a game mode :
    echo "(0) - Terminal Mode"
    echo "(1) - Visual Mode"
    read gameMode
    if [ $gameMode == "0" ]
    then
        echo Game mode selected : $gameMode 
        java -cp $CLASSPATH view.TerminalInterface
    elif [ $gameMode == "1" ]
    then
        echo Game mode selected : $gameMode
        java -cp $CLASSPATH view.App
    else
        echo Error : please enter 0 or 1
        exit 1
    fi    

  fi
DELCLASSPATH="src/classFiles/*"
rm -r $DELCLASSPATH
rm files.txt
