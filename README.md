# ASIO - Sistema de gestión

Sistema de gestión de datos del módulo de procesamiento para el proyecto Backend SGI (ASIO). 

## OnBoarding

Para iniciar el entorno de desarrollo se necesita cumplir los siguientes requisitos:

* OpenJDK 11 (en caso de querer JDK8: Oracle JDK 8)
* Eclipse JEE 2019-09 con plugins:
** Spring Tools 4
** m2e-apt
** Lombok
* Docker

También se debe instalar el entorno de desarrollo acorde con lo explicado en: [https://corpus.izertis.com/arquitectura/java/configuracion-del-entorno](https://corpus.izertis.com/arquitectura/java/configuracion-del-entorno)


### Instalar Lombok

Para la instalación de Lombok, es preciso descargar la última versión desde [https://projectlombok.org/download](https://projectlombok.org/download). Se descargará un jar que precisa ser ejecutado:

	java -jar lombok.jar

Se seleccionará la ubicación en la que se encuentra instalado Eclipse.

En caso que de problemas a la hora de generar las clases de Mapstruct, es preciso utilizar una versión parcheada de lombok. Para ello, se ha dejado en \\rackstation\Desarrollo\fuentes\Entorno de desarrollo\Eclipses el fichero lombok-patched-1.18.6.jar. Se deberá configurar en el fichero eclipse.ini, sustituyendo el jar que tiene configurado actualmente por el parcheado

```
-javaagent:C:\desarrollo\java\install\eclipse-jee-2018-12-R-win32-x86_64\lombok-patched-1.18.6.jar
```

## Java 11

La aplicación está preparada para funcionar con JDK 11. En caso de necesitar trabajar con un JDK anterior, es preciso especificar una propiedad en el POM:

```xml
<properties>
	<java.version>1.8</java.version>
</properties>
```

Para descargar JDK 11, se precisa utilizar openjdk, la cual se puede obtener de https://jdk.java.net/11/

### Swagger

Se ha añadido la posibilidad de utilizar Swagger, el cual se ha configurado como Starter:

Para acceder a Swagger, se utilizará la siguiente URL:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Instalación en entorno real

Será preciso configurar las siguientes variables de entorno cuando se instale en un entorno real:

|Variable|Descripción|Valor por defecto|
|---|---|---|
|`APP_KAFKA_GENERAL_TOPIC_NAME`|Nombre del topic de Kafka general|general-data|
|`APP_KAFKA_MANAGEMENT_TOPIC_NAME`|Nombre del topic de Kafka de gestión|management-data|
|`APP_KAFKA_CREATE_TOPICS`|Flag que indica si debe crear automáticamente los topics de Kafka. Valores admisibles `true` y `false`|false|
| `SPRING_KAFKA_BOOTSTRAP_SERVERS` | URL del servicio de Kafka para los productores | localhost:29092 |
| `SPRING_KAFKA_CONSUMER_BOOTSTRAP_SERVERS` | URL del servicio de Kafka para los consumidores | localhost:29092 |
| `SPRING_KAFKA_CONSUMER_GROUP_ID` | ID del grupo de consumidores | management-system |
|`APP_SWAGGER_ENABLED`|Flag que indica si se habilita Swagger. Valores admisibles `true` y `false`|false|
