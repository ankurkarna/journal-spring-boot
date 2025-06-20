package com.karna.ankur.Journal.Service;

import com.karna.ankur.Journal.Entity.SentimentMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SentimentConsumerService {

    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "weekly-sentiments", groupId = "weekly-sentiment-group")
    public void consume(SentimentMessage message){
        sendEmail(message);
        log.info("Consumed Kafka message: {}", message);
    }

    private void sendEmail(SentimentMessage sentimentMessage){
        emailService.sendEmail(sentimentMessage.getEmail(), "Sentiment Summary for Last 7 Days", "Your overall sentiment: " + sentimentMessage.getSentiment());
    }
}
