package com.projeto.locatecar.service;

import com.projeto.locatecar.model.Veiculo;

import java.util.List;
public interface VeiculoService {
    Veiculo cadastrarVeiculo(Veiculo novoVeiculo);
    List<Veiculo> listarVeiculos();
    List<Veiculo> listarVeiculosDisponiveis();
    Veiculo buscarVeiculoPorPlaca(String placa);
    void alterarVeiculo(Veiculo veiculo);
    List<Veiculo> buscarVeiculoPorNome(String nome);
}