package com.example.datastore.configuration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSession;
import org.springframework.session.hazelcast.Hazelcast4IndexedSessionRepository;
import org.springframework.session.hazelcast.Hazelcast4PrincipalNameExtractor;
import org.springframework.session.hazelcast.HazelcastSessionSerializer;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;

import com.example.datastore.adapter.SettingStore;
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
    public SpringManagedContext managedContext(final ApplicationContext applicationContext) {
        return new SpringManagedContext(applicationContext);
    }

    @Bean
    public Config config(final SpringManagedContext managedContext, final SettingStore settingStore) {
        final Config config = new ClasspathYamlConfig("hazelcast.yml");

        config.getMapConfig("Setting").getMapStoreConfig().setImplementation(settingStore);

        enableSessions(config);

        config.setManagedContext(managedContext);

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
