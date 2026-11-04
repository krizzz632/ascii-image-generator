#!/bin/sh
# Usage: ./run.sh [path/to/image.png]
mkdir -p out && javac -d out $(find . -name "*.java") && java -cp out ascii_art.Shell "$@"
