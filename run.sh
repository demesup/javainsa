#!/bin/bash

SRC_DIR=src
RESOURCES_DIR=src/resources
COMPILED_DIR="compiled"
SOURCES_FILE="sources.txt"

> "$SOURCES_FILE"

find "$SRC_DIR" -type f -name "*.java" > "$SOURCES_FILE"
javac -d "$COMPILED_DIR" @"$SOURCES_FILE"
rsync -av --exclude="*.java" "$RESOURCES_DIR" "$COMPILED_DIR"

rm "$SOURCES_FILE"

echo "Compilation complete. All compiled files and resources are in the '$COMPILED_DIR' directory."

cd compiled
java Main
