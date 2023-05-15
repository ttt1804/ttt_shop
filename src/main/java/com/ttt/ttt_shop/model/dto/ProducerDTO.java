package com.ttt.ttt_shop.model.dto;

public class ProducerDTO {
    private Long id;
    private String name;

    public ProducerDTO() {
    }

    public ProducerDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}