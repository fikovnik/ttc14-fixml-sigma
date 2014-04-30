#!/bin/bash

pushd . > /dev/null

function die () {
  echo "${*}" 1>&2
  popd > /dev/null
  exit 1
}

for i in "ttc14-fixml-base" "ttc14-fixml-extension-1" "ttc14-fixml-extension-2" "ttc14-fixml-extension-3"; do
	echo "Testing $i"
	cd $i
	pwd
	./generate-all.sh || die "generate.all in $i failed"
	./compile-all.sh || die "generate.all in $i failed"
	cd ..
	echo
done