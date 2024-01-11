#!/bin/bash
## This is a simple bash script to compile all files and run the TerminalInterface file.

## How to run the program
# Path: launch.sh

echo Which platform are you on ?
echo "(0) - Windows"
echo "(1) - Linux"
read platform

if [ $platform != "0" -a $platform != "1" ]
then
    echo "Error : Invalid argument supplied. Should be 0 (Windows) or 1 (Linux)"
    exit 1
fi 


echo "Starting..."
find -name "*.java" > files.txt
echo "Compiling..."
javac -d src/classFiles @files.txt

if [ $platform -eq 0 ] 
then
    CLASSPATH=".;src/classFiles/;src/classFiles/*;src/classFiles/*/*;src/classFiles/*/*/*;src/classFiles/*/*/*/*"
elif [ $platform -eq 1 ] 
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
    echo "Running..."
    java -cp $CLASSPATH view.TerminalInterface
elif [ $gameMode == "1" ]
then
    echo Game mode selected : $gameMode
    echo "Running..."
    java -cp $CLASSPATH view.App
else
    echo Error : please enter 0 or 1
    exit 1
fi


DELCLASSPATH="src/classFiles/*"
rm -r $DELCLASSPATH
rm files.txt
#For Windows
#read -p "Press any key to continue" x



