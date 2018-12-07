package database;

import java.util.Hashtable;

import org.bson.Document;


import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.google.gson.*;
import document.TokenDocuments;

public class IndexDB extends Database{
	
	private static final String C_INDEX ="C_INDEX";
	private static final String TOKEN="token";
	private static final String DOCUMENTS="documents";
	
	public void add(String token,TokenDocuments docs){
		if(exist(token)) {
			throw new IllegalArgumentException("token déja ajouter");
		}
		MongoCollection<Document> tokens=db().getCollection(C_INDEX);
		Document tokenDoc = new Document(TOKEN,token)
				.append(DOCUMENTS,docs);	
		tokens.insertOne(tokenDoc);
	}

	public boolean exist(String token) {
		MongoCollection<Document> tokens = db().getCollection(C_INDEX);
	    FindIterable<Document> iterable = tokens.find(Filters.eq(TOKEN, token));
		return iterable.first() != null;
	}
	
	public void delete(String token){
		MongoCollection<Document> tokens = db().getCollection(C_INDEX);
		tokens.deleteOne(Filters.eq(TOKEN,token));
	}
	
	public TokenDocuments getDocs(String token){
		if(!exist(token)) {
			throw new IllegalArgumentException("token n'existe pas");
		}
		
		MongoCollection<Document> tokens = db().getCollection(C_INDEX);
		Document doc = tokens.find(Filters.eq(TOKEN, token)).first();
		doc.remove(TOKEN);doc.remove("_id");
		Gson gson = new Gson();
		return (TokenDocuments) gson.fromJson(trimJSON(doc.toJson()), TokenDocuments.class);
	}
	
	public String trimJSON(String json){
		String [] split=json.split(":", 2);
		return split[1].substring(0,split[1].length()-1);
	}
	
	public void setDocs(String token,TokenDocuments docs){
		if(!exist(token)) {
			throw new IllegalArgumentException("token n'existe pas");
		}
		MongoCollection<Document> tokens = db().getCollection(C_INDEX);
		tokens.findOneAndUpdate((Filters.eq(TOKEN, token)),
				new Document("$set", new Document(DOCUMENTS,docs)));
	}
	
	public Hashtable<String,TokenDocuments> getIndex(){
		Hashtable<String,TokenDocuments> index=new Hashtable<String,TokenDocuments>();
		MongoCollection<Document> tokens = db().getCollection(C_INDEX);
		for(Document doc : tokens.find()){
			String token=doc.get(TOKEN).toString();
			index.put(token, getDocs(token));
		}
		return index;
	}
	
	
	
	
	

}
