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
import java.net.http.HttpResponse;
import java.util.ArrayList;

/**
 *
 * @author Lenovo LOQ
 */
public class clientePedidos {

    private final String apiUrl = "http://localhost/examenParcial2/src/main/java/api/apiPedidos.php";
    private final Gson gson = new Gson();
    private final HttpClient HttpCliente = HttpClient.newHttpClient();

    public ArrayList<Pedidos> obtenerPedidos() {
        //2.Crear la petición deseada (GET, POST, PUT, DELETE, etc)con la clase HTTPRequest.
        try {
            HttpRequest peticionGet = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl))
                    .GET()
                    .build();
            //3.Envíar la petición utilizando el cliente HTTPClient
            //creado.
            //4.Almacenar la respuesta de la petición.
            HttpResponse<String> respuestaGet = HttpCliente.send(peticionGet, HttpResponse.BodyHandlers.ofString());

            //5.Programar en base a la respuesta de la petición.
            if (respuestaGet.statusCode() == 200) {
                String jsonRespuesta = respuestaGet.body();
                //System.out.println(jsonRespuesta);

                Type tipoArray = new TypeToken<ArrayList<Pedidos>>() {
                }.getType();
                ArrayList<Pedidos> pedidosLista = gson.fromJson(jsonRespuesta, tipoArray);
                return pedidosLista;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public String construirParametros(Pedidos pedido) {
        return "id_ped=" + pedido.getId_ped()
                + "&ced_cli_ped=" + pedido.getCed_cli_ped()
                + "&cod_pro_ped=" + pedido.getCod_pro_ped()
                + "&can_ped=" + pedido.getCan_ped();
    }

    public boolean insertarPedidos(Pedidos pedido) {
        String parametros = construirParametros(pedido);
        //2.Crear la petición deseada (GET, POST, PUT, DELETE, etc)con la clase HTTPRequest.
        try {
            HttpRequest peticionPost = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl))
                    .header("Content-Type",
                            "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(parametros))
                    .build();
            //3.Envíar la petición utilizando el cliente HTTPClient
            //creado.
            //4.Almacenar la respuesta de la petición.
            HttpResponse<String> respuestaPost = HttpCliente.send(peticionPost, HttpResponse.BodyHandlers.ofString());

            //5.Programar en base a la respuesta de la petición.
            if (respuestaPost.statusCode() == 200) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    public boolean editarPedidos(Pedidos pedidos) {
        String urlEditar = construirConsulta(construirParametros(pedidos));
        //2.Crear la petición deseada (GET, POST, PUT, DELETE, etc)con la clase HTTPRequest.
        try {
            HttpRequest peticionPut = HttpRequest.newBuilder()
                    .uri(new URI(urlEditar))
                    .PUT(HttpRequest.BodyPublishers.noBody())
                    .build();
            //3.Envíar la petición utilizando el cliente HTTPClient
            //creado.
            //4.Almacenar la respuesta de la petición.
            HttpResponse<String> respuestaPut = HttpCliente.send(peticionPut, HttpResponse.BodyHandlers.ofString());

            //5.Programar en base a la respuesta de la petición.
            if (respuestaPut.statusCode() == 200) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public String construirConsulta(String parametros) {
        return apiUrl + "?" + parametros;
    }

    public ArrayList<Pedidos> obtenerProductoPorCedula(String cedula) {
        //2.Crear la petición deseada (GET, POST, PUT, DELETE, etc)con la clase HTTPRequest.
        String urlCodigo = construirConsulta("ced_cli_ped=" + cedula);
        try {
            HttpRequest peticionGet = HttpRequest.newBuilder()
                    .uri(new URI(urlCodigo))
                    .GET()
                    .build();
            //3.Envíar la petición utilizando el cliente HTTPClient
            //creado.
            //4.Almacenar la respuesta de la petición.
            HttpResponse<String> respuestaGet = HttpCliente.send(peticionGet, HttpResponse.BodyHandlers.ofString());

            //5.Programar en base a la respuesta de la petición.
            if (respuestaGet.statusCode() == 200) {
                String jsonRespuesta = respuestaGet.body();
                //System.out.println(jsonRespuesta);

                Type tipoArray = new TypeToken<ArrayList<Pedidos>>() {
                }.getType();
                ArrayList<Pedidos> pedidosClientes = gson.fromJson(jsonRespuesta, tipoArray);
                return pedidosClientes;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return null;
    }
    
    public ArrayList<Pedidos> obtenerProductoPorCodigo(String codigo) {
        //2.Crear la petición deseada (GET, POST, PUT, DELETE, etc)con la clase HTTPRequest.
        String urlCodigo = construirConsulta("cod_pro_ped=" + codigo);
        try {
            HttpRequest peticionGet = HttpRequest.newBuilder()
                    .uri(new URI(urlCodigo))
                    .GET()
                    .build();
            //3.Envíar la petición utilizando el cliente HTTPClient
            //creado.
            //4.Almacenar la respuesta de la petición.
            HttpResponse<String> respuestaGet = HttpCliente.send(peticionGet, HttpResponse.BodyHandlers.ofString());

            //5.Programar en base a la respuesta de la petición.
            if (respuestaGet.statusCode() == 200) {
                String jsonRespuesta = respuestaGet.body();
                //System.out.println(jsonRespuesta);

                Type tipoArray = new TypeToken<ArrayList<Pedidos>>() {
                }.getType();
                ArrayList<Pedidos> pedidosClientes = gson.fromJson(jsonRespuesta, tipoArray);
                return pedidosClientes;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return null;
    }
    
}
