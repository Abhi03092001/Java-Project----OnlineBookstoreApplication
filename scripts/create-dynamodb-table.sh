#!/usr/bin/env bash
set -euo pipefail
: "${AWS_REGION:=us-east-1}"
: "${DYNAMODB_ENDPOINT:=http://localhost:8000}"

echo "[dynamodb] Creating Recommendations table (if not exists) at $DYNAMODB_ENDPOINT"

aws dynamodb create-table   --table-name Recommendations   --attribute-definitions AttributeName=username,AttributeType=S   --key-schema AttributeName=username,KeyType=HASH   --billing-mode PAY_PER_REQUEST   --region "$AWS_REGION"   --endpoint-url "$DYNAMODB_ENDPOINT" || true

echo "[dynamodb] Seeding sample recommendations for user 'testuser'"
aws dynamodb put-item   --table-name Recommendations   --item '{"username":{"S":"testuser"},"title":{"S":"Effective Java"},"score":{"N":"0.95"}}'   --region "$AWS_REGION" --endpoint-url "$DYNAMODB_ENDPOINT"

aws dynamodb put-item   --table-name Recommendations   --item '{"username":{"S":"testuser"},"title":{"S":"Refactoring"},"score":{"N":"0.91"}}'   --region "$AWS_REGION" --endpoint-url "$DYNAMODB_ENDPOINT"

aws dynamodb put-item   --table-name Recommendations   --item '{"username":{"S":"testuser"},"title":{"S":"The Pragmatic Programmer"},"score":{"N":"0.88"}}'   --region "$AWS_REGION" --endpoint-url "$DYNAMODB_ENDPOINT"
echo "[dynamodb] Done."
