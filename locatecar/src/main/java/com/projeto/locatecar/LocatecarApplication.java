package com.projeto.locatecar;

import com.projeto.locatecar.enums.TipoVeiculo;
import com.projeto.locatecar.model.*;
import com.projeto.locatecar.service.AluguelService;
import com.projeto.locatecar.service.ClienteService;
import com.projeto.locatecar.service.VeiculoService;
import com.projeto.locatecar.service.impl.AluguelServiceImpl;
import com.projeto.locatecar.service.impl.ClienteServiceImpl;
import com.projeto.locatecar.service.impl.VeiculoServiceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class LocatecarApplication {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		AluguelService aluguelService = new AluguelServiceImpl();
		ClienteService clienteService = new ClienteServiceImpl();
		VeiculoService veiculoService = new VeiculoServiceImpl();

		int escolha = 0;
		do {
			try {
				System.out.println("===== Menu Principal =====");
				System.out.println("1. Cadastrar Veículo");
				System.out.println("2. Alterar Veículo");
				System.out.println("3. Buscar Veículo por Nome");
				System.out.println("4. Cadastrar Cliente");
				System.out.println("5. Alterar Cliente");
				System.out.println("6. Alugar Veículo");
				System.out.println("7. Devolver Veículo");
				System.out.println("8. Sair");
				System.out.print("Escolha uma opção: ");

				escolha = scanner.nextInt();
				scanner.nextLine();

				switch (escolha) {
					case 1 -> cadastrarVeiculo(scanner, veiculoService);
					case 2 -> alterarVeiculo(scanner, veiculoService);
					case 3 -> buscarVeiculoPorNome(scanner, veiculoService);
					case 4 -> cadastrarCliente(scanner, clienteService);
					case 5 -> alterarCliente(scanner, clienteService);
					case 6 -> alugarVeiculo(scanner, aluguelService, clienteService, veiculoService);
					case 7 -> devolverVeiculo(scanner, aluguelService);
					case 8 -> System.out.println("Saindo do programa.");
					default -> System.out.println("Opção inválida. Tente novamente.");
				}
			} catch (Exception e) {
				System.out.println("Ocorreu um erro: " + e.getMessage());
			}
		} while (escolha != 8);

		scanner.close();
	}

	private static void cadastrarVeiculo(Scanner scanner, VeiculoService veiculoService) {
		System.out.println("===== Cadastro de Veículo =====");
		System.out.print("Nome do veículo: ");
		String nome = scanner.nextLine();
		System.out.print("Placa do veículo: ");
		String placa = scanner.nextLine();
		System.out.print("Tipo do veículo (PEQUENO, MEDIO, SUV): ");
		String tipoVeiculoStr = scanner.nextLine();
		TipoVeiculo tipoVeiculo = TipoVeiculo.valueOf(tipoVeiculoStr);

		Veiculo novoVeiculo = new Veiculo(nome, placa, tipoVeiculo);
		veiculoService.cadastrarVeiculo(novoVeiculo);

		System.out.println("Veículo cadastrado com sucesso!");
	}

	private static void alterarVeiculo(Scanner scanner, VeiculoService veiculoService) {
		System.out.println("===== Alteração de Veículo =====");
		System.out.print("Placa do veículo a ser alterado: ");
		String placa = scanner.nextLine();

		Veiculo veiculo = veiculoService.buscarVeiculoPorPlaca(placa);

		if (veiculo == null) {
			System.out.println("Veículo não encontrado.");
		} else {
			System.out.print("Novo nome do veículo: ");
			String novoNome = scanner.nextLine();
			System.out.print("Novo tipo do veículo (PEQUENO, MEDIO, SUV): ");
			String novoTipoVeiculoStr = scanner.nextLine();
			TipoVeiculo novoTipoVeiculo = TipoVeiculo.valueOf(novoTipoVeiculoStr);

			veiculo.setNome(novoNome);
			veiculo.setTipoVeiculo(novoTipoVeiculo);

			veiculoService.alterarVeiculo(veiculo);
			System.out.println("Veículo alterado com sucesso!");
		}
	}

	private static void buscarVeiculoPorNome(Scanner scanner, VeiculoService veiculoService) {
		System.out.println("===== Busca de Veículo por Nome =====");
		System.out.print("Nome do veículo a ser buscado: ");
		String nome = scanner.nextLine();

		List<Veiculo> veiculosEncontrados = veiculoService.buscarVeiculoPorNome(nome);

		if (veiculosEncontrados.isEmpty()) {
			System.out.println("Nenhum veículo encontrado com o nome fornecido.");
		} else {
			System.out.println("Veículos encontrados:");
			for (Veiculo veiculo : veiculosEncontrados) {
				System.out.println(veiculo);
			}
		}
	}

	private static void cadastrarCliente(Scanner scanner, ClienteService clienteService) {
		System.out.println("===== Cadastro de Cliente =====");
		System.out.print("Nome do cliente: ");
		String nome = scanner.nextLine();
		System.out.print("Telefone do cliente: ");
		String telefone = scanner.nextLine();
		System.out.print("Tipo do cliente (PF para Pessoa Física, PJ para Pessoa Jurídica): ");
		String tipoCliente = scanner.nextLine();

		Cliente novoCliente = null;
		if ("PF".equalsIgnoreCase(tipoCliente)) {
			System.out.print("CPF do cliente: ");
			String cpf = scanner.nextLine();
			System.out.print("RG do cliente: ");
			String rg = scanner.nextLine();

			novoCliente = new PessoaFisica(nome, telefone, cpf, rg);
		} else if ("PJ".equalsIgnoreCase(tipoCliente)) {
			System.out.print("CNPJ do cliente: ");
			String cnpj = scanner.nextLine();
			System.out.print("Nome da empresa: ");
			String nomeEmpresa = scanner.nextLine();

			novoCliente = new PessoaJuridica(nome, telefone, cnpj, nomeEmpresa);
		}

		Cliente clienteCadastrado = clienteService.cadastrarCliente(novoCliente);

		System.out.println("Cliente cadastrado com sucesso!");
		System.out.println("ID do cliente: " + clienteCadastrado.getId());
	}

	private static void alterarCliente(Scanner scanner, ClienteService clienteService) {
		System.out.println("===== Alteração de Cliente =====");
		System.out.print("ID do cliente a ser alterado: ");
		Long id = scanner.nextLong();
		scanner.nextLine();

		Cliente cliente = clienteService.buscarClientePorId(id);

		if (cliente == null) {
			System.out.println("Cliente não encontrado.");
			return;
		}

		System.out.print("Novo nome do cliente: ");
		String novoNome = scanner.nextLine();
		System.out.print("Novo telefone do cliente: ");
		String novoTelefone = scanner.nextLine();

		cliente.setNome(novoNome);
		cliente.setTelefone(novoTelefone);

		if (cliente instanceof PessoaFisica pf) {
			System.out.print("Novo CPF do cliente: ");
			String novoCpf = scanner.nextLine();
			System.out.print("Novo RG do cliente: ");
			String novoRg = scanner.nextLine();

			pf.setCpf(novoCpf);
			pf.setRg(novoRg);
		} else if (cliente instanceof PessoaJuridica pj) {
			System.out.print("Novo CNPJ do cliente: ");
			String novoCnpj = scanner.nextLine();
			System.out.print("Novo nome da empresa: ");
			String novoNomeEmpresa = scanner.nextLine();

			pj.setCnpj(novoCnpj);
			pj.setNomeEmpresa(novoNomeEmpresa);
		} else {
			System.out.println("Tipo de cliente não reconhecido.");
		}

		clienteService.alterarCliente(cliente);

		System.out.println("Cliente alterado com sucesso!");
	}

	private static void alugarVeiculo(Scanner scanner, AluguelService aluguelService, ClienteService clienteService, VeiculoService veiculoService) {
		System.out.println("===== Aluguel de Veículo =====");
		System.out.print("ID do cliente: ");
		Long clienteId = scanner.nextLong();
		scanner.nextLine();

		Cliente cliente = clienteService.buscarClientePorId(clienteId);

		if (cliente == null) {
			System.out.println("Cliente não encontrado.");
			return;
		}
		System.out.print("Placa do veículo: ");
		String placa = scanner.nextLine();
		Veiculo veiculo = veiculoService.buscarVeiculoPorPlaca(placa);

		if (veiculo == null) {
			System.out.println("Veículo não encontrado.");
			return;
		}

		LocalDateTime dataHoraAluguel = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		Aluguel aluguel = aluguelService.alugarVeiculo(cliente, veiculo, dataHoraAluguel);

		System.out.println("Veículo alugado com sucesso!");
		System.out.println("ID do aluguel: " + aluguel.getId());
		System.out.println("Data/hora de aluguel: " + dataHoraAluguel.format(formatter));
	}
	private static void devolverVeiculo(Scanner scanner, AluguelService aluguelService) {
		System.out.println("===== Devolução de Veículo =====");
		System.out.print("ID do aluguel: ");
		long aluguelId = scanner.nextLong();
		scanner.nextLine();

		Aluguel aluguel = aluguelService.buscarAluguelPorId(aluguelId);

		if (aluguel == null) {
			System.out.println("Aluguel não encontrado.");
			return;
		}

		LocalDateTime dataHoraDevolucao = null;
		do {
			try {
				System.out.print("Data/hora de devolução (dd/MM/yyyy HH:mm): ");
				String dataHoraDevolucaoStr = scanner.nextLine();

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
				dataHoraDevolucao = LocalDateTime.parse(dataHoraDevolucaoStr, formatter);
			} catch (DateTimeParseException e) {
				System.out.println("Formato de data/hora inválido. Tente novamente.");
			}
		} while (dataHoraDevolucao == null);

		aluguelService.devolverVeiculo(aluguel, dataHoraDevolucao);

		System.out.println("Veículo devolvido com sucesso!");
		System.out.println("Valor total do aluguel: R$" + aluguel.getValorTotal());
	}
}




