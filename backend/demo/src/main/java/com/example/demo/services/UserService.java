package com.example.demo.services;
import com.example.demo.entities.UserEntity;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;


    public ArrayList<UserEntity> getUsers(){
        return(ArrayList<UserEntity>) userRepository.findAll();
    }

    public UserEntity getUserByRut(String rut){
        return userRepository.findByRut(rut);
    }

    public UserEntity getUserById(Long id){return userRepository.findById(id).get();}
    public UserEntity registerUser(UserEntity user){
        String rut = user.getRut();
        String password = user.getPassword();
        String mail = user.getMail();
        String name = user.getName();
        validateIntAttribute(user.getAge(),"age");
        String work = user.getWork();
        validateIntAttribute(user.getYears_working(),"years working");
        validateIntAttribute(user.getIncome(), "income");
        validateIntAttribute(user.getDebt(), "debt");
        validateIntAttribute(user.getYearsAccount(), "years acount");

        return userRepository.save(user);
    }

    public UserEntity updateUser(UserEntity user) {
        return userRepository.save(user);
    }

    public boolean deleteUser(Long id) throws Exception {
        try{
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    private void validateIntAttribute(int value, String attributeName) {
        if (value <= 0) {
            throw new IllegalArgumentException("El valor del atributo " + attributeName + " debe ser mayor a 0.");
        }
    }

}



