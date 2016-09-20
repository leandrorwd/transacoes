import exercicio.Conta;
import exercicio.ContaDAO;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Lucas on 18/09/2016.
 */
public class testeConta {

    private Conta conta;
    private ContaDAO contaDAO = new ContaDAO();
    private static final double DELTA = 1e-15;

    @Before
    public void setUp() {
        conta = new Conta(123, 5000.00);
    }

    @Test
    public void testGetNumeroConta() {
        assertEquals(123, conta.getNumero());
    }

    @Test
    public void testGetSaldoConta() {
        assertEquals(5000.00, conta.getSaldo(), DELTA);
    }

    @Test
    public void testToStringConta() {
        assertEquals("Saldo da conta 123: R$ 5.000", conta.toString());
    }

    @Test
    public void testTransferencia() {
        ContaDAO.createBd();
        int conta1 = 123;
        int conta2 = 456;
        contaDAO.inserirConta(new Conta(conta1, 5000.00));
        contaDAO.inserirConta(new Conta(conta2, 5000.00));
        // antes da transferencia
        assertEquals(10000.00, contaDAO.saldoBanco(), DELTA);
        assertEquals(5000.00, contaDAO.saldoConta(conta1), DELTA);
        assertEquals(5000.00, contaDAO.saldoConta(conta2), DELTA);
        contaDAO.tranfer(conta1, conta2);
        // depois da transferencia
        assertEquals(10000.00, contaDAO.saldoBanco(), DELTA);
        assertEquals(5050.00, contaDAO.saldoConta(conta1), DELTA);
        assertEquals(4950.00, contaDAO.saldoConta(conta2), DELTA);
    }
}
