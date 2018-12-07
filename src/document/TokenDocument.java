package document;

import java.io.Serializable;
import java.util.ArrayList;

import org.bson.types.ObjectId;

public class TokenDocument implements Serializable{
	private String docID;
	int frequence;
	private ArrayList<Integer> position;
	
	private static final long serialVersionUID = 1L;
	ObjectId id;
	
	
	public void setId(ObjectId id){
		this.id=id;
	}
	
	public ObjectId  getId(){
		return this.id;
	}
	
	public TokenDocument(String num){
		this.docID=num;
		position=new ArrayList<Integer>();
	}
	
	public void addPosition(int p){
		this.position.add(p);
	}
	public String getDocID(){
		return docID;
	}
	
	public int getFrequence(){
		return position.size();
	}
	
	public ArrayList<Integer> getPosition(){
		return this.position;
	}
	
}
