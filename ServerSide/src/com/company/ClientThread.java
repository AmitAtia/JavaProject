package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientThread extends Thread {

    // Action Keys
    public static final int SHOW_LIST = 100;
    public static final int REMOVE_FROM_CART = 101;
    public static final int ADD_TO_CART = 103;

    // Returns
    public static final int OKAY = 50;
    public static final int FAILS = 51;

    // Connection details
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    // Shop list
    private static Cart productList = new Cart();
    // Product[] productList = {new Product("Milk", 10, 15), new Product("Rice", 20, 10), new Product("Waffle", 12, 10)};

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            int action = inputStream.read();
            switch (action){
                case SHOW_LIST:
                    showList();
                    break;
                case REMOVE_FROM_CART:
                    addProduct();
                    break;
                case ADD_TO_CART:
                    addToCart();
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Returns to inputStream the product details, by product name.
    public void addToCart() throws IOException {
        int productNameLength = inputStream.read();
        if(productNameLength == -1)
            throw new IOException("Error reading product name");
        byte[] productNameBytes = new byte[productNameLength];
        int actuallyRead = inputStream.read(productNameBytes);
        if(actuallyRead != productNameLength)
            throw new IOException("Error reading product name");
        String productName = new String(productNameBytes);
        for (int i = 0; i < productList.size(); i++) {
            if(productList.get(i).getName().equals(productName)) {
                outputStream.write(OKAY);
                outputStream.write(productList.get(i).getBytes()); // Found, sending to client.
                boolean inStock = productList.get(i).lowerQuantity();
                if(!inStock)
                    productList.remove(productList.get(i).getName());
                return;
            }
        }
        outputStream.write(FAILS); // Could not find product.
    }

    // Sends the client the list.
    private void showList() throws IOException{
        for (int i = 0; i < productList.size(); i++) {
            outputStream.write(productList.get(i).getBytes());
        }
    }

    // If the customer deleted a product from the list, add it back to the products list
    private void addProduct() throws IOException{
        int productLength = inputStream.read();
        if(productLength != -1) {
            byte[] productNameBytes = new byte[productLength];
            int actuallyRead = inputStream.read(productNameBytes);
            if(actuallyRead != productLength)
                throw new IOException("Error reading product name");
            String productName = new String(productNameBytes);

            productList.addProduct(productName);
        }
    }

}