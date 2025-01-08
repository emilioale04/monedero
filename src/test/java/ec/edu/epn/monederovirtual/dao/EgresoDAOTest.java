package ec.edu.epn.monederovirtual.dao;

import ec.edu.epn.monederovirtual.model.Cuenta;
import ec.edu.epn.monederovirtual.model.Egreso;
import ec.edu.epn.monederovirtual.util.HibernateUtil;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EgresoDAOTest {

    private EgresoDAO egresoDAO;
    private CuentaDAO cuentaDAO;
    private Session session;
    private Cuenta cuentaOrigen;

    @BeforeEach
    void setUp() {
        egresoDAO = new EgresoDAO();
        cuentaDAO = new CuentaDAO(); // Instanciar el DAO de Cuenta
        session = HibernateUtil.getSessionFactory().openSession(); // Obtener la sesión de Hibernate

        cuentaOrigen = new Cuenta("Cuenta Origen", 200.0);

        // Guardar la cuentaOrigen en la base de datos usando el DAO de Cuenta
        session.beginTransaction();
        cuentaDAO.save(cuentaOrigen); // Guardar la cuenta usando el DAO
        session.getTransaction().commit();
    }

    @AfterEach
    void cleanUp() {
        List<Egreso> egresos = egresoDAO.findByCuentaOrigen(cuentaOrigen);
        for (Egreso egreso : egresos) {
            egresoDAO.delete(egreso); // Eliminar cada egreso relacionado con la cuentaOrigen
        }

        // Limpiar la cuentaOrigen usando el DAO de Cuenta
        session.beginTransaction();
        cuentaDAO.delete(cuentaOrigen); // Eliminar la cuenta usando el DAO
        session.getTransaction().commit();
    }

    @Test
    void testFindByCuentaOrigen() {
        // Guardar algunos egresos asociados a la cuentaOrigen
        Egreso egreso1 = new Egreso(cuentaOrigen, 50.0, "Egreso 1");
        Egreso egreso2 = new Egreso(cuentaOrigen, 75.0, "Egreso 2");

        session.beginTransaction();
        egresoDAO.save(egreso1);
        egresoDAO.save(egreso2);
        session.getTransaction().commit();

        // Llamar al método findByCuentaOrigen
        List<Egreso> result = egresoDAO.findByCuentaOrigen(cuentaOrigen);

        // Verificar que los resultados sean los esperados
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Egreso 1", result.get(0).getConcepto());
        assertEquals("Egreso 2", result.get(1).getConcepto());
    }

    @Test
    void testFindByCuentaOrigenNoResults() {
        // Usar una cuenta que no tiene egresos asociados
        Cuenta otraCuenta = new Cuenta("Cuenta Sin Egresos", 300.0);

        session.beginTransaction();
        cuentaDAO.save(otraCuenta); // Guardar otra cuenta usando el DAO
        session.getTransaction().commit();

        // Llamar al método findByCuentaOrigen con una cuenta sin egresos
        List<Egreso> result = egresoDAO.findByCuentaOrigen(otraCuenta);

        // Verificar que el resultado esté vacío
        assertNotNull(result);
        assertTrue(result.isEmpty());

        // Limpiar la cuenta creada para esta prueba usando el DAO
        session.beginTransaction();
        cuentaDAO.delete(otraCuenta); // Eliminar la otra cuenta usando el DAO
        session.getTransaction().commit();
    }
}
