#!/bin/bash

BASE="output/results"

if [[ -d $BASE/java ]]; then
  pushd . > /dev/null
  echo "Compiling Java"
  cd $BASE/java
  javac *.java
  popd > /dev/null
fi

if [[ -d $BASE/cs ]]; then
  pushd . > /dev/null
  echo "Compiling C#"
  cd $BASE/cs
  gmcs -target:library *.cs
  popd > /dev/null
fi

if [[ -d $BASE/cpp ]]; then
  pushd . > /dev/null
  echo "Compiling C++"
  cd $BASE/cpp
 	g++ -std=c++0x -c *.cpp
  g++ -shared -o libFIXML.so *.o
  popd > /dev/null
fi