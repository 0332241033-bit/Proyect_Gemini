package geminiAPP.service;

import geminiAPP.entity.food;
import geminiAPP.repository.jpa.foodRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class foodServiceIMPL {
    private final foodRepo foodRepo;


    public food getFoodById(int id) {
        return foodRepo.findById(id).orElse(null);
    }

    public List<food> getAllFood() {
        return foodRepo.findAll();
    }
    public food addFood(food food) {
        return foodRepo.save(food);
    }

    public food updateFood(food food) {
        return foodRepo.save(food);
    }
    public void deleteFoodById(int id) {
        foodRepo.deleteById(id);
    }

    public void deleteAllFood() {
        foodRepo.deleteAll();
    }



}
