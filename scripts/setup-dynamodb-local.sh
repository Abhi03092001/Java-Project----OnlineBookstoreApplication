#!/usr/bin/env bash
set -euo pipefail
echo "[dynamodb] Starting DynamoDB Local via docker-compose"
docker-compose up -d dynamodb
