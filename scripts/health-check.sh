#!/usr/bin/env bash
set -e
URL=$1
if [ -z "$URL" ]; then
  echo "Usage: $0 <health-url>"
  exit 2
fi

echo "Checking $URL ..."
HTTP_STATUS=$(curl -s -o /dev/null -w "%{http_code}" $URL)
if [ "$HTTP_STATUS" -ne 200 ]; then
  echo "Health check failed: status $HTTP_STATUS"
  exit 1
fi
echo "OK"
