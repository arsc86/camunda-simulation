package com.tesis.automation.dto;

import lombok.Data;

@Data
public class MaquinaVirtualDTO {
	private String name;
	private String guestSO;
	private String cluster;
	private String datastore;
	private String folder;
	private String host;
	private String resource;
	
	public MaquinaVirtualDTO() {}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGuestSO() {
		return guestSO;
	}
	public void setGuestSO(String guestSO) {
		this.guestSO = guestSO;
	}
	public String getCluster() {
		return cluster;
	}
	public void setCluster(String cluster) {
		this.cluster = cluster;
	}
	public String getDatastore() {
		return datastore;
	}
	public void setDatastore(String datastore) {
		this.datastore = datastore;
	}
	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}

}
