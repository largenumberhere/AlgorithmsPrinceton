#!/bin/bash
# export CLASSPATH="algs4.jar"
echo $PWD

# RandomWord
javac -cp .:algs4.jar Percolation.java
javac -cp .:algs4.jar PercolationStats.java
javac -cp .:algs4.jar Main.java

java -cp .:algs4.jar Main
# javac -cp .:algs4.jar RandomWord.java
# java -cp .:algs4.jar RandomWord < animals8.txt

# Hello world
# java HelloWorld.java

# # Hello Goodbye
# java HelloGoodbye.java Kevin Bob

