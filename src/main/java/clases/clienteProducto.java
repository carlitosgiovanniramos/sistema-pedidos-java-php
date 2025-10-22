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
public class clienteProducto {

    private final String apiUrl = "http://localhost/examenParcial2/src/main/java/api/apiProducto.php";
    private final Gson gson = new Gson();
    private final HttpClient HttpCliente = HttpClient.newHttpClient();

    public ArrayList<Producto> obtenerProductos() {
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

                Type tipoArray = new TypeToken<ArrayList<Producto>>() {
                }.getType();
                ArrayList<Producto> productoLista = gson.fromJson(jsonRespuesta, tipoArray);
                return productoLista;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public String construirParametros(Producto producto) {
        return "cod_pro=" + producto.getCod_pro()
                + "&des_pro=" + producto.getDes_pro()
                + "&pre_uni=" + producto.getPre_uni()
                + "&stock=" + producto.getStock();
    }

    public boolean insertarProductos(Producto producto) {
        String parametros = construirParametros(producto);
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

    public String construirConsulta(String parametros) {
        return apiUrl + "?" + parametros;
    }
    
    public boolean editarProductos(Producto producto) {
        String urlEditar = construirConsulta(construirParametros(producto));
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
    
        public boolean eliminarProductos(String codigo) {
        String urlEliminar = construirConsulta("cod_pro=" + codigo);
        //2.Crear la petición deseada (GET, POST, PUT, DELETE, etc)con la clase HTTPRequest.
        try {
            HttpRequest peticionDelete = HttpRequest.newBuilder()
                    .uri(new URI(urlEliminar))
                    .DELETE()
                    .build();
            //3.Envíar la petición utilizando el cliente HTTPClient
            //creado.
            //4.Almacenar la respuesta de la petición.
            HttpResponse<String> respuestaDelete = HttpCliente.send(peticionDelete, HttpResponse.BodyHandlers.ofString());

            //5.Programar en base a la respuesta de la petición.
            if (respuestaDelete.statusCode() == 200) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
        
        public ArrayList<Producto> obtenerProductoPorCodigo(String codigo) {
        //2.Crear la petición deseada (GET, POST, PUT, DELETE, etc)con la clase HTTPRequest.
        String urlCodigo = construirConsulta("cod_pro=" + codigo);
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

                Type tipoArray = new TypeToken<ArrayList<Producto>>() {
                }.getType();
                ArrayList<Producto> productoLista = gson.fromJson(jsonRespuesta, tipoArray);
                return productoLista;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return null;
    }
}
