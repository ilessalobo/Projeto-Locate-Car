package com.projeto.locatecar.service;

import com.projeto.locatecar.model.Cliente;

import java.util.List;

public interface ClienteService {

    Cliente buscarClientePorId(Long id);
    Cliente cadastrarCliente(Cliente novoCliente);
    List<Cliente> listarClientes();
    Cliente buscarClientePorCPF(String cpf);
    Cliente buscarClientePorCNPJ(String cnpj);
    void alterarCliente(Cliente cliente);
}
