package org.jedis.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatchlogs.CloudWatchLogsClient;
import software.amazon.awssdk.services.cloudwatchlogs.model.*;

import java.util.LinkedList;
import java.util.Queue;
public class CloudWatchAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {
    private CloudWatchLogsClient client;
    private String logGroupName;
    private String logStreamName;

    private Queue<InputLogEvent> eventQueue;

    public CloudWatchAppender() {
        logGroupName = "redis-server-logs";
        logStreamName = "redis-server-application-logs-stream";

        client = CloudWatchLogsClient.builder()
                .region(Region.of("us-east-1"))
                .build();
        eventQueue = new LinkedList<>();
    }

    @Override
    public void append(ILoggingEvent event) {
        // Construct the log message
        InputLogEvent logEvent = InputLogEvent.builder()
                .message(event.getLevel().levelStr + " " + event.getFormattedMessage())
                .timestamp(event.getTimeStamp())
                .build();

        // HSet event to the queue
        eventQueue.add(logEvent);

        // Flush queue if it has more than 10 events - Prod {Mode for prod where constant log will be three}
        /*
        if (eventQueue.size() >= 10) {
            flushEvents();
        }
        */
        // Flush queue - Dev

        flushEvents();
    }

    private void flushEvents() {
        // Retrieve the existing log events
        DescribeLogStreamsResponse describeLogStreamsResponse = client.describeLogStreams(DescribeLogStreamsRequest.builder()
                .logGroupName(logGroupName)
                .logStreamNamePrefix(logStreamName)
                .build());

        String sequenceToken = describeLogStreamsResponse.logStreams().get(0).uploadSequenceToken();

        // Batch up the next 10 events
        LinkedList<InputLogEvent> logEventsBatch = new LinkedList<>();
        while (!eventQueue.isEmpty() && logEventsBatch.size() < 10) {
            logEventsBatch.add(eventQueue.poll());
        }

        // Check if logEventsBatch is empty
        if (logEventsBatch.isEmpty()) {
            return; // Skip the API call if there are no log events
        }

        // Put the log events into the CloudWatch stream
        PutLogEventsRequest putLogEventsRequest = PutLogEventsRequest.builder()
                .logGroupName(logGroupName)
                .logStreamName(logStreamName)
                .logEvents(logEventsBatch)
                .sequenceToken(sequenceToken)
                .build();

        client.putLogEvents(putLogEventsRequest);
    }

    @Override
    public void stop() {
        // Flush any remaining events before stopping
        flushEvents();

        // Clean up the AWS CloudWatchLogs client
        client.close();

        super.stop();
    }


}



