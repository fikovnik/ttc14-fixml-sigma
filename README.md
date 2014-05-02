## TTC'14 FIXML case study - SIGMA solution

[SIGMA](http://fikovnik.net/Sigma/) is a family of [Scala](http://scala-lang.org) internal Domain-Specific Languages (DSLs) for model manipulation that provides expressive and efficient API for model consistency checking, model-to-model and model-to-text transformations. In this paper we describe a SIGMA solution for the [Transformation Tool Contest 2014]((http://www.transformation-tool-contest.eu/) [FIXML case ctudy](https://github.com/transformationtoolcontest/ttc2014-fixml), a transformation of FIXML XML format into class definitions in Java, C# and C++. The full case study including all three extensions have been realized and are publicly available on [Github](https://github.com/fikovnik/ttc14-fixml-sigma) and in the [SHARE environment](http://is.ieis.tue.nl/staff/pvgorp/share/) in the `Ubuntu12LTS_TTC14_64bit_SIGMA.vdi` image.


### Requirements

- java 7
- maven 3.2.1
- mono-gmcs
- gcc
- g++
- curl

### Building

    ./build-all.sh
    ./test-all.sh

The `test-all.sh` will trigger the generation of all the provided test cases and a consecutive compilation for all generated languages.

### License

Distributed under the [Eclipse Public License 1.0](http://www.eclipse.org/legal/epl-v10.html).
