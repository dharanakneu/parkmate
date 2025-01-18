package com.neu.csye6220.parkmate.config;

import com.neu.csye6220.parkmate.model.*;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class HibernateConfig {

    @Bean
    public SessionFactory getSessionFactory() {
        Map<String, Object> settings = new HashMap<>();
        settings.put("hibernate.connection.driver_class", "oracle.jdbc.OracleDriver");

        // Fetching secrets from environment variables
        String dbUrl = System.getenv("DB_URL");
        String dbUsername = System.getenv("DB_USERNAME");
        String dbPassword = System.getenv("DB_PASSWORD");

        settings.put("hibernate.connection.url", dbUrl);
        settings.put("hibernate.connection.username", dbUsername);
        settings.put("hibernate.connection.password", dbPassword);

        //settings.put("hibernate.current_session_context_class", "spring");

        settings.put("hibernate.hbm2ddl.auto", "update");
        settings.put("hibernate.dialect", "org.hibernate.dialect.OracleDialect");
        settings.put("hibernate.dialect.storage_engine", "innodb");
        settings.put("hibernate.show-sql", "true");

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(settings)
                .build();
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        metadataSources.addPackage("com.neu.csye6220.parkmate.model");
        metadataSources.addAnnotatedClass(User.class);
        metadataSources.addAnnotatedClass(Renter.class);
        metadataSources.addAnnotatedClass(Rentee.class);
        metadataSources.addAnnotatedClass(ParkingLocation.class);
        metadataSources.addAnnotatedClass(ParkingSpot.class);
        metadataSources.addAnnotatedClass(Booking.class);
        metadataSources.addAnnotatedClass(Payment.class);
        metadataSources.addAnnotatedClass(Review.class);

        Metadata metadata = metadataSources.buildMetadata();

        return metadata.getSessionFactoryBuilder().build();
    }
}
