package com.by.CareersService.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;

@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {
    @Override
    protected String getContactPoints() {
        return "localhost";
    }

    @Override
    protected String getLocalDataCenter() {
        return "datacenter1";
    }

    @Override
    protected String getKeyspaceName() {
        return "bootcamp";
    }
}
