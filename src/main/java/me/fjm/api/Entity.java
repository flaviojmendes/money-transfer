package me.fjm.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Entity {

    public Entity() {
    }

    public Entity(Long id) {
        this.id = id;
    }

    private Long id;

    @JsonProperty
    public Long getId() {
        return id;
    }

    public Entity setId(Long id) {
        this.id = id;
        return this;
    }
}
