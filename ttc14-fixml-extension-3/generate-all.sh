#!/bin/bash

JAXB="jaxb-ri-2.2.7.zip"
FIXML="fixml-4.4.zip"

function die () {
  echo "${*}" 1>&2
  exit 1
}

if [ ! -f $JAXB ]; then
  echo "Downloading JAXB 2.2.7"
  curl -o $JAXB https://jaxb.java.net/2.2.7/$JAXB || die "Unable to download JAXB"
fi  
if [ ! -d ${JAXB%.*} ]; then
  echo "Extracting JAXB 2.2.7"
  unzip -o $JAXB || die "Unable to extract $JAXB"
fi

if [ ! -f $FIXML ]; then 
	echo "Downloading FIXML 4.4 Schema"
	curl -o $FIXML http://www.fixtradingcommunity.org/mod/file/download.php?file_guid=30011
fi
if [ ! -d ${FIXML%.*} ]; then
	echo "Extracting FIXML 4.4 Schema"
	unzip -o $FIXML -d fixml-4.4 || die "Unable to extract $FIXML"
fi

mvn exec:java
