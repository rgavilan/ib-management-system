# Importar datos desde ETL

# Requisitos

## Obligatorios

- Tener instalado Pentaho en local. [https://marketplace.hitachivantara.com/pentaho/](https://marketplace.hitachivantara.com/pentaho/)  Versión 9
- Bases de datos: etl, uris, app

## Recomendados

- Conduktor (para ver los mensajes que llegan a las colas kafka), crear topics...

![Importar%20datos%20desde%20ETL%2070c44957c2ac481aaf74d0e198415c50/Untitled.png](Importar%20datos%20desde%20ETL%2070c44957c2ac481aaf74d0e198415c50/Untitled.png)

# Antes de ejecutar una nueva importación

Tener instalado [Pentaho](https://www.notion.so/Pentaho-0cb0bd85cf5e4ac0b288a4293ee07064)

1. Ejecutar `cleanTrellis.bat` . Lo que hace este fichero es parar las imágenes de docker kafka, zookeeper, fuseki-sandbox y trellis. Limpiar las colas y las vuelve a levantar. (Modificar las variables managementSystemPath y tripleStoragePath poniendo la ruta de cada uno)
2. Limpiar las tablas de la base de datos etl 

```docker
TRUNCATE table etl.EmpresaExplotacionPatente;
TRUNCATE table etl.FacturaProyecto;
TRUNCATE table etl.GrupoInvestigacion;
TRUNCATE table etl.InventorPatente;
TRUNCATE table etl.Patente;
TRUNCATE table etl.Proyecto;
TRUNCATE table etl.Universidad;
```

3. Limpiar la cola activeMQ. (borrar y volver a crearla)

![Importar%20datos%20desde%20ETL%2070c44957c2ac481aaf74d0e198415c50/Untitled%201.png](Importar%20datos%20desde%20ETL%2070c44957c2ac481aaf74d0e198415c50/Untitled%201.png)

4. Borrar la base de datos uris y volverla a crear

5. Crear los topic `general-data` y `general-link-data`

![Importar%20datos%20desde%20ETL%2070c44957c2ac481aaf74d0e198415c50/Untitled%202.png](Importar%20datos%20desde%20ETL%2070c44957c2ac481aaf74d0e198415c50/Untitled%202.png)

6. Revisar la configuración de Pentaho

![Importar%20datos%20desde%20ETL%2070c44957c2ac481aaf74d0e198415c50/Untitled%203.png](Importar%20datos%20desde%20ETL%2070c44957c2ac481aaf74d0e198415c50/Untitled%203.png)

7. Arrancar el backend (Uris, TripleStorage, Management-system, Event-processor)

8. Arrancar la ETL

![Importar%20datos%20desde%20ETL%2070c44957c2ac481aaf74d0e198415c50/Untitled%204.png](Importar%20datos%20desde%20ETL%2070c44957c2ac481aaf74d0e198415c50/Untitled%204.png)

# Después de ejecutar la importación

1. Revisar que la importación se ejecutó correctamente sin errores en el backend, mirando los logs del tripleStorage

![Importar%20datos%20desde%20ETL%2070c44957c2ac481aaf74d0e198415c50/Untitled%205.png](Importar%20datos%20desde%20ETL%2070c44957c2ac481aaf74d0e198415c50/Untitled%205.png)

2. Hacer un backup de los datos de trellis utilizando fuseki.

![Importar%20datos%20desde%20ETL%2070c44957c2ac481aaf74d0e198415c50/Untitled%206.png](Importar%20datos%20desde%20ETL%2070c44957c2ac481aaf74d0e198415c50/Untitled%206.png)

Necesitamos conocer el container id de la imágen fuseki-trellis_fuseki_sandbox  con el comando `docker ps` para recuperar el backup

![Importar%20datos%20desde%20ETL%2070c44957c2ac481aaf74d0e198415c50/Untitled%207.png](Importar%20datos%20desde%20ETL%2070c44957c2ac481aaf74d0e198415c50/Untitled%207.png)

```docker
docker cp 848ed82d3723:/fuseki/backups/ .
# Syntaxis
docker cp <containerId>:/file/path/within/container /host/path/target
```

![Importar%20datos%20desde%20ETL%2070c44957c2ac481aaf74d0e198415c50/Untitled%208.png](Importar%20datos%20desde%20ETL%2070c44957c2ac481aaf74d0e198415c50/Untitled%208.png)

# Restaurar backup Trellis

![Importar%20datos%20desde%20ETL%2070c44957c2ac481aaf74d0e198415c50/Untitled%209.png](Importar%20datos%20desde%20ETL%2070c44957c2ac481aaf74d0e198415c50/Untitled%209.png)

# Pentaho

Instalar pentaho

Cargar proyecto de  [https://git.izertis.com/universidaddemurcia/semantmurc/dataset-etl](https://git.izertis.com/universidaddemurcia/semantmurc/dataset-etl)