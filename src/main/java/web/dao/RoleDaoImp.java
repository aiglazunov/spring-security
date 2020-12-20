package web.dao;


import org.springframework.stereotype.Repository;
import web.model.Role;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;
@Repository
public class RoleDaoImp {


    @PersistenceContext
    EntityManager entityManager;

    public List<Role> listRoles() {
        TypedQuery<Role> query = entityManager.createQuery("from Role", Role.class);
        return query.getResultList();
    }



    public Role readOne(String role) {
        TypedQuery<Role> query = entityManager
                .createQuery("select r " +
                        "from Role as r " +
                        "where r.role = :role", Role.class);
        query.setParameter("role", role);
        return query.getSingleResult();
    }
    public Role findRoleByName(String role) {
        TypedQuery<Role> query = entityManager
                .createQuery("select r " +
                        "from Role as r " +
                        "where r.role like :role", Role.class);
        query.setParameter("role", "%" + role + "%") ;
        return query.getSingleResult();
    }
}
