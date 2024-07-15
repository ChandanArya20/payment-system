package com.cpt.payments.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Autowired
    private Environment env;

    public DataSource processingServiceJdbcTemplate() {
        System.out.println("db.url:" + env.getProperty("spring.datasource.url"));

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driverClassName"));
        dataSource.setJdbcUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        dataSource.setMaximumPoolSize(Integer.parseInt(env.getProperty("spring.datasource.maxActive")));
        dataSource.setConnectionInitSql(env.getProperty("spring.datasource.validationQuery"));
        dataSource.setMinimumIdle(Integer.parseInt(env.getProperty("spring.datasource.minIdle")));
        return dataSource;
    }

    @Bean
    public NamedParameterJdbcTemplate namedJdbcTemplate() {
        return new NamedParameterJdbcTemplate(processingServiceJdbcTemplate());
    }
}
