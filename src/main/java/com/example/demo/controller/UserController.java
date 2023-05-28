package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.services.RoleService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/users")
/*@ComponentScan("com.example.demo")*/
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String viewAllUser(Model model) {
        List<User> listUser = userService.getAllUsers();
        model.addAttribute("users",listUser);
        return "user/list";
    }
    @GetMapping("/add")
    public String showNewUserPage(Model model){
        User user = new User();
        model.addAttribute("user",user);
        model.addAttribute("role", roleService.getAllRoles());
        return "user/add";
    }
    @PostMapping("/add")
    public String saveUser(@ModelAttribute("user") User user)
        throws IOException {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.addUser(user);
            return "redirect:/users";
        }

    @GetMapping("/edit/{id}")
    public String showEditUserPage(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);

        if(user == null){
            return "notfound";
        }else {
            model.addAttribute("user",user);
            model.addAttribute("role", roleService.getAllRoles());
            return "user/edit";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        User user = userService.getUserById(id);
        if(user == null){
            return "notfound";
        }else {
            userService.deletUser(id);
            return "redirect:/users";
        }
    }
}
