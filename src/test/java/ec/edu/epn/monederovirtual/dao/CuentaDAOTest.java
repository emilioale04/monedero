package ec.edu.epn.monederovirtual.dao;

import ec.edu.epn.monederovirtual.model.Cuenta;
import ec.edu.epn.monederovirtual.model.exceptions.BalanceInsuficiente;
import ec.edu.epn.monederovirtual.util.HibernateUtil;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CuentaDAOTest {

    private CuentaDAO cuentaDAO;

    @BeforeAll
    void setUp() {
        cuentaDAO = new CuentaDAO();
    }

    @AfterAll
    void tearDown() {

    }

    @AfterEach
    void cleanUp() {
        // Limpieza después de cada prueba
        List<Cuenta> cuentas = cuentaDAO.findAll();
        for (Cuenta cuenta : cuentas) {
            cuentaDAO.delete(cuenta);  // Elimina cada cuenta
        }
    }

    @Test
    void testSave() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNombre("Cuenta Test");

        assertDoesNotThrow(() -> cuentaDAO.save(cuenta));
    }

    @Test
    void testFindById() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNombre("Cuenta FindById");
        cuentaDAO.save(cuenta);

        Cuenta fetchedCuenta = cuentaDAO.findById(cuenta.getId());
        assertNotNull(fetchedCuenta, "La cuenta no debe ser nula.");
        assertEquals("Cuenta FindById", fetchedCuenta.getNombre(), "Los nombres deben coincidir.");
    }

    @Test
    void testFindAll() {
        Cuenta cuenta1 = new Cuenta();
        cuenta1.setNombre("Cuenta 1");
        cuentaDAO.save(cuenta1);

        Cuenta cuenta2 = new Cuenta();
        cuenta2.setNombre("Cuenta 2");
        cuentaDAO.save(cuenta2);

        List<Cuenta> cuentas = cuentaDAO.findAll();
        assertTrue(cuentas.size() >= 2, "Debe haber al menos dos cuentas en la base de datos.");
    }

    @Test
    void testUpdate() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNombre("Cuenta Original");
        cuentaDAO.save(cuenta);

        cuenta.setNombre("Cuenta Actualizada");
        assertDoesNotThrow(() -> cuentaDAO.update(cuenta));

        Cuenta updatedCuenta = cuentaDAO.findById(cuenta.getId());
        assertEquals("Cuenta Actualizada", updatedCuenta.getNombre(), "El nombre debe actualizarse correctamente.");
    }

    @Test
    void testDelete() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNombre("Cuenta a Eliminar");
        cuentaDAO.save(cuenta);

        assertDoesNotThrow(() -> cuentaDAO.delete(cuenta));

        Cuenta deletedCuenta = cuentaDAO.findById(cuenta.getId());
        assertNull(deletedCuenta, "La cuenta debe ser eliminada correctamente.");
    }

    @Test
    void testRetirarDinero() {
        Cuenta cuenta = new Cuenta("Cuenta Retiro", 500.0);
        cuentaDAO.save(cuenta);

        assertDoesNotThrow(() -> cuenta.retirarDinero(200.0));
        assertEquals(300.0, cuenta.getBalance(), 0.01, "El balance debe ser 300 después del retiro.");
    }

    @Test
    void testDepositarDinero() {
        Cuenta cuenta = new Cuenta("Cuenta Depósito", 100.0);
        cuentaDAO.save(cuenta);

        assertDoesNotThrow(() -> cuenta.depositarDinero(150.0));
        assertEquals(250.0, cuenta.getBalance(), 0.01, "El balance debe ser 250 después del depósito.");
    }

    @Test
    void testValidarRetiroConBalanceSuficiente() {
        Cuenta cuenta = new Cuenta("Cuenta Validar Retiro", 300.0);
        cuentaDAO.save(cuenta);

        assertDoesNotThrow(() -> cuenta.validarRetiro(200.0));
    }

    @Test
    void testValidarRetiroConBalanceInsuficiente() {
        Cuenta cuenta = new Cuenta("Cuenta Validar Insuficiente", 100.0);
        cuentaDAO.save(cuenta);

        Exception exception = assertThrows(BalanceInsuficiente.class, () -> cuenta.validarRetiro(200.0));
        assertEquals("El balance de la cuenta es insuficiente.", exception.getMessage(), "El mensaje de excepción debe coincidir.");
    }
}
