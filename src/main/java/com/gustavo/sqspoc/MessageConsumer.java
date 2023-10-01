package com.gustavo.sqspoc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.List;

@Service
public class MessageConsumer {

    @Value("${sqs.queue.url}")
    private String sqsQueueUrl;

    private final SqsClient sqsClient;

    public MessageConsumer(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    @Scheduled(fixedDelay = 3000)
    public void consumeMessages() {
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(sqsQueueUrl)
                .maxNumberOfMessages(5)
                .waitTimeSeconds(20)
                .build();

        List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).messages();

        for (Message message : messages) {
            processMessage(message);
        }
    }

    private void processMessage(Message message) {

        System.out.println(message.body());

        // Delete the message from the queue
        sqsClient.deleteMessage(DeleteMessageRequest.builder()
                .queueUrl(sqsQueueUrl)
                .receiptHandle(message.receiptHandle())
                .build());
    }
}