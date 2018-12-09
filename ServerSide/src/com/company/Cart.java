package com.company;

public class Cart {

    private static final int PRODUCT_LIST_LENGTH = 3;
    private int size;
    private Product[] arr;

    public Cart() {
        arr = new Product[PRODUCT_LIST_LENGTH];
        size = 0;
        add("Milk", 10, 3);
        add("Rice", 20, 10);
        add("Waffle", 12, 10);
    }

    public void add(String name, int price, int quantity) {
        Product newProduct = new Product(name,price,quantity);
        arr[size] =  newProduct;
        size++;
    }

    public void add(Product product){
        for (int i = 0; i < size; i++) {
            if(arr[i].getName().equals(product.getName())) {
                arr[i].setQuantity(arr[i].getQuantity() + 1);
                return;
            }
        }
        Product addedProduct = new Product(product.getName(), product.getPrice());
        arr[size] = addedProduct;
        size++;
    }

    public Product get(int index) {
        if(index > size)
            throw new IndexOutOfBoundsException();
        return arr[index];
    }

    public void set(int index, Product product) {
        arr[index] = product;
    }

    public void remove(String productName) {
        for (int i = 0; i < size; i++) {
            if(arr[i].getName().equals(productName)) {
                if(arr[i].getQuantity() > 1)
                    arr[i].setQuantity(arr[i].getQuantity()-1);
                else {
                    for (int j = i; j < size - 1; j++) {
                        arr[j] = arr[j + 1];
                    }
                    size--;
                }
                return;
            }
        }
    }

    public void addProduct(String productName) {
        for (int i = 0; i < size; i++) {
            if(arr[i].getName().equals(productName)) {
                arr[i].setQuantity(arr[i].getQuantity() + 1);
                return;
            }
        }
        if(productName.equals("Milk"))
            arr[size] = new Product(productName, 10);
        else if(productName.equals("Rice"))
            arr[size] = new Product(productName, 20);
        else
            arr[size] = new Product(productName, 12);
        size++;
    }

    public int size() {
        return size;
    }

    public void show() {
        for (int i = 0; i < size; i++) {
            System.out.println(i+1 + ". " + arr[i].getName() + ", Price: " + arr[i].getPrice() + ", Quantity: " + arr[i].getQuantity());
        }
    }
}
