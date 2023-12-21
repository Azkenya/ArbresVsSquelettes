#!/bin/bash
## This is a simple bash script to compile all files and run the TerminalInterface file.

## How to run the program
# Path: launch.sh
echo "Starting..."
find -name "*.java" > files.txt
echo "Compiling..."
javac -d src/classFiles @files.txt
echo "Running..."
CLASSPATH=".;src/classFiles/;src/classFiles/*;src/classFiles/*/*;src/classFiles/*/*/*;src/classFiles/*/*/*/*"
java -cp $CLASSPATH view.TerminalInterface
DELCLASSPATH="src/classFiles/*"
rm -r $DELCLASSPATH
rm files.txt