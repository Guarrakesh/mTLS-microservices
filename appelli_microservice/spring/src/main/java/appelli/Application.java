package appelli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

	@RequestMapping("/")
	public String home() {
		return "Hello PDSx ";
	}

	public static void main(String[] args) {

		System.getenv("SSL_KEYSTORE_DIR");
		SpringApplication.run(Application.class, args);
	}

}
