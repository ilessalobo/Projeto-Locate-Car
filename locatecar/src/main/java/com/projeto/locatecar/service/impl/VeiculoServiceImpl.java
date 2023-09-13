package com.projeto.locatecar.service.impl;

import com.projeto.locatecar.model.Veiculo;
import com.projeto.locatecar.service.VeiculoService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VeiculoServiceImpl implements VeiculoService {
    private final List<Veiculo> veiculos = new ArrayList<>();

    @Override
    public Veiculo cadastrarVeiculo(Veiculo novoVeiculo) {
        if (veiculoJaExiste(novoVeiculo.getPlaca())) {
            throw new IllegalArgumentException("Veículo com placa duplicada.");
        }

        veiculos.add(novoVeiculo);
        return novoVeiculo;
    }

    @Override
    public List<Veiculo> listarVeiculos() {
        return veiculos;
    }

    @Override
    public List<Veiculo> listarVeiculosDisponiveis() {
        return veiculos.stream()
                .filter(Veiculo::isDisponivel)
                .collect(Collectors.toList());
    }

    @Override
    public Veiculo buscarVeiculoPorPlaca(String placa) {
        return veiculos.stream()
                .filter(veiculo -> veiculo.getPlaca().equals(placa))
                .findFirst()
                .orElse(null);
    }
    @Override
    public void alterarVeiculo(Veiculo veiculo) {

        Veiculo veiculoExistente = buscarVeiculoPorPlaca(veiculo.getPlaca());

        if (veiculoExistente == null) {
            throw new IllegalArgumentException("Veículo não encontrado.");
        }
        veiculoExistente.setNome(veiculo.getNome());
        veiculoExistente.setTipoVeiculo(veiculo.getTipoVeiculo());
        veiculoExistente.setDisponivel(veiculo.isDisponivel());
    }

    @Override
    public List<Veiculo> buscarVeiculoPorNome(String nome) {
        return veiculos.stream()
                .filter(veiculo -> veiculo.getNome().equalsIgnoreCase(nome))
                .collect(Collectors.toList());
    }

    private boolean veiculoJaExiste(String placa) {
        return veiculos.stream().anyMatch(veiculo -> veiculo.getPlaca().equals(placa));
    }
}
