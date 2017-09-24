package com.fishes.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ServiceAsync {
	
	void checkLoginData (String username , String password , AsyncCallback<Login> callback);

	void deleteFish(String fishId, AsyncCallback<DeleteFish> callback);

	void insertFish(String fishId, String fishName, String fishPrice, AsyncCallback<InsertFish> callback);

	void getFishes(AsyncCallback<FishTable> callback);

	void updateFish(String fishId, String fishName, String fishPrice, AsyncCallback<UpdateFish> callback);
	
}
