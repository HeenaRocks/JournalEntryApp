package net.engineeringdigest.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.MongoDbFactoryParser;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class JournalApplication {

	public static void main(String[] args) {

     ConfigurableApplicationContext context= SpringApplication.run(JournalApplication.class, args);
        ConfigurableEnvironment environment=context.getEnvironment();
        String[] profiles = environment.getActiveProfiles();

        System.out.println("Raw length: " + profiles.length);
        System.out.println("Using Env: " + Arrays.toString(profiles));
	}

    @Bean
    public PlatformTransactionManager heena(MongoDatabaseFactory mongoDatabaseFactory)
    {
        return new MongoTransactionManager(mongoDatabaseFactory);
    }

    @Bean
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }
}


//PlatformTransactionManager
//MongoTransactionManager