#!/bin/bash
PROGOUT=$( java TextAlignment  )

if [[ $PROGOUT != "usage: java TextAlignment <filename> <alignmentType> <lineLength>" ]]; then
  echo "Expected 'usage: java TextAlignment <filename> <alignmentType> <lineLength>'"
  echo "Your program printed '$PROGOUT'"
  exit 1
fi;
