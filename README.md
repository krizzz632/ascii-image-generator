# ascii-image-generator
# ASCII Art Generator

A command-line program written in **Java** that converts an image into ASCII art.
You choose the image, the characters, the resolution and the output format
(console or HTML), then generate the result with one command.

## Features
- Converts images (`.jpg`, `.jpeg`, `.png`, `.gif`) to ASCII art
- Interactive command-line shell
- Adjustable resolution and character set
- Output to the console or to an `out.html` file

## Project structure
```
Ascii-art/
├── ascii_art/              Shell.java (main), AsciiArtAlgorithm.java
├── ascii_output/           Console and HTML output
├── exception/              Custom exception
├── image/                  Image loading and brightness calculation
├── image_char_matching/    Matches brightness to characters
├── run.sh / run.bat        Compile and run scripts
└── README.md
```

## Requirements
- JDK 11 or newer (check with `javac -version`)

## How to run
**Windows:** `run.bat cat.png`
**Mac / Linux:** `./run.sh cat.png`

Or compile manually:
```
javac -d out ascii_art/*.java ascii_output/*.java exception/*.java image/*.java image_char_matching/*.java
java -cp out ascii_art.Shell cat.png
```
The main class is `ascii_art.Shell`. The image path argument is optional.

## Commands
| Command | Description |
|---|---|
| `image <path>` | Load an image, e.g. `image cat.png` |
| `chars` | Show the current character set |
| `add <x \| a-z \| all \| space>` | Add characters |
| `remove <x \| a-z \| all \| space>` | Remove characters |
| `res up` / `res down` | Double / halve the resolution |
| `output console` / `output html` | Choose the output format |
| `asciiArt` | Generate the ASCII art |
| `exit` | Quit |

## Example session
```
>>> image cat.png
>>> add all
>>> output html
>>> asciiArt
```
Then open `out.html` in your browser.

## Tips
- Use `add all` for more detail; the default set is only the digits 0-9.
- Use HTML output for high resolutions, as the terminal wraps long lines.
