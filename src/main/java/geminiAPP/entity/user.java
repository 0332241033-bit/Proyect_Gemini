package geminiAPP.entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user_gemini")
public class user {
    @Id
    private String id;
    @NotEmpty
    private String name;

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;

}
