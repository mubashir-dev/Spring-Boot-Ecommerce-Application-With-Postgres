package com.ecommerce.config;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class InitialSeed {

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    private static final Logger log = LoggerFactory.getLogger(InitialSeed.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void addAdminUser() {
        Boolean adminExist = jdbcTemplate.queryForObject("SELECT Count(*) from users where email = '%s' AND username = '%s'".formatted(adminEmail, adminUsername), Integer.class) > 0;
        if (!adminExist) {
            String encodedPassword = passwordEncoder.encode(adminPassword);
            UUID uuid = UUID.randomUUID();

            String sql = """
                        INSERT INTO users (name, username, password, email, uuid)
                        VALUES (?, ?, ?, ?, ?)
                    """;

            jdbcTemplate.update(sql, "Super Admin", adminUsername, encodedPassword, adminEmail, uuid);

            log.info("✅ Successfully added Super Admin");
            return;
        }
        log.info(" ✅ Super Admin Already Exists");
    }
}
