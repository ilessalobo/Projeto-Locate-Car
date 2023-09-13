package com.projeto.locatecar.model;

import com.projeto.locatecar.enums.TipoVeiculo;
import lombok.Data;

@Data
public class Veiculo {
    private static long nextId = 1;
    private long id;
    private String nome;
    private String placa;
    private TipoVeiculo tipoVeiculo;
    private boolean disponivel;

    public Veiculo(String nome, String placa, TipoVeiculo tipoVeiculo) {
        this.id = nextId++;
        this.nome = nome;
        this.placa = placa;
        this.tipoVeiculo = tipoVeiculo;
        this.disponivel = true;
    }
}
