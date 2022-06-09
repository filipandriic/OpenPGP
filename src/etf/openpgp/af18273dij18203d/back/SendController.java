package etf.openpgp.af18273dij18203d.back;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.bouncycastle.bcpg.ArmoredOutputStream;

import org.bouncycastle.bcpg.CompressionAlgorithmTags;
import org.bouncycastle.openpgp.PGPCompressedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedDataGenerator;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPLiteralData;
import org.bouncycastle.openpgp.PGPLiteralDataGenerator;
import org.bouncycastle.openpgp.PGPPrivateKey;
import org.bouncycastle.openpgp.PGPPublicKeyRing;

import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.PGPSecretKeyRing;
import org.bouncycastle.openpgp.PGPSignature;
import org.bouncycastle.openpgp.PGPSignatureGenerator;
import org.bouncycastle.openpgp.PGPSignatureSubpacketGenerator;
import org.bouncycastle.openpgp.PGPUtil;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPContentSignerBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePBESecretKeyDecryptorBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePGPDataEncryptorBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePublicKeyKeyEncryptionMethodGenerator;

public class SendController {

	//3des with ede conf
	//aes 128
	
	public static void send(File encryptionFile, boolean toEncrypt, boolean toSign, boolean toCompress, boolean toRadix, Long secretKeyRingID, List<Long> publicKeyRingCollectionIDs, int algorithm, String password) {
		OutputStream transmissionOutput = null;
		PGPSignatureGenerator signatureGenerator = null;
		File file = new File("encrypted_message_" + new Date().getTime() + ".gpg");
		try {
			transmissionOutput = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Signing
		if (toSign) {
			PGPSecretKeyRing secretKeyRing = ManageKeysController.getSecretKeyRing(secretKeyRingID);
			
			if (secretKeyRing == null) {
				//Window.showError("Bad secret key");
				return;
			}
			
			PGPPrivateKey privateKey = null;
			try {
				privateKey = secretKeyRing.getSecretKey().extractPrivateKey(new JcePBESecretKeyDecryptorBuilder().setProvider("BC").build(password.toCharArray()));
				signatureGenerator = new PGPSignatureGenerator(new JcaPGPContentSignerBuilder(secretKeyRing.getSecretKey().getPublicKey().getAlgorithm(), PGPUtil.SHA1).setProvider("BC"));
				signatureGenerator.init(PGPSignature.BINARY_DOCUMENT, privateKey);
	        
		        Iterator<String> it = secretKeyRing.getSecretKey().getPublicKey().getUserIDs();
		        if (it.hasNext())
		        {
		            PGPSignatureSubpacketGenerator  signatureSubpacketGenerator = new PGPSignatureSubpacketGenerator();
		            
		            signatureSubpacketGenerator.addSignerUserID(false, (String)it.next());
		            signatureGenerator.setHashedSubpackets(signatureSubpacketGenerator.generate());
		        }
			
				signatureGenerator.generateOnePassVersion(false).encode(transmissionOutput);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PGPException e) {
				//Window.showError("Bad password");
				try {
					transmissionOutput.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				file.delete();
				return;
			}
			
			
		}
			
		// Compression
		if (toCompress)
			transmissionOutput = compress(transmissionOutput);

		// Encryption
		if (toEncrypt) {
			PGPPublicKeyRingCollection publicKeyRingCollection = null;
			try {
				publicKeyRingCollection = new PGPPublicKeyRingCollection(Collections.emptyList());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PGPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for (Long publicKeyRingID : publicKeyRingCollectionIDs) {
				PGPPublicKeyRing publicKeyRing = ManageKeysController.getPublicKeyRing(publicKeyRingID);
				
				if (publicKeyRing == null) {
					//Window.showError("Bad public key");
					try {
						transmissionOutput.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					file.delete();
					return;
				}
				
				publicKeyRingCollection = PGPPublicKeyRingCollection.addPublicKeyRing(publicKeyRingCollection, publicKeyRing);
			}
			
			PGPEncryptedDataGenerator encryptedDataGenerator = new PGPEncryptedDataGenerator(
	                new JcePGPDataEncryptorBuilder(algorithm).setWithIntegrityPacket(true).setSecureRandom(new SecureRandom()).setProvider("BC"));

			for (PGPPublicKeyRing publicKeyRing : publicKeyRingCollection)
				encryptedDataGenerator.addMethod(new JcePublicKeyKeyEncryptionMethodGenerator(publicKeyRing.getPublicKey()).setProvider("BC"));
			
			try {
				transmissionOutput = encryptedDataGenerator.open(transmissionOutput, new byte[1]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PGPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Radix64
		if (toRadix) transmissionOutput = new ArmoredOutputStream(transmissionOutput);

		// Output
		PGPLiteralDataGenerator litelDataGenerator = new PGPLiteralDataGenerator();
        OutputStream literalData;
		try {
			literalData = litelDataGenerator.open(transmissionOutput, PGPLiteralData.BINARY, encryptionFile);
		
	        FileInputStream fileInputStream = new FileInputStream(encryptionFile);
	        int ch;
	        
	        while ((ch = fileInputStream.read()) >= 0)
	        {
	        	literalData.write(ch);
				if (toSign)
	        		signatureGenerator.update((byte)ch);
	        }
	        
			literalData.close();
			litelDataGenerator.close();
			
			if (toSign)
				signatureGenerator.generate().encode(transmissionOutput);
			
			
			transmissionOutput.close();
		} catch (IOException | PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static OutputStream encrypt(int algorithm, PGPPublicKeyRingCollection publicKeyRingCollection, OutputStream message) {
		OutputStream ret = null;
		PGPEncryptedDataGenerator encryptedDataGenerator = new PGPEncryptedDataGenerator(
                new JcePGPDataEncryptorBuilder(algorithm).setWithIntegrityPacket(true).setSecureRandom(new SecureRandom()).setProvider("BC"));

		for (PGPPublicKeyRing publicKeyRing : publicKeyRingCollection)
			encryptedDataGenerator.addMethod(new JcePublicKeyKeyEncryptionMethodGenerator(publicKeyRing.getPublicKey()).setProvider("BC"));
		
		try {
			ret = encryptedDataGenerator.open(message, new byte[1]);
			encryptedDataGenerator.close();
			return ret;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	private static OutputStream compress(OutputStream message) {
		OutputStream ret = null;
		PGPCompressedDataGenerator compressedDataGenerator = new PGPCompressedDataGenerator(CompressionAlgorithmTags.ZIP);
		try {
			ret = compressedDataGenerator.open(message);
			return ret;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
}
