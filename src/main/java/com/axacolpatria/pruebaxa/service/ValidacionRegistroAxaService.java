package com.axacolpatria.pruebaxa.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;  
import java.util.regex.Pattern; 

@Service
public class ValidacionRegistroAxaService {


	public boolean validaDatosNulosVacios(String name, String age, String phoneNumber, String address) {
		boolean valid =true;
		if ( name==null || age==null || phoneNumber==null || address==null ||
		name.equals("") || age.equals("") || phoneNumber.equals("") || address.equals("")  ){
			valid =false;
		}
		return valid;
	}

	public boolean validaDireccion(String direccion) {
		boolean valid =true;
    	Pattern p = Pattern.compile("^[a-zA-Z]{1,5}\\s\\d{1,2}\\s\\#\\s\\d{1,2}[a-zA-Z]{1,3}\\s\\-\\s\\d{1,2}[a-zA-Z]{1,3}$");
		Matcher m;
		m = p.matcher(direccion);
		if(m.matches()){
			System.out.println("La direccion esta escrita correctamente");
			valid = true;
		}else{
			System.out.println("La direccion NO esta escrita correctamente");
			valid = false;
		}
		return valid;
	}

	public boolean validaTelefono(String telefono) {
		boolean valid =true;
    	Pattern p = Pattern.compile("^[0-9]{10}$");
		Matcher m;
		m = p.matcher(telefono);
		if(m.matches()){
			System.out.println("El telefono esta escrito correctamente");
			valid = true;
		}else{
			System.out.println("El telefono NO esta escrito correctamente");
			valid = false;
		}
		return valid;
	}

	public boolean validaAge(String age) {
		boolean valid =true;
    	Pattern p = Pattern.compile("^[0-9]{1,2}$");
		Matcher m;
		m = p.matcher(age);
		if(m.matches()){
			System.out.println("La edad esta escrito correctamente");
			valid = true;
		}else{
			System.out.println("La edad NO esta escrito correctamente");
			valid = false;
		}
		return valid;
	}

	public boolean validaName(String name) {
		boolean valid =true;
    	Pattern p = Pattern.compile("^[A-Z][a-z]+([A-Z][a-z0-9]+)*$");
		Matcher m;
		m = p.matcher(name.replace(" ",""));
		if(m.matches()){
			System.out.println("El nombre tiene formato camel case");
			valid = true;
		}else{
			System.out.println("El nombre NO tiene formato camel case");
			valid = false;
		}
		return valid;
	}



}