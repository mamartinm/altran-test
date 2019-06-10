# Altran Kas Test

Proyecto que consume una api rest con fines didácticos.

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
