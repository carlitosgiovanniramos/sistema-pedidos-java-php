<?php
// importa la clase CRUD que contiene los métodos
// para cada operación de la API 
include_once "crudProducto.php";

// obtiene el método de la petición HTTP
// usando la variable superglobal $_SERVER
// y el índice 'REQUEST_METHOD'
// ejemplo de los métodos: GET, POST, PUT, DELETE
$opc = $_SERVER['REQUEST_METHOD'];

// estructura de control switch para llamar al método
// correspondiente según el método de la petición

switch ($opc) {
    case 'GET':
        if(isset($_GET['cod_pro'])) {
        CRUD::selectProducto(); 
        } else {
        CRUD::select();
        }
        break;
    
    case 'POST':
        CRUD::insert();
        break;
    case 'PUT':
        CRUD::update();
        break;
    case 'DELETE':
        CRUD::delete();
        break;
    default:
        # code...
        break;
}
