LinkedData-TextMining
=====================

 export the data extracted  text mining system into Linked Data format 

develop an RDF Schema to capture the information from
Information Extraction System:
• Entities (University, University Position, etc.)
• Relations (Belongs to relation, etc.)

use of inheritance (e.g., University Person is-a Person).  re-use of 
existing schemas within your schema (e.g., FOAF).
Coreferences are to be exported using owl:sameAs, that is, when you detect two person names as
corefering, you export both of them, and create an owl:sameAs link between them.

RDF export ---

Export the information from  text mining system into RDF using the RDF
Schema developed above. Develop a custom GATE PR for this that uses the Jena libraries. Store
the resulting information in a triplestore


Linked Data ---

Generate links using the construct owl:sameAs between university mentions detected in your system and the corresponding university names in DBpedia


SPARQL Endpoint ---
Set up a SPARQL endpoint for your triplestore using Jena Fuseki


You can Develop  SPARQL queries for fetch the result from your RDF data:
• Find the names of all universities
• Find the names of all university employees, together with their position (if available) and
organizational unit (if available)
• For a given university name, provide all information from your knowledge base, as well as
DBPedia (i.e., query across two endpoints)

Application pipeling is located in the AssingmentPipeling Folder.
