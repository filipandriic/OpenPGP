package etf.openpgp.af18273dij18203d.back;

public class KeyInfoWrapper {
	private String name;
	private String email;
	private long keyID;
	
	public KeyInfoWrapper(String name_, String email_, long keyID_) {
		name = name_;
		email = email_;
		keyID = keyID_;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getKeyID() {
		return keyID;
	}

	public void setKeyID(long keyID) {
		this.keyID = keyID;
	}

	@Override
	public String toString() {
		return name + " | " + email + " | " + keyID;
	}
	
	
}
