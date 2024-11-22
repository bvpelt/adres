#!/bin/bash

curl -v -u admin:12345 -X 'POST' \
  'http://localhost:8080/adresses?override=false' \
  -H 'accept: application/json' \
  -H 'x-api-key: f0583805-03f6-4c7f-8e40-f83f55b7c077' \
  -H 'Content-Type: application/json' \
  -d '{
  "street": "Kerkewijk",
  "housenumber": "125",
  "zipcode": "3904 JB",
  "city": "Veenendaal"
}'