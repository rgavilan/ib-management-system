set managementSystemPath=C:\Users\amartino\Documents\murcia\management-system\docker-devenv
set /P managementSystemPath="management-system path? default [%managementSystemPath%]"

set tripleStoragePath=C:\Users\amartino\Documents\murcia\triples-storage-adapter\docker-devenv\fuseki-trellis
set /P tripleStoragePath="triplestorage path? default [%tripleStoragePath%]"

set discoveryPath=C:\Users\amartino\Documents\murcia\discovery\docker-devenv
set /P discoveryPath="discovery path? default [%discoveryPath%]"


docker stop asio_kafka_1
docker stop asio_zookeeper_1
docker stop fuseki-sandbox
docker stop trellis
docker stop redis_asio
docker stop elasticsearch_asio
docker stop kibana_asio


docker container prune -f
docker volume prune -f
docker network prune -f

cd %discoveryPath%
docker-compose up -d
cd %managementSystemPath% 
docker-compose up -d
cd %tripleStoragePath%
docker-compose up -d


cd c:\desarrollo\bat-files





