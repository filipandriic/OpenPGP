package etf.openpgp.af18273dij18203d.back;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.SecureRandom;

import org.bouncycastle.bcpg.ArmoredOutputStream;
<<<<<<< HEAD
=======
import org.bouncycastle.bcpg.CompressionAlgorithmTags;
import org.bouncycastle.openpgp.PGPCompressedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedDataGenerator;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
>>>>>>> b5de229f91e91f44713a0898cf30655f5e3f8b3e
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.PGPSecretKeyRing;
import org.bouncycastle.openpgp.operator.jcajce.JcePGPDataEncryptorBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePublicKeyKeyEncryptionMethodGenerator;

public class SendController {

	//3des with ede conf
	//aes 128
	
	public static void send(boolean toEncrypt, boolean toSign, boolean toCompress, boolean toRadix, long secretKeyRingID, PGPPublicKeyRingCollection publicKeyRingCollection, int algorithm, String password) {
		OutputStream message = null;
		try {
			message = new FileOutputStream(new File("encrypted_message.gpg"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Signing
		if (toSign) {
			
			
			message = sign(message);
		}
			
		// Compression
		if (toCompress)
			message = compress(message);

		// Encryption
		if (toEncrypt) {
			
			
			message = encrypt(algorithm, publicKeyRingCollection, message);
		}
		
		// Radix64
		if (toRadix) message = new ArmoredOutputStream(message);

		
		
	}
	
	private static OutputStream encrypt(int algorithm, PGPPublicKeyRingCollection publicKeyRingCollection, OutputStream message) {
		PGPEncryptedDataGenerator encryptedDataGenerator = new PGPEncryptedDataGenerator(
                new JcePGPDataEncryptorBuilder(algorithm).setWithIntegrityPacket(true).setSecureRandom(new SecureRandom()).setProvider("BC"));

		for (PGPPublicKeyRing publicKeyRing : publicKeyRingCollection)
			encryptedDataGenerator.addMethod(new JcePublicKeyKeyEncryptionMethodGenerator(publicKeyRing.getPublicKey()).setProvider("BC"));
		
		try {
			return encryptedDataGenerator.open(message, new byte[1024]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private static OutputStream compress(OutputStream message) {
		PGPCompressedDataGenerator compressedDataGenerator = new PGPCompressedDataGenerator(CompressionAlgorithmTags.ZIP);
		try {
			return compressedDataGenerator.open(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private static OutputStream sign(OutputStream message) {
		return null;
	}
	
}
