# Graphical Numerical/Regular Tic-Tac Toe Game

A GUI-based Numerical and Regular Tic-Tac-Toe game that I programmed for CIS*2430.

## Description

I programmed this GUI application keeping CIS*2430 OOP principles in mind. In this assignment, I applied my knowledge of encapuslation, cohesion/coupling and polymorphism. Most notably, my skills with regards to interfaces and inheritence allowed me to implement Professor McCuaig's saveable interface as well as extending other classes. With regard to my program, it is a GUI-based application that allows the user to play two different games: Numerical Tic-Tac-Toe and Regular Tic-Tac-Toe. It has an easy-to-use user experience, as well as a robust UI that allows for invalid input. Additionally, it allows to save player info and current games in progress, with the ability to load those files later.

## Getting Started

### Dependencies

* JDK 11
* Gradle 7.5
* Junit 4

### Executing program


1. Make sure to change into the A2 folder using the cd command, or another method.
2. Then run these commands:
* Build the file
```
gradle build

Should see:
BUILD SUCCESSFUL in 1s
3 actionable tasks: 3 executed

```
* Get the run command
```
gradle run

> Task :run
To run the program:
java -jar build/libs/A3.jar
To run the program with TextUI:
java -cp build/classes/java/main boardgame.ui.TextUI
```
* Input the first command to run the GUI based program, or the second to run the TextUI version of the Tic-Tac-Toe game.

## Player File Format

Here is an example of the Player file format: 
Player Two,5,5,11

The first part is the player name. The second portion is the wins, losses, and games played (in order) listed as integers. Note that the games played can be more than the total wins and losses combined because there is a possibility that no one wins a game.

The player information is stored in a CSV file.

## Limitations

While I believe that my player saving/loading methods are robust, I believe that more additions could be added to the Player class (if given more time). Currently, the capabilities of the player class are rather limited. I would also love to make it so that my program doesn't store game data only in the "assets" folder, and could store it anywhere on my local computer. Lastly, I would've enjoyed adding more unique graphical elements to the program to make the GUI more visually appealing.

## Author Information

Isaiah Sinclair
isincl01@uoguelph.ca

## Development History
* Updated README.md
    * See [commit change](11261214cfb817a462d484054eb2a786773c2784)
* Edited turn count when playing numerical tic-tac-toe
    * See [commit change](b893c3e49ba61af84dd9c6f2737996e96642bc65)
* Removed csv files
    * See [commit change](745467374e08e55ea466f73f2ede7689145fe0df)
* Updated README.md
    * See [commit change](0b8c502976de57ab9a856bb5311cdbe61f717295)
* Updated some UI aspects
    * See [commit change](262ff9096dd34fb4ba91f6ed2b65d2a03be6b3e1)
* Fixed combobox issue
    * See [commit change](bbe609a902d342d05e1a28c43175c3d60eae57fe)
* Fixed issue with equality signs not comparing properly
    * See [commit change](e38ed7eee4938d3b1120ea1662eb424a5a659cc3)
* Added more exception handling
    * See [commit change](7f6e15e7705e5f207c1ba92f8acd7dfa4a19115d)
* Update README.md with commit info.
    * See [commit change](f3c64cd70c013524e2a4600adc88f5eae09cfd65)
* Update README.md
    * See [commit change](46fffc4c64cf9219a50ab847c575f32dcf63fced)
* Added README
    * See [commit change](82fa085ec6eb78717197781f5fa054d261d594f5)
* Moved files around, improved user experience
    * See [commit change](3955b89c1e459824415034c2acbec04c875e7673)
* Added saving/loading for files
    * See [commit change](d7114a53fca3a378404bbace451df71b3da7967b)
* Changed sizing of gameContainer, started implemenation of game loading.
    * See [commit change](cbd48213c7cc3a5e2e84b0f01a8f344e5af5f23f)
* Updated menu options
    * See [commit change](ba7635701d4f8d60771a33ab699b37b30ddb6280)
* Added in basic saving components for players
    * See [commit change](aff1cc407e299f134a098be5fbd2c14f8da27755)
* Added Javadocs
    * See [commit change](1f6036501966f1defb11d53edf2bffb425484151)
* Added SaveInfo.java
    * See [commit change](ab0b7c459643d4b2d61ec4716c389d60a15a78c6)
* Removed start screen
    * See [commit change](a192c1e56ffc1d4c5d74f07a7b75eefa9444e696)
* Added first revision of player class
    * See [commit change](5f863acb48123a04ffa03d0854c2427b35244f13)
* Finished logic of Numerical Tic Tac Toe
    * See [commit change](62801c20a432a58365d8bab1662f55a25729d69e)
* Almost have full functionality of Numerical Tic-Tac-Toe game
    * See [commit change](129c0bec8531c2489c942f69c09d859763b00d67)
* Started to add comboboxes
    * See [commit change](dfc874350b1e891c7efef22aab39963b75ae6d7c)
* In the process of refactoring TicTacToe code to make the Numerical Tic Tac Toe Game
    * See [commit change](237c261480b1eebc5606bd5b1655334f4e9445f0)
* Added new game/no new game functionality.
    * See [commit change](e78a909954b072bc0e6137cf2ac154be9dfc8d3f1)
* Added GameUI -- was untracked before.
    * See [commit change](ecff82b771570de33d2aa99e561f7713be85f7f0)
* Updated user response.
    * See [commit change](2ce43efbb93e4f3a8e39b98931821b5b78c1ba20)
* Have fully-working Tic-Tac-Toe game
    * See [commit change](e89981a4800fe4c2617f0cffb9fb3a4997b5d935)
* Updated TicTacToeView
    * See [commit change](39cd535f21051df621d9de1d38ee990b2ec6054c)
* Added basics of GUI
    * See [commit change](8ddfe207cd5f2c89e3ff9b7d08689c9d4b9efab7)
* Finished the bulk of TextUI
    * See [commit change](b55d2b9cf0f96ca31e5546c3c4387852c38e53cc)
* Finished majority of TextUI
    * See [commit change](8660974a93021245d78f236ee32ed91049662225)
* First iteration of game
    * See [commit change](d221e3a16b93a5e7552299236e34aa7165a61d5a)
* 0.1
    * Initial Release

## Acknowledgments

* Copied and refactored code with regard to Professor McCuaig's example game (Kakuro).
    * specifically, I copied the button grid function that was used in the Kakuro game.
* I also copied and refactored the SaveToFile class from one of Professor McCuaig's polymorphism workshops.
* I also copied and refactored parts of my A1 to implement the Tic-Tac-Toe game.
* [awesome-readme](https://github.com/matiassingers/awesome-readme)
* [simple-readme] (https://gist.githubusercontent.com/DomPizzie/7a5ff55ffa9081f2de27c315f5018afc/raw/d59043abbb123089ad6602aba571121b71d91d7f/README-Template.md)
