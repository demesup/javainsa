# Java Platformer Game

## Introduction
This is a platformer game written in Java. It’s our project, and we really enjoyed creating and refining this game. We hope you have as much fun playing it as we had making it!

---

## Features
- **Smooth character movement** with options to run, jump, and attack.
- **Basic and power attack functionalities** for varied gameplay.
- **Multiple enemy types**:
    - **Mushrooms**
    - **Flying Eyes**
- **Environmental hazards**:
    - **Spikes** as traps
    - **Cannons** that shoot projectiles
- **Potions for player enhancements**:
    - **Red Potions**: Heal the player.
    - **Blue Potions**: Add power for power attacks.
        - **Note**: If your stats are already full, potions will disappear upon collection, so use them wisely.
    - **Hidden Potions**:
        - Found in **boxes**: **Blue Potions**
        - Found in **barrels**: **Red Potions**
- **Level progression**: To proceed to the next level, **all enemies on the current level must be defeated**.
- Most configuration values are managed through `Enums` or the `Constants` class for easier maintainability.

---

## Controls
- **Run**: Use `A/D` or `Left/Right Arrow` keys
- **Jump**: Use `W`, `Spacebar`, or `Up Arrow`
- **Simple Attack**: Left Mouse Button
- **Power Attack**: Right Mouse Button

---

## Running the Game
### Option 1: Using the `run.sh` Script
1. Navigate to the root directory of the project.
2. (Optional step) make script executable
```bash
chmod +x run.sh
```
3. Run the `run.sh` script to launch the game:
   ```bash
   ./run.sh
   ```
### Option 2: Running Commands Manually
#### Linux/Mac:
1. Navigate to the root directory of your project in the terminal.
2. Create a file named `sources.txt` that lists all the `.java` files:
   ```bash
   find src -type f -name "*.java" > sources.txt
   ```
3. Compile all the `.java` files listed in `sources.txt` and output them to a `compiled` directory:
   ```bash
   javac -d compiled @sources.txt
   ```
4. Copy all resource files (excluding `.java` files) to the `compiled` directory while preserving the directory structure:
   ```bash
   rsync -av --exclude="*.java" src/resources compiled
   ```
5. Delete the `sources.txt` file to clean up:
   ```bash
   rm sources.txt
   ```
6. Navigate to the `compiled` directory:
   ```bash
   cd compiled
   ```
7. Run the game:
   ```bash
   java Main
   ```

#### Windows (Command Prompt):
1. Open Command Prompt (or Windows Terminal) and navigate to your project directory.
2. Create a file named `sources.txt` with the paths of all `.java` files:
   ```cmd
   dir /s /b src\*.java > sources.txt
   ```
3. Compile all `.java` files listed in `sources.txt` into a `compiled` directory:
   ```cmd
   javac -d compiled @sources.txt
   ```
4. Copy all resource files (excluding `.java` files) to the `compiled\resources` directory:
   ```cmd
   xcopy /e /i src\resources compiled\resources
   ```
5. Delete the `sources.txt` file to clean up:
   ```cmd
   del sources.txt
   ```
6. Navigate to the `compiled` directory:
   ```cmd
   cd compiled
   ```
7. Run the game:
   ```cmd
   java Main
   ```
---

## Codebase Overview
- The code is structured to be intuitive, with method names clearly describing their purpose.
- While there are minimal comments above methods, the focus on readability ensures that understanding the functionality is straightforward.
- Most configuration values are stored in the `Enums` or `Constants` class to avoid hard-coded values. However, there are still a few hard-coded values that will be addressed in future iterations.
- Development utilities such as `drawAttackBox` and `drawHitbox` methods are present in the codebase but are not used in the final version. These were utilized during development and have been retained for potential debugging or enhancements.

---

## Asset and Inspiration Credits
- **Tutorial Inspiration**: This game was inspired by the Java platformer tutorial provided by [Kaarin Gaming](https://www.kaaringaming.com/).
- **Visual Assets**:
    - Some sprites were sourced from [Luiz Melo's Monsters Creatures Fantasy](https://luizmelo.itch.io/monsters-creatures-fantasy).
    - Additional character sprites from [RV Ros' Animated Pixel Hero](https://rvros.itch.io/animated-pixel-hero).
- **Audio**:
    - Background music for levels and menu was taken from [Pixabay](https://pixabay.com/).
- **Image Editing**:
    - Pixel art updates were made using [Piskel](https://www.piskelapp.com/).

---

## Known Issues
- A small number of values remain hard-coded and will require refactoring for full configurability.
- When running the game from the linux command line(at least subsystem for Windows), **audio clips are not loaded correctly**. This issue remains unresolved and is planned for future work.

---

## Future Improvements
- Add more comments for methods where additional context might be beneficial.
- Refactor remaining hard-coded values into enums or constants.
- Address the command-line audio loading issue.
- Implement additional gameplay features for enhanced user engagement.

---
