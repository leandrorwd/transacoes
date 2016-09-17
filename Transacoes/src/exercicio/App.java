package exercicio;

import java.text.DecimalFormat;

public class App {
	public static void main(String[] args) {
		
		int conta1 = 30168;
		int conta2 = 94965;
		DecimalFormat df = new DecimalFormat("#,###.00");
		ContaDAO novaconta = new ContaDAO();

		System.out.println("Antes da transferência:");
		System.out.println("Saldo da conta " + conta1 + ": R$ " + df.format(novaconta.saldoConta(conta1)));
		System.out.println("Saldo da conta " + conta2 + ": R$ " + df.format(novaconta.saldoConta(94965)) + "\n");

		novaconta.tranfer(conta1, conta2);
		System.out.println("\nSaldo da conta " + conta1 + ": R$ " + df.format(novaconta.saldoConta(conta1)));
		System.out.println("Saldo da conta " + conta2 + ": R$ " + df.format(novaconta.saldoConta(94965)));
		System.out.println("\nSaldo do Banco: R$ " + df.format(novaconta.saldoBanco()));
	}
}
