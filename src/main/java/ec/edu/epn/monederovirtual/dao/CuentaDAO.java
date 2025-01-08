package ec.edu.epn.monederovirtual.dao;

import ec.edu.epn.monederovirtual.model.Cuenta;
import ec.edu.epn.monederovirtual.model.Usuario;
import ec.edu.epn.monederovirtual.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class CuentaDAO extends BaseDAO<Cuenta> {

    public CuentaDAO() {
        super(Cuenta.class);
    }

    public List<Cuenta> findByUsuario(Usuario usuario) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Cuenta> query = session.createQuery("FROM Cuenta WHERE usuario = :usuario", Cuenta.class);
            query.setParameter("usuario", usuario);
            return query.getResultList();
        }
    }

}