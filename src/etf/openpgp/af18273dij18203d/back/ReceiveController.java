package etf.openpgp.af18273dij18203d.back;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchProviderException;
import java.util.Iterator;

import org.bouncycastle.bcpg.ArmoredInputStream;
import org.bouncycastle.openpgp.PGPCompressedData;
import org.bouncycastle.openpgp.PGPEncryptedData;
import org.bouncycastle.openpgp.PGPEncryptedDataList;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPLiteralData;
import org.bouncycastle.openpgp.PGPMarker;
import org.bouncycastle.openpgp.PGPOnePassSignature;
import org.bouncycastle.openpgp.PGPOnePassSignatureList;
import org.bouncycastle.openpgp.PGPPrivateKey;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyEncryptedData;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.PGPSecretKey;
import org.bouncycastle.openpgp.PGPSignatureList;
import org.bouncycastle.openpgp.PGPUtil;
import org.bouncycastle.openpgp.jcajce.JcaPGPObjectFactory;
import org.bouncycastle.openpgp.operator.jcajce.JcaKeyFingerprintCalculator;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPContentVerifierBuilderProvider;
import org.bouncycastle.openpgp.operator.jcajce.JcePBESecretKeyDecryptorBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePublicKeyDataDecryptorFactoryBuilder;
import org.bouncycastle.util.io.Streams;

import etf.openpgp.af18273dij18203d.util.DecypheredInfo;

public class ReceiveController {

	private File file;
	private DecypheredInfo info;
	private File publicKeys;
	private File privateKeys;
	private String password = null;

	public ReceiveController(File file, String password) {
		this.file = file;
		this.info = new DecypheredInfo();
		this.password = password;
		this.publicKeys = new File(ManageKeysController.PUBLIC_KEY_RING_COLLECTION_PATH);
		this.privateKeys = new File(ManageKeysController.SECRET_KEY_RING_COLLECTION_PATH);
	}

	private boolean decryptFile() throws IOException, NoSuchProviderException {

		// Radix check
		InputStream in = PGPUtil.getDecoderStream(new FileInputStream(file));

		if (in instanceof ArmoredInputStream) {
			this.info.setRadixStatus(true);
			this.info.setRadixSuccess(true);
		} else {
			this.info.setRadixStatus(false);
		}

			JcaPGPObjectFactory pgpFact = new JcaPGPObjectFactory(in);
			PGPEncryptedDataList enc;
			PGPPrivateKey sKey = null;
			PGPPublicKeyEncryptedData pbe = null;
			Object o = pgpFact.nextObject();

			this.info.setDecryptionStatus(false);
			// PGP Marker check

			if (o instanceof PGPMarker) {
				o = pgpFact.nextObject();
			}
			// Decryption check
			else if (o instanceof PGPEncryptedDataList) {
				this.info.setDecryptionStatus(true);
				enc = (PGPEncryptedDataList) o;
				Iterator<PGPEncryptedData> it = enc.getEncryptedDataObjects();

				while (sKey == null && it.hasNext()) {
					pbe = (PGPPublicKeyEncryptedData) it.next();

					PGPSecretKey pgpSecKey = ManageKeysController.getSecretKeyRing(pbe.getKeyID()).getSecretKey();

					if (pgpSecKey != null) {
						this.info.setPrivatekey(new KeyInfoWrapper(pgpSecKey.getUserIDs().next(), "", pgpSecKey.getKeyID()));
						try {
							sKey = pgpSecKey.extractPrivateKey(
									new JcePBESecretKeyDecryptorBuilder().setProvider("BC").build(password.toCharArray()));
						} catch (PGPException e) {
							System.out.println("Wrong password.");
							//Window.showError("Wrong pass");
							return false;
						}
					}
					
				}
				
//				if (sKey == null) {
//					throw new IllegalArgumentException("Secret key for message not found.");
//				}
				
				InputStream decryptedStream = null;
				try {
					decryptedStream = pbe
							.getDataStream(new JcePublicKeyDataDecryptorFactoryBuilder().setProvider("BC").build(sKey));
				} catch (PGPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				pgpFact = new JcaPGPObjectFactory(decryptedStream);

				o = pgpFact.nextObject();

				this.info.setDecryptionSuccess(true);
			}
			// Compression check
			this.info.setCompressionStatus(false);
			if (o instanceof PGPCompressedData) {
				this.info.setCompressionStatus(true);
				PGPCompressedData cData = (PGPCompressedData) o;
				try {
					pgpFact = new JcaPGPObjectFactory(cData.getDataStream());
				} catch (PGPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				o = pgpFact.nextObject();
				this.info.setCompressionSuccess(true);
			}

			// Signature check

			if (o instanceof PGPOnePassSignatureList) {

				this.info.setVerificationStatus(true);
				PGPOnePassSignatureList lsig = (PGPOnePassSignatureList) o;

				PGPOnePassSignature sig = lsig.get(0);

				PGPPublicKeyRingCollection pgpRing = null;
				PGPPublicKey key = null;
				try {
					pgpRing = new PGPPublicKeyRingCollection(
							PGPUtil.getDecoderStream(new FileInputStream(publicKeys)), new JcaKeyFingerprintCalculator());

					key = pgpRing.getPublicKey(sig.getKeyID());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (PGPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
				String[] identity = key.getUserIDs().next().split(" ");
				this.info.setPublickey(new KeyInfoWrapper(identity[0], identity[1], key.getKeyID()));
				
				try {
					sig.init(new JcaPGPContentVerifierBuilderProvider().setProvider("BC"), key);
				} catch (PGPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				PGPLiteralData ld = (PGPLiteralData) pgpFact.nextObject();

				InputStream dIn = ld.getInputStream();
				File tmp = new File(ld.getFileName());

				int ch;
				FileOutputStream out = new FileOutputStream(tmp);

				while ((ch = dIn.read()) >= 0) {
					sig.update((byte) ch);
					out.write(ch);
				}

				out.close();
				this.info.setDecryptedFile(tmp);
				PGPSignatureList vsig = (PGPSignatureList) pgpFact.nextObject();

				try {
					if (sig.verify(vsig.get(0))) {
						this.info.setVerificationSuccess(true);
						System.out.println("Signature verified.");

					} else {
						this.info.setVerificationSuccess(false);
						System.err.println("Signature verification failed.");
					}
				} catch (PGPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (o instanceof PGPLiteralData) {
				this.info.setVerificationStatus(false);
				PGPLiteralData ld = (PGPLiteralData) o;

				String outFileName = ld.getFileName();
				if (outFileName.length() == 0) {
					outFileName = this.file.getName();
				}
				File tmp = new File(outFileName);
				InputStream dIn = ld.getInputStream();
				OutputStream out = new FileOutputStream(outFileName);

//				Streams.pipeAll(dIn, out, 8192);

				out.close();

				this.info.setDecryptedFile(tmp);
			}

			this.info.setIntegritySuccess(true);
//			if (pbe != null && pbe.isIntegrityProtected()) {
//				this.info.setIntegrityStatus(true);
//				if (!pbe.verify()) {
//					System.err.println("Message failed integrity check");
//					this.info.setIntegritySuccess(false);
//				} else {
//					this.info.setIntegritySuccess(true);
//					System.out.println("Message integrity check passed");
//				}
//			} else {
//				this.info.setIntegrityStatus(false);
//				System.err.println("No message integrity check");
//			}
//		} catch (PGPException e) {
//			this.info.setDecryptionSuccess(false);
//			System.err.println(e);
//			if (e.getUnderlyingException() != null) {
//				e.getUnderlyingException().printStackTrace();
//			}
//			return false;
//		}
		return true;
	}

	public DecypheredInfo receive() throws Exception {

		boolean result = decryptFile();
		if (result) {
			return this.info;
		}
		return null;
	}

}
