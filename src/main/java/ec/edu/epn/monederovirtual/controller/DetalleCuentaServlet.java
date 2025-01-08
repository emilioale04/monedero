package ec.edu.epn.monederovirtual.controller;

import ec.edu.epn.monederovirtual.dao.CuentaDAO;
import ec.edu.epn.monederovirtual.dao.EgresoDAO;
import ec.edu.epn.monederovirtual.dao.IngresoDAO;
import ec.edu.epn.monederovirtual.dao.TransferenciaDAO;
import ec.edu.epn.monederovirtual.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@WebServlet("/detalleCuentaServlet")
public class DetalleCuentaServlet extends HttpServlet {
    private IngresoDAO ingresoDAO;
    private EgresoDAO egresoDAO;
    private TransferenciaDAO transferenciaDAO;
    private CuentaDAO cuentaDAO;

    @Override
    public void init() {
        ingresoDAO = new IngresoDAO();
        egresoDAO = new EgresoDAO();
        transferenciaDAO = new TransferenciaDAO();
        cuentaDAO = new CuentaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.ruteador(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.ruteador(req, resp);
    }

    private void ruteador(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        listar(req, resp);
    }

    private void listar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int cuentaId = Integer.parseInt(req.getParameter("cuentaId"));

        Cuenta cuenta = cuentaDAO.findById(cuentaId);
        List<Movimiento> movimientos = new ArrayList<>();
        movimientos.addAll(ingresoDAO.findByCuentaDestino(cuenta));
        movimientos.addAll(egresoDAO.findByCuentaOrigen(cuenta));
        movimientos.addAll(transferenciaDAO.findByCuentaAsociada(cuenta));

        movimientos.forEach(movimiento -> movimiento.formatearFecha("dd/MM/yyyy HH:mm"));


        req.setAttribute("movimientos", ordenarPorFecha(movimientos));
        req.setAttribute("cuenta", cuenta);
        req.getRequestDispatcher("/detalleCuentas.jsp").forward(req, resp);
    }

    private List<Movimiento> ordenarPorFecha(List<Movimiento> movimientos) {
        return movimientos.stream()
                .sorted(Comparator.comparing(Movimiento::getFecha))
                .toList();
    }
}
