package appelli.security;

import java.security.KeyPair;

public class EncryptedKeyPair
{
	KeyPair kp;


	byte[] encryptedPkcs8;
	

	EncryptedKeyPair(KeyPair kp, byte[] encryptedPkcs8) {
		this.kp = kp;
		this.encryptedPkcs8 = encryptedPkcs8;
		
	}
	
	public KeyPair getKp() {
		return kp;
	}

	public void setKp(KeyPair kp) {
		this.kp = kp;
	}
	
	public byte[] getEncryptedPkcs8() {
		return encryptedPkcs8;
	}

	public void setEncryptedPkcs8(byte[] encryptedPkcs8) {
		this.encryptedPkcs8 = encryptedPkcs8;
	}

}