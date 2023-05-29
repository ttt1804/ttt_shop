package com.ttt.ttt_shop.model.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CategoryDTO {
    private Long id;

    @Pattern(regexp = "^[a-zA-Z]+$", message = "Tên chỉ được chứa chữ cái")
    private String name;

    public CategoryDTO() {
    }

    public CategoryDTO(Long id, String name) {
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
