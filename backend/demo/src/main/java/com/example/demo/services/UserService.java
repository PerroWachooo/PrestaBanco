package com.example.demo.services;
import com.example.demo.entities.UserEntity;
import com.example.demo.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Optional;

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
        // Verifica si el usuario ya existe en la base de datos
        Optional<UserEntity> existingUser = userRepository.findById(user.getId());

        if (existingUser.isPresent()) {
            // Si existe, actualiza los datos necesarios
            UserEntity userToUpdate = existingUser.get();
            userToUpdate.setRut(user.getRut());
            userToUpdate.setPassword(user.getPassword());
            userToUpdate.setMail(user.getMail());
            userToUpdate.setName(user.getName());
            userToUpdate.setAge(user.getAge());
            userToUpdate.setState_hist(user.getState_hist());
            userToUpdate.setWork(user.getWork());
            userToUpdate.setYears_working(user.getYears_working());
            userToUpdate.setIncome(user.getIncome());
            userToUpdate.setDebt(user.getDebt());
            userToUpdate.setYearsAccount(user.getYearsAccount());
            userToUpdate.setBalanceAccount(user.getBalanceAccount());

            // Guarda el usuario actualizado
            return userRepository.save(userToUpdate);
        } else {
            throw new EntityNotFoundException("El usuario con ID " + user.getId() + " no existe.");
        }
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



