package com.axacolpatria.pruebaxa.controller;


import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;
import com.axacolpatria.pruebaxa.service.ValidacionRegistroAxaService;


@Controller
@SessionAttributes("name")
public class GuardarRegistroAxaController {
	
	@Autowired
	ValidacionRegistroAxaService service;

 	@Autowired
	RestTemplate restTemplatew;
  	@Bean
	public RestTemplate restTemplatew(RestTemplateBuilder builder) {
		return builder.build();
	}


	
	@RequestMapping(value="/formulario", method = RequestMethod.GET)
	public String mostrarFormulario(ModelMap model){
		return "formulario";
	}


	@RequestMapping(value="/formulario", method = RequestMethod.POST)
	public String mostrarFormulario(ModelMap model,  @Valid @NotBlank @RequestParam String name, 
	@Valid @NotNull String age, @Valid @NotBlank @RequestParam String phoneNumber ,@Valid @NotBlank @RequestParam String address){
	
		try{

			boolean validaDireccion = service.validaDireccion(address);
			if (!validaDireccion) {
				model.put("errorMessage", "Direccion Invalida");
				return "formulario";
			}

			boolean isValidCampos = service.validaDatosNulosVacios(name, age,phoneNumber, address);
			if (!isValidCampos) {
				model.put("errorMessage", "Algunos campos esta vacios o nulos");
				return "formulario";
			}

			boolean isValidAge= service.validaAge(age);
			if (!isValidAge) {
				model.put("errorMessage", "Valor edad invalida");
				return "formulario";
			}

			boolean isValidTelefono= service.validaTelefono(phoneNumber);
			if (!isValidTelefono) {
				model.put("errorMessage", "Valor telefono celular invalido");
				return "formulario";
			}

			boolean isValidName= service.validaName(name);
			if (!isValidName) {
				model.put("errorMessage", "Valor name no es valido, no tiene formato camel case, debe comenzar con mayuscula");
				return "formulario";
			}
		}catch(Exception e){
			
					model.put("estado", ""+e.getMessage());
		}	

		JSONObject request = new JSONObject();
			try{

				request.put("username", name);
				request.put("age", age);
				request.put("phoneNumber", phoneNumber);
				request.put("address", address);
			}catch(Exception e){
				System.out.println("Error mostrarFormulario:"+e);
			}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);
		try{
			
				String urlString ="https://8e7c6b8a-fc46-4674-a529-4ebec57295d3.mock.pstmn.io/customers";
				ResponseEntity<String> responseEntity = restTemplatew
				.exchange(urlString, HttpMethod.POST, entity, String.class);

				if (responseEntity.getStatusCode() == HttpStatus.OK) {
					String result = responseEntity.getBody();
					char[] caracteres = result.toCharArray();
					for (int i = 0; i < caracteres.length; i++) {
					int valor= (int) caracteres[i];
						if ( valor == 8220 || valor == 8221){
							result=result.replace(Character.toString(caracteres[i]),"\"");
						}
					}

				result="["+result+"]";
				JSONObject object=new JSONObject();
				try{
					JSONArray array = new JSONArray(result);
			
					for(int i=0; i < array.length(); i++)   
					{  
						object = array.getJSONObject(i); 
						String estado=object.getString("estado");
						Integer codigo= Integer.valueOf(object.getString("codigo"));
						model.put("codigo", codigo);
						model.put("estado", estado);
						model.put("name", name);
						model.put("age", age);
						model.put("phoneNumber", phoneNumber);
						model.put("address", address);
					}  
				}catch(Exception e){
					System.out.println("Error Json:"+e);
				}

				}else if (responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED) {
					model.put("codigo", responseEntity.getStatusCode().toString());
					model.put("estado", "NO AUTORIZADO");
					}
				else if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND) {
					model.put("codigo", responseEntity.getStatusCode().toString());
					model.put("estado", "Record Not Found");
					}
				else if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
					model.put("codigo", responseEntity.getStatusCode().toString());
					model.put("estado", "Server Error");
					}
				else if (responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST) {
					model.put("codigo", responseEntity.getStatusCode().toString());
					model.put("estado", "Validation Failed");
					}
				else {
					model.put("codigo", responseEntity.getStatusCode().toString());
					model.put("estado", "Error generado al consumir servicio");

					}


		}catch(Exception e){
			
					model.put("estado", ""+e.getMessage());
		}	
				
		return "respuestapost";
	}

}

