package com.misiontic2022.grupo11.securityBackend.services;

import com.misiontic2022.grupo11.securityBackend.models.User;
import com.misiontic2022.grupo11.securityBackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.set;

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
        Optional<User> user = this.userRepository.findById(id);
        if(user.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "User.id does not exist in db");
        return user;
    }

    public Optional<User> showByNickname(String nickname) {
        Optional<User> user = this.userRepository.findByNickname(nickname);
        if(user.isEmpty())
            throw new ResponseStatusException(httpStatus.NOT_FOUND, "" +
                "User.nickname requested does not exist in database");
        return user;
    }

    public Optional<User> showByEmail(String email) {
        Optional<User> user =  this.userRepository.findByEmail(email);
        if(user.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "User.email requested does not exist in database");
        return user;
    }


    /**
     *
     * @param newUser
     * @return
     */
    public User create(User newUser){
        if (newUser.getId()) != nul){
            Optional<User> user = this.userRepository.findById(newUser.getId());
            if(user.isPresent())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User.id is already in the database, Remove from request");
        }
       if (newUser.getEmail() != null && newUser.getNickname() != null &&
                newUser.getPassword() != null && newUser.getRol() != null) {
            Optional<User> tempUser = this.showByNickname(newUser.getNickname());
            if(tempUser.isPresent())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User.nickname is already in the database, Remove from request");
            tempUser = this.showByEmail(newUser.getEmail());
            if(tempUser.isPresent())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User.email is already in the database, Remove from request");
            newUser.setPassword(this.convertToSHA256(newUser.getPassword()));
            return this.userRepository.save(newUser);
        }
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "madatory fields have not been provied");
    }

    /**
     *
     * @param id
     * @param updateUser
     * @return
     */
    public User update(int id, User updateUser){

        if(id > 0){
            Optional<User> tempUser = this.show(id);
            if(tempUser.isPresent()){
                if(updateUser.getNickname() != null)
                    tempUser.get().setNickname(updateUser.getNickname());
                if(updateUser.getPassword() != null)
                    tempUser.get().setPassword( this.convertToSHA256(updateUser.getPassword()) );
                if(updateUser.getRol() != null)
                    tempUser.get().setRol( updateUser.getRol());
                try {
                    return this.userRepository.save(tempUser.get());
                }
                catch(Exception e){
                    e.printStackTrace();
                    throw new ResponseStatusException(HttpStatus.CONFLICT,
                            "cannot be update due to constraint conflict");
                }
            }
            else{
               throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "requested user does not exist in database");
            }
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User.id cannot be negative");
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
    /**
     *
     * @param user
     * @return
     */
    public User login(User user){
        User response;
        if(user.getPassword() != null && user.getEmail() != null) {
            String email = user.getEmail();
            String password = this.convertToSHA256(user.getPassword());
            Optional<User> result = this.userRepository.login(email, password);
            if(result.isEmpty())
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Invalid access.");
            else
                response = result.get();

        }
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mandatory fields had not been sent");

        return response;

    }
    /**
     *
     * @param password
     * @return
     */
    public String convertToSHA256(String password){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(algorithm: "SHA-256");
        }
        catch(NoSuchAlgortihmException e){
            e.printStackTrace();
            return null;
        }
        byte[] hash = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();
        for(byte b: hash)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }
}
