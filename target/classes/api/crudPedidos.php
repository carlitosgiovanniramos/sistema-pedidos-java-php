<?php

include_once "conn.php";

class CRUD {

    public static function select() {
        header('Content-Type: application/json; charset=utf-8');
        $connect = new Conexion();
        $conectat = $connect->connect();
        
        try {
            // JOIN implícito para traer nombre cliente y descripción producto
            $sql = "SELECT v.id_ped, v.ced_cli_ped, v.cod_pro_ped, v.can_ped,
                           CONCAT(c.nom_cli, ' ', c.ape_cli) AS CLIENTE, 
                           p.des_pro AS PRODUCTO
                    FROM pedidos v, clientes c, poductos p
                    WHERE v.ced_cli_ped = c.ced_cli 
                      AND v.cod_pro_ped = p.cod_pro";
            $result = $conectat->prepare($sql);
            $result->execute();
            $data = $result->fetchAll(PDO::FETCH_ASSOC);

            echo json_encode($data);
        } catch (PDOException $e) {
            http_response_code(500);
            echo json_encode(['error' => $e->getMessage()]);
        }
    }
    
        public static function insert()
    {
        $connect = new Conexion();
        $conectat = $connect->connect();

        // se obtienen los datos enviados por POST esto se puede
        // cambiar por los datos que se quieran enviar
        // en este caso se envían los datos del estudiante
        $pedido = $_POST['id_ped'];
        $cedula = $_POST['ced_cli_ped'];
        $codigo = $_POST['cod_pro_ped'];
        $cantidad = $_POST['can_ped'];
        
        // se crea la consulta
        $sqlInsert = "INSERT INTO pedidos (id_ped, ced_cli_ped, cod_pro_ped, can_ped) VALUES ('$pedido' ,'$cedula','$codigo', '$cantidad')";
        $resultado = $conectat->prepare($sqlInsert);
        $resultado->execute();
        $data = "Insertado";
        echo json_encode($data);
    }
    
    public static function selectPedidoPorCliente() {
        header('Content-Type: application/json; charset=utf-8');
        $connect = new Conexion();
        $conectat = $connect->connect();

        // Validar parámetro requerido
        if (!isset($_GET['ced_cli_ped'])) {
            http_response_code(400);
            echo json_encode(["error" => "Falta el parámetro 'Cedula'"]);
            return;
        }

        $cedula = $_GET['ced_cli_ped'];
        
        try {
            // JOIN implícito para traer nombre cliente y descripción producto
            $sqlSelect = "SELECT v.id_ped, v.ced_cli_ped, v.cod_pro_ped, v.can_ped,
                                 CONCAT(c.nom_cli, ' ', c.ape_cli) AS CLIENTE, 
                                 p.des_pro AS PRODUCTO
                          FROM pedidos v, clientes c, poductos p
                          WHERE v.ced_cli_ped = c.ced_cli 
                            AND v.cod_pro_ped = p.cod_pro
                            AND v.ced_cli_ped = '$cedula'";
            $result = $conectat->prepare($sqlSelect);
            $result->execute();
            $data = $result->fetchAll(PDO::FETCH_ASSOC);

            echo json_encode($data);
        } catch (PDOException $e) {
            http_response_code(500);
            echo json_encode(['error' => $e->getMessage()]);
        }
    }
    
        public static function selectPedidoPorProducto() {
        header('Content-Type: application/json; charset=utf-8');
        $connect = new Conexion();
        $conectat = $connect->connect();

        // Validar parámetro requerido
        if (!isset($_GET['cod_pro_ped'])) {
            http_response_code(400);
            echo json_encode(["error" => "Falta el parámetro 'Codigo'"]);
            return;
        }

        $codigo = $_GET['cod_pro_ped'];
        
        try {
            // JOIN implícito para traer nombre cliente y descripción producto
            $sqlSelect = "SELECT v.id_ped, v.ced_cli_ped, v.cod_pro_ped, v.can_ped,
                                 CONCAT(c.nom_cli, ' ', c.ape_cli) AS CLIENTE, 
                                 p.des_pro AS PRODUCTO
                          FROM pedidos v, clientes c, poductos p
                          WHERE v.ced_cli_ped = c.ced_cli 
                            AND v.cod_pro_ped = p.cod_pro
                            AND v.cod_pro_ped = '$codigo'";
            $result = $conectat->prepare($sqlSelect);
            $result->execute();
            $data = $result->fetchAll(PDO::FETCH_ASSOC);

            echo json_encode($data);
        } catch (PDOException $e) {
            http_response_code(500);
            echo json_encode(['error' => $e->getMessage()]);
        }
    }


    public static function update() {
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

    public static function delete() {
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
