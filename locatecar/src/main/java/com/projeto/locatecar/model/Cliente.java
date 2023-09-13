package com.projeto.locatecar.model;

import lombok.Data;

@Data
public abstract class Cliente {
    private static long nextId = 1;
    private Long id;
    private String nome;
    private String telefone;

    public Cliente() {
        this.id = nextId++;
    }

    public Cliente(String nome, String telefone) {
        this();
        this.nome = nome;
        this.telefone = telefone;
    }
}
