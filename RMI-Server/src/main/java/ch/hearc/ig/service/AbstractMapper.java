package ch.hearc.ig.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

public abstract class AbstractMapper<T> {
    JsonNode root;
    Date createdAt;

    public AbstractMapper(String jsonAsStr) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.root = mapper.readTree(jsonAsStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        this.createdAt = new Date();
    }
    public abstract T create() throws NoDataFoundException;

}
