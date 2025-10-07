#!/usr/bin/env bash
set -euo pipefail
echo "[deploy] Building JAR..."
mvn -q -DskipTests package
echo "[deploy] Bringing up Docker stack..."
docker-compose up --build -d
echo "[deploy] Done."
