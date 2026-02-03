package geminiAPP.service;

import geminiAPP.entity.user;
import geminiAPP.repository.mongo.userRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class userServiceIMPL {

private  final userRepo user;


public user findUserById(String ID) {
    return user.findById(ID).orElse(null);

}

public List<user> findAll() {
return user.findAll();
}

public user addUser(user u) {
    return user.save(u);
}

public user updateUser(user u) {
    return user.save(u);
}


    public void deleteUser(String userID) {
        if (user.existsById(userID)) {
            user.deleteById(userID);
        } else {
            throw new RuntimeException("Usuario no encontrado con ID: " + userID);
        }
    }

    public void deleteAll() {
        user.deleteAll();
    }

}
