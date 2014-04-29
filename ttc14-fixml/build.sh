#!/bin/sh

ARGS=""
if [ $# -eq 0 ]; then
  ARGS="install"
else
  ARGS=$@
fi
mvn -f ttc14-fixml-parent/pom.xml $ARGS
