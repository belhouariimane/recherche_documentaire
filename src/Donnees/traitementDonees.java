package Donnees;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import database.IndexDB;
import document.TokenDocument;
import document.TokenDocuments;

public class traitementDonees {
	public  static ArrayList<Document> listeDesDoc =new ArrayList<Document>();
	public static ArrayList<String> stopWord=new ArrayList<String>();
	//Methode qui permet de parser un repertoire et de retrouver ses fichiers
	private  void findFilesRecursively(File file, ArrayList<File> all, final String extension) {
	    //Liste des fichiers correspondant a l'extension souhaitee
	    final File[] children = file.listFiles(new FileFilter() {
	    	public boolean accept(File f) {
	                return f.getName().endsWith(extension);
	            }}
	    );
	    if (children != null) {
	        //Pour chaque fichier recupere, on appelle a nouveau la methode
	        for (File child : children) {
	            all.add(child);
	            findFilesRecursively(child, all, extension);
	        }
	    }
	}
	public   void allDoc() {
		 final 	ArrayList<File> all = new ArrayList<File>();
		    findFilesRecursively(new File("C:\\Users\\IMANE\\Desktop\\RechercheDoc\\AP"), all, "");
		    for (File file : all) {
		    	splitDoc(file);
		    }
		    System.out.println("end of all docs");
	}
	private  Document convertStringToXMLDocument(String xmlString)
    {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         
        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try
        {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();
             
            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
	private  void splitDoc(File f) {
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(f));
		
			String line;
			String Doc="";
			while((line=in.readLine())!=null){
				 Doc+=line+"\n";
				if(line.equals("</DOC>")){
					//Use method to convert XML string content to XML Document object
			        Document doc = convertStringToXMLDocument( Doc );
			        System.out.println("doc ajouter");
					listeDesDoc.add(doc);
					Doc="";
				}
			}
			
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println("end of split doc");
	}
	
	public void InfoDoc() throws ParserConfigurationException, IOException {
		IndexDB bd=new IndexDB();
		allDoc();
		ArrayList<String> stopswords= stopWords();
		for(Document f :listeDesDoc ) {
				NodeList Docnum = f.getElementsByTagName("DOCNO");
				
				Node node = Docnum.item(0);
			    System.out.println("");    //Just a separator
				Element eElement = (Element) node;
			   // System.out.println("doc num : "    +eElement.getTextContent());
			    
				NodeList nList = f.getElementsByTagName("TEXT");
			    Node node1 = nList.item(0);
				System.out.println("");    //Just a separator
				Element eElement1 = (Element) node1;
				//System.out.println("doc text : "    +eElement1.getTextContent());
				//traitement base de donnees
				String text=eElement1.getTextContent();
				String[]txt= text.split("\\s+|\\n+");
				int position = 0;
				for(String t:txt ) {
				System.out.println(t);
				System.out.println("\n");
				}
				for(int i=0;i<txt.length;i++) {
					 position += txt[i].length();
					if(bd.exist(txt[i])) {
						System.out.println("exist"+bd);
						bd.addDocPosition(txt[i],eElement.getTextContent() , position+i);
					}else{
						if(!stopswords.contains(txt[i])) {
							System.out.println(txt[i]);
							TokenDocument td=new TokenDocument(eElement.getTextContent());
							td.addPosition(position+i);
							TokenDocuments tds=new TokenDocuments();
							tds.addDoc(td);
							bd.add(txt[i],tds );
						}
				}
			}
				
		}
			 
			//Normalize the XML Structure; It's just too important !!
			
		//}
	}
	public  ArrayList<String> stopWords() throws IOException{
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader("C:\\Users\\IMANE\\Desktop\\RechercheDoc\\stopwords.txt"));
			String line;
			while((line=in.readLine())!=null){
				stopWord.add(line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stopWord;
	}
	
}

