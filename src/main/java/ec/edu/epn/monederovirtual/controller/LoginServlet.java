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

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

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

        switch (route) {
            case "login":
                this.login(req, resp);
                break;
            case "logout":
                this.logout(req, resp);
                break;
            default:
                resp.sendRedirect("index.jsp");
        }
    }

    private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().invalidate();
        resp.sendRedirect("index.jsp");
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String usuarioInput = req.getParameter("usuario").trim();
        String claveInput = req.getParameter("clave");

        Usuario usuario = usuarioDAO.findByUsuario(usuarioInput);
        if(usuario == null) {
            resp.sendRedirect("index.jsp?error=true");
        } else if(usuario.getClave().equals(claveInput)){
            req.getSession().setAttribute("usuario", usuario);
            resp.sendRedirect("homeServlet?route=listar");
        }
    }
}