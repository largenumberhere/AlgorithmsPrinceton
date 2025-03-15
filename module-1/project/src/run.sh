#!/bin/bash
# export CLASSPATH="algs4.jar"
echo $PWD

# RandomWord
javac -cp .:algs4.jar RandomWord.java
java -cp .:algs4.jar RandomWord < animals8.txt

# Hello world
java HelloWorld.java

# Hello Goodbye
java HelloGoodbye.java Kevin Bob

