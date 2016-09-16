package exercicio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ContaDAO {

	public static String DB_CONN_STRING_CREATE = "jdbc:derby:banco";
	public static String DB_NAME = "banco";
	public static String USER_NAME = "usuario";
	public static String PASSWORD = "senha";

	public void tranfer(int contaOrigem, int contaDestino) {

		try (Connection conexao = getConexaoViaDriverManager()) {
			conexao.setAutoCommit(false);

			String sql1 = "update contas set valor = valor + 50 where conta = (?)";
			PreparedStatement comando1 = conexao.prepareStatement(sql1);
			comando1.setInt(1, contaOrigem);

			String sql2 = "update contas set valor = valor - 50 where conta = (?)";
			PreparedStatement comando2 = conexao.prepareStatement(sql2);
			comando2.setInt(1, contaDestino);

			if (comando1.executeUpdate() > 0 && comando2.executeUpdate() > 0) {
				conexao.commit();
				System.out.println("Inserção efetuada com sucesso");
			} else {
				System.out.println("Falha na inserção");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void listBD() {
		try (Connection conexao = getConexaoViaDriverManager()) {
			String sql3 = "select * from contas";
			try (PreparedStatement comando = conexao.prepareStatement(sql3)) {
				try (ResultSet resultados = comando.executeQuery()) {
					while (resultados.next()) {
						System.out.println(
								"Conta: " + resultados.getInt("conta") + " Valor: " + resultados.getDouble("valor"));
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public Connection getConexaoViaDriverManager() throws Exception {
		return DriverManager.getConnection(DB_CONN_STRING_CREATE, USER_NAME, PASSWORD);
	}
}
