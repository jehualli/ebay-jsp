#!/bin/sh
# Build and run eBay 1999 on http://localhost:8081/
set -e
cd "$(dirname "$0")"
docker build -t ebay-jsp .
docker rm -f ebay >/dev/null 2>&1 || true
docker run -d --name ebay -p 8081:8080 ebay-jsp
echo "eBay is starting on http://localhost:8081/  (guest / ebay)"
echo "Logs:  docker logs -f ebay"
echo "Stop:  docker rm -f ebay"
