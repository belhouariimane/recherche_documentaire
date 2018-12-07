package Donnees;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import database.IndexDB;
import document.TokenDocument;
import document.TokenDocuments;

public class TestTraitement {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		traitementDonees traitement= new traitementDonees();
		try {
			traitement.InfoDoc();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	IndexDB indexDB=new IndexDB();
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
