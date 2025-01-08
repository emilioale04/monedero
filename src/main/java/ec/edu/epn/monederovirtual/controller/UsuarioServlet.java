package ec.edu.epn.monederovirtual.controller;

import ec.edu.epn.monederovirtual.dao.*;
import ec.edu.epn.monederovirtual.model.*;
import ec.edu.epn.monederovirtual.util.HibernateUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/usuarioServlet")
public class UsuarioServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() {
        usuarioDAO = new UsuarioDAO();
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
        String route = (req.getParameter("route") == null) ? "" : req.getParameter("route");

        if (route.equals("registrar")) {
            this.registrar(req, resp);
        } else {
            resp.sendRedirect("index.jsp");
        }
    }

    private void registrar(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String nombre = req.getParameter("nombre").trim();
        String usuario = req.getParameter("usuario").trim();
        String claveInput = req.getParameter("clave");

        Usuario u = new Usuario();
        u.setNombre(nombre);
        u.setUsuario(usuario);
        u.setClave(claveInput);

        usuarioDAO.save(u);
        resp.sendRedirect("index.jsp");
    }
}