package etf.openpgp.af18273dij18203d.front;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import etf.openpgp.af18273dij18203d.back.ManageKeysController;

public class Main {

	public static void main(String[] args) {
		Security.addProvider(new BouncyCastleProvider());
		ManageKeysController.init();
		WelcomeWindow.getInstance();
	}

}
