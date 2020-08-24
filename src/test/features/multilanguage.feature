#Author: Izertis
#Keywords Summary : multilanguage
Feature: Generation of RDF in a specific language from the entities ...

    # Project (Spanish)
    Scenario: the ETL process a Project object and this object is sent to Management-System
        Given a new message arrives to managementdata queue
        Then the management-system creates Project RDF object in Spanish language
        Examples:
            | Property          | Value                          |
            | IDPROYECTO        | 151                            |
            | NOMBRE            | CIRUGIA VETERINARIA-RADIOLOGIA |
            | PROYECTOFINALISTA | N                              |
            | LIMITATIVO        | N                              |

    # Person (English)
    Scenario: the ETL process a Person object and this object is sent to Management-System
        Given a new message arrives to managementdata queue
        Then the management-system creates Person RDF object in English language
        Examples:
            | Property         | Value                          |
            | IDPERSONA        | 1                              |
            | PERS_CENT_CODIGO | 0E01                           |
            | CED_NOMBRE       | FACULTY OF BIOLOGY             |
            | PERS_DEPT_CODIGO | E0A8                           |
            | DEP_NOMBRE       | CELLULAR BIOLOGY AND HISTOLOGY |

    # Article (French)
    Scenario: the ETL process an Article object and this object is sent to Management-System
        Given a new message arrives to managementdata queue
        Then the management-system creates Articule RDF object in French language
        Examples:
            | Property        | Value                                                                                                                                     |
            | CODIGO          | 334                                                                                                                                       |
            | TITULO          | ETUDE CINÉTIQUE DE LA PHASE DE TRANSITION D'UN SYSTÈME DE RÉACTION CHIMIQUE COUPLÉ À UNE ÉTAPE ENZYMATIQUEMENT CATALYSÉE. APPLICATION À T |
            | ANO             | 1987                                                                                                                                      |
            | PAIS_CODIGO     |                                                                                                                                           |
            | REIS_ISSN       |                                                                                                                                           |
            | CATALOGO        |                                                                                                                                           |
            | AREA            |                                                                                                                                           |
            | NOMBRE_REVISTA  |                                                                                                                                           |
            | CUARTIL_REVISTA |                                                                                                                                           |
            | URL_REVISTA     |                                                                                                                                           |
            | VOLUMEN         | 27                                                                                                                                        |
            | NUMERO          |                                                                                                                                           |
            | PAGDESDE        | 15                                                                                                                                        |
            | PAGHASTA        | 25                                                                                                                                        |
            | NUMPAG          |                                                                                                                                           |
            | COAUT_EXT       | N                                                                                                                                         |

    # Postdoctoral grants (German)
    Scenario: the ETL process a Grant object and this object is sent to Management-System
        Given a new message arrives to managementdata queue
        Then the management-system creates Grant RDF object in German language
        Examples:
            | Property                   | Value                                                                                                                                                    |
            | SOLAY_NUMERO               | 97                                                                                                                                                       |
            | IDPERSONA                  | 6678                                                                                                                                                     |
            | IDPERSONADIRECTOR          | 657                                                                                                                                                      |
            | SOLAY_CODIGO_TESIS_SIVA    | 002745                                                                                                                                                   |
            | TINA_FECHA                 | 1992/07/03 00:00:00                                                                                                                                      |
            | TINA_CALIFICACION          |                                                                                                                                                          |
            | TINA_TITULO                | Anwendungen des Trainings sozialer Kompetenzen in der Primärprävention: Untersuchung der unterschiedlichen Auswirkungen der Anwendung eines Di-Programms |
            | SOLAY_FECINI_PREDOC        |                                                                                                                                                          |
            | SOLAY_FECFIN_PREDOC        |                                                                                                                                                          |
            | SOLAY_SOLB_NPOSTDOC        | 10699                                                                                                                                                    |
            | SOLAY_FECCONV_POSTDOC      |                                                                                                                                                          |
            | SOLAY_ENTIDAD_CONV_POSTDOC |                                                                                                                                                          |
            | SOLAY_ESTADO               | B                                                                                                                                                        |
