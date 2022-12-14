package com.tesis.automation.cons;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class VmwareConstants {
	
	@Value("${vmware.vcenter.host}")
    public String vmwareSimulatorUrl;
	
	@Value("${vmware.vcenter.datastore.all.api}")
	public String vmwDatastorePath;
	
	@Value("${vmware.vcenter.cluster.api}")
	public String vmwClusterGetPath;
	
	@Value("${vmware.vcenter.resource-pool.api}")
	public String vmwResourcePoolGetPath;
	
	@Value("${vmware.vcenter.resource-pool.all.api}")
	public String vmwResourcePoolGetAllPath;
	
	@Value("${vmware.vcenter.folder.api}")
	public String vmwFolderPath;
	
	@Value("${vmware.vcenter.vm.api}")
	public String vmwMaquinaVirtualPath;
	
	@Value("${vmware.vcenter.vm.relocate.api}")
	public String vmwRelocateMaquinaVirtualPath;
	
	@Value("${vmware.vcenter.host.api}")
	public String vmwHostPath;
	
	@Value("${vmware.host.hostname}")
	public String hostname;
	
	@Value("${vmware.host.username}")
	public String hostUsername;
	
	@Value("${wmware.host.password}")
	public String hostPassword;

	public String getVmwareSimulatorUrl() {
		return vmwareSimulatorUrl;
	}

	public void setVmwareSimulatorUrl(String vmwareSimulatorUrl) {
		this.vmwareSimulatorUrl = vmwareSimulatorUrl;
	}

	public String getVmwDatastorePath() {
		return vmwDatastorePath;
	}

	public void setVmwDatastorePath(String vmwDatastorePath) {
		this.vmwDatastorePath = vmwDatastorePath;
	}

	public String getVmwClusterGetPath() {
		return vmwClusterGetPath;
	}

	public void setVmwClusterGetPath(String vmwClusterGetPath) {
		this.vmwClusterGetPath = vmwClusterGetPath;
	}

	public String getVmwResourcePoolGetPath() {
		return vmwResourcePoolGetPath;
	}

	public void setVmwResourcePoolGetPath(String vmwResourcePoolGetPath) {
		this.vmwResourcePoolGetPath = vmwResourcePoolGetPath;
	}

	public String getVmwResourcePoolGetAllPath() {
		return vmwResourcePoolGetAllPath;
	}

	public void setVmwResourcePoolGetAllPath(String vmwResourcePoolGetAllPath) {
		this.vmwResourcePoolGetAllPath = vmwResourcePoolGetAllPath;
	}

	public String getVmwFolderPath() {
		return vmwFolderPath;
	}

	public void setVmwFolderPath(String vmwFolderPath) {
		this.vmwFolderPath = vmwFolderPath;
	}

	public String getVmwMaquinaVirtualPath() {
		return vmwMaquinaVirtualPath;
	}

	public void setVmwMaquinaVirtualPath(String vmwMaquinaVirtualPath) {
		this.vmwMaquinaVirtualPath = vmwMaquinaVirtualPath;
	}

	public String getVmwRelocateMaquinaVirtualPath() {
		return vmwRelocateMaquinaVirtualPath;
	}

	public void setVmwRelocateMaquinaVirtualPath(String vmwRelocateMaquinaVirtualPath) {
		this.vmwRelocateMaquinaVirtualPath = vmwRelocateMaquinaVirtualPath;
	}

	public String getVmwHostPath() {
		return vmwHostPath;
	}

	public void setVmwHostPath(String vmwHostPath) {
		this.vmwHostPath = vmwHostPath;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getHostUsername() {
		return hostUsername;
	}

	public void setHostUsername(String hostUsername) {
		this.hostUsername = hostUsername;
	}

	public String getHostPassword() {
		return hostPassword;
	}

	public void setHostPassword(String hostPassword) {
		this.hostPassword = hostPassword;
	}
	
	
}
