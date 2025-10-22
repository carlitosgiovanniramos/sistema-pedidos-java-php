<?php
include_once "conn.php";

class CRUD
{

    public static function select()
    {
        $connect = new Conexion();
        $conectat = $connect->connect();
        //se crea la consulta
        $sqlSelect = "SELECT * FROM clientes";
        //preparar la consulta
        //el prepare es para evitar inyecciones SQL porque
        //si se pone directamente en el execute puede ser peligroso 
        $result = $conectat->prepare($sqlSelect);
        // el execute ejecuta la consulta
        $result->execute();
        //fetchAll obtiene todos los registros
        // PDO::FETCH_ASSOC obtiene solo los nombres de las columnas
        // ejemplo: array("CEDULA"=>"123456","NOMBRE"=>"Juan")
        $data = $result->fetchAll(PDO::FETCH_ASSOC);

        echo json_encode($data);
    }

    public static function insert()
    {
        $connect = new Conexion();
        $conectat = $connect->connect();

        // se obtienen los datos enviados por POST esto se puede
        // cambiar por los datos que se quieran enviar
        // en este caso se envían los datos del estudiante
        $cedula = $_POST['ced_cli'];
        $nombre = $_POST['nom_cli'];
        $apellido = $_POST['ape_cli'];
        
        // se crea la consulta
        $sqlInsert = "INSERT INTO clientes (ced_cli, nom_cli, ape_cli ) VALUES ('$cedula' ,'$nombre','$apellido')";
        $resultado = $conectat->prepare($sqlInsert);
        $resultado->execute();
        $data = "Insertado";
        echo json_encode($data);
    }
    
    public static function  selectCedula()
    {
        $connect = new Conexion();
        $conectat = $connect->connect();

        // Validar parámetro requerido
        if (!isset($_GET['ced_cli'])) {
            http_response_code(400);
            echo json_encode(["error" => "Falta el parámetro 'CEDULA'"]);
            return;
        }

        $cedula = $_GET['ced_cli'];
        // Se crea la consulta para buscar por cédula
        $sqlSelect = "SELECT * FROM clientes WHERE ced_cli='$cedula'";
        $result = $conectat->prepare($sqlSelect);
        $result->execute();
        $data = $result->fetchAll(PDO::FETCH_ASSOC);

        echo json_encode($data);
    }

    public static function update()
    {
        $connect = new Conexion();
        $conectat = $connect->connect();

        // se obtienen los datos enviados por PUT
        // OBTENEMOS LA cEDULA POR LA URL
        // EL Get es para obtener los datos de la URL
        // ejemplo: api.php?cedula=123456         
        $cedula = $_GET['ced_cli'];
        $nombre = $_GET['nom_cli'];
        $apellido = $_GET['ape_cli'];
        // se crea la consulta
        $sqlUpdate = "UPDATE clientes SET nom_cli='$nombre', ape_cli='$apellido' WHERE ced_cli='$cedula'";
        $resultado = $conectat->prepare($sqlUpdate);
        $resultado->execute();
        $data = "Actualizado";
        echo json_encode($data);
    }

    public static function delete()
    {
        $connect = new Conexion();
        $conectat = $connect->connect();

        // se obtienen los datos enviados por DELETE
        // OBTENEMOS LA CEDULA POR LA URL
        // EL Get es para obtener los datos de la URL
        // ejemplo: api.php?CEDULA=123456
        if (isset($_GET['ced_cli'])) {
            $cedula = $_GET['ced_cli'];
        } else {
            http_response_code(400);
            echo json_encode(["error" => "Falta el parámetro 'CEDULA'"]);
            return;
        }
        // se crea la consulta
        $sqlDelete = "DELETE FROM clientes WHERE ced_cli='$cedula'";
        $resultado = $conectat->prepare($sqlDelete);
        $resultado->execute();
        $data = "Eliminado";
        echo json_encode($data);
    }
}
