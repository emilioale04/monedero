package ec.edu.epn.monederovirtual.dao;

import ec.edu.epn.monederovirtual.model.Cuenta;
import ec.edu.epn.monederovirtual.model.Ingreso;
import ec.edu.epn.monederovirtual.model.Usuario;
import ec.edu.epn.monederovirtual.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class IngresoDAO extends BaseDAO<Ingreso> {

    public IngresoDAO() {
        super(Ingreso.class);
    }

    public List<Ingreso> findByCuentaDestino(Cuenta cuenta) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Ingreso> query = session.createQuery("FROM Ingreso WHERE cuentaDestino = :cuenta", Ingreso.class);
            query.setParameter("cuenta", cuenta);
            return query.list();
        }
    }

    public List<Ingreso> findByUsuario(Usuario usuario) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Ingreso> query = session.createQuery("FROM Ingreso WHERE cuentaDestino.usuario = :usuario", Ingreso.class);
            query.setParameter("usuario", usuario);
            return query.list();
        }
    }
}
