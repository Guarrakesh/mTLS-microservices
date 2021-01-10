#!/bin/bash

# usage: ./extract-pem-from-jks {p12_path} {destination_directory}

keytool -exportcert -keystore $1 -file $2/new.pem