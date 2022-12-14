package com.tesis.automation.dto;

import lombok.Data;

@Data
public class ResourcePoolDTO {
	private ResourceAllocationDTO cpu_allocation;
	private ResourceAllocationDTO memory_allocation;
	private String name;
	public ResourceAllocationDTO getCpu_allocation() {
		return cpu_allocation;
	}
	public void setCpu_allocation(ResourceAllocationDTO cpu_allocation) {
		this.cpu_allocation = cpu_allocation;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ResourceAllocationDTO getMemory_allocation() {
		return memory_allocation;
	}
	public void setMemory_allocation(ResourceAllocationDTO memory_allocation) {
		this.memory_allocation = memory_allocation;
	}
	
	public class ResourceAllocationDTO {
		
		private String expandable_reservation;
		private int limit;
		private int reservation;
		public String getExpandable_reservation() {
			return expandable_reservation;
		}
		public void setExpandable_reservation(String expandable_reservation) {
			this.expandable_reservation = expandable_reservation;
		}
		public int getLimit() {
			return limit;
		}
		public void setLimit(int limit) {
			this.limit = limit;
		}
		public int getReservation() {
			return reservation;
		}
		public void setReservation(int reservation) {
			this.reservation = reservation;
		}
}

  
	
	

}
