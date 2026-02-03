package geminiAPP.controller;

import geminiAPP.entity.user;
import geminiAPP.service.userServiceIMPL;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class userController {

    private final userServiceIMPL userService;
  @GetMapping("/findbyid/{userID}")
    public ResponseEntity<user> findById(@PathVariable String userID){
         user u = userService.findUserById(userID);
         if(u==null){
             throw new RuntimeException("Usuario no encontrado con ID: " + userID);
         }
         return ResponseEntity.ok(u);
    }
    @GetMapping("findall")
    public ResponseEntity<List<user>> findAll(){
      return ResponseEntity.ok(userService.findAll());
    }
    @PostMapping("/add")
    public ResponseEntity<user> addUser(@RequestBody user user){


      user u = userService.addUser(user);
      return  new ResponseEntity<>(u, HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{userID}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userID){
      userService.deleteUser(userID);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
    @PutMapping("/update")
    public ResponseEntity<user> updateUser(@RequestBody user user){

      user u = userService.updateUser(user);
      return  new ResponseEntity<>(u, HttpStatus.OK);
    }
    @DeleteMapping("/deleteall")
    public ResponseEntity<Void> deleteAll(){
      userService.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}
