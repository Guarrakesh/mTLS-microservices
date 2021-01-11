package appelli.security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.swing.*;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class KeyManager {

	static KeyManager _instance;
	
	private KeyManager() {};

	public appelli.security.EncryptedKeyPair getEncryptedKeyPair() {
		return EncryptedKeyPair;
	}

	public void setEncryptedKeyPair(appelli.security.EncryptedKeyPair encryptedKeyPair) {
		EncryptedKeyPair = encryptedKeyPair;
	}

	private EncryptedKeyPair EncryptedKeyPair;
	


	public static KeyManager getInstance() {
		if (KeyManager._instance == null) {
			KeyManager._instance = new KeyManager();
			KeyManager._instance.init();
		}
		return _instance; 
	}
	
	private void init()
	{
		Security.addProvider(new BouncyCastleProvider());
		try {
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	public EncryptedKeyPair GenerateKeyPair(char[] password)
	{
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(4096);
			KeyPair keyPair = keyPairGenerator.genKeyPair();


			String PBEALG = "PBEWithSHA1AndDESede";

			int rounds = 16;
			SecureRandom random = new SecureRandom();
			byte[] salt = new byte[8];
			random.nextBytes(salt);

			PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, rounds);
			PBEKeySpec pbeKeySpec = new PBEKeySpec(password);

			SecretKeyFactory keyFac = SecretKeyFactory.getInstance(PBEALG);
			SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);

			Cipher pbeCipher = Cipher.getInstance(PBEALG);
			pbeCipher.init(Cipher.ENCRYPT_MODE,  pbeKey, pbeParamSpec);


			// Encrypt the encoded Private Key with the PBE key
			byte[] cipherText = pbeCipher.doFinal(keyPair.getPrivate().getEncoded());

			// Now construct  PKCS #8 EncryptedPrivateKeyInfo object
			AlgorithmParameters algparms = AlgorithmParameters.getInstance(PBEALG);
			algparms.init(pbeParamSpec);
			EncryptedPrivateKeyInfo encinfo = new EncryptedPrivateKeyInfo(algparms, cipherText);

			// and here we have it! a DER encoded PKCS#8 encrypted key!
			byte[] encryptedPkcs8 = encinfo.getEncoded();

			this.EncryptedKeyPair = new EncryptedKeyPair(keyPair, encryptedPkcs8);
			return this.EncryptedKeyPair;
			
			
			} catch (NoSuchAlgorithmException ex) {
				ex.printStackTrace();
			} catch (NoSuchPaddingException ex) {
				ex.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			} catch (InvalidParameterSpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		return null; 
			
	}
	


	public byte[] encrypt(byte[] text, PublicKey pubKey)
	{
		try {
			Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING", "BC");
			rsaCipher.init(Cipher.ENCRYPT_MODE,  pubKey);
			return rsaCipher.doFinal(text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;


	}

	public byte[] decrypt(byte[] cipherText, PrivateKey pvtKey)
	{

		try {
			Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING", "BC");

			rsaCipher.init(Cipher.DECRYPT_MODE, pvtKey);
			return rsaCipher.doFinal(cipherText);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	public Key selectKeyFile(boolean isPublic)
	{
		JFileChooser f = new JFileChooser();
		f.setFileSelectionMode(JFileChooser.FILES_ONLY);
		f.showSaveDialog(null);
		if (f.getSelectedFile() != null) {
			try {

				//
//				String modulusBase64 = "..."; // your Base64 string here
//				BigInteger modulus = new BigInteger(1,
//						new Base64Encoder.decode(modulusBase64.getBytes("UTF-8")));
//				KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//				RSAPublicKeySpec ks = new RSAPublicKeySpec(modulus, pubExp);
//				RSAPublicKey pubKey = (RSAPublicKey)keyFactory.generatePublic(KeySpec);
				byte[] keyBytes = Files.readAllBytes(Paths.get(f.getSelectedFile().toURI()));
				byte[] encodedKey = java.util.Base64.getDecoder().decode(keyBytes);
				KeySpec spec;
				if (isPublic) {
					spec = new X509EncodedKeySpec(encodedKey);
				} else {
					spec = new PKCS8EncodedKeySpec(encodedKey);
				}
				KeyFactory kf = KeyFactory.getInstance("RSA");
				if (isPublic) {
					return kf.generatePublic(spec);
				} else {
					return kf.generatePrivate(spec);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return null;
	}


	public boolean SaveKeyPairToFile(KeyPair keyPair, String dir ) throws IOException
	{
		DataOutputStream dos = null;
		System.out.println("Saving keys to " + dir);

		dos = new DataOutputStream(new FileOutputStream(dir + "jcaPublicKey"));
		dos.write(Base64.toBase64String(keyPair.getPublic().getEncoded()).getBytes());
		dos.flush();
		dos.close();
		dos = new DataOutputStream(new FileOutputStream(dir + "jcaPrivateKey"));
		dos.write(Base64.toBase64String(keyPair.getPrivate().getEncoded()).getBytes());
		dos.flush();
		dos.close();

		return true;
	}


}

