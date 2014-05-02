#! /bin/bash

echo "cleaning..."
rm -f *.class
rm -f classes/*.class
rm -f sharedClasses/*.class
echo "compiling..."
javac main.java
echo "done."