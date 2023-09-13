package com.projeto.locatecar.service.impl;

import com.projeto.locatecar.model.Cliente;
import com.projeto.locatecar.model.PessoaFisica;
import com.projeto.locatecar.model.PessoaJuridica;
import com.projeto.locatecar.service.ClienteService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final List<Cliente> clientes = new ArrayList<>();

    @Override
    public void alterarCliente(Cliente cliente) {
        for (int i = 0; i < clientes.size(); i++) {
            Cliente c = clientes.get(i);
            if (c.getId().equals(cliente.getId())) {
                clientes.set(i, cliente);
                return;
            }
        }
        throw new IllegalArgumentException("Cliente não encontrado para alteração.");
    }

    @Override
    public Cliente buscarClientePorId(Long id) {
        return clientes.stream()
                .filter(cliente -> cliente.getId() != null && cliente.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Cliente cadastrarCliente(Cliente novoCliente) {
        if (clienteJaExiste(novoCliente)) {
            throw new IllegalArgumentException("Cliente já cadastrado.");
        }
        clientes.add(novoCliente);
        return novoCliente;
    }

    @Override
    public List<Cliente> listarClientes() {
        return clientes;
    }

    @Override
    public Cliente buscarClientePorCPF(String cpf) {
        return clientes.stream()
                .filter(cliente -> cliente instanceof PessoaFisica && ((PessoaFisica) cliente).getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Cliente buscarClientePorCNPJ(String cnpj) {
        return clientes.stream()
                .filter(cliente -> cliente instanceof PessoaJuridica && ((PessoaJuridica) cliente).getCnpj().equals(cnpj))
                .findFirst()
                .orElse(null);
    }
    private boolean clienteJaExiste(Cliente cliente) {
        return clientes.contains(cliente);
    }
}
