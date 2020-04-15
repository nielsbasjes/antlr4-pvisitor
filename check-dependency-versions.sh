#!/bin/bash

( cd devtools && mvn install )
echo "==============================="
echo "===- CHECKING DEPENDENCIES -==="
echo "vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv"
mvn versions:display-dependency-updates versions:display-plugin-updates | grep -E '( -> | Building )'
echo "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"
echo "===-         DONE!         -==="
echo "==============================="
