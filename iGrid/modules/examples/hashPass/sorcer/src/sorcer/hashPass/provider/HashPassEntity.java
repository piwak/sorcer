package sorcer.hashPass.provider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Random;

public class HashPassEntity implements Serializable {

	private String pass;
	private String hash;
	
	private static Map<String, String> cache = new HashMap<String, String>();

	public HashPassEntity(String pass, String hash) {
		if (pass != null) {
			this.pass = pass;
			this.hashPassword();
		} else if (hash != null) {
			this.hash = hash;
		}
	}

	public HashPassEntity() {
		this.pass = "";
		this.hash = "Click on Hash btn!";
	}

	public void hashPassword() {
		char[] chars = {'a', 'b', 'c', 'd', 'e' , 'f', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

		if(this.pass == null || this.pass.trim().equals("")) {
			this.hash = "Empty String!!!!!";
		} else if (cache.containsKey(this.pass)) {
			this.hash = cache.get(this.pass);
		} else {
			Random rand = new Random();
			String randHash = "";
			for(int i=0; i < 16; i++)
				randHash += chars[rand.nextInt(chars.length)];
			this.hash = randHash;
			cache.put(this.pass, this.hash);
		}
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}
}
