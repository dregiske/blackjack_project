# Blackjack in Java programming

My implementation of Blackjack using Java. It is a command-line game that allows players to hit, stand, double, and split based on Blackjack's rules.

## Features
- Hit / Stand / Double / Split
- Betting system
- Double deck, shuffled new for each round
- Win / Loss / Push

## How to run
1. Clone or download project files.
2. Open terminal in project directory.
3. Compile program: ```javac BJ.java```
4. Run program: ```BJ java```

## Usage
- Game starts with $200 to bet with
- Place bets before round start
- Pick your move (hit, stand, double, split)
- Round ends after dealer reveals their hand
- Game ends when player loses all their money

## File structure
- `BJ.java`: Main class with game loop
- `BJAbstract.java`: Abstract class with core logic
- `BJInterface.java`: Interface declaring methods
- `CustomerTester.java`: Tester class with test cases

## Known issues
- Splitting hands is limited
- Cannot have variation betting between hands
- Tester file needs test cases

## License
This project is open-source and free to use.

## Author
Created by Andre Giske, for personal project, March 2025.




