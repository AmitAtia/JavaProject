package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class StoreConnection {

    // Connection
    public static final String HOST = "127.0.0.1";
    public static final int PORT = 3000;
    public static Socket socket;
    public static InputStream inputStream;
    public static OutputStream outputStream;

    // Constant variables
    public static final int SHOW_LIST = 100;
    public static final int REMOVE_FROM_CART = 101;
    public static final int ADD_TO_CART = 103;

    // User Cart
    public static Cart myCart = new Cart();
    public static int cartSum = 0;

    // Returns
    public static final int OKAY = 50;

    public static void Actions(int action, String productName) {
        try{
            socket = new Socket(HOST, PORT);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            switch(action) {
                case ADD_TO_CART:
                    addToCart(productName);
                    break;
                case SHOW_LIST:
                    showList();
                    break;
                case REMOVE_FROM_CART:
                    removeFromCart(productName);
                    break;
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
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

    // Remove product from cart
    private static void removeFromCart(String productName) throws IOException {
        // Lowering the product price from the total
        int indexOf = myCart.getIndex(productName);
        if(indexOf == -1)
            throw new IndexOutOfBoundsException("Do not attempt to delete if you do not have the product");
        cartSum -= myCart.get(indexOf).getPrice();

        // Removes the product from the cart list
        myCart.remove(productName);

        // Sends the details to the server
        outputStream.write(REMOVE_FROM_CART);
        outputStream.write(productName.getBytes().length);
        outputStream.write(productName.getBytes());
    }

    // Add product to cart
    // Returns true if the product was successfully added, else - false.
    public static boolean addToCart(String productName) throws IOException {
        Product product = new Product();

        // Sending a request to the server with the product name details
        outputStream.write(ADD_TO_CART);
        outputStream.write(productName.getBytes().length);
        outputStream.write(productName.getBytes());

        // Getting the answer from the server
        int answer = inputStream.read();
        if(answer == OKAY) {
            int productNameLength = inputStream.read();
            if (productNameLength == -1)
                throw new IOException("Error reading product name");
            byte[] productNameBytes = new byte[productNameLength];
            int actuallyRead = inputStream.read(productNameBytes);
            if (actuallyRead != productNameLength)
                throw new IOException("Error reading product name");
            // Set the new product details.
            product.setName(new String(productNameBytes));
            product.setPrice(inputStream.read());
            product.setQuantity(inputStream.read());
        } else {
            System.out.println("Product not listed, please try again.");
            return false;
        }
        if(product.getName() != null) {
            myCart.add(product);
            cartSum += product.getPrice();
        }
        return true;
    }

    public static void showList() throws IOException{
        // Sending a request to the server
        outputStream.write(SHOW_LIST);
        int counter = 0;
        int productNameSize = inputStream.read();
        while(productNameSize != -1) {
            byte[] productNameBytes = new byte[productNameSize];
            int actuallyRead = inputStream.read(productNameBytes);
            if(actuallyRead != productNameSize)
                throw new IOException("Error reading products name.");
            String productName = new String(productNameBytes);
            int productPrice = inputStream.read();
            int productQuantity = inputStream.read();
            counter++;
            System.out.println(counter + ". " + productName + ", Price: " + productPrice + ", Quantity: " + productQuantity + ".");
            productNameSize = inputStream.read();
        }
    }

    public static int sum(){
        return cartSum;
    }
}