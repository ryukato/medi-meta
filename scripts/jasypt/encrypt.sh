#!/bin/bash
echo "How to use ./scripts/encrypt.sh password input"
java -cp utils/jasypt-1.9.3/lib/jasypt-1.9.3.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI \
algorithm="PBEWITHHMACSHA512ANDAES_256" \
keyObtentionIterations=1000 \
providerName="SunJCE" \
saltGeneratorClassName="org.jasypt.salt.RandomSaltGenerator" \
ivGeneratorClassName="org.jasypt.iv.RandomIvGenerator" \
stringOutputType="Base64" \
input=$2 \
password=$1
