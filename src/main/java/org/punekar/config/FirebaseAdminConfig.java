package org.punekar.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.InputStream;

@Configuration
public class FirebaseAdminConfig {
    private static final Logger log = LoggerFactory.getLogger(FirebaseAdminConfig.class);

    @PostConstruct
    public void init() {
        try {
            // Priority: env var path -> classpath resource
            String envPath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
            InputStream serviceAccount = null;

            if (envPath != null && !envPath.isEmpty()) {
                log.info("Loading Firebase service account from {}", envPath);
                serviceAccount = new FileInputStream(envPath);
            } else {
                log.info("Loading Firebase service account from classpath:/firebase-service-account.json");
                serviceAccount = getClass().getClassLoader().getResourceAsStream("firebase-service-account.json");
            }

            if (serviceAccount == null) {
                log.warn("Firebase service account not found; Firebase Admin will not be initialized.\n" +
                        "Set GOOGLE_APPLICATION_CREDENTIALS env var or add src/main/resources/firebase-service-account.json");
                return;
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("Initialized Firebase App");
            } else {
                log.info("Firebase App already initialized");
            }
        } catch (Exception e) {
            log.error("Failed to initialize Firebase Admin", e);
        }
    }
}
