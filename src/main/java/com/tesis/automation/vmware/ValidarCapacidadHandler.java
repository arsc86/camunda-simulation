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
import com.tesis.automation.dto.DatastoreDTO;
import com.tesis.automation.dto.ResourcePoolCollectionDTO;
import com.tesis.automation.dto.ResourcePoolDTO;
import com.tesis.automation.utils.CustomRestTemplate;
import com.tesis.automation.utils.Utils;

@Component
@ExternalTaskSubscription("validarCapacidad")
public class ValidarCapacidadHandler implements ExternalTaskHandler 
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
		Map<String, Object> data = externalTask.getAllVariables();
		VariableMap variables = Variables.createVariables();

		String className = "Proceso Validar capacidades";

		String vmwareUri           = vmwareConstants.getVmwareSimulatorUrl();

		Logger.getLogger(className).log(Level.INFO, "----------------------------------------------");
		Logger.getLogger(className).log(Level.INFO, "----------Validaciones de capacidades---------");
		Logger.getLogger(className).log(Level.INFO, "----------------------------------------------");
		Logger.getLogger(className).log(Level.INFO, "Informacion de Maquina Virtual a validar capacidades :");

		String tipoDisco        = (String) data.get("tipoDisco");
		int cantidadDisco       = (int) data.get("cantidadDisco");
		String tipoMemoria      = (String) data.get("tipoMemoria");
		int cantidadMemoria     = (int) data.get("cantidadMemoria");
		String tipoProcesador   = (String) data.get("tipoProcesador");
		int cantidadProcesador  = (int) data.get("cantidadProcesador");
		String nombreMaquina    = (String) data.get("nombreMaquina");
		String sistemaOperativo = (String) data.get("sistemaOperativo");

		Logger.getLogger(className).log(Level.INFO, "Nombre Maquina : {0}", new Object[] { nombreMaquina });
		Logger.getLogger(className).log(Level.INFO, "Sistema Operativo : {0}", new Object[] { sistemaOperativo });
		Logger.getLogger(className).log(Level.INFO, "Disco");
		Logger.getLogger(className).log(Level.INFO, "--- Tipo: {0}", new Object[] { tipoDisco });
		Logger.getLogger(className).log(Level.INFO, "--- Cantidad: {0}", new Object[] { cantidadDisco });
		Logger.getLogger(className).log(Level.INFO, "Memoria");
		Logger.getLogger(className).log(Level.INFO, "--- Tipo: {0}", new Object[] { tipoMemoria });
		Logger.getLogger(className).log(Level.INFO, "--- Cantidad: {0}", new Object[] { cantidadMemoria });
		Logger.getLogger(className).log(Level.INFO, "Procesador");
		Logger.getLogger(className).log(Level.INFO, "--- Tipo: {0}", new Object[] { tipoProcesador });
		Logger.getLogger(className).log(Level.INFO, "--- Cantidad: {0}", new Object[] { cantidadProcesador });
		Logger.getLogger(className).log(Level.INFO,"");
		Logger.getLogger(className).log(Level.INFO, "----------------------------------------------");
		Logger.getLogger(className).log(Level.INFO, "--------Verificando capacidad de disco--------");
		Logger.getLogger(className).log(Level.INFO, "----------------------------------------------");
		Logger.getLogger(className).log(Level.INFO, "Obtiendo los datastore disponibles...");
		
		String getDatastoreUri       = vmwareUri  + vmwareConstants.getVmwDatastorePath();
		String getResourcePoolAllUri = vmwareUri  + vmwareConstants.getVmwResourcePoolGetAllPath();
		String getResourcePoolUri    = vmwareUri  + vmwareConstants.getVmwResourcePoolGetPath();
		Logger.getLogger(className).log(Level.INFO,"");
		Logger.getLogger(className).log(Level.INFO, "METHOD GET " + getDatastoreUri);
		Logger.getLogger(className).log(Level.INFO,"");
		
		try 
		{
			Thread.sleep(1000);
			
			RestTemplate restTemplate = customRestTemplate.getCustomRestTemplate();
			
			Object object = restTemplate.getForObject(getDatastoreUri,Object.class);
			
			List<DatastoreDTO> datastoreDTOs = Utils.mapearListObjDeserializado(object,DatastoreDTO.class);
			
			Logger.getLogger(className).log(Level.INFO, "Datastores registrados = " + object.toString());
			Logger.getLogger(className).log(Level.INFO, "Validando capacidades de disco...");
			
			DatastoreDTO selectedDatastore    = null;
			ResourcePoolDTO selectResoPoolDTO = null;
			
			for(DatastoreDTO ds: datastoreDTOs)
			{
				if(ds.getFree_space() > cantidadDisco) 
				{
					selectedDatastore = ds;
					break;
				}
			}
			
			if(selectedDatastore == null) throw new Exception("No existe disponibilidad de disco");
			
			Logger.getLogger(className).log(Level.INFO, "Datastore disponibile = [ name : {0} , freeSpace : {1} GB]", 
					                        new Object[] { selectedDatastore.getName(), selectedDatastore.getFree_space() });
			
			Thread.sleep(1000);
			Logger.getLogger(className).log(Level.INFO,"");
			Logger.getLogger(className).log(Level.INFO, "------------------------------------------------------");
			Logger.getLogger(className).log(Level.INFO, "--------Verificando capacidad de memoria y CPU--------");
			Logger.getLogger(className).log(Level.INFO, "------------------------------------------------------");
			Logger.getLogger(className).log(Level.INFO, "Obtiendo los recursos disponibles...");
			Logger.getLogger(className).log(Level.INFO,"");
			Logger.getLogger(className).log(Level.INFO, "METHOD GET " + getResourcePoolAllUri);
			Logger.getLogger(className).log(Level.INFO,"");
			
			object = restTemplate.getForObject(getResourcePoolAllUri,Object.class);
			
			List<ResourcePoolCollectionDTO> resourcePoolCollectionDTOs = Utils.mapearListObjDeserializado(object, ResourcePoolCollectionDTO.class);
			
			Logger.getLogger(className).log(Level.INFO, "Resources Pool Lista registrada = " + object.toString());
			Logger.getLogger(className).log(Level.INFO, "Validando Recursos individuales");
			
			for(ResourcePoolCollectionDTO ds: resourcePoolCollectionDTOs)
			{
				String uri = getResourcePoolUri.replace("{variable}", ds.getName());
				Logger.getLogger(className).log(Level.INFO,"");
				Logger.getLogger(className).log(Level.INFO, "METHOD GET " + uri);
				Logger.getLogger(className).log(Level.INFO,"");
				object = restTemplate.getForObject(uri,Object.class);
				ResourcePoolDTO rs = Utils.mapearObjDeserializado(object, ResourcePoolDTO.class);
				
				Logger.getLogger(className).log(Level.INFO, "Resources Pool information = " + object.toString());
				
				int memoryAllocate = rs.getMemory_allocation().getLimit();
				int cpuAllocate    = rs.getCpu_allocation().getLimit();
				
				if(memoryAllocate > cantidadMemoria && cpuAllocate > cantidadProcesador) 
				{
					Logger.getLogger(className).log(Level.INFO, "Resource Pool disponible = [ name : {0} , cpu : {1} GB, memory : {2} GB]", 
	                        new Object[] { rs.getName(),cpuAllocate, memoryAllocate });
					selectResoPoolDTO = rs;
					break;
				}
				else throw new Exception("No existe disponibilidad de recursos de memoria/procesador");
			}
			
			variables.put("disponible",true);
			variables.put("datastore", selectedDatastore.getDatastore());
			variables.put("resource-pool", selectResoPoolDTO.getName());
			
			Logger.getLogger(className).log(Level.INFO, "Capacidades validades correctamente");
			Logger.getLogger(className).log(Level.INFO,"");
			
			Thread.sleep(10000);
		} 
		catch (Exception e1) 
		{
			Logger.getLogger(className).log(Level.WARNING, e1.getMessage());
			variables.put("disponible", false);
			variables.put("error", e1.getMessage());
		}

		// complete the external task
		externalTaskService.complete(externalTask, variables);
	}

}
