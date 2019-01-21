package me.fjm.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import me.fjm.api.Account;
import me.fjm.api.Entity;
import org.apache.commons.text.WordUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class Repository<T extends Entity> {

    List<T> data;
    Logger logger = Logger.getLogger(getClass().getName());

    public Repository(String datasource) {
        try {
            initData(datasource);
        } catch (IOException e) {
            throw new RuntimeException(
                    datasource + " missing or is unreadable", e);
        }
    }

    private void initData(String datasource) throws IOException {
        URL url = Resources.getResource(datasource);
        String json = Resources.toString(url, Charsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        CollectionType type = mapper
                .getTypeFactory()
                .constructCollectionType(List.class, Account.class);
        data = mapper.readValue(json, type);
    }
    public List<T> findAll() {
        return data;
    }

    public Optional<T> findById(Long id){
        return data.stream().filter(e -> e.getId() == id).findFirst();
    }

    public T save(T entity){
        Optional<Long> maxId = data.stream()
                .map(T::getId)
                .max(Long::compare);
        long nextId = maxId.map(x -> x + 1).orElse(1L);
        entity.setId(nextId);
        data.add(entity);
        return entity;
    }

    public void delete(Long id){
        data.removeIf(e -> e.getId() == id);
    }

    public Optional<T> update(Long id, T entity) {
        Optional<T> existingEntity = findById(id);
        existingEntity.ifPresent(e -> updateExceptId(entity, e));
        return existingEntity;
    }

    private T updateExceptId(T newEntity, T dbEntity) {

        Field[] fields = newEntity.getClass().getDeclaredFields();

        for(Field field : fields) {
            try {
                Object newValue = newEntity.getClass().getDeclaredMethod("get"+ WordUtils.capitalize(field.getName())).invoke(newEntity);
                if(newValue != null) {
                    dbEntity.getClass().getDeclaredMethod("set" + WordUtils.capitalize(field.getName()), newValue.getClass()).invoke(dbEntity, newValue);
                }
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                logger.severe(e.getMessage());
            }
        }

        return dbEntity;
    }
}
