package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import web.dao.RoleDaoImp;
import web.dao.UserDao;
import web.model.Role;
import web.model.User;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService  /*implements UserDetailsService */ {

    @Autowired
    @Qualifier("userDaoImp")
    private UserDao userDao;

    @Autowired
    @Qualifier("roleDaoImp")
    private RoleDaoImp roleDao;

    @Transactional
    //@Override
    public void add(User user) {
        userDao.create(user);
    }

    @Transactional
    public void update(User user) {
        userDao.update(user);
    }

    @Transactional
    public void deleteById(long id) {
        userDao.deleteById(id);
    }

    //@Override
    public void delete(User user) {
        userDao.deleteById(user.getId());
    }

    //@Override
    @Transactional
    public User show(long id) {
        return userDao.read(id);
    }

    @Transactional
    public User readOne(String username) {
        return userDao.readOne(username);
    }

    @Transactional
    //@Override
    public List<User> listUsers() {
        return userDao.listUsers();
    }

    @Transactional
    public List<Role> listRoles() {
        return roleDao.listRoles();
    }

    @Transactional
    public Role readRoleOne(String role) {
        return roleDao.readOne(role);
    }

    /*
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.readOne(username);
        if (user == null) {
            throw  new UsernameNotFoundException(String.format("User '%s' not exists", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername()
        ,user.getPassword()
        ,mapRolesToAuthorities(user.getRoles()));

    }


    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getRole())).collect(Collectors.toSet());
    }

     */




}
