/*
 *  MyTagger.java
 *
 *
 * Copyright (c) 2000-2001, The University of Sheffield.
 *
 * This file is part of GATE (see http://gate.ac.uk/), and is free
 * software, licenced under the GNU Library General Public License,
 * Version 2, June1991.
 *
 * A copy of this licence is included in the distribution in the file
 * licence.html, and is also available at http://gate.ac.uk/gate/licence.html.
 *
 *  Ricardo KAKA, 30/1/2014
 *
 *  $Id: MyTagger.jav 2820 2001-11-14 17:15:43Z oana $
 */

package com.akash.MyTagger;


import java.util.*;
import java.io.*;

import gate.*;
import gate.creole.*;
import gate.util.*;


/** 
 * This class is the implementation of the resource MYTAGGER.
 */
public class MyTagger  extends AbstractLanguageAnalyser
  implements ProcessingResource {

	public void execute() throws ExecutionException{

  		// Remove the indexes.

		AnnotationSet Nameofperson  = this.document.getAnnotations().get("Person") ;
		AnnotationSet Naming = this.document.getAnnotations().get("Belong to");
        
		ArrayList checking = new ArrayList();
		
		
      Iterator  ii = Nameofperson.iterator();

      
      
      
      while(ii.hasNext()){
           Annotation temp = (Annotation) ii.next();
           boolean check = false ;
           if(temp.getFeatures().get("matches") != null){
        	   
        	    
                ArrayList<Integer> a = (ArrayList) temp.getFeatures().get("matches") ;
                Annotation annot = (Annotation) this.document.getAnnotations().get(a.get(0));
                
                if(!checking.contains(annot)){
                long start  = annot.getStartNode().getOffset(); 
                long end = annot.getEndNode().getOffset() ;
                String stringData = this.document.getContent().toString();
                stringData = stringData.substring((int)start,(int)end);
               
                
                
                Annotation annot1 = null ;
                Iterator ite = Naming.iterator() ;
                while(ite.hasNext()){
                	annot1= (Annotation)ite.next() ;
               
                	String matchData =(String)annot1.getFeatures().get("dom");
                	
                    
                    if(stringData.equals(matchData) ){
                    	check =true ;
                    	break;
                    }
                	
                }
                
                
                if(check == true){
                	 Annotation finalannot = (Annotation) this.document.getAnnotations().get(a.get(1)); 
                	 
                	 System.out.println(finalannot);
                	 
                	 long secondStart = finalannot.getStartNode().getOffset() ;
                	 long secondEnd = finalannot.getEndNode().getOffset() ;
                	 
                	 String Content1 = this.document.getContent().toString();
                     Content1 = Content1.substring((int)secondStart,(int)secondEnd);
                     
                	 annot1.getFeatures().put("Second_name", Content1);
                	 
                	 System.out.println("Matched" + Content1);
                }
                
               
                	 checking.add(annot);
               
               }
                
           }

      }
		

	}

} // class MyTagger
