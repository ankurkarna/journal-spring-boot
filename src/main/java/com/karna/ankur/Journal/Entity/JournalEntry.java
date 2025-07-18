package com.karna.ankur.Journal.Entity;

import com.karna.ankur.Journal.enums.Sentiment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "journal_entries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JournalEntry {

    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;
    private Sentiment sentiment;

    @JsonProperty("stringId")
    public String getStringId() {
        return id != null ? id.toString() : null;
    }

    // public static void main(String[] args) {
    // JournalEntry o = new JournalEntry()
    //
    // }
}
