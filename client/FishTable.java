package com.fishes.client;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FishTable implements Serializable {
	
	public String[] idArray;
	public String[] nameArray;
	public String[] priceArray;
	
	public FishTable(){
		
	}
	
	public String[] getIdArray(){
		return this.idArray;
	}
	
	public String[] getNameArray(){
		return this.nameArray;
	}
	
	public String[] getPriceArray(){
		return this.priceArray;
	}
	
	public void setIdArray(String[] idArray){
		
		this.idArray = new String[idArray.length];
		
		for(int i = 0; i < idArray.length; i++){
			this.idArray[i] = idArray[i];
		}
	}
	
	public void setNameArray(String[] nameArray) {
		
		this.nameArray = new String[nameArray.length];
		
		for(int i = 0; i < nameArray.length; i++){
			this.nameArray[i] = nameArray[i];
		}
	}
	
	public void setPriceArray(String[] priceArray){
		
		this.priceArray = new String[priceArray.length];
		
		for(int i = 0; i < priceArray.length; i++){
			this.priceArray[i] = priceArray[i];
		}
	}
	
}
