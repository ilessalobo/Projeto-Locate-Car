package com.projeto.locatecar.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Aluguel {
        private static long nextId = 1;
        private long id;
        private Veiculo veiculo;
        private Cliente cliente;
        private String LocalDevolucao;
        private LocalDateTime dataHoraAluguel;
        private LocalDateTime dataHoraDevolucao;
        private double valorTotal;

        public Aluguel(Cliente cliente, Veiculo veiculo, LocalDateTime dataHoraAluguel) {
                this.id = nextId++;
                this.cliente = cliente;
                this.veiculo = veiculo;
                this.dataHoraAluguel = dataHoraAluguel;
        }
}
