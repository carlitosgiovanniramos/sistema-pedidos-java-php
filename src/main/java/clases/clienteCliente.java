/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;

/**
 *
 * @author Lenovo LOQ
 */
public class clienteCliente {
    
    private final String apiUrl = "http://localhost/examenParcial2/src/main/java/api/apiCliente.php";
    private final Gson gson = new Gson();
    private final HttpClient HttpCliente = HttpClient.newHttpClient();
    
    
    public ArrayList<Cliente> obtenerClientes() {
        //2.Crear la petición deseada (GET, POST, PUT, DELETE, etc)con la clase HTTPRequest.
        try {
            HttpRequest peticionGet = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl))
                    .GET()
                    .build();
            //3.Envíar la petición utilizando el cliente HTTPClient
            //creado.
            //4.Almacenar la respuesta de la petición.
            HttpResponse<String> respuestaGet = HttpCliente.send(peticionGet, BodyHandlers.ofString());

            //5.Programar en base a la respuesta de la petición.
            if (respuestaGet.statusCode() == 200) {
                String jsonRespuesta = respuestaGet.body();
                //System.out.println(jsonRespuesta);

                Type tipoArray = new TypeToken<ArrayList<Cliente>>() {
                }.getType();
                ArrayList<Cliente> clientesLista = gson.fromJson(jsonRespuesta, tipoArray);
                return clientesLista;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return null;
    }
    
    public String construirParametros(Cliente cliente){
        return "ced_cli=" + cliente.getCed_cli()                
               + "&nom_cli=" + cliente.getNom_cli()
                +"&ape_cli=" + cliente.getApe_cli();
    }
    
    public boolean insertarClientes(Cliente cliente) {
        String parametros = construirParametros(cliente);
        //2.Crear la petición deseada (GET, POST, PUT, DELETE, etc)con la clase HTTPRequest.
        try {
            HttpRequest peticionPost = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl))
                    .header("Content-Type",
                            "application/x-www-form-urlencoded")
                    .POST(BodyPublishers.ofString(parametros))
                    .build();
            //3.Envíar la petición utilizando el cliente HTTPClient
            //creado.
            //4.Almacenar la respuesta de la petición.
            HttpResponse<String> respuestaPost = HttpCliente.send(peticionPost, BodyHandlers.ofString());

            //5.Programar en base a la respuesta de la petición.
            if (respuestaPost.statusCode() == 200) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    
        public String construirConsulta(String parametros){
            return apiUrl + "?" + parametros;
        }
        public boolean editarClientes(Cliente cliente) {
        String urlEditar = construirConsulta(construirParametros(cliente));
        //2.Crear la petición deseada (GET, POST, PUT, DELETE, etc)con la clase HTTPRequest.
        try {
            HttpRequest peticionPut = HttpRequest.newBuilder()
                    .uri(new URI(urlEditar))
                    .PUT(BodyPublishers.noBody())
                    .build();
            //3.Envíar la petición utilizando el cliente HTTPClient
            //creado.
            //4.Almacenar la respuesta de la petición.
            HttpResponse<String> respuestaPut = HttpCliente.send(peticionPut, BodyHandlers.ofString());

            //5.Programar en base a la respuesta de la petición.
            if (respuestaPut.statusCode() == 200) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
        public boolean eliminarClientes(String cedula) {
        String urlEliminar = construirConsulta("ced_cli=" + cedula);
        //2.Crear la petición deseada (GET, POST, PUT, DELETE, etc)con la clase HTTPRequest.
        try {
            HttpRequest peticionDelete = HttpRequest.newBuilder()
                    .uri(new URI(urlEliminar))
                    .DELETE()
                    .build();
            //3.Envíar la petición utilizando el cliente HTTPClient
            //creado.
            //4.Almacenar la respuesta de la petición.
            HttpResponse<String> respuestaDelete = HttpCliente.send(peticionDelete, BodyHandlers.ofString());

            //5.Programar en base a la respuesta de la petición.
            if (respuestaDelete.statusCode() == 200) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
        
        
        public ArrayList<Cliente> obtenerClientesPorCedula(String cedula) {
        //2.Crear la petición deseada (GET, POST, PUT, DELETE, etc)con la clase HTTPRequest.
        String urlCedula = construirConsulta("ced_cli=" + cedula);
        try {
            HttpRequest peticionGet = HttpRequest.newBuilder()
                    .uri(new URI(urlCedula))
                    .GET()
                    .build();
            //3.Envíar la petición utilizando el cliente HTTPClient
            //creado.
            //4.Almacenar la respuesta de la petición.
            HttpResponse<String> respuestaGet = HttpCliente.send(peticionGet, BodyHandlers.ofString());

            //5.Programar en base a la respuesta de la petición.
            if (respuestaGet.statusCode() == 200) {
                String jsonRespuesta = respuestaGet.body();
                //System.out.println(jsonRespuesta);

                Type tipoArray = new TypeToken<ArrayList<Cliente>>() {
                }.getType();
                ArrayList<Cliente> clientesLista = gson.fromJson(jsonRespuesta, tipoArray);
                return clientesLista;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return null;
    }
        
}
