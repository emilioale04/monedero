package ec.edu.epn.monederovirtual.controller;

import ec.edu.epn.monederovirtual.dao.CuentaDAO;
import ec.edu.epn.monederovirtual.dao.EgresoDAO;
import ec.edu.epn.monederovirtual.dao.IngresoDAO;
import ec.edu.epn.monederovirtual.dao.TransferenciaDAO;
import ec.edu.epn.monederovirtual.model.Cuenta;
import ec.edu.epn.monederovirtual.model.Egreso;
import ec.edu.epn.monederovirtual.model.Ingreso;
import ec.edu.epn.monederovirtual.model.Transferencia;
import ec.edu.epn.monederovirtual.util.HibernateUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/movimientoServlet")
public class MovimientoServlet extends HttpServlet {
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
        String route = (req.getParameter("route") == null) ? "" : req.getParameter("route");

        switch (route) {
            case "ingresar":
                this.ingresar(req, resp);
                break;
            case "egresar":
                this.egresar(req, resp);
                break;
            case "transferir":
                this.transferir(req, resp);
                break;
            default:
                break;
        }
    }

    private void ingresar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int cuentaId = Integer.parseInt(req.getParameter("cuentaId").trim());
        double valor = Double.parseDouble(req.getParameter("valor").trim());
        String concepto = req.getParameter("concepto").trim();

        Cuenta cuentaDestino = cuentaDAO.findById(cuentaId);
        Ingreso ingreso = new Ingreso(cuentaDestino, valor, concepto);
        ingreso.setFecha(LocalDateTime.now());

        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            ingreso.realizarTransaccion();
            ingresoDAO.save(ingreso);
            cuentaDAO.update(cuentaDestino);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        req.setAttribute("cuentaId", cuentaId);
        req.getRequestDispatcher("homeServlet?route=realizarMovimiento").forward(req, resp);
    }

    private void egresar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int cuentaId = Integer.parseInt(req.getParameter("cuentaId").trim());
        double valor = Double.parseDouble(req.getParameter("valor").trim());
        String concepto = req.getParameter("concepto").trim();

        Cuenta cuentaOrigen = cuentaDAO.findById(cuentaId);
        Egreso egreso = new Egreso(cuentaOrigen, valor, concepto);
        egreso.setFecha(LocalDateTime.now());

        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            egreso.realizarTransaccion();
            egresoDAO.save(egreso);
            cuentaDAO.update(cuentaOrigen);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        req.setAttribute("cuentaId", cuentaId);
        req.getRequestDispatcher("homeServlet?route=realizarMovimiento").forward(req, resp);
    }

    private void transferir(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int cuentaOrigenId = Integer.parseInt(req.getParameter("cuentaOrigenId").trim());
        int cuentaDestinoId = Integer.parseInt(req.getParameter("cuentaDestinoId").trim());
        double valor = Double.parseDouble(req.getParameter("valor").trim());
        String concepto = req.getParameter("concepto").trim();

        Cuenta cuentaOrigen = cuentaDAO.findById(cuentaOrigenId);
        Cuenta cuentaDestino = cuentaDAO.findById(cuentaDestinoId);
        Transferencia transferencia = new Transferencia(cuentaOrigen, cuentaDestino, valor, concepto);
        transferencia.setFecha(LocalDateTime.now());

        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            transferencia.realizarTransaccion();
            transferenciaDAO.save(transferencia);
            cuentaDAO.update(cuentaOrigen);
            cuentaDAO.update(cuentaDestino);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        req.setAttribute("cuentaId", cuentaOrigenId);
        req.getRequestDispatcher("homeServlet?route=realizarMovimiento").forward(req, resp);
    }
}