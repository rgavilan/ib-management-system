#Author: Izertis
#Keywords Summary : pojo etl
Feature: Generation of RDF with nested objects

    Scenario: the ETL process a Project object and this object sin sent to Management-System
        Given a new message arrives to managementdata queue
        Then the management-system creates Project RDF object with nested objects
        Examples:
            | Data               | Value       |
            | operation          | UPDATE      |
            | data               |             |
            | id                 | 1           |
            | nombre             | PROJECT ONE |
            | clase              | Proyecto    |
            | grupoInvestigacion |             |
            | id                 | 1           |
