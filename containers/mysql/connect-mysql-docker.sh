#!/bin/bash
username="${1:-test}"
password="${2:-test}"
db_name="${3:-test}"

mysql --user=$username --password=$password --host=localhost --port=20306 --protocol=tcp $db_name 
