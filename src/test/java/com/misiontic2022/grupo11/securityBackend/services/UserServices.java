package com.misiontic2022.grupo11.securityBackend.services;

import com.misiontic2022.grupo11.securityBackend.models.User;
import com.misiontic2022.grupo11.securityBackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
/**
 *
 */
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    /**
     *
     * @return
     */
    public List<User> index(){
        return (List<User>) this.userRepository.findAll();
    }

    /**
     *
     * @param id
     * @return
     */
    public Optional<User> show(int id){
        return this.userRepository.findById(id);
    }

    /**
     *
     * @param newUser
     * @return
     */
    public User create(User newUser){
        if (newUser.getId()== null){
            if (newUser.getEmail() != null && newUser.getNickname() != null && newUser.getPassword() != null)
                return this.userRepository.save(newUser);
            else{
                //TODO 400 BadRequest
                return newUser;
            }

        }
        else{
            //TODO validate if id exists, 400 BadRequest
            return newUser;
        }
    }

    /**
     *
     * @param id
     * @param updateUser
     * @return
     */
    public User update(int id, User updateUser){

        if(id>0){
            Optional<User> tempUser = this.show(id);
            if(tempUser.isPresent()){
                if(updateUser.getNickname() != null)
                    tempUser.get().setNickname(updateUser.getNickname());
                if(updateUser.getPassword() != null)
                    tempUser.get().setPassword(updateUser.getPassword());
                return this.userRepository.save(tempUser.get());
            }
            else{
                //TODO 404 NotFound
                return updateUser;
            }
        }
        else{
            //TODO 400 BadRequest, id <= 0
            return updateUser;
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean delete(int id){

        Boolean success = this.show(id).map(user ->{
            this.userRepository.delete(user);
            return true;
        }).orElse(false);
        return success;
    }
}
