package exercicio;

import org.apache.derby.jdbc.EmbeddedDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class ContaDAO {

	public static String DB_CONN_STRING_CREATE = "jdbc:derby:banco";
	public static String DB_NAME = "banco";
	public static String USER_NAME = "usuario";
	public static String PASSWORD = "senha";
	private static DataSource dataSource;

	private static Connection getConexaoViaDataSource() throws Exception {
		if (dataSource == null) {
			EmbeddedDataSource ds = new EmbeddedDataSource();
			ds.setDatabaseName(DB_NAME);
			ds.setCreateDatabase("create");
			ds.setUser(USER_NAME);
			ds.setPassword(PASSWORD);
			dataSource = ds;
		}
		return dataSource.getConnection();
	}
	public static void createBd(){
		try (Connection conexao = getConexaoViaDataSource()) {
			String sql = "create table contas (MAP_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), conta integer, valor float(10))";
			try (Statement comando = conexao.createStatement()) {
				comando.executeUpdate(sql);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void inserirConta(Conta conta) {
		String sql = "insert into contas(conta, valor) values(?,?)";
		int result = 0;
		try (Connection connection = getConexaoViaDataSource()) {
			try (PreparedStatement command = connection.prepareStatement(sql)) {
				command.setInt(1, conta.getNumero());
				command.setDouble(2, conta.getSaldo());
				result = command.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(result == 0) {
			throw new IllegalArgumentException("Falha ao inserir conta");
		}
	}


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
				System.out.println("Transfer�ncia de R$ 50,00 efetuada com sucesso");
				conexao.commit();
			} else {
				throw new IllegalArgumentException("Falha na transferencia");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public double saldoConta(int conta) {
		
		double saldo = 0;
		try (Connection conexao = getConexaoViaDriverManager()) {
			String sql3 = "select * from contas where conta = (?)";
			try (PreparedStatement comando = conexao.prepareStatement(sql3)) {
				comando.setInt(1, conta);
				try (ResultSet resultados = comando.executeQuery()) {
					if (resultados.next()) {
						saldo = resultados.getDouble("valor");
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return saldo;
		
	}

	public double saldoBanco() {
		double saldo = 0;
		try (Connection conexao = getConexaoViaDriverManager()) {
			String sql3 = "select * from contas";
			try (PreparedStatement comando = conexao.prepareStatement(sql3)) {
				try (ResultSet resultados = comando.executeQuery()) {
					while (resultados.next()) {
						saldo += resultados.getDouble("valor");
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return saldo;
	}

	public Connection getConexaoViaDriverManager() throws Exception {
		return DriverManager.getConnection(DB_CONN_STRING_CREATE, USER_NAME, PASSWORD);
	}
}
