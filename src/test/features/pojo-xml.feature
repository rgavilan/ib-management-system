#Author: Izertis
#Keywords Summary : pojo-xml
Feature: Generation of RDF from imported xml files

    Scenario: the input processor sends xml file to Management-System
        Given a new message arrives to managementdata queue
        Then the management-system creates Activity RDF object
        Examples:
            | Data                 | Value                                   |
            | @class               | es.um.asio.domain.actividades.Actividad |
            | entityId             | 13765bbf-d677-4f8b-acf6-4da94b69fb5c    |
            | version              | 4                                       |
            | operation            | INSERT                                  |
            | idActividad          | 7819                                    |
            | idGrupoActividades   | 0                                       |
            | codTipoActividad     | 00                                      |
            | titulo               | SERVICIO REALIZADO MARZO A MAYO 2020    |
            | idTercero            | 122861                                  |
            | terceroConfidencial  | N                                       |
            | codTipoMoneda        | EUR                                     |
            | importeBase          | 617.5                                   |
            | tipoImpuesto         |                                         |
            | tipoImpositivo       | null                                    |
            | importeRepercutido   | 129.68                                  |
            | importeTotal         | 747.18                                  |
            | fechaInicioActividad | 2014/06/06 00:00:00                     |