package com.example.logistics.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Data
@EqualsAndHashCode(callSuper = false)
@Primary
@Component
public class MailConfig extends MailProperties {

    private Map<String, String> properties;

    public MailConfig() {
        properties = new HashMap<>();
        properties.put("mail.smtp.auth", Boolean.TRUE.toString());
        properties.put("mail.smtp.ssl.enable", Boolean.TRUE.toString());
        properties.put("mail.smtp.timeout", "10000");
    }


}
