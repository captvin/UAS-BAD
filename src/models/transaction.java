package models;

import java.sql.Timestamp;


public class transaction {
    private int transactionId;
    private int productId;
    private int quantity;
    private String type;
    private String name;
    private String category;
    private Timestamp timestamp;

    public transaction(){}

    public transaction(int transactionId, int productId, int quantity, String type, Timestamp timestamp){
        this.transactionId = transactionId;
        this.productId = productId;
        this.quantity = quantity;
        this.type = type;
        this.timestamp = timestamp;
    }

    public transaction(int productId, int quantity){
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getId(){
        return transactionId;
    }
    public void setId(int transactionId){
        this.transactionId = transactionId;
    }

    public String getCategory(){
        return category;
    }
    public void setCategory(String category){
        this.category = category;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public int getProductId(){
        return productId;
    }
    public void setProductId(int productId){
        this.productId = productId;
    }

    public int getQty(){
        return quantity;
    }
    public void setQty(int quantity){
        this.quantity = quantity;
    }

    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type = type;
    }

    public Timestamp getTimestamp(){
        return timestamp;
    }
    public void setTimestamp(Timestamp timestamp){
        this.timestamp = timestamp;
    }
}
