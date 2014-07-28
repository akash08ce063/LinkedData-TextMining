package sheffield.creole.simpletest;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.apache.jena.riot.RDFDataMgr;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.VCARD;

import gate.Annotation;
import gate.AnnotationSet;
import gate.ProcessingResource;
import gate.creole.AbstractLanguageAnalyser;
import gate.creole.ExecutionException;

public class SimpleTest  extends AbstractLanguageAnalyser
  implements ProcessingResource {

	public void execute() throws ExecutionException{

  		// Remove the indexes. 

		 String NSP = "http://www.semanticweb.org/akash/ontologies/2014/2/untitled-ontology-3#Person_name";
	     String NSU = "http://www.semanticweb.org/akash/ontologies/2014/2/untitled-ontology-3#University_Name" ;
	     String NSBelong = "http://www.semanticweb.org/akash/ontologies/2014/2/untitled-ontology-3#Belong_to" ;
	     String NSPos = "http://www.semanticweb.org/akash/ontologies/2014/2/untitled-ontology-3#Position_of_Person";
	     String NSHasPos = "http://www.semanticweb.org/akash/ontologies/2014/2/untitled-ontology-3#Has_Position";
	     String NSOrg = "http://www.semanticweb.org/akash/ontologies/2014/2/untitled-ontology-3#Organization_Unit";
		
		
 //  String NSU = "http://UniName/" ;
     String NSFOAF = "http://xmlns.com/foaf/0.1/";
  // String NSP = "http://Name_of_Person/";
 //  String NSPos= "http://Position_of_person/";
 //  String NSBelong = "http://Belong_to/";
//   String NSHasPos = "http://Has_Pos/";
     Model model = null ;
     boolean check = false ;
     File ff = new File("Data8.rdf");
     if(ff.exists()){
    	 model =  RDFDataMgr.loadModel("Data8.rdf") ;
    	 check =true; 
     }
     
   OntModel base1 = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM ); 
    base1.read("jj2.owl");
   
   base1.setNsPrefix("foaf", NSFOAF);
   
		
		
		AnnotationSet a  = this.document.getAnnotations().get("Belong to");
		AnnotationSet b  = this.document.getAnnotations().get(" Has_Positions ");
		AnnotationSet c  = this.document.getAnnotations().get("OrganizationalUnit");
		
		System.out.println("Size of b"+ b.size());
		
		OntClass FOAFClass = base1.createClass(NSFOAF+"Person");
		Iterator itr = a.iterator();
		Iterator itr1 = b.iterator() ;
		Iterator itr2 = c.iterator() ;
		
		while(itr.hasNext()){
			Annotation annot = (Annotation) itr.next();
			String PersonName1 = (String)annot.getFeatures().get("dom");
		    String UniName = 	(String)annot.getFeatures().get("ran");
		    String SameName = "";
		    if((String)annot.getFeatures().get("Second_name")!=null ){
		    	SameName = (String)annot.getFeatures().get("Second_name") ;
		    }
		    UniName = UniName.replaceAll(" ", "_");
		    OntClass UniClass = base1.getOntClass(NSU);
		    OntClass PersonName = base1.getOntClass(NSP);
		   
		    
		    // Create instance here
		    
		    Individual UniversityInstance = base1.createIndividual( NSU+"/"+UniName , UniClass );
		    Individual PersonNameInstance = base1.createIndividual( NSP+"/"+PersonName1 , PersonName );
	
		    PersonNameInstance.addProperty(FOAF.name, PersonName1);
		    UniversityInstance.addProperty(RDFS.label, UniName);
		    
		//    PersonName.setSuperClass(FOAFClass);
		    
		    if(SameName != ""){
		    	
		    	Individual sameclass = base1.createIndividual( NSP+"/"+SameName , PersonName );
		    	PersonNameInstance.setSameAs(sameclass);
		    	
		    }
		    
		    UniversityInstance.setSameAs(base1.createResource("http://dbpedia.org/resource/"+ UniName));
		    
		  //  ObjectProperty belong_to = base1.createObjectProperty(NSBelong+"Belong_to/"+PersonName1);
		    ObjectProperty belong_toclass = base1.getObjectProperty(NSBelong);
		    Individual belong_to_instance = base1.createIndividual( NSBelong+"/"+PersonName1 , belong_toclass );
			
	        belong_to_instance.addProperty(RDFS.domain,PersonNameInstance) ;
	        belong_to_instance.addProperty(RDFS.range, UniversityInstance);
	        
			
		}
		
		while(itr1.hasNext()){
			

			Annotation annot1 = (Annotation) itr1.next();
			String PersonName1 = (String)annot1.getFeatures().get("dom");
			String PersonPosition = (String)annot1.getFeatures().get("ran");
			
			OntClass PersonName = base1.getOntClass(NSP);
			
			OntClass PersonPos = base1.getOntClass(NSPos);
			
			Individual PersonNameInstance = base1.createIndividual( NSP+"/"+PersonName1 , PersonName );
			Individual PersonPosInstance = base1.createIndividual( NSPos+"/"+PersonPosition , PersonPos );
				
			
		    ObjectProperty has_posclass = base1.getObjectProperty(NSHasPos);
		    Individual has_pos = base1.createIndividual(NSHasPos+"/"+PersonName1,has_posclass);
			
		    has_pos.addProperty(RDFS.domain,PersonNameInstance) ;
		    has_pos.addProperty(RDFS.range, PersonPosInstance);
	        
			   
			PersonNameInstance.addProperty(FOAF.name, PersonName1);
			PersonPosInstance.addProperty(RDFS.label, PersonPosition);

			       
		}
		
		 while(itr2.hasNext()){
			 
			Annotation annotation =  (Annotation) itr2.next();
			String OrgUnitt = (String)annotation.getFeatures().get("OrgUnit");
			OntClass OrgUnit = base1.getOntClass(NSOrg);
			Individual OrgNameInstance = base1.createIndividual( NSOrg+"/"+OrgUnitt , OrgUnit );
			
			OrgNameInstance.addProperty(RDFS.label, OrgUnitt) ;
			 
		 }
		
		   
	
		try {
			FileWriter file = new FileWriter(new File("Data8.rdf"));
			
			
			if(check == true){
				Model m = model.add(base1);
				m.write(file);
			
			}else{
				//FileWriter file = new FileWriter(new File("Data6.rdf"));
				base1.write(file);
			}
			file.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}