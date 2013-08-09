package com.inovagro.inovagrofdc1;

public class ComboRowData {
	String Caption;
	int ID;
	int ParentID;
	
	ComboRowData(String c, int id, int parent){
		Caption=c;
		ID=id;
		ParentID=parent;
	}
	public String toString(){
		return Caption;
	}
}
