package geminiAPP.repository.jpa;

import geminiAPP.entity.food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface foodRepo extends JpaRepository<food, Integer> {
}
