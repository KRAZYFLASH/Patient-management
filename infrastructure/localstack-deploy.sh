#!/bin/bash

set -e

aws --endpoint-url=http://localhost:4566 cloudformation deploy \
    --stack-name patient-management \
    --template-file "./cdk.out/LocalStack.template.json"

aws --endpoint-url=http://localhost:4566 elbv2 describe-load-balancers \
    --query "LoadBalancers[0].DNSName" --output text

aws --endpoint-url=http://localhost:4566 cloudformation describe-stacks \
  --stack-name patient-management \
  --query "Stacks[0].{Status:StackStatus,Reason:StackStatusReason}"

aws --endpoint-url=http://localhost:4566 cloudformation describe-stack-resources \
  --stack-name patient-management