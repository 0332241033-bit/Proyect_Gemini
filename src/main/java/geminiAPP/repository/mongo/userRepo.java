package geminiAPP.repository.mongo;

import geminiAPP.entity.user;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface userRepo extends MongoRepository<user,String> {
}
