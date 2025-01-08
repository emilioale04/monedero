package ec.edu.epn.monederovirtual.dao;

import ec.edu.epn.monederovirtual.model.Cuenta;
import ec.edu.epn.monederovirtual.model.Egreso;
import ec.edu.epn.monederovirtual.model.Ingreso;
import ec.edu.epn.monederovirtual.model.Usuario;
import ec.edu.epn.monederovirtual.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class EgresoDAO extends BaseDAO<Egreso> {
    public EgresoDAO() {
        super(Egreso.class);
    }

    public List<Egreso> findByCuentaOrigen(Cuenta cuenta) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Egreso> query = session.createQuery("FROM Egreso WHERE cuentaOrigen = :cuenta", Egreso.class);
            query.setParameter("cuenta", cuenta);
            return query.list();
        }
    }

    public List<Egreso> findByUsuario(Usuario usuario) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Egreso> query = session.createQuery("FROM Egreso WHERE cuentaOrigen.usuario = :usuario", Egreso.class);
            query.setParameter("usuario", usuario);
            return query.list();
        }
    }
}
