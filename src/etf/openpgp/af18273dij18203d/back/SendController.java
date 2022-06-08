package etf.openpgp.af18273dij18203d.back;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.PGPSecretKeyRing;

public class SendController {

	//3des with ede conf
	//aes 128
	
	public static void send(boolean toEncrypt, boolean toSign, boolean toCompress, boolean toRadix, PGPSecretKeyRing secretKeyRing, PGPPublicKeyRingCollection publicKeyRingCollection, int algorithm, String password) {
		OutputStream message = null;
		try {
			message = new FileOutputStream(new File("encrypted_message.gpg"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		// Signing
		

		// Compression
		

		// Encryption
		
		
		// Radix64
		if (toRadix) message = new ArmoredOutputStream(message);
		
		
		
	}
	
	private static void compress() {
		
	}
	
}
