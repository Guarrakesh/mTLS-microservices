#!/bin/bash

# usage: ./extract-keys-from-p12 {p12_path} {destination_directory}

openssl pkcs12 -in $1 -nocerts -out $2/privateKey.pem
openssl pkcs12 -in $1 -clcerts -nokeys -out $2/publicCert.pem

