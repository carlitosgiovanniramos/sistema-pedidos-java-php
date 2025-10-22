<?php
include_once "conn.php";

class CRUD
{

    public static function select()
    {
        $connect = new Conexion();
        $conectat = $connect->connect();
        //se crea la consulta
        $sqlSelect = "SELECT * FROM poductos";
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
        $codigo = $_POST['cod_pro'];
        $descripcion = $_POST['des_pro'];
        $precio = $_POST['pre_uni'];
        $stock = $_POST['stock'];
        
        // se crea la consulta
        $sqlInsert = "INSERT INTO poductos (cod_pro, des_pro, pre_uni, stock) VALUES ('$codigo' ,'$descripcion','$precio', '$stock')";
        $resultado = $conectat->prepare($sqlInsert);
        $resultado->execute();
        $data = "Insertado";
        echo json_encode($data);
    }
    
    public static function  selectProducto()
    {
        $connect = new Conexion();
        $conectat = $connect->connect();

        // Validar parámetro requerido
        if (!isset($_GET['cod_pro'])) {
            http_response_code(400);
            echo json_encode(["error" => "Falta el parámetro 'Codigo'"]);
            return;
        }

        $codigo = $_GET['cod_pro'];
        // Se crea la consulta para buscar por cédula
        $sqlSelect = "SELECT * FROM poductos WHERE cod_pro='$codigo'";
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
        $codigo = $_GET['cod_pro'];
        $descripcion = $_GET['des_pro'];
        $precio = $_GET['pre_uni'];
        $stock = $_GET['stock'];
        // se crea la consulta
        $sqlUpdate = "UPDATE poductos SET des_pro='$descripcion', pre_uni='$precio', stock='$stock' WHERE cod_pro='$codigo'";
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
        if (isset($_GET['cod_pro'])) {
            $codigo = $_GET['cod_pro'];
        } else {
            http_response_code(400);
            echo json_encode(["error" => "Falta el parámetro 'Codigo'"]);
            return;
        }
        // se crea la consulta
        $sqlDelete = "DELETE FROM poductos WHERE cod_pro='$codigo'";
        $resultado = $conectat->prepare($sqlDelete);
        $resultado->execute();
        $data = "Eliminado";
        echo json_encode($data);
    }
}
