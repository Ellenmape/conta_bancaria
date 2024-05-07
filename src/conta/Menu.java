package conta;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import conta.controller.ContaController;
import conta.model.Conta;
import conta.model.ContaCorrente;
import conta.model.ContaPoupanca;
import conta.util.Cores;

public class Menu {
	public static void main(String[] args) {
		ContaController contas = new ContaController();
		
		Scanner scanner = new Scanner(System.in);

		int opcao, numero, agencia, tipo, aniversario, numeroDestino;
		String titular;
		float saldo, limite, valor;
		
		System.out.println("\nCriar Conta");

		// Teste da Classe Conta Poupança
		ContaCorrente cc1 = new ContaCorrente (contas.gerarNumero(), 123, 1, "João da Silva", 1000f, 100.0f);
		contas.cadastrar(cc1);
		ContaCorrente cc2 = new ContaCorrente (contas.gerarNumero(), 124, 1, "Maria da Silva", 2000f, 100.0f);
		contas.cadastrar(cc2);
		ContaPoupanca cp1 = new ContaPoupanca (contas.gerarNumero(), 125, 2, "Mariana dos Santos", 4000f, 12);
		contas.cadastrar(cp1);
		ContaPoupanca cp2 = new ContaPoupanca (contas.gerarNumero(), 125, 2, "Juliana Ramos", 8000f, 15);
		contas.cadastrar(cp2);
		
		contas.listarTodas();

		while (true) {

			System.out.println(Cores.TEXT_GREEN + Cores.ANSI_BLACK_BACKGROUND
					+ "*****************************************************");
			System.out.println("                                                     ");
			System.out.println("                BANCO DO BRAZIL COM Z                ");
			System.out.println("                                                     ");
			System.out.println("*****************************************************");
			System.out.println("                                                     ");
			System.out.println("            1 - Criar Conta                          ");
			System.out.println("            2 - Listar todas as Contas               ");
			System.out.println("            3 - Buscar Conta por Numero              ");
			System.out.println("            4 - Atualizar Dados da Conta             ");
			System.out.println("            5 - Apagar Conta                         ");
			System.out.println("            6 - Sacar                                ");
			System.out.println("            7 - Depositar                            ");
			System.out.println("            8 - Transferir valores entre Contas      ");
			System.out.println("            9 - Sair                                 ");
			System.out.println("                                                     ");
			System.out.println("*****************************************************");
			System.out.println("Entre com a opção desejada:                          ");
			System.out.println("                                                     " + Cores.TEXT_RESET);

			try {
				opcao = scanner.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("\nDigite valores inteiros!");
				scanner.nextLine();
				opcao = 0;
			}

			if (opcao == 9) {
				System.out.println(Cores.TEXT_WHITE_BOLD + "\nBanco do Brazil com Z - O seu Futuro começa aqui!");
				sobre();
				scanner.close();
				System.exit(0);
			}

			switch (opcao) {
			case 1:
				System.out.println(Cores.TEXT_WHITE + "Criar Conta\n\n");
				
				System.out.println("Digite o número da agência: ");
					agencia = scanner.nextInt();
					System.out.println("Digite o nome do titular: ");
					scanner.skip("\\R?"); //para permitir que o comando nextLine() leia palavras compostas. Lembre-se que o titular possui pelo menos nome e um sobrenome.
					titular = scanner.nextLine();
				
					do {
						System.out.println("Digite o tipo de conta: 1-CC ou 2-CP");
						tipo = scanner.nextInt();
					} while(tipo < 1 && tipo > 2);
					
					System.out.println("Digite o saldo da conta (R$): ");
					saldo = scanner.nextFloat();
					
				switch(tipo) {
				case 1 -> {
					System.out.println("Digite o Limite de Crédito (R$): ");
					limite = scanner.nextFloat();
					contas.cadastrar(new ContaCorrente(contas.gerarNumero(), agencia, tipo, titular, saldo, limite));
				}
				case 2 -> {
					System.out.println("Digite o dia do Aniversario da Conta: ");
					aniversario = scanner.nextInt();
					contas.cadastrar(new ContaPoupanca(contas.gerarNumero(), agencia, tipo, titular, saldo, aniversario));
				}
				}

				keyPress();
				break;
			case 2:
				System.out.println("\nListar todas as Contas");
				contas.listarTodas();
				keyPress();
				break;
			case 3:
				System.out.println(Cores.TEXT_WHITE + "Consultar dados da Conta - por número\n\n");
				System.out.println("Digite o número da conta: ");
				numero = scanner.nextInt();
				
				contas.procurarPorNumero(numero);

				keyPress();
				break;
			case 4:
				System.out.println(Cores.TEXT_WHITE + "Atualizar dados da Conta\n\n");
				System.out.println("Digite o número da conta: ");
				numero = scanner.nextInt();
				
				var buscaConta = contas.buscarNaCollection(numero);

				if (buscaConta != null) {
					
					System.out.println("Digite o Numero da Agência: ");
					agencia = scanner.nextInt();
					System.out.println("Digite o Nome do Titular: ");
					scanner.skip("\\R?");
					titular = scanner.nextLine();
						
					System.out.println("Digite o Saldo da Conta (R$): ");
					saldo = scanner.nextFloat();
					
					tipo = buscaConta.getTipo();
					
					switch(tipo) {
						case 1 -> {
							System.out.println("Digite o Limite de Crédito (R$): ");
							limite = scanner.nextFloat();
							contas.atualizar(new ContaCorrente(numero, agencia, tipo, titular, saldo, limite));
						}
						case 2 -> {
							System.out.println("Digite o dia do Aniversario da Conta: ");
							aniversario = scanner.nextInt();
							contas.atualizar(new ContaPoupanca(numero, agencia, tipo, titular, saldo, aniversario));
						}
						default ->{
							System.out.println("Tipo de conta inválido!");
						}
					}
					
				}else
					System.out.println("\nConta não encontrada!");

                keyPress();
				break;
			case 5:
				System.out.println(Cores.TEXT_WHITE + "Apagar a Conta\n\n");
				System.out.println("Digite o número da conta: ");
				numero = scanner.nextInt();

				keyPress();
				break;
			case 6:
				System.out.println(Cores.TEXT_WHITE + "Saque\n\n");
				System.out.println("Digite o número da conta: ");
				numero = scanner.nextInt();
				
				do {
					System.out.println("Digite o valor de saque (R$): ");
					valor = scanner.nextFloat();
				}while(valor <= 0);

				keyPress();
				break;
			case 7:
				System.out.println(Cores.TEXT_WHITE + "Depósito\n\n");
				System.out.println("\nDepositar");
				System.out.println("Digite o numero da conta: ");
				numero = scanner.nextInt();
				
				do {
					System.out.println("Digite o valor do Deposito (R$): ");
					valor = scanner.nextFloat();
				} while (valor <= 0);	
				
				contas.depositar(numero, valor);				

				keyPress();
				break;
			case 8:
				System.out.println(Cores.TEXT_WHITE + "Transferência entre Contas\n\n");
				System.out.println("Digite o número da conta de origem: ");
				numero = scanner.nextInt();
				System.out.println("Digite o número da conta de destino: ");
				numeroDestino = scanner.nextInt();
				
				do {
					System.out.println("Digite o valor da transferência (R$): ");
						valor = scanner.nextInt();
						}while(valor <= 0);
				contas.transferir(numero, numeroDestino, valor);

				keyPress();
				break;
			default:
				System.out.println(Cores.TEXT_RED_BOLD + "\nOpção Inválida!\n" + Cores.TEXT_RESET);

				keyPress();
				break;
			}
		}
	}

	public static void sobre() {
		System.out.println("\n*********************************************************");
		System.out.println("Projeto Desenvolvido por: Ellen Silva");
		System.out.println("Generation Brasil - ellen.silva@genstudents.org");
		System.out.println("github.com/ellenmape");
		System.out.println("*********************************************************");
	}

	public static void keyPress() {

		try {

			System.out.println(Cores.TEXT_RESET + "\nPressione Enter para Continuar...");
			System.in.read();

		} catch (IOException e) {

			System.out.println("Você pressionou uma tecla diferente de enter!");

		}

	}
}