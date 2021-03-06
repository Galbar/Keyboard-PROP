#! /bin/bash

if [[ ! -f Main.java ]]; then
	echo "copying Main class.."
	cp -f ../FONTS/Samuel/Main.java ./
fi
if [[ ! -d classes/ ]]; then
	echo "copying specific classes.."
	mkdir classes
	cp -f ../FONTS/Albert/Alphabet.java classes/
	cp -f ../FONTS/Albert/AlphabetDriver.java classes/
	cp -f ../FONTS/Albert/PROPKeyboardException.java classes/
	cp -f ../FONTS/Albert/Position.java classes/
	cp -f ../FONTS/Albert/PositionDriver.java classes/
	cp -f ../FONTS/Albert/PositionsSet.java classes/
	cp -f ../FONTS/Albert/PositionsSetDriver.java classes/
	cp -f ../FONTS/Albert/PersistanceController.java classes/

	cp -f ../FONTS/Alessio/DomainController.java classes/
	cp -f ../FONTS/Alessio/DomainControllerDriver.java classes/
	
	cp -f ../FONTS/Marc/Character.java classes/
	cp -f ../FONTS/Marc/CharacterDriver.java classes/
	cp -f ../FONTS/Marc/SolutionView.java classes/
	cp -f ../FONTS/Marc/ScreenImage.java classes/
	cp -f ../FONTS/Marc/CharactersSetDriver.java classes/
	cp -f ../FONTS/Marc/CharactersSet.java classes/
	cp -f ../FONTS/Marc/RelationDriver.java classes/
	cp -f ../FONTS/Marc/Relation.java classes/

	cp -f ../FONTS/Samuel/Keyboard.java classes/
	cp -f ../FONTS/Samuel/KeyboardDriver.java classes/
	cp -f ../FONTS/Samuel/MainWindow.java classes/
	cp -f ../FONTS/Samuel/Explorer.java classes/
	cp -f ../FONTS/Samuel/Loader.java classes/
	cp -f ../FONTS/Samuel/NewKeyboard.java classes/
	cp -f ../FONTS/Samuel/Text.java classes/
	cp -f ../FONTS/Samuel/InterfaceController.java classes/
	cp -f ../FONTS/Samuel/AlphabetView.java classes/
	mkdir classes/enumerations/
	cp -f ../FONTS/Samuel/TopologyType.java classes/enumerations/
	cp -f ../FONTS/Samuel/UsageMode.java classes/enumerations/
fi
if [[ ! -d sharedClasses/ ]]; then
	echo "copying shared classes.."
	mkdir sharedClasses
	cp -f ../FONTS/COMU/*.java sharedClasses/
fi

echo "cleaning..."
find . -type f -name "*.class" -delete
echo "compiling..."
javac Main.java
echo "done."