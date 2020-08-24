#Author: Izertis
#Keywords Summary : pojo etl
Feature: Sent messages between Management-System and Triple-Store-Adapter

    Scenario: one message is sent by Management-System and the Event-processor catches it sending again for Triple-Store-Adapter
        Given a new message in managementdata queue
        Then the Event-processor sends the new message against Triple-Store-Adapter
        Examples:
            | Data               | Value       |
            | operation          | UPDATE      |
            | data               |             |
            | id                 | 1           |
            | nombre             | PROJECT ONE |
            | clase              | Proyecto    |
            | grupoInvestigacion |             |
            | id                 | 1           |
