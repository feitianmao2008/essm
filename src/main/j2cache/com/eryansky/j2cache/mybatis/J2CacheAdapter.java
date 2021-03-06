package com.eryansky.j2cache.mybatis;

import com.eryansky.j2cache.CacheChannel;
import com.eryansky.j2cache.J2Cache;
import com.eryansky.j2cache.util.Encrypt;
import org.apache.ibatis.cache.Cache;

import java.util.Collection;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 实现了 MyBatis 的缓存接口
 * @author 尔演&Eryan eryanwcp@gmail.com
 * @date 2018-07-24
 */
public class J2CacheAdapter implements Cache {

    private static final String DEFAULT_REGION = "default";

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private CacheChannel cache = J2Cache.getChannel();
    private String id;
    private boolean encodeKey;

    public J2CacheAdapter(String id) {
        if (id == null)
            id = DEFAULT_REGION;
        this.id = id;
    }

    public void setId(String id) {
        if (id == null)
            id = DEFAULT_REGION;
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        String _key = encodeKey ? Encrypt.md5(key.toString()):key.toString();
        this.cache.set(this.id, _key, value);
    }

    @Override
    public Object getObject(Object key) {
        String _key = encodeKey ? Encrypt.md5(key.toString()):key.toString();
        return this.cache.get(this.id, _key).getValue();
    }

    @Override
    public Object removeObject(Object key) {
        String _key = encodeKey ? Encrypt.md5(key.toString()):key.toString();
        Object obj = this.cache.get(this.id, _key).getValue();
        if (obj != null)
            this.cache.evict(this.id, _key);
        return obj;
    }

    @Override
    public void clear() {
        this.cache.clear(this.getId());
    }

    @Override
    public int getSize() {
        Collection<String> keys = this.cache.keys(this.getId());
        return keys != null ? keys.size() : 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }

    public Boolean getEncodeKey() {
        return encodeKey;
    }

    public void setEncodeKey(Boolean encodeKey) {
        this.encodeKey = encodeKey;
    }
}