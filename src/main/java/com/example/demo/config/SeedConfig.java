package com.example.demo.config;

import com.example.demo.models.Role;
import com.example.demo.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SeedConfig implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public SeedConfig(RoleRepository roleRepository) {

        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        var roleAdmin = new Role("ROLE_ADMIN");
        var roleUser = new Role("ROLE_USER");

        roleRepository.saveAll(List.of(roleAdmin, roleUser));
    }
}