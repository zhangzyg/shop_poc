package com.loren.em.poc.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

/**
 * use "public" schema as default schema
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.loren.em.poc.repository")
@EntityScan(basePackages = "com.loren.em.poc.domain")
public class DataSourceConfig {

    @Bean
    public SpringLiquibase liquibaseDataSource(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:db/changelog/master.xml");
        liquibase.setShouldRun(true);
        return liquibase;
    }
}
