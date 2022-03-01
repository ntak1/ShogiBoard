#!bin/bash

# This script converts .svg files into png using image magik
# 1. Copy this to the folder containing the .svg files
# 2. Run this script
BASE_DIR="../shogi-pieces/kanji_brown"
ls $BASE_DIR | grep "svg" > files.txt
while IFS= read -r line; do
    echo "Text read from file: $line"
	mkdir -p $BASE_DIR/png
	new_name=`echo $line | cut -f1 -d"."`.png
	echo $new_name
	convert -background none $BASE_DIR/$line png/$new_name
done < files.txt
