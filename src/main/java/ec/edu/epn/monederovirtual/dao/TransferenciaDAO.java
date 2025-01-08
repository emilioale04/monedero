package ec.edu.epn.monederovirtual.dao;

import ec.edu.epn.monederovirtual.model.Cuenta;
import ec.edu.epn.monederovirtual.model.Ingreso;
import ec.edu.epn.monederovirtual.model.Transferencia;
import ec.edu.epn.monederovirtual.model.Usuario;
import ec.edu.epn.monederovirtual.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class TransferenciaDAO extends BaseDAO<Transferencia> {
    public TransferenciaDAO() {
        super(Transferencia.class);
    }

    public List<Transferencia> findByCuentaOrigen(Cuenta cuenta) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Transferencia> query = session.createQuery("FROM Transferencia WHERE cuentaOrigen = :cuentaOrigen", Transferencia.class);
            query.setParameter("cuentaOrigen", cuenta);
            return query.list();
        }
    }

    public List<Transferencia> findByCuentaDestino(Cuenta cuentaDestino) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Transferencia> query = session.createQuery("FROM Transferencia WHERE cuentaDestino = :cuentaDestino", Transferencia.class);
            query.setParameter("cuentaDestino", cuentaDestino);
            return query.list();
        }
    }

    public List<Transferencia> findByCuentaAsociada(Cuenta cuenta) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Transferencia> query = session.createQuery("FROM Transferencia WHERE cuentaDestino = :cuenta OR cuentaOrigen = :cuenta", Transferencia.class);
            query.setParameter("cuenta", cuenta);
            return query.list();
        }
    }

    public List<Transferencia> findByUsuario(Usuario usuario) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Transferencia> query = session.createQuery(
                    "FROM Transferencia WHERE cuentaDestino.usuario = :usuario OR cuentaOrigen.usuario = :usuario",
                    Transferencia.class);
            query.setParameter("usuario", usuario);
            return query.list();
        }
    }
}
