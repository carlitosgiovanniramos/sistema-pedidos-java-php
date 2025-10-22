<?php

class Conexion
{
    public function connect()
    {
        $servername = "localhost:3306";
        $username = "root";
        $password = "";
        $dbname = "ventas";

        try {

            // Si el servidor incluye puerto, separarlo en host y puerto
            $host = 'localhost';
            $port = '3306';
            // Construir DSN con host y puerto explícitos
            $connect = new PDO("mysql:host={$host};port={$port};dbname={$dbname}", $username, $password);
        } catch (Exception $e) {
            die("Fallo : " . $e->getMessage());
        }

        return $connect;
    }
}
