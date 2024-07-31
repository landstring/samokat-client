package com.example.samokatclient.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

@Configuration
public class PaymentConfiguration {

    @Bean
    HttpClient paymentHttpClient(){
        return HttpClient.newHttpClient();
    }
}
