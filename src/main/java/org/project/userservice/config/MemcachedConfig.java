package org.project.userservice.config;

import net.spy.memcached.MemcachedClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.InetSocketAddress;

@Configuration
public class MemcachedConfig {

    @Bean
    public MemcachedClient memcachedClient() throws IOException {
        return new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
    }
}

