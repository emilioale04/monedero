package ec.edu.epn.monederovirtual.dao;

import ec.edu.epn.monederovirtual.model.Cuenta;
import ec.edu.epn.monederovirtual.model.Ingreso;
import ec.edu.epn.monederovirtual.util.HibernateUtil;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IngresoDAOTest {

    private IngresoDAO ingresoDAO;
    private CuentaDAO cuentaDAO;
    private Session session;
    private Cuenta cuentaDestino;

    @BeforeEach
    void setUp() {
        ingresoDAO = new IngresoDAO();
        cuentaDAO = new CuentaDAO();  // Instanciar el DAO de Cuenta
        session = HibernateUtil.getSessionFactory().openSession();  // Obtener la sesión de Hibernate

        cuentaDestino = new Cuenta("Cuenta 1", 100.0);

        // Guardar la cuentaDestino en la base de datos usando el DAO de Cuenta
        session.beginTransaction();
        cuentaDAO.save(cuentaDestino);  // Guardar la cuenta usando el DAO
        session.getTransaction().commit();
    }

    @AfterEach
    void cleanUp() {
        List<Ingreso> ingresos = ingresoDAO.findByCuentaDestino(cuentaDestino);
        for (Ingreso ingreso : ingresos) {
            ingresoDAO.delete(ingreso);  // Eliminar cada ingreso relacionado con la cuentaDestino
        }

        // Limpiar la cuentaDestino usando el DAO de Cuenta
        session.beginTransaction();
        cuentaDAO.delete(cuentaDestino);  // Eliminar la cuenta usando el DAO
        session.getTransaction().commit();
    }

    @Test
    void testFindByCuentaDestino() {
        // Guardar algunos ingresos asociados a la cuentaDestino
        Ingreso ingreso1 = new Ingreso(cuentaDestino, 50.0, "Ingreso 1");
        Ingreso ingreso2 = new Ingreso(cuentaDestino, 100.0, "Ingreso 2");

        session.beginTransaction();
        ingresoDAO.save(ingreso1);
        ingresoDAO.save(ingreso2);
        session.getTransaction().commit();

        // Llamar al método findByCuentaDestino
        List<Ingreso> result = ingresoDAO.findByCuentaDestino(cuentaDestino);

        // Verificar que los resultados sean los esperados
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Ingreso 1", result.get(0).getConcepto());
        assertEquals("Ingreso 2", result.get(1).getConcepto());
    }

    @Test
    void testFindByCuentaDestinoNoResults() {
        // Usar una cuenta que no tiene ingresos asociados
        Cuenta otraCuenta = new Cuenta("Cuenta 2", 200.0);

        session.beginTransaction();
        cuentaDAO.save(otraCuenta);  // Guardar otra cuenta usando el DAO
        session.getTransaction().commit();

        // Llamar al método findByCuentaDestino con una cuenta sin ingresos
        List<Ingreso> result = ingresoDAO.findByCuentaDestino(otraCuenta);

        // Verificar que el resultado esté vacío
        assertNotNull(result);
        assertTrue(result.isEmpty());

        // Limpiar la cuenta creada para esta prueba usando el DAO
        session.beginTransaction();
        cuentaDAO.delete(otraCuenta);  // Eliminar la otra cuenta usando el DAO
        session.getTransaction().commit();
    }
}