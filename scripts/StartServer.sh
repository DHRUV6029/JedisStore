#!/bin/bash

# Set AWS credentials as environment variables
export AWS_ACCESS_KEY_ID=your-access-key-id
export AWS_SECRET_ACCESS_KEY=your-secret-access-key
export AWS_REGION=your-region

# Your startup commands

# Create AWS CloudWatch log group
aws logs create-log-group --log-group-name your-log-group-name

# Create AWS CloudWatch log stream
aws logs create-log-stream --log-group-name your-log-group-name --log-stream-name your-log-stream-name

# Run your Java application
java -jar your-application.jar   #todo change the name of the Jar
