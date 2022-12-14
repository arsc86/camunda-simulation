package com.tesis.automation.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DatastoreDTO {
	private int capacity;
	private String datastore;
	private int free_space;
	private String name;
	private String type;
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public String getDatastore() {
		return datastore;
	}
	public void setDatastore(String datastore) {
		this.datastore = datastore;
	}
	public int getFree_space() {
		return free_space;
	}
	public void setFree_space(int free_space) {
		this.free_space = free_space;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
