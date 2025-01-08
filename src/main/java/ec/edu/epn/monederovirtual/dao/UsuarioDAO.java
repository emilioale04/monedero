package ec.edu.epn.monederovirtual.dao;

import ec.edu.epn.monederovirtual.model.Usuario;
import ec.edu.epn.monederovirtual.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UsuarioDAO extends BaseDAO<Usuario> {
    public UsuarioDAO() {
        super(Usuario.class);
    }

    public Usuario findByUsuario(String usuario) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Usuario> query = session.createQuery("FROM Usuario WHERE usuario = :usuario", Usuario.class);
            query.setParameter("usuario", usuario);
            return query.uniqueResult();
        }
    }
}
