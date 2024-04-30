package com.axacolpatria.pruebaxa.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.client.RestTemplate;
import com.axacolpatria.pruebaxa.model.RegistroModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.nio.charset.Charset;
import java.util.Arrays;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;



@Controller
public class ListaRegistrosAxaController {

  @Autowired
	RestTemplate restTemplate;
  @Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

    @RequestMapping("/Listaregistros")
    public String list(Model model) {
        model.addAttribute("registrosList", buildRegistrosList());
        return "registrosList";
    }

    private List<RegistroModel> buildRegistrosList() {

      String Mensaje="";
      String codigo="";

      List<RegistroModel> registros = new ArrayList<>();   

      HttpHeaders headers = new HttpHeaders();
      Charset utf8 = Charset.forName("UTF-8");
      MediaType mediaType = new MediaType("text", "html", utf8);
      headers.setContentType(mediaType.APPLICATION_JSON);
      HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
      try{
            ResponseEntity<String> responseEntity = restTemplate.exchange("https://8e7c6b8a-fc46-4674-a529-4ebec57295d3.mock.pstmn.io/customers", 
            HttpMethod.GET, entity, String.class);
          
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    String result = responseEntity.getBody();
                    char[] caracteres = result.toCharArray();
                    for (int i = 0; i < caracteres.length; i++) {
                      int valor= (int) caracteres[i];
                          if ( valor == 8220 || valor == 8221){
                            result=result.replace(Character.toString(caracteres[i]),"\"");
                          }
                    }

                  JSONObject object=new JSONObject();
                  try{
                    JSONArray array = new JSONArray(result);
            
                    for(int i=0; i < array.length(); i++)   
                      {  
                        object = array.getJSONObject(i); 
                        String name=object.getString("name");
                        Integer age= Integer.valueOf(object.getString("age"));
                        String phoneNumber=object.getString("phoneNumber");
                        String address=object.getString("address");
                        registros.add(new RegistroModel(name, age,phoneNumber,address));
                      }  
                  }catch(Exception e){}

            }
            else if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND) {
                Mensaje="Record Not Found";
                codigo=responseEntity.getStatusCode().toString();
            }
            else if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                Mensaje="Server Error";
                codigo=responseEntity.getStatusCode().toString();
            }
            else if (responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST) {
                Mensaje="Validation Failed";
                codigo=responseEntity.getStatusCode().toString();
            }
            else {
                Mensaje="Error generado al consumir servicio";
                codigo=responseEntity.getStatusCode().toString();

            }
      }catch(Exception e){

            if(Mensaje==null || Mensaje.equals("")){
              Mensaje=""+e.getMessage();
            }
                registros.add(new RegistroModel(Mensaje, 0,codigo,""));
      }

        return registros;
    }

}

