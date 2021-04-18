package com.example.datastore.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSession;
import org.springframework.session.hazelcast.Hazelcast4IndexedSessionRepository;
import org.springframework.session.hazelcast.Hazelcast4PrincipalNameExtractor;
import org.springframework.session.hazelcast.HazelcastSessionSerializer;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;

import com.hazelcast.config.AttributeConfig;
import com.hazelcast.config.ClasspathYamlConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.IndexConfig;
import com.hazelcast.config.IndexType;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.spring.context.SpringManagedContext;

@Configuration
@EnableHazelcastHttpSession
public class HazelcastConfig {

    @Bean
    public SpringManagedContext managedContext() {
        return new SpringManagedContext();
    }

    @Bean
    public Config config(final SpringManagedContext context) {
        final Config config = new ClasspathYamlConfig("hazelcast.yml");

        enableSessions(config);

        config.setManagedContext(context);

        return config;
    }

    private void enableSessions(final Config config) {
        final AttributeConfig attributeConfig = new AttributeConfig()
            .setName(Hazelcast4IndexedSessionRepository.PRINCIPAL_NAME_ATTRIBUTE)
            .setExtractorClassName(Hazelcast4PrincipalNameExtractor.class.getName());

        config
            .getMapConfig(Hazelcast4IndexedSessionRepository.DEFAULT_SESSION_MAP_NAME)
            .addAttributeConfig(attributeConfig)
            .addIndexConfig(new IndexConfig(IndexType.HASH, Hazelcast4IndexedSessionRepository.PRINCIPAL_NAME_ATTRIBUTE));

        final SerializerConfig serializerConfig = new SerializerConfig()
            .setImplementation(new HazelcastSessionSerializer())
            .setTypeClass(MapSession.class);

        config.getSerializationConfig().addSerializerConfig(serializerConfig);
    }

}
