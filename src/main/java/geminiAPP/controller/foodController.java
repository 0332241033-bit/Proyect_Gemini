package geminiAPP.controller;

import geminiAPP.entity.food;
import geminiAPP.service.foodServiceIMPL;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/food")
public class foodController {
    private final foodServiceIMPL foodService;
    @GetMapping("/findbyid/{id}")
    public ResponseEntity <food> findById(@PathVariable Integer id){

       food fo = foodService.getFoodById(id);
        return new ResponseEntity<>(fo, HttpStatus.OK);
    }

    @GetMapping("/findall")
    public ResponseEntity <List<food>> findAll(){
        List<food> fo = foodService.getAllFood();
        return new ResponseEntity<>(fo, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity <food> save(@RequestBody food food){
        food fo = foodService.addFood(food);
        return new ResponseEntity<>(fo, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity <food> update(@RequestBody food food){
        food fo = foodService.updateFood(food);
        return new ResponseEntity<>(fo, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity <Void> delete(@PathVariable Integer id){

        foodService.deleteFoodById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/deleteall")
    public ResponseEntity <Void> deleteAll(){
        foodService.deleteAllFood();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
