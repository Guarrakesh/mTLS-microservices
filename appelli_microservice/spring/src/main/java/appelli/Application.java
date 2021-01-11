package appelli;

import appelli.security.KeyStoreManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
import java.util.Date;
import java.util.Enumeration;

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
				GetNewCertificate() ; 			}
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
				now = now.plusHours(12);
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