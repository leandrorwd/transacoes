package exercicio;

public class App {
	public static void main(String[] args) {
		ContaDAO novaconta = new ContaDAO();
		novaconta.tranfer(30168, 94965);
		novaconta.listBD();
	}
}
