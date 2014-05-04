#! /bin/bash


if [[ ! -d classes/ ]]; then
	echo "copying specific classes.."
	mkdir classes
	cp ../FONTS/Albert/*.java classes/
	cp ../FONTS/Alessio/*.java classes/
	cp ../FONTS/Marc/*.java classes/
	cp ../FONTS/Samuel/*.java classes/
	cp ../FONTS/Samuel/Main.java ./
	mkdir classes/enumerations/
	mv classes/TopologyType.java classes/enumerations/
	mv classes/UsageMode.java classes/enumerations/
fi
if [[ ! -d sharedClasses/ ]]; then
	echo "copying shared classes.."
	mkdir sharedClasses
	cp ../FONTS/COMU/*.java sharedClasses/
fi

echo "cleaning..."
find . -type f -name "*.class" -delete
echo "compiling..."
javac Main.java
echo "done."