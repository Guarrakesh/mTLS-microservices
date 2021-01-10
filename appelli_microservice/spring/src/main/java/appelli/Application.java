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
		return "Hello Appelli ";
	}

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);

		Thread certificateThread = new Thread(new CertificateThread());
		certificateThread.run();
	}



}

class CertificateThread implements Runnable {


	@Override
	public void run() {
		System.out.println("Certificate Thread: hello");
	}
}