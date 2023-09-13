package com.projeto.locatecar.service.impl;

import com.projeto.locatecar.enums.TipoVeiculo;
import com.projeto.locatecar.model.*;
import com.projeto.locatecar.service.AluguelService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AluguelServiceImpl implements AluguelService {

    private final List<Aluguel> alugueis = new ArrayList<>();

    @Override
    public Aluguel alugarVeiculo(Cliente cliente, Veiculo veiculo, LocalDateTime dataHoraAluguel) {
        if (!veiculo.isDisponivel()) {
            throw new IllegalArgumentException("O veículo não está disponível para aluguel.");
        }

        Aluguel aluguel = new Aluguel(cliente, veiculo, dataHoraAluguel);
        alugueis.add(aluguel);
        veiculo.setDisponivel(false);

        return aluguel;
    }

    @Override
    public void devolverVeiculo(Aluguel aluguel, LocalDateTime dataHoraDevolucao) {
        LocalDateTime dataHoraAluguel = aluguel.getDataHoraAluguel();

        if (dataHoraDevolucao.isAfter(dataHoraAluguel)) {
            Duration duration = Duration.between(dataHoraAluguel, dataHoraDevolucao);
            long horasAlugadas = duration.toHours();

            long diasAlugados = horasAlugadas / 24;

            if (horasAlugadas % 24 != 0) {
                diasAlugados++;
            }

            Cliente cliente = aluguel.getCliente();
            double desconto = calcularDesconto(cliente, diasAlugados);

            Veiculo veiculo = aluguel.getVeiculo();
            double valorDiaria = obterValorDiariaPorTipo(veiculo.getTipoVeiculo());

            double valorTotal = valorDiaria * diasAlugados * (1 - desconto);

            aluguel.setValorTotal(valorTotal);
            aluguel.setDataHoraDevolucao(dataHoraDevolucao);

            alugueis.remove(aluguel);

            veiculo.setDisponivel(true);
        } else {
            throw new IllegalArgumentException("Data e hora de devolução inválidas.");
        }
    }

    public double obterValorDiariaPorTipo(TipoVeiculo tipoVeiculo) {
        switch (tipoVeiculo) {
            case PEQUENO:
                return 100.0;
            case MEDIO:
                return 150.0;
            case SUV:
                return 200.0;
            default:
                throw new IllegalArgumentException("Tipo de veículo inválido.");
        }
    }
    @Override
    public List<Aluguel> listarAlugueisPorCliente(Cliente cliente) {
        return alugueis.stream()
                .filter(aluguel -> aluguel.getCliente().equals(cliente))
                .collect(Collectors.toList());
    }

    private double calcularDesconto(Cliente cliente, long diasAlugados) {
        if (cliente instanceof PessoaFisica && diasAlugados > 5) {
            return 0.05;
        } else if (cliente instanceof PessoaJuridica && diasAlugados > 3) {
            return 0.10;
        }
        return 0.0;
    }

    @Override
    public Aluguel buscarAluguelPorId(long id) {
        for (Aluguel aluguel : alugueis) {
            if (aluguel.getId() == id) {
                return aluguel;
            }
        }
        return null;
    }
}