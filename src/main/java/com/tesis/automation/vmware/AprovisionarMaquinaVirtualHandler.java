package com.tesis.automation.vmware;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.tesis.automation.cons.VmwareConstants;
import com.tesis.automation.dto.FolderDTO;
import com.tesis.automation.dto.MaquinaVirtualDTO;
import com.tesis.automation.utils.CustomRestTemplate;
import com.tesis.automation.utils.Utils;

@Component
@ExternalTaskSubscription("crearMaquina")
public class AprovisionarMaquinaVirtualHandler implements ExternalTaskHandler
{
	@Autowired
	VmwareConstants vmwareConstants;
	
	@Autowired
	Utils utils;
	
	@Autowired
	CustomRestTemplate customRestTemplate;
	
	@Override
	public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) 
	{
		String className = "Proceso creacion maquina virtual";
		
		VariableMap variables = Variables.createVariables();
		
		String vmwareUri           = vmwareConstants.getVmwareSimulatorUrl();
		
		Map<String, Object> data = externalTask.getAllVariables();
		
		String dataSource            = (String)data.get("datasource");
		String resourcePool          = (String)data.get("resource-pool");
		String nombreMaquina         = (String) data.get("nombreMaquina");
		String sistemaOperativo      = (String) data.get("sistemaOperativo");
		String cluster               = (String) data.get("cluster");
		
		String getFolderUri       = vmwareUri  + vmwareConstants.getVmwFolderPath();
		String postHostUri        = vmwareUri  + vmwareConstants.getVmwHostPath();
		String postVMUri          = vmwareUri  + vmwareConstants.getVmwRelocateMaquinaVirtualPath().replace("{variable}", nombreMaquina);
		  
		Logger.getLogger(className).log(Level.INFO, "----------------------------------------------");
		Logger.getLogger(className).log(Level.INFO, "----------Aprovisionamiento de MVs-------------");
		Logger.getLogger(className).log(Level.INFO, "----------------------------------------------");
		
		Logger.getLogger(className).log(Level.INFO, "------------------------------------------------------");
		Logger.getLogger(className).log(Level.INFO, "----------- Asignacion de Directorio de MV------------");
		Logger.getLogger(className).log(Level.INFO, "------------------------------------------------------");
		
		Logger.getLogger(className).log(Level.INFO, "Obteniendo el Folder a asignar a la nueva Maquina Virtual");
		Logger.getLogger(className).log(Level.INFO,"");
		Logger.getLogger(className).log(Level.INFO, "METHOD GET " + getFolderUri);
		Logger.getLogger(className).log(Level.INFO,"");
		
		try
		{
			Thread.sleep(1000);
			
			RestTemplate restTemplate = customRestTemplate.getCustomRestTemplate();
			
			Object object = restTemplate.getForObject(getFolderUri,Object.class);
			
			Logger.getLogger(className).log(Level.INFO, "Folders registrados = " + object.toString());
			
			List<FolderDTO> folderDTOs = Utils.mapearListObjDeserializado(object, FolderDTO.class);
			
			if(folderDTOs==null || folderDTOs.size()==0)throw new Exception("No existe Folder a ser asiginada");
			
			FolderDTO selectedFolder = folderDTOs.get(0);
			
			Logger.getLogger(className).log(Level.INFO, "Folder disponible = [ name : {0} , type : {1}]", 
                    new Object[] { selectedFolder.getFolder(), selectedFolder.getType() });
			Logger.getLogger(className).log(Level.INFO,"");
			Logger.getLogger(className).log(Level.INFO, "------------------------------------------------------");
			Logger.getLogger(className).log(Level.INFO, "----------- Creando nuevo HOST para MV----------------");
			Logger.getLogger(className).log(Level.INFO, "------------------------------------------------------");
			
			Logger.getLogger(className).log(Level.INFO, "Creando un nuevo HOST");
			Logger.getLogger(className).log(Level.INFO, "Hostname : "+vmwareConstants.getHostname());
			Logger.getLogger(className).log(Level.INFO, "Username : "+vmwareConstants.getHostUsername());
			Logger.getLogger(className).log(Level.INFO, "Password : "+vmwareConstants.getHostPassword());
			Logger.getLogger(className).log(Level.INFO,"");
			Logger.getLogger(className).log(Level.INFO, "METHOD POST " + postHostUri);
			Logger.getLogger(className).log(Level.INFO,"");
			object = restTemplate.getForObject(getFolderUri,Object.class);
			
			Logger.getLogger(className).log(Level.INFO, "Host creado = " + object.toString());
			Logger.getLogger(className).log(Level.INFO, "Host disponible = [ hostname : {0} ]", 
                    new Object[] { vmwareConstants.getHostname() });
			Logger.getLogger(className).log(Level.INFO,"");
			
			Logger.getLogger(className).log(Level.INFO, "------------------------------------------------------");
			Logger.getLogger(className).log(Level.INFO, "----------- Creando nueva Maquina Virtual-------------");
			Logger.getLogger(className).log(Level.INFO, "------------------------------------------------------");
			Logger.getLogger(className).log(Level.INFO, "Datos de Maquina Virtual a ser creada:");
			Logger.getLogger(className).log(Level.INFO, "Name      : "+nombreMaquina);
			Logger.getLogger(className).log(Level.INFO, "Guest_OS  : "+sistemaOperativo);
			Logger.getLogger(className).log(Level.INFO, "Cluster   : "+cluster);
			Logger.getLogger(className).log(Level.INFO, "Datastore : "+dataSource);
			Logger.getLogger(className).log(Level.INFO, "Folder    : "+selectedFolder.getFolder());
			Logger.getLogger(className).log(Level.INFO, "Host      : "+vmwareConstants.getHostname());
			Logger.getLogger(className).log(Level.INFO, "Resource  : "+resourcePool);
			Logger.getLogger(className).log(Level.INFO,"");
			Logger.getLogger(className).log(Level.INFO, "METHOD POST " + postVMUri);
			Logger.getLogger(className).log(Level.INFO,"");
			
			object = restTemplate.getForObject(postVMUri,Object.class);
			Logger.getLogger(className).log(Level.INFO, "Maquina Virtual creada = " + object.toString());
			
			MaquinaVirtualDTO maquinaVirtualDTO = new MaquinaVirtualDTO();
			maquinaVirtualDTO.setCluster(cluster);
			maquinaVirtualDTO.setDatastore(dataSource);
			maquinaVirtualDTO.setFolder(selectedFolder.getFolder());
			maquinaVirtualDTO.setGuestSO(sistemaOperativo);
			maquinaVirtualDTO.setHost(vmwareConstants.getHostname());
			maquinaVirtualDTO.setName(nombreMaquina);
			maquinaVirtualDTO.setResource(resourcePool);
			
			variables.put("maquinaVirtual", maquinaVirtualDTO.getName());
			
			Thread.sleep(10000);
		}
		catch(Exception e)
		{
			Logger.getLogger(className).log(Level.WARNING, e.getMessage());
			variables.put("disponible", false);
			variables.put("error", e.getMessage());
			
		}
	
	    // complete the external task
	    externalTaskService.complete(externalTask, variables);
	}

}
