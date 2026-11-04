@echo off
dir /s /b *.java > sources.txt
if not exist out mkdir out
javac -d out @sources.txt && java -cp out ascii_art.Shell %*
