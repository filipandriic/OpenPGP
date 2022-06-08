package etf.openpgp.af18273dij18203d.back;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.bouncycastle.bcpg.ArmoredInputStream;
import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.bcpg.HashAlgorithmTags;
import org.bouncycastle.openpgp.PGPEncryptedData;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPKeyPair;
import org.bouncycastle.openpgp.PGPKeyRingGenerator;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.PGPSecretKeyRing;
import org.bouncycastle.openpgp.PGPSecretKeyRingCollection;
import org.bouncycastle.openpgp.PGPSignature;
import org.bouncycastle.openpgp.operator.PGPDigestCalculator;
import org.bouncycastle.openpgp.operator.bc.BcKeyFingerprintCalculator;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPContentSignerBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPDigestCalculatorProviderBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPKeyPair;
import org.bouncycastle.openpgp.operator.jcajce.JcePBESecretKeyDecryptorBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePBESecretKeyEncryptorBuilder;

import etf.openpgp.af18273dij18203d.front.ManageKeysWindow;

public class ManageKeysController {
	
	
	
	private static String PUBLIC_KEY_RING_COLLECTION_PATH = "public_key_ring_collection.asc";
	private static String SECRET_KEY_RING_COLLECTION_PATH = "secret_key_ring_collection.asc";
	
	private static PGPPublicKeyRingCollection publicKeyRingCollection;
	private static PGPSecretKeyRingCollection secretKeyRingCollection;
	
	public static PGPPublicKeyRingCollection getPublicKeyRingCollection() {
		return publicKeyRingCollection;
	}

	public static PGPSecretKeyRingCollection getSecretKeyRingCollection() {
		return secretKeyRingCollection;
	}

	private static BcKeyFingerprintCalculator keyFingerprintCalculator = new BcKeyFingerprintCalculator();
	
	public static void init() {
		try {
			File publicKeyRingCollectionFile = new File(PUBLIC_KEY_RING_COLLECTION_PATH);
			File secretKeyRingCollectionFile = new File(SECRET_KEY_RING_COLLECTION_PATH);
			
			if (publicKeyRingCollectionFile.exists())
				publicKeyRingCollection = new PGPPublicKeyRingCollection(new ArmoredInputStream(new FileInputStream(publicKeyRingCollectionFile)), keyFingerprintCalculator);
			else publicKeyRingCollection = new PGPPublicKeyRingCollection(Collections.emptyList());
			
			if (secretKeyRingCollectionFile.exists())
				secretKeyRingCollection = new PGPSecretKeyRingCollection(new ArmoredInputStream(new FileInputStream(secretKeyRingCollectionFile)), keyFingerprintCalculator);
			else secretKeyRingCollection = new PGPSecretKeyRingCollection(Collections.emptyList());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static PGPPublicKeyRing getPublicKeyRing(long keyID) {
		try {
			PGPPublicKeyRing publicKeyRing = publicKeyRingCollection.getPublicKeyRing(keyID);
			return publicKeyRing;
		} catch (PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static PGPSecretKeyRing getSecretKeyRing(long keyID) {
		try {
			PGPSecretKeyRing secretKeyRing = secretKeyRingCollection.getSecretKeyRing(keyID);
			return secretKeyRing;
		} catch (PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<KeyInfoWrapper> loadSecretKeyRingCollection() {
		List<KeyInfoWrapper> secretKeyRings = new LinkedList<>();
		
		for (PGPSecretKeyRing secretKeyRing : secretKeyRingCollection) {
			secretKeyRings.add(parseSecretKeyRing(secretKeyRing));
		}
		
		return secretKeyRings;
	}
	
	public static List<KeyInfoWrapper> loadPublicKeyRingCollection() {
		List<KeyInfoWrapper> publicKeyRings = new LinkedList<>();
		
		for (PGPPublicKeyRing publicKeyRing : publicKeyRingCollection) {
			publicKeyRings.add(parsePublicKeyRing(publicKeyRing));
		}
		
		return publicKeyRings;
	}
	
	public static void importPublicKeyRings(File file) {
		PGPPublicKeyRingCollection publicKeyRingCollectionFromFile = null;
		try {
			if (file.exists()) {
				publicKeyRingCollectionFromFile = new PGPPublicKeyRingCollection(new ArmoredInputStream(new FileInputStream(file)), keyFingerprintCalculator);
				
				for (PGPPublicKeyRing publicKeyRing : publicKeyRingCollectionFromFile)
					if (publicKeyRingCollection.getPublicKey(publicKeyRing.getPublicKey().getKeyID()) == null)
						publicKeyRingCollection = PGPPublicKeyRingCollection.addPublicKeyRing(publicKeyRingCollection, publicKeyRing);
					else ManageKeysWindow.showError("Can't import that.");
				
				exportPublicKeyRingCollection();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PGPException e) {
			ManageKeysWindow.showError("Can't import that.");
		}
	}
	
	public static void importSecretKeyRings(File file) {
		PGPSecretKeyRingCollection secretKeyRingCollectionFromFile = null;
		try {
			if (file.exists()) {
				secretKeyRingCollectionFromFile = new PGPSecretKeyRingCollection(new ArmoredInputStream(new FileInputStream(file)), keyFingerprintCalculator);
				
				for (PGPSecretKeyRing secretKeyRing : secretKeyRingCollectionFromFile)
					if (secretKeyRingCollection.getSecretKeyRing(secretKeyRing.getSecretKey().getKeyID()) == null)
						secretKeyRingCollection = PGPSecretKeyRingCollection.addSecretKeyRing(secretKeyRingCollection, secretKeyRing);
					else ManageKeysWindow.showError("Can't import that.");
				
				exportSecretKeyRingCollection();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PGPException e) {
			ManageKeysWindow.showError("Can't import that.");
		}
	}
	
	private static KeyInfoWrapper parsePublicKeyRing(PGPPublicKeyRing publicKeyRing) {
		String identity[] = publicKeyRing.getPublicKey().getUserIDs().next().split(" ");
		return new KeyInfoWrapper(identity[0], identity[1], publicKeyRing.getPublicKey().getKeyID());
	}
	
	private static KeyInfoWrapper parseSecretKeyRing(PGPSecretKeyRing secretKeyRing) {
		String identity[] = secretKeyRing.getSecretKey().getUserIDs().next().split(" ");
		return new KeyInfoWrapper(identity[0], identity[1], secretKeyRing.getSecretKey().getKeyID());
	}
	
	public static void generateKeys(String name, String email, String password, int keySize) throws Exception {
		KeyPairGenerator keyPairGenerator = null;
		try {
			keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (keyPairGenerator == null) throw new Exception("KeyPairGenerator not initialized");
        
		keyPairGenerator.initialize(keySize);
        
        KeyPair pair = keyPairGenerator.generateKeyPair();
        
        PGPDigestCalculator sha1Calc = new JcaPGPDigestCalculatorProviderBuilder().build().get(HashAlgorithmTags.SHA1);
        PGPKeyPair keyPair = new JcaPGPKeyPair(PGPPublicKey.RSA_GENERAL, pair, new Date());
        String identity = name + " " + email;
        
        PGPKeyRingGenerator keyRingGenerator = new PGPKeyRingGenerator(PGPSignature.POSITIVE_CERTIFICATION, keyPair,
                identity, sha1Calc, null, null, new JcaPGPContentSignerBuilder(keyPair.getPublicKey().getAlgorithm(), HashAlgorithmTags.SHA1), new JcePBESecretKeyEncryptorBuilder(PGPEncryptedData.AES_256, sha1Calc).setProvider("BC").build(password.toCharArray()));
       
        PGPPublicKeyRing publicKeyRing = keyRingGenerator.generatePublicKeyRing();
        PGPSecretKeyRing secretKeyRing = keyRingGenerator.generateSecretKeyRing();
        
        
        publicKeyRingCollection = PGPPublicKeyRingCollection.addPublicKeyRing(publicKeyRingCollection, publicKeyRing);
        secretKeyRingCollection = PGPSecretKeyRingCollection.addSecretKeyRing(secretKeyRingCollection, secretKeyRing);
        
        exportKeyRingCollections();
	}
	
	private static void exportKeyRingCollections()
    {
		exportPublicKeyRingCollection();
        exportSecretKeyRingCollection();
    }
	
	private static void exportSecretKeyRingCollection() {
		ArmoredOutputStream privateKeysOutput = null;
		try {
			privateKeysOutput = new ArmoredOutputStream(new FileOutputStream(new File(SECRET_KEY_RING_COLLECTION_PATH)));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        try {
        	for (PGPSecretKeyRing secretKeyRing : secretKeyRingCollection)
        		secretKeyRing.encode(privateKeysOutput);
			privateKeysOutput.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	private static void exportPublicKeyRingCollection() {
		ArmoredOutputStream publicKeysOutput = null;
		
		try {
			publicKeysOutput = new ArmoredOutputStream(new FileOutputStream(new File(PUBLIC_KEY_RING_COLLECTION_PATH)));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        try {
			for (PGPPublicKeyRing publicKeyRing : publicKeyRingCollection)
				publicKeyRing.encode(publicKeysOutput);
	        publicKeysOutput.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void deletePublicKeyRing(long keyID) {
		try {
			PGPPublicKeyRing publicKeyRing = publicKeyRingCollection.getPublicKeyRing(keyID);
			publicKeyRingCollection = PGPPublicKeyRingCollection.removePublicKeyRing(publicKeyRingCollection, publicKeyRing);
			
			exportPublicKeyRingCollection();
		} catch (PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void deleteSecretKeyRing(long keyID, String password) {
		try {
			PGPSecretKeyRing secretKeyRing = secretKeyRingCollection.getSecretKeyRing(keyID);
			secretKeyRing.getSecretKey().extractPrivateKey(new JcePBESecretKeyDecryptorBuilder().setProvider("BC").build(password.toCharArray()));
			
			secretKeyRingCollection = PGPSecretKeyRingCollection.removeSecretKeyRing(secretKeyRingCollection, secretKeyRing);
			exportSecretKeyRingCollection();
			ManageKeysWindow.disableError();
		} catch (PGPException e) {
			ManageKeysWindow.showError("Wrong password");
		}
	}
	
	public static void exportPublicKeyRing(long keyID) {
		ArmoredOutputStream publicKeyOutput = null;
		
		try {
			publicKeyOutput = new ArmoredOutputStream(new FileOutputStream(new File("public_key_" + keyID + ".asc")));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        try {
        	PGPPublicKeyRing publicKeyRing = publicKeyRingCollection.getPublicKeyRing(keyID);
			publicKeyRing.encode(publicKeyOutput);
	        publicKeyOutput.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void exportSecretKeyRing(long keyID) {
		ArmoredOutputStream secretKeyOutput = null;
		
		try {
			secretKeyOutput = new ArmoredOutputStream(new FileOutputStream(new File("secret_key_" + keyID + ".asc")));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        try {
        	PGPSecretKeyRing secretKeyRing = secretKeyRingCollection.getSecretKeyRing(keyID);
        	secretKeyRing.encode(secretKeyOutput);
	        secretKeyOutput.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

