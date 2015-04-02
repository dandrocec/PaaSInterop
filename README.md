# PaaSInterop

PaaS interoperability prototype

This is prototype for PaaS interoperability at PaaS storage and API remote levels that currently includes three PaaS offers (Google App Engine, Salesforce, and Microsoft Azure). Prototype supports two use cases:
In the first use case, data can be migrated between different providers of platform as a service. Two main requirements are defined. First, the user must be able to port all data from one PaaS provider to another. Secondly, the user may move only one chosen data container (for example, table, custom object, or entity) from one PaaS offer to another. Additionally, the migration method should be flexible and use the ontologies and AI planning method described later in this dissertation. This use case will check if the ontology can be used to semantically annotate relevant API operations and whether data type mappings work. Successful execution of more complex interoperability scenarios cannot be imagined without being able to move data from one vendor to another.

In the second use case, current user information from one PaaS offer will be added to the application hosted on another PaaS offer. The main aim is to investigate interoperability problems on service layer when using APIs from different providers. To solve possible interoperability issues, the ontology driven data mediation will be used and tested in this use case. Web operations and their inputs/outputs will be semantically annotated, and SAWSDL and XSLT will be used to define service type mappings. 
