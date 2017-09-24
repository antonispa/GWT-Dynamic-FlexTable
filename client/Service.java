package com.fishes.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("service")
public interface Service extends RemoteService {
	
	Login checkLoginData(String username, String password);
	FishTable getFishes();
	InsertFish insertFish(String fishId , String fishName , String fishPrice);
	DeleteFish deleteFish(String fishId);
	UpdateFish updateFish(String fishId , String fishName , String fishPrice);
	
}
