#!/bin/bash

for i in *.yaml
do
  echo $i
  kubectl delete -f $i
done
