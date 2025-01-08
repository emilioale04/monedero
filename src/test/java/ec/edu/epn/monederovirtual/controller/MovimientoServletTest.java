package ec.edu.epn.monederovirtual.controller;

import ec.edu.epn.monederovirtual.dao.CuentaDAO;
import ec.edu.epn.monederovirtual.dao.EgresoDAO;
import ec.edu.epn.monederovirtual.dao.IngresoDAO;
import ec.edu.epn.monederovirtual.dao.TransferenciaDAO;
import ec.edu.epn.monederovirtual.model.Cuenta;
import ec.edu.epn.monederovirtual.model.Egreso;
import ec.edu.epn.monederovirtual.model.Ingreso;
import ec.edu.epn.monederovirtual.model.Transferencia;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class MovimientoServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private CuentaDAO cuentaDAO;

    @Mock
    private IngresoDAO ingresoDAO;

    @Mock
    private EgresoDAO egresoDAO;

    @Mock
    private TransferenciaDAO transferenciaDAO;

    @InjectMocks
    private MovimientoServlet movimientoServlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenRouteIngresar_whenDoPost_thenIngresoIsSavedAndRedirected() throws ServletException, IOException {
        // Configuración de mocks
        Cuenta mockCuenta = new Cuenta();
        when(request.getParameter("route")).thenReturn("ingresar");
        when(request.getParameter("cuentaId")).thenReturn("1");
        when(request.getParameter("valor")).thenReturn("100.0");
        when(request.getParameter("concepto")).thenReturn("Depósito inicial");
        when(cuentaDAO.findById(1)).thenReturn(mockCuenta);

        doNothing().when(ingresoDAO).save(any(Ingreso.class));
        doNothing().when(cuentaDAO).update(mockCuenta);
        when(request.getSession()).thenReturn(session);

        // Ejecutar método
        movimientoServlet.doPost(request, response);

        // Verificaciones
        verify(ingresoDAO).save(any(Ingreso.class));
        verify(cuentaDAO).update(mockCuenta);
        verify(session).setAttribute("mensaje", "Ingreso registrado exitosamente.");
        verify(response).sendRedirect("cuentaServlet");
    }

    @Test
    void givenRouteEgresar_whenDoPost_thenEgresoIsSavedAndRedirected() throws ServletException, IOException {
        // Configuración de mocks
        Cuenta mockCuenta = new Cuenta();
        mockCuenta.setBalance(100.0);
        when(request.getParameter("route")).thenReturn("egresar");
        when(request.getParameter("cuentaId")).thenReturn("1");
        when(request.getParameter("valor")).thenReturn("50.0");
        when(request.getParameter("concepto")).thenReturn("Retiro de efectivo");
        when(cuentaDAO.findById(1)).thenReturn(mockCuenta);

        doNothing().when(egresoDAO).save(any(Egreso.class));
        doNothing().when(cuentaDAO).update(mockCuenta);
        when(request.getSession()).thenReturn(session);

        movimientoServlet.doPost(request, response);

        // Verificaciones
        verify(egresoDAO).save(any(Egreso.class));
        verify(cuentaDAO).update(mockCuenta);
        verify(session).setAttribute("mensaje", "Egreso registrado exitosamente.");
        verify(response).sendRedirect("cuentaServlet");
    }

    @Test
    void givenRouteTransferir_whenDoPost_thenTransferenciaIsSavedAndRedirected() throws ServletException, IOException {
        // Configuración de mocks
        Cuenta mockCuentaOrigen = new Cuenta();
        Cuenta mockCuentaDestino = new Cuenta();
        mockCuentaOrigen.setBalance(200.0);
        when(request.getParameter("route")).thenReturn("transferir");
        when(request.getParameter("cuentaOrigenId")).thenReturn("1");
        when(request.getParameter("cuentaDestinoId")).thenReturn("2");
        when(request.getParameter("valor")).thenReturn("200.0");
        when(request.getParameter("concepto")).thenReturn("Pago de servicios");
        when(cuentaDAO.findById(1)).thenReturn(mockCuentaOrigen);
        when(cuentaDAO.findById(2)).thenReturn(mockCuentaDestino);

        doNothing().when(transferenciaDAO).save(any(Transferencia.class));
        doNothing().when(cuentaDAO).update(mockCuentaOrigen);
        doNothing().when(cuentaDAO).update(mockCuentaDestino);
        when(request.getSession()).thenReturn(session);

        // Ejecutar método
        movimientoServlet.doPost(request, response);

        // Verificaciones
        verify(transferenciaDAO).save(any(Transferencia.class));
        verify(cuentaDAO).update(mockCuentaOrigen);
        verify(cuentaDAO).update(mockCuentaDestino);
        verify(session).setAttribute("mensaje", "Transferencia registrada exitosamente.");
        verify(response).sendRedirect("cuentaServlet");
    }
}
