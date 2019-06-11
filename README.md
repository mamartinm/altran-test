# Altran Kas Test

Proyecto que consume una api rest del Ayuntamiento de Barcelona con fines didácticos y expone code, organization, description y URI 

Se divide en 3 modulos:`beans`, `model`, y `webcontroller`

## `Beans`
Uso de clases generales y entidades, también puede incluir algunas clases utiles (Fechas, I/O, Formateo de texto y números)
  
## `Model`
Logica de negocio que hace uso del modulo (`beans`).
Conecta a la url del ayuntamiento y cachea los resultados durante 30 sg.

## `Webcontroller`
 
Aplicacion que expone un api rest conecta con (`model`).

## Prerequisitos

* **java 11** 
* **maven 3.6.x** 
* **Conexion a internet**


## Compilacion y entregable 
 
Hace uso de maven para la compilacion y generación de entregables:

* **mvn clean compile** para compilar
* **mvn clean package** para generar el entregable en format jar

## Ejecucción
    
Una vez empaquetado, se puede ejecutar mediante:

**java -jar webcontroller/target/webcontroller.jar**

## Endpoints

* **http://localhost:8080/rest/api/v1/entity/all** Devuelve todos los resultados
* **http://localhost:8080/rest/api/v1/entity?page={x}&size={y}** Devuelve todos los resultados paginados
* **http://localhost:8080/rest/api/v1/entity/{id}** Devuelve solo el resultado del id 

**Monitorizacion**

* **http://localhost:8080/rest/api/v1/actuator** Informacion general sobre el estado del servidor
* **http://localhost:8080/rest/api/v1/actuator/metrics/** Metricas sobre servidor y maquina virtual
* **http://localhost:8080/rest/api/v1/actuator/healt** Informacion sobre el estado del servidor (UP,DOWN)
* **http://localhost:8080/rest/api/v1/actuator/info** Devuelve todos los resultados
Para más endpoints consultar: https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready-endpoints

## Fuente de datos
Es necesario tener acceso a esta url para que la aplicacion pueda funcionar (http://opendata-ajuntament.barcelona.cat/data/api/3/action/package_search)

## Codigo fuente
Se hace uso de la guia de estilos de formateo de codigo de google, https://github.com/google/styleguide cambiando el retorno de carro a 160

Servidor Git: https://github.com/mamartinm/altran-test


## Arrancar servidor java

<code>java -jar webcontroller/target/webcontroller.jar</code>

## Ejecutar Test

Una vez arrancado el servidor, ejecutar con maven:

<code>mvn test -Prun-test-e2e</code>

## Arrancar en docker

Al hacer mvn package se crea la imagen mamartin/webcontroller

Para arrancar el contenedor:
<code> docker run -it -p 8080:8080 --rm mamartin/webcontroller:latest</code>

