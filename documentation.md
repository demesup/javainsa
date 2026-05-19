# JavaINSA Documentation

## Tech Stack

- Java
- Swing/AWT-style desktop game rendering
- Sprite-based assets
- WAV audio resources

## Why This Stack

- Java is suitable for an educational 2D game project.
- A desktop rendering loop keeps the project self-contained.
- Static assets make the game easy to run without external services.

## Methodology

- Separate gameplay, input, and utility code into dedicated packages.
- Keep states explicit for menu, options, playing, and quit flows.
- Use handlers for enemies, items, audio, and levels to reduce coupling.

