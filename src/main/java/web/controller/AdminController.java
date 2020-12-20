package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.dao.RoleDaoImp;
import web.model.Role;
import web.model.User;
import web.service.UserService;

import java.security.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleDaoImp roleDao;

    @Autowired
    public AdminController(UserService userService, RoleDaoImp roleDao) {
        this.userService = userService;
        this.roleDao = roleDao;
    }

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/admin")
    public String adminPage(Principal principal, ModelMap modelMap) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        Set<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

        StringBuilder sb = new StringBuilder();
        for (String role : roles) {
            sb.append(role).append(" ");
        }


        modelMap.addAttribute("username", principal.getName());
        modelMap.addAttribute("roleSet", new String(sb));

        return "admin/admin";
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", userService.listUsers());
        //Получим всех людей из DAO и передадим на отображение в представление
        return "admin/index";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", userService.listRoles());
        return "admin/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user,
                         @RequestParam(value = "allRoles") String[] roles) {

        System.out.println(user);
        Set<Role> roleSet = new HashSet<>();
        for (String role : roles) {
            System.out.println(role);
            roleSet.add(roleDao.findRoleByName(role));
        }
        user.setRoles(roleSet);
        userService.add(user);

        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.show(id));
        //model.addAttribute("allRoles",  new ArrayList<>(userService.show(id).getRoles()));
        model.addAttribute("allRoles", userService.listRoles());
        return "admin/edit";
    }

    /*
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        userService.update(id, user);
        return "redirect:/users";
    }
    */
    @PostMapping("/{id}/edit")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam(value = "allRoles") String[] roles) {
        System.out.println(user);
        Set<Role> roleSet = new HashSet<>();
        for (String role : roles) {
            System.out.println(role);
            roleSet.add(roleDao.findRoleByName(role));
        }
        user.setRoles(roleSet);
        userService.update(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }


}
