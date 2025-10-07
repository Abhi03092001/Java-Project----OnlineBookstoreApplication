#!/usr/bin/env bash
set -euo pipefail
echo "[tests] Running unit tests (JUnit)"
mvn -q -DskipITs test
echo "[tests] To run Cucumber BDD tests (optional): mvn -q -Pcucumber test"
