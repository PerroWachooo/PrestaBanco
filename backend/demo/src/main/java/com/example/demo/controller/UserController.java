package com.example.demo.controller;

import com.example.demo.entities.UserEntity;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("api/v1/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<UserEntity>> listUsers(){
        List<UserEntity> users =userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/by-rut/{rut}")
    public ResponseEntity<UserEntity> userByrut(@PathVariable String rut){
        UserEntity user = userService.getUserByRut(rut);
        return ResponseEntity.ok(user);
    }




    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getEmployeeById(@PathVariable Long id) {
        UserEntity user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<UserEntity> registerUser(@RequestBody UserEntity user){
        UserEntity newUser = userService.registerUser(user);
        return ResponseEntity.ok(newUser);
    }

   @PutMapping("/update")
   public ResponseEntity<UserEntity> updateUser(@RequestBody UserEntity user){
       UserEntity updatedUser = userService.updateUser(user);
       return ResponseEntity.ok(updatedUser);
   }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUserById(@PathVariable Long id) throws Exception {
        var isDeleted = userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


}
