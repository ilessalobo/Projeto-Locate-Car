package com.projeto.locatecar.model;

import lombok.Data;

@Data
public class PessoaJuridica extends Cliente {
    private String cnpj;
    private String nomeEmpresa;

    public PessoaJuridica(String nome, String telefone, String cnpj, String nomeEmpresa) {
        super(nome, telefone);
        this.cnpj = cnpj;
        this.nomeEmpresa = nomeEmpresa;
    }
}
