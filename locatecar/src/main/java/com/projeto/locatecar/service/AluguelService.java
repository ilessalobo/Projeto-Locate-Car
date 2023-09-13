package com.projeto.locatecar.service;

import com.projeto.locatecar.model.Aluguel;
import com.projeto.locatecar.model.Cliente;
import com.projeto.locatecar.model.Veiculo;

import java.time.LocalDateTime;
import java.util.List;

public interface AluguelService {
    Aluguel alugarVeiculo(Cliente cliente, Veiculo veiculo, LocalDateTime dataHoraAluguel);
    void devolverVeiculo(Aluguel aluguel, LocalDateTime dataHoraDevolucao);
    List<Aluguel> listarAlugueisPorCliente(Cliente cliente);
    Aluguel buscarAluguelPorId(long id);
}
