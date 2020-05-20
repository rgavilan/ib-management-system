# ASIO - Sistema de gestión

Sistema de gestión de datos del módulo de procesamiento para el proyecto Backend SGI (ASIO). 

## OnBoarding

Para iniciar el entorno de desarrollo se necesita cumplir los siguientes requisitos:

* OpenJDK 11
* Eclipse JEE 2019-09 con plugins:
  * Spring Tools 4
  * m2e-apt
  * Lombok
* Docker

## Metodología de desarrollo

La metodología de desarrollo es Git Flow.

## Entorno de desarrollo Docker

La inicialización de los elementos adicionales al entorno de desarrollo se realiza con docker. 

En el directorio docker-devenv se ha configurado un fichero docker-compose.yml para poder arrancar el entorno de desarrollo.

Para arrancar el entorno:

```bash
docker-compose up -d
```

Para pararlo:

```bash
docker-compose down
```

## Instalación en entorno real

Será preciso configurar las siguientes variables de entorno cuando se instale en un entorno real:

|Variable|Descripción|Valor por defecto|
|---|---|---|
|`APP_KAFKA_GENERAL_TOPIC_NAME`|Nombre del topic de Kafka general|general-data|
|`APP_KAFKA_GENERAL_CONTINGENCY_TOPIC_NAME`|Nombre del topic de Kafka general contingency|general-contingency-data|
|`APP_KAFKA_MANAGEMENT_TOPIC_NAME`|Nombre del topic de Kafka de gestión|management-data|
|`APP_KAFKA_CREATE_TOPICS`|Flag que indica si debe crear automáticamente los topics de Kafka. Valores admisibles `true` y `false`|false|
| `SPRING_KAFKA_BOOTSTRAP_SERVERS` | URL del servicio de Kafka para los productores | localhost:29092 |
| `SPRING_KAFKA_CONSUMER_BOOTSTRAP_SERVERS` | URL del servicio de Kafka para los consumidores | localhost:29092 |
| `SPRING_KAFKA_CONSUMER_GROUP_ID` | ID del grupo de consumidores | management-system |
| `APP_GENERATOR_URIS_MOCKUP_ENABLED` | Flag que indica si se debe llamar al mockup uris factory. Valores admisibles `true` y `false`|false|
| `APP_GENERATOR_URIS_ENDPOINT` | URL del servicio URIS Factory | http://localhost:9326 |

### Ejecución

Al generarse un JAR bootable la ejecución se realizará mediante el siguiente comando:

```bash
java -jar {jar-name}.jar
```

Sustituyendo `{jar-name}` por el nombre del fichero JAR generado.

No es necesario especificar la clase de inicio de la aplicación, ya que el fichero MANIFEST.MF generado ya contiene la información necesaria. Solamente se especificarán los parametros necesarios.

##  Documentación adicional

* [Compilación](docs/build.md)
* [Generación Docker](docs/docker.md)
