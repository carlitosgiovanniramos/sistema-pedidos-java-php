# Sistema de Pedidos Java + PHP

Sistema CRUD desarrollado como aplicación de escritorio utilizando Java Swing, Maven y una API REST básica en PHP para la gestión de clientes, productos y pedidos.

---

# Características

- Registro de clientes
- Registro de productos
- Registro de pedidos
- Actualización de información
- Eliminación de registros
- Consumo de API mediante HTTP
- Interfaz gráfica con Java Swing
- Serialización y deserialización JSON usando Gson

---

# Tecnologías utilizadas

- Java 23
- Java Swing
- Maven
- PHP
- MySQL
- Gson
- Apache / XAMPP

---

# Estructura del proyecto

```plaintext
examenParcial2/
│
├── src/main/java/
│   ├── clases/
│   │   ├── Cliente.java
│   │   ├── Producto.java
│   │   ├── Pedidos.java
│   │   ├── clienteCliente.java
│   │   ├── productoCliente.java
│   │   └── pedidosCliente.java
│   │
│   └── api/
│       ├── apiCliente.php
│       ├── apiProducto.php
│       ├── apiPedidos.php
│       ├── conn.php
│       └── index.php
│
├── pom.xml
└── target/
```

---

# Requisitos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- JDK 23
- Maven
- XAMPP o Apache con PHP
- MySQL

---

# Configuración de la base de datos

1. Crear una base de datos en MySQL.
2. Configurar las credenciales en:

```php
conn.php
```

Ejemplo:

```php
$host = "localhost";
$user = "root";
$password = "";
$database = "sistema_pedidos";
```

---

# Ejecución de la API PHP

1. Copiar el proyecto dentro de:

```plaintext
htdocs/
```

Ejemplo:

```plaintext
C:\xampp\htdocs\examenParcial2
```

2. Iniciar:
- Apache
- MySQL

3. Verificar que la API funcione:

```plaintext
http://localhost/examenParcial2/src/main/java/api/
```

---

# Ejecución del proyecto Java

1. Abrir el proyecto en NetBeans o IntelliJ IDEA.
2. Ejecutar Maven:

```bash
mvn clean install
```

3. Ejecutar la aplicación principal.

---

# Dependencias Maven

```xml
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.10.1</version>
</dependency>
```

---

# Mejoras futuras

- Implementar arquitectura MVC
- Separar completamente frontend y backend
- Agregar autenticación de usuarios
- Mejorar validaciones
- Implementar DAO y patrón Repository
- Desplegar API en servidor remoto
- Agregar documentación Swagger

---

# Autor

Carlos Giovanni Ramos

---

# Licencia

Proyecto desarrollado con fines académicos.
