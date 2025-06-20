package com.karna.ankur.Journal.Entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "config_journal_app")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfigJournalAppEntity {

    private String key;
    private String value;


}
