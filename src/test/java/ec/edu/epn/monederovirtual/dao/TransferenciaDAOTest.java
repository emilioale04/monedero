package ec.edu.epn.monederovirtual.dao;

import ec.edu.epn.monederovirtual.model.Cuenta;
import ec.edu.epn.monederovirtual.model.Transferencia;
import ec.edu.epn.monederovirtual.util.HibernateUtil;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransferenciaDAOTest {

    private TransferenciaDAO transferenciaDAO;
    private CuentaDAO cuentaDAO;
    private Session session;
    private Cuenta cuentaOrigen;
    private Cuenta cuentaDestino;

    @BeforeEach
    void setUp() {
        transferenciaDAO = new TransferenciaDAO();
        cuentaDAO = new CuentaDAO();
        session = HibernateUtil.getSessionFactory().openSession();

        cuentaOrigen = new Cuenta("Cuenta Origen", 1000.0);
        cuentaDestino = new Cuenta("Cuenta Destino", 500.0);

        session.beginTransaction();
        cuentaDAO.save(cuentaOrigen);
        cuentaDAO.save(cuentaDestino);
        session.getTransaction().commit();
    }

    @AfterEach
    void cleanUp() {
        List<Transferencia> transferenciasOrigen = transferenciaDAO.findAll();        for (Transferencia transferencia : transferenciasOrigen) {
            transferenciaDAO.delete(transferencia);
        }

        session.beginTransaction();
        cuentaDAO.delete(cuentaOrigen);
        cuentaDAO.delete(cuentaDestino);
        session.getTransaction().commit();
    }

    @Test
    void testFindByCuentaOrigen() {
        // Guardar algunas transferencias asociadas a la cuentaOrigen
        Transferencia transferencia1 = new Transferencia(cuentaOrigen, cuentaDestino, 100.0, "Transferencia 1");
        Transferencia transferencia2 = new Transferencia(cuentaOrigen, cuentaDestino, 200.0, "Transferencia 2");

        session.beginTransaction();
        transferenciaDAO.save(transferencia1);
        transferenciaDAO.save(transferencia2);
        session.getTransaction().commit();

        // Llamar al método findByCuentaOrigen
        List<Transferencia> result = transferenciaDAO.findByCuentaOrigen(cuentaOrigen);

        // Verificar que los resultados sean los esperados
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Transferencia 1", result.get(0).getConcepto());
        assertEquals("Transferencia 2", result.get(1).getConcepto());
    }

    @Test
    void testFindByCuentaDestino() {
        // Guardar algunas transferencias asociadas a la cuentaDestino
        Transferencia transferencia1 = new Transferencia(cuentaOrigen, cuentaDestino, 150.0, "Transferencia 3");
        Transferencia transferencia2 = new Transferencia(cuentaOrigen, cuentaDestino, 250.0, "Transferencia 4");

        session.beginTransaction();
        transferenciaDAO.save(transferencia1);
        transferenciaDAO.save(transferencia2);
        session.getTransaction().commit();

        // Llamar al método findByCuentaDestino
        List<Transferencia> result = transferenciaDAO.findByCuentaDestino(cuentaDestino);

        // Verificar que los resultados sean los esperados
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Transferencia 3", result.get(0).getConcepto());
        assertEquals("Transferencia 4", result.get(1).getConcepto());
    }

    @Test
    void testFindByCuentaAsociada() {
        // Guardar algunas transferencias asociadas a las cuentas
        Transferencia transferencia1 = new Transferencia(cuentaOrigen, cuentaDestino, 50.0, "Transferencia 5");
        Transferencia transferencia2 = new Transferencia(cuentaDestino, cuentaOrigen, 75.0, "Transferencia 6");

        session.beginTransaction();
        transferenciaDAO.save(transferencia1);
        transferenciaDAO.save(transferencia2);
        session.getTransaction().commit();

        // Llamar al método findByCuentaAsociada
        List<Transferencia> result = transferenciaDAO.findByCuentaAsociada(cuentaOrigen);

        // Verificar que los resultados sean los esperados
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(t -> t.getConcepto().equals("Transferencia 5")));
        assertTrue(result.stream().anyMatch(t -> t.getConcepto().equals("Transferencia 6")));
    }

    @Test
    void testFindByCuentaOrigenNoResults() {
        // Usar una cuenta que no tiene transferencias asociadas
        Cuenta cuentaNueva = new Cuenta("Cuenta Nueva", 300.0);

        session.beginTransaction();
        cuentaDAO.save(cuentaNueva);
        session.getTransaction().commit();

        // Llamar al método findByCuentaOrigen
        List<Transferencia> result = transferenciaDAO.findByCuentaOrigen(cuentaNueva);

        // Verificar que el resultado esté vacío
        assertNotNull(result);
        assertTrue(result.isEmpty());

        session.beginTransaction();
        cuentaDAO.delete(cuentaNueva);
        session.getTransaction().commit();
    }
}
