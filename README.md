# Altran Kas Test

Proyecto que consume una api rest del Ayuntamiento de Barcelona con fines didácticos y expone code, organization, description y URI 

Se divide en 3 modulos:`beans`, `model`, y `webcontroller`

## `Beans`
Uso de clases generales y entidades, también puede incluir algunas clases utiles (Fechas, I/O, Formateo de texto y números)
  
## `Model`
Logica de negocio que hace uso del modulo (`beans`)

## `Webcontroller`
 
Aplicacion que muestra el contenido de la prueba que hace uso de spring mvc y conecta con (`model`).

## Compilacion y entregable 
 
Hace uso de maven para la compilacion y generación de entregables:

* **mvn clean compile** para compilar
* **mvn clean package** para generar el entregable en format jar
* **mvn clean package -P war** para generar el entregable en format war

## Ejecucción
    
En ambas versiones (jar/war) se puede ejecutar mediante:

**java -jar webcontroller/target/webcontroller.{jar/war}**

## Endpoints

* **http://localhost:8080/rest/api/v1/entity/all** Devuelve todos los resultados
* **http://localhost:8080/rest/api/v1/entity?page=1&size=2** Devuelve todos los resultados paginados

**Monitorizacion**

* **http://localhost:8080/rest/api/v1/actuator** Informacion general sobre el estado del servidor
* **http://localhost:8080/rest/api/v1/actuator/metrics/** Metricas sobre servidor y maquina virtual
* **http://localhost:8080/rest/api/v1/actuator/healt** Informacion sobre el estado del servidor (UP,DOWN)
* **http://localhost:8080/rest/api/v1/actuator/info** Devuelve todos los resultados
Para más endpoints consultar: https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready-endpoints

## Fuente de datos
(http://opendata-ajuntament.barcelona.cat/data/api/3/action/package_search)

## Codigo fuente
Se hace uso de la guia de estilos de formateo de codigo de google, https://github.com/google/styleguide cambiando el retorno de carro a 160

Git: https://github.com/mamartinm/altran-test


