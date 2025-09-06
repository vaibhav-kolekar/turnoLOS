package com.example.los.config;

import com.example.los.model.Agent;
import com.example.los.repository.AgentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner init(AgentRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                Agent m = Agent.builder().name("Manager Bob").phone("9000000000").available(true).build();
                repo.save(m);
                Agent a1 = Agent.builder().name("Agent Alice").phone("9000000001").manager(m).available(true).build();
                Agent a2 = Agent.builder().name("Agent Charlie").phone("9000000002").manager(m).available(true).build();
                repo.save(a1);
                repo.save(a2);
            }
        };
    }
}
