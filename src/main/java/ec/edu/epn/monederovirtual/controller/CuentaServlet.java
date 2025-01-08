package ec.edu.epn.monederovirtual.controller;

import ec.edu.epn.monederovirtual.dao.CuentaDAO;
import ec.edu.epn.monederovirtual.model.Cuenta;
import ec.edu.epn.monederovirtual.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/cuentaServlet")
public class CuentaServlet extends HttpServlet {
    private CuentaDAO cuentaDAO;

    @Override
    public void init() {
        cuentaDAO = new CuentaDAO();
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
                //this.listar(req, resp);
                break;
            case "crear":
                this.crear(req, resp);
                break;
            default:
                break;
        }
    }

    private void crear(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nombre = req.getParameter("nombre").trim();
        double balance = Double.parseDouble(req.getParameter("balance").trim());
        Usuario usuario = (Usuario) req.getSession().getAttribute("usuario");

        Cuenta cuenta = new Cuenta(nombre, balance);
        cuenta.setUsuario(usuario);

        cuentaDAO.save(cuenta);

        resp.sendRedirect("homeServlet?route=listar");
    }
}
