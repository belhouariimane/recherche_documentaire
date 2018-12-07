package document;

import java.io.Serializable;
import java.util.ArrayList;

import org.bson.types.ObjectId;


public class TokenDocuments implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ObjectId id;
	private ArrayList<TokenDocument> documents;
	
	public TokenDocuments() {
		documents=new ArrayList<TokenDocument>();
	}
	
	
	public void setId(ObjectId id){
		this.id=id;
	}
	
	public ObjectId  getId(){
		return this.id;
	}
	public void addDocument(String id){
		documents.add(new TokenDocument(id));
	}
	public void addDoc(TokenDocument doc) {
		documents.add(doc);
	}
	public void addDocumentPostion(String id,int p){
		TokenDocument doc=getDocument(id);
		doc.addPosition(p);
		if(!existDoc(id))
			documents.add(doc);
	}
	
	public ArrayList<TokenDocument> getDocuments(){
		return this.documents;
	}
	
	
	public boolean existDoc(String id){
		boolean found=false;
		if(!this.documents.isEmpty()){
			int i=0;
			do{
				if(this.documents.get(i).getDocID()==id){
						found=true;
				}		
				i++;
			}while(!found && (i<this.documents.size()));
		}
		return found;
	}
	public TokenDocument getDocument(String id){
		TokenDocument doc=new TokenDocument(id);
		boolean found=false;
		if(!this.documents.isEmpty()){
			int i=0;
			while((i<this.documents.size()) && !found ){
				if(this.documents.get(i).getDocID()==id){
					found=true;
					doc=this.documents.get(i);
				}	
				i++;
			}
		}
		return doc;
	}

}
