package ec.edu.epn.monederovirtual.controller;

import ec.edu.epn.monederovirtual.dao.*;
import ec.edu.epn.monederovirtual.model.Cuenta;
import ec.edu.epn.monederovirtual.model.Movimiento;
import ec.edu.epn.monederovirtual.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/homeServlet")
public class HomeServlet extends HttpServlet {
    private CuentaDAO cuentaDAO;
    private IngresoDAO ingresoDAO;
    private EgresoDAO egresoDAO;
    private TransferenciaDAO transferenciaDAO;

    @Override
    public void init() {
        cuentaDAO = new CuentaDAO();
        ingresoDAO = new IngresoDAO();
        egresoDAO = new EgresoDAO();
        transferenciaDAO = new TransferenciaDAO();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.ruteador(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.ruteador(req, resp);
    }

    private void ruteador(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String route = (req.getParameter("route") == null) ? "listar" : req.getParameter("route");

        switch (route) {
            case "listar":
                this.listar(req, resp);
                break;
            case "crearCuenta":
                this.crearCuenta(req, resp);
                break;
            case "realizarMovimiento":
                this.realizarMovimiento(req, resp);
                break;
            case "verMovimientos":
                this.verMovimientos(req, resp);
            default:
                break;
        }
    }

    private void listar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario usuario = (Usuario) req.getSession().getAttribute("usuario");

        List<Cuenta> cuentas = cuentaDAO.findByUsuario(usuario);

        List<Movimiento> movimientos = new ArrayList<>();
        movimientos.addAll(ingresoDAO.findByUsuario(usuario));
        movimientos.addAll(egresoDAO.findByUsuario(usuario));
        movimientos.addAll(transferenciaDAO.findByUsuario(usuario));

        movimientos.forEach(movimiento -> movimiento.formatearFecha("dd/MM/yyyy"));

        List<Movimiento> movimientosOrdenados = movimientos.stream()
                .sorted(Comparator.comparing(Movimiento::getFecha).reversed()) // Orden descendente por fecha
                .toList();

        List<Movimiento> top10Movimientos = movimientosOrdenados.size() > 10
                ? movimientosOrdenados.subList(0, 5)
                : movimientosOrdenados;

        req.setAttribute("cuentas", cuentas);
        req.setAttribute("movimientos", top10Movimientos);

        req.getRequestDispatcher("home.jsp").forward(req, resp);
    }

    private void crearCuenta(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("crearCuenta.jsp");
    }

    private void realizarMovimiento(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario usuario = (Usuario) req.getSession().getAttribute("usuario");
        int cuentaId = Integer.parseInt(req.getParameter("cuentaId"));

        Cuenta cuenta = cuentaDAO.findById(cuentaId);
        List<Cuenta> cuentas = cuentaDAO.findByUsuario(usuario);

        req.setAttribute("cuenta", cuenta);
        req.setAttribute("cuentas", cuentas);
        req.getRequestDispatcher("movimiento.jsp").forward(req, resp);
    }

    private void verMovimientos(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario usuario = (Usuario) req.getSession().getAttribute("usuario");

        List<Cuenta> cuentas = cuentaDAO.findByUsuario(usuario);

        List<Movimiento> movimientos = new ArrayList<>();
        movimientos.addAll(ingresoDAO.findByUsuario(usuario));
        movimientos.addAll(egresoDAO.findByUsuario(usuario));
        movimientos.addAll(transferenciaDAO.findByUsuario(usuario));

        movimientos.forEach(movimiento -> movimiento.formatearFecha("dd/MM/yyyy"));

        List<Movimiento> movimientosOrdenados = movimientos.stream()
                .sorted(Comparator.comparing(Movimiento::getFecha).reversed()) // Orden descendente por fecha
                .toList();

        req.setAttribute("cuentas", cuentas);
        req.setAttribute("movimientos", movimientos);

        req.getRequestDispatcher("verMovimientos.jsp").forward(req, resp);
    }
}
