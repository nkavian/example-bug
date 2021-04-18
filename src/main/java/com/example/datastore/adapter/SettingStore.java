package com.example.datastore.adapter;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.datastore.service.SettingService;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.MapLoaderLifecycleSupport;
import com.hazelcast.map.MapStore;
import com.hazelcast.map.PostProcessingMapStore;
import com.hazelcast.spring.context.SpringAware;

/**
 * The class SettingStore.
 */
@SpringAware
@Component
public final class SettingStore implements MapLoaderLifecycleSupport, MapStore<String, String>, PostProcessingMapStore {

    /** The setting service. */
    @Autowired
    private SettingService settingService;

    @Override
    public void init(final HazelcastInstance hazelcastInstance, final Properties properties, final String mapName) {
        hazelcastInstance
            .getConfig()
            .getManagedContext()
            .initialize(this);
    }

    @Override
    public void destroy() {
        //
    }

    /* (non-Javadoc)
     * @see com.hazelcast.core.MapStore#delete(java.lang.Object)
     */
    @Override
    public void delete(final String key) {
        // Ignore.
    }

    /* (non-Javadoc)
     * @see com.hazelcast.core.MapStore#deleteAll(java.util.Collection)
     */
    @Override
    public void deleteAll(final Collection<String> keys) {
        // Ignore.
    }

    /* (non-Javadoc)
     * @see com.hazelcast.core.MapLoader#load(java.lang.Object)
     */
    @Override
    public String load(final String key) {
        return settingService.readValue(key);
    }

    /* (non-Javadoc)
     * @see com.hazelcast.core.MapLoader#loadAll(java.util.Collection)
     */
    @Override
    public Map<String, String> loadAll(final Collection<String> keys) {
        final Map<String, String> map = new LinkedHashMap<>();
        keys.forEach(key -> map.put(key, settingService.readValue(key)));
        return map;
    }

    /* (non-Javadoc)
     * @see com.hazelcast.core.MapLoader#loadAllKeys()
     */
    @Override
    public Iterable<String> loadAllKeys() {
        return null;
    }

    /* (non-Javadoc)
     * @see com.hazelcast.core.MapStore#store(java.lang.Object, java.lang.Object)
     */
    @Override
    public void store(final String key, final String value) {
        settingService.writeValue(key, value);
    }

    /* (non-Javadoc)
     * @see com.hazelcast.core.MapStore#storeAll(java.util.Map)
     */
    @Override
    public void storeAll(final Map<String, String> map) {
        map.entrySet().forEach(entry -> settingService.writeValue(entry.getKey(), entry.getValue()));
    }

}
