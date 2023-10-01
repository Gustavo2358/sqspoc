package com.gustavo.sqspoc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private SqsClient sqsClient;

    @Value("${sqs.queue.url}")
    private String sqsQueueUrl;

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody String messageBody) {
        SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
                .queueUrl(sqsQueueUrl)
                .messageBody(messageBody)
                .build();

        sqsClient.sendMessage(sendMsgRequest);
        return ResponseEntity.ok("Message sent to SQS");
    }
}