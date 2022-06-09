package etf.openpgp.af18273dij18203d.util;

import java.io.File;

import etf.openpgp.af18273dij18203d.back.KeyInfoWrapper;

public class DecypheredInfo {
	private File decryptedFile;
	private boolean verificationStatus;
	private boolean verificationSuccess;
	private boolean compressionStatus;
	private boolean compressionSuccess;
	private boolean decryptionStatus;
	private boolean decryptionSuccess;
	private boolean radixStatus;
	private boolean radixSuccess;
	private boolean integrityStatus;
	private boolean integritySuccess;
	private KeyInfoWrapper publickey;
	private KeyInfoWrapper privatekey;
	
//	public DecypheredInfo(String name) {
//		this.decryptedFile = new File(name);
//	}
	
	public File getDecryptedFile() {
		return decryptedFile;
	}
	public void setDecryptedFile(File decryptedFile) {
		this.decryptedFile = decryptedFile;
	}
	public boolean isVerificationStatus() {
		return verificationStatus;
	}
	public void setVerificationStatus(boolean verificationStatus) {
		this.verificationStatus = verificationStatus;
	}
	public boolean isVerificationSuccess() {
		return verificationSuccess;
	}
	public void setVerificationSuccess(boolean verificationSuccess) {
		this.verificationSuccess = verificationSuccess;
	}
	public boolean isCompressionStatus() {
		return compressionStatus;
	}
	public void setCompressionStatus(boolean compressionStatus) {
		this.compressionStatus = compressionStatus;
	}
	public boolean isCompressionSuccess() {
		return compressionSuccess;
	}
	public void setCompressionSuccess(boolean compressionSuccess) {
		this.compressionSuccess = compressionSuccess;
	}
	public boolean isDecryptionStatus() {
		return decryptionStatus;
	}
	public void setDecryptionStatus(boolean decryptionStatus) {
		this.decryptionStatus = decryptionStatus;
	}
	public boolean isDecryptionSuccess() {
		return decryptionSuccess;
	}
	public void setDecryptionSuccess(boolean decryptionSuccess) {
		this.decryptionSuccess = decryptionSuccess;
	}
	public boolean isRadixStatus() {
		return radixStatus;
	}
	public void setRadixStatus(boolean radixStatus) {
		this.radixStatus = radixStatus;
	}
	public boolean isRadixSuccess() {
		return radixSuccess;
	}
	public void setRadixSuccess(boolean radixSuccess) {
		this.radixSuccess = radixSuccess;
	}
	public boolean isIntegrityStatus() {
		return integrityStatus;
	}
	public void setIntegrityStatus(boolean integrityStatus) {
		this.integrityStatus = integrityStatus;
	}
	public boolean isIntegritySuccess() {
		return integritySuccess;
	}
	public void setIntegritySuccess(boolean integritySuccess) {
		this.integritySuccess = integritySuccess;
	}
	public KeyInfoWrapper getPublickey() {
		return publickey;
	}
	public void setPublickey(KeyInfoWrapper publickey) {
		this.publickey = publickey;
	}
	public KeyInfoWrapper getPrivatekey() {
		return privatekey;
	}
	public void setPrivatekey(KeyInfoWrapper privatekey) {
		this.privatekey = privatekey;
	}
	
	
}
