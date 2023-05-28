package com.example.demo.services;
import com.example.demo.entity.User;
import com.example.demo.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    public IUserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        Optional<User> optional = userRepository.findById(id);
        return optional.orElse(null);
    }

    public void addUser(User user){
        userRepository.save(user);
    }

    public void deletUser(Long id){
        userRepository.deleteById((id));
    }

}
