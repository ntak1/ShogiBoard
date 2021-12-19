#!bin/bash

# This script converts .svg files into png using image magik
# 1. Copy this to the folder containing the .svg files
# 2. Run this script

ls | grep "svg" > files.txt
while IFS= read -r line; do
    echo "Text read from file: $line"
	mkdir -p png
	new_name=`echo $line | cut -f1 -d"."`
	echo $new_name
	convert $line png/$new_name
done < files.txt
