package com.fishes.client;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Login implements Serializable {
	

	private int loginCount = 0;
	
	public Login(){
		
	}
	
	public int getLoginCount(){
		return this.loginCount;
	}
	
	public void setLoginCount(int loginCount){
		this.loginCount = loginCount;
	}
	
}
