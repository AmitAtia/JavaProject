package com.company;

public class Product {

    private int quantity;
    private int price;
    private String name;

    public Product() {
        this(null, -1, -1);
    }

    public Product(String name, int price) {
        this(name,price,1);
    }

    public Product(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public byte[] getBytes() {
        int byteSize = name.getBytes().length + 3;
        byte[] productBytes = new byte[byteSize];
        byte[] nameBytes = name.getBytes(); // Username bytes array.
        productBytes[0] = (byte)nameBytes.length; // Save username bytes length.
        for (int i = 0; i < nameBytes.length; i++) {
            productBytes[i+1] = nameBytes[i];
        }
        productBytes[byteSize - 2] = (byte)price;
        productBytes[byteSize - 1] = (byte)quantity;
        return productBytes;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Returns false if cannot lower anymore , means the product out of stock.
    public boolean lowerQuantity() {
        if(quantity == 1)
            return false;
        quantity -= 1;
        return true;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
