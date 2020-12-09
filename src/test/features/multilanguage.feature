#Author: Izertis
#Keywords Summary : multilanguage
Feature: Generation of RDF in a specific language from the entities ...

    # Project (Spanish)
    Scenario: the ETL process a FinancialProject object and this object is sent to Management-System
        Given a new message arrives to managementdata queue
        | Property          | Value                          		|
        | id								| SUBV															|
        | codTipoFuente			| PGE											 					|
        | @class        		| FinanciacionProyecto           		|        
        | tipoFinanciacion  | SUBVENCIÓN 										 		|
        | tipoFuente        | PRESUPUESTOS GENERALES DEL ESTADO |
        Then the management-system creates Project RDF object in Spanish language            

    # Person (English)
    Scenario: the ETL process a Person object and this object is sent to Management-System
        Given a new message arrives to managementdata queue
        | Property         | Value                          										|
        | id							 | 4897																								|
        | @class					 | Persona										    										|
        | Centro           | SCHOOL OF MEDICINE                             		|
        | Dpto             | SURGERY, PEDIATRICS, OBSTETRICS AND GYNECOLOGY     |
        Then the management-system creates Person RDF object in English language
        
        
    # Article (French)
    Scenario: the ETL process an Article object and this object is sent to Management-System
        Given a new message arrives to managementdata queue
        | Property        | Value                                                                                                                                     |
        | id							| 1																																																																					|
        | clase						| articulo																																																																	|
        | CODIGO          | 334                                                                                                                                       |
        | TITULO          | ETUDE CINÉTIQUE DE LA PHASE DE TRANSITION D UN SYSTÈME DE RÉACTION CHIMIQUE COUPLÉ À UNE ÉTAPE ENZYMATIQUEMENT CATALYSÉE. APPLICATION À T |
        | ANO             | 1987                                                                                                                                      |
        | VOLUMEN         | 27                                                                                                                                        |      
        | PAGDESDE        | 15                                                                                                                                        |
        | PAGHASTA        | 25                                                                                                                                        |
        | COAUT_EXT       | N																																																																					|
        Then the management-system creates Articule RDF object in French language
        
    # Postdoctoral grants (German)
    Scenario: the ETL process a Grant object and this object is sent to Management-System
        Given a new message arrives to managementdata queue
        | Property                   | Value                                                                                                                                                    |
        | id												 | 1															|
        | clase											 | posdocturado-grants					  |
        | SOLAY_NUMERO               | 97                                                                                                                                                       |
        | IDPERSONA                  | 6678                                                                                                                                                     |
        | IDPERSONADIRECTOR          | 657                                                                                                                                                      |
        | SOLAY_CODIGO_TESIS_SIVA    | 002745                                                                                                                                                   |
        | TINA_FECHA                 | 1992/07/03 00:00:00                                                                                                                                      |
        | TINA_TITULO                | Anwendungen des Trainings sozialer Kompetenzen in der Primärprävention: Untersuchung der unterschiedlichen Auswirkungen der Anwendung eines Di-Programms |
        | SOLAY_SOLB_NPOSTDOC        | 10699                                                                                                                                                    |
        | SOLAY_ESTADO               | B  																																																																											|
        Then the management-system creates Grant RDF object in German language
                                                                                                                                                                  
