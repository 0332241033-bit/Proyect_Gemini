package geminiAPP.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "geminiAPP.repository.jpa")
public class JpaConfig {
}
