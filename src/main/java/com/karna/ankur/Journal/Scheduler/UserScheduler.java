package com.karna.ankur.Journal.Scheduler;

import com.karna.ankur.Journal.Entity.JournalEntry;
import com.karna.ankur.Journal.Entity.UserEntity;
import com.karna.ankur.Journal.Repository.UserRepositoryImpl;
import com.karna.ankur.Journal.Service.EmailService;
import com.karna.ankur.Journal.Service.SentimentAnalysisService;
import com.karna.ankur.Journal.cache.AppCache;
import com.karna.ankur.Journal.enums.Sentiment;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {
    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private EmailService emailService;

    // @Autowired
    // private KafkaTemplate<String, SentimentMessage> kafkaTemplate;

    @Autowired
    private AppCache appCache;

    // @Autowired
    // private SentimentMessage sentimentMessage;

    // @Scheduled(cron = "0 */2 * * * *")
    // @Scheduled(cron = "0 0 9 * * SUN")
    @Scheduled(cron = "0 5 21 * * *")
    public void fetchUsersAndSendSAMail() {
        List<UserEntity> users = userRepository.getUserForSA();
        for (UserEntity user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream()
                    .filter(a -> a.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(a -> a.getSentiment()).collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            for (Sentiment sentiment : sentiments) {
                if (sentiment != null) {
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
                }
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
            if (mostFrequentSentiment != null) {
                // Kafka code commented out. Now sending email directly.
                // SentimentMessage sentimentMessage =
                // SentimentMessage.builder().email(user.getEmail()).sentiment("This is only for
                // testing purpose. \nMessages are sent by kafka producer and logs are sent to
                // consumer for future reference. \nSentiment for last 7 days " +
                // mostFrequentSentiment).build();
                // kafkaTemplate.send("weekly-sentiments", sentimentMessage.getEmail(),
                // sentimentMessage);
                String subject = "Your Weekly Sentiment Report";
                String message = "Hi,\n\nYour most frequent sentiment for the last 7 days is: " + mostFrequentSentiment
                        + ".\n\nStay mindful!\n\n- Crynza";
                emailService.sendEmail(user.getEmail(), subject, message);
            }
        }
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void getAppCache() {
        appCache.init();
    }
}
