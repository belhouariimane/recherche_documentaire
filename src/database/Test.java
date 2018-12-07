package database;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import document.TokenDocument;
import document.TokenDocuments;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IndexDB indexDB=new IndexDB();
		TokenDocuments tokens=new TokenDocuments();
		tokens.addDocument("doc3");
		tokens.addDocumentPostion("doc4",125);
		tokens.addDocumentPostion("doc4",23);
		
		indexDB.add("token6",tokens);
		indexDB.addDocPosition("token6", "doc4", 34);
		Hashtable<String,TokenDocuments> index=indexDB.getIndex();
		Set keys = index.keySet();
		 
		//obtenir un iterator des clés
		 Iterator itr = keys.iterator();
		 
		 String key="";
		 //affichage des pairs clé-valeur
		    while (itr.hasNext()) { 
		       // obtenir la clé
		       key = (String)itr.next();

		       /*public V get(Object key): retourne la valeur correspondante
		        * à la clé, sinon null si le map ne contient aucune valeur
		        * correspondante
		        */
		       System.out.println("Key: "+key+" & Value: ");
		       for(TokenDocument doc : index.get(key).getDocuments()){
					System.out.println(doc.getDocID()+"		"+doc.getFrequence());
		       }
		    } 
		

	}

}
