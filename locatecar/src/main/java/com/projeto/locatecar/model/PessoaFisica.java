package com.projeto.locatecar.model;

import lombok.Data;

@Data
public class PessoaFisica extends Cliente {
   private String cpf;
   private String rg;

   public PessoaFisica(String nome, String telefone, String cpf, String rg) {
      super(nome, telefone);
      this.cpf = cpf;
      this.rg = rg;
   }
}
