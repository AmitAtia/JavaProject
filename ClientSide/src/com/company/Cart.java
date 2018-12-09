package com.company;

public class Cart {

    private int size;
    private Product[] arr;

    public Cart() {
        arr = new Product[2];
        size = 0;
    }

    public void add(Product product){
        for (int i = 0; i < size; i++) {
            if(arr[i].getName().equals(product.getName())) {
                arr[i].setQuantity(arr[i].getQuantity() + 1);
                return;
            }
        }
        makeRoom();
        Product addedProduct = new Product(product.getName(), product.getPrice());
        arr[size] = addedProduct;
        size++;
    }

    public Product get(int index) {
        if(index > size)
            throw new IndexOutOfBoundsException();
        return arr[index];
    }

    private void makeRoom() {
        if(size == arr.length){
            Product[] temp = new Product[size*2];
            for (int i = 0; i < size; i++) {
                temp[i] = arr[i];
            }
            arr = temp;
        }
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
            }
        }
    }

    public int size() {
        return size;
    }

    public void show() {
        if(size == 0)
            System.out.println("Your cart is empty at the moment.");
        for (int i = 0; i < size; i++) {
            System.out.println(i+1 + ". " + arr[i].getName() + ", Price: " + arr[i].getPrice() + ", Quantity: " + arr[i].getQuantity());
        }
    }

    public int getIndex(String productName) {
        for (int i = 0; i < size; i++) {
            if(arr[i].getName().equals(productName))
                return i;
        }
        return -1;
    }
}
