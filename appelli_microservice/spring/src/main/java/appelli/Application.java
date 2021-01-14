package appelli;

import appelli.security.KeyStoreManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import sun.security.x509.CertificateSerialNumber;

import java.io.*;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

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

	String jwt;

	KeyStoreManager keyStoreManager;
	@Override
	public void run() {
		System.out.println("Certificate Thread: hello");
		do {
			jwt = System.getProperty("jwt");
			if (jwt != null) {
				// check certificates
				System.out.println("[Certificate Thread] Got the JWT: " + jwt);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} while (jwt == null);

		// check certificaste expiration every 10 seconds

		while (true) {
			boolean expired = CheckExpiredCertificate();
			if (expired) {
				System.out.println("Performing Certificate refresh...");
				GetNewCertificate() ;
				getOldCertificate();
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// get new certificate from LEMUR
	private void GetNewCertificate() {
	}


	//@RequestMapping(value ="https://172.31.0.5:8000/api/1/certificates/4", method = RequestMethod.GET)
	public Map<String, Object> getOldCertificate() {
		RestTemplate restTemplate = new RestTemplate();
		String fooResourceUrl = "http://172.31.0.5:8000/api/1/certificates/4";
		HttpHeaders header = new HttpHeaders();
		header.setBearerAuth(jwt);
		final HttpEntity<String> entity = new HttpEntity<String>(header);
		ResponseEntity<String> response = restTemplate.exchange(fooResourceUrl, HttpMethod.GET, entity, String.class);
		try {

			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readValue(response.getBody(), JsonNode.class);
			ObjectNode n = JsonNodeFactory.instance.objectNode();
			n.put("id", 4);
			((ObjectNode) root).putArray("replaces").add(n);


			System.out.print(root.get("replaces"));
			//restTemplate.postForObject("http://172.31.0.5:8000/api/1/certificate", )
			return null;
			//	return response.getBody();


		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}


		private boolean CheckExpiredCertificate()
	{
		String keystore = System.getProperty("javax.net.ssl.keyStore");
		String keystorePassword = System.getProperty("javax.net.ssl.keyStorePassword");
		if (keystore != null) {
			try {
				FileInputStream stream = new FileInputStream(new File(keystore));
				KeyStoreManager.getInstance().loadKeyStore(keystorePassword, stream);
				Enumeration<String> aliases = KeyStoreManager.getInstance().getAllEntries();
				X509Certificate cert = null;
				while (aliases.hasMoreElements()) {
					String alias = aliases.nextElement();
					System.out.println(alias);

					cert = (X509Certificate) KeyStoreManager.getInstance().getCertificate(alias);
					if (cert != null) {
						break;
					}
				}

				if (cert == null) {
					return false;
				}

				System.out.println("Certificate expires in : " + cert.getNotAfter());
				LocalDateTime now = LocalDateTime.now();
				now = now.minusHours(12);
				LocalDateTime certDate = cert.getNotAfter().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				if (certDate.isBefore(now)) {
					return false;
				}
				return true;

			} catch (FileNotFoundException ex)  {
				return false;
			} catch (CertificateException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (KeyStoreException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}