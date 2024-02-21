@echo off

rem Set AWS credentials as environment variables
set AWS_ACCESS_KEY_ID=your-access-key-id
set AWS_SECRET_ACCESS_KEY=your-secret-access-key
set AWS_REGION=your-region

rem Your startup commands

rem Create AWS CloudWatch log group
aws logs create-log-group --log-group-name redis-server-logs
rem Create AWS CloudWatch log stream
aws logs create-log-stream --log-group-name redis-server-logs --log-stream-name redis-server-application-logs-stream
rem Run your Java application
java -jar your-application.jar   rem todo change the name of the Jar
