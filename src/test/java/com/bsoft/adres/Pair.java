package com.bsoft.adres;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Pair {
    private String name;
    private String description;

    public Pair(String name, String description) {
        this.name = name;
        this.description = description;
    }
}