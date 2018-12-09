/**
 * Store explanation:
 * Any customer can enter and purchase products from the list of existing products.
 * There is a list of possible actions with which you can:
 * Show the products list, Add product to your cart, Remove product from your cart, Show your cart products, Pay and leave the store.
 */

package com.company;

import java.util.Scanner;

public class Main {

    public static String inputProudct;

    public static void main(String[] args) {

        System.out.println("*** Welcome to myStore, these are our products: ***");
        StoreConnection.Actions(StoreConnection.SHOW_LIST, "");
        while (true){
            System.out.println(); // Space line
            System.out.println("Please Choose:");
            System.out.println("1. Show List");
            System.out.println("2. Add to cart");
            System.out.println("3. Remove from cart");
            System.out.println("4. Show my cart products");
            System.out.println("5. Pay and exit");
            System.out.print("your choice: ");
            int choice = readInteger();
            if(choice == 5) {
                System.out.println("Purchase amount: " + StoreConnection.sum());
                System.out.println("Thanks, bye!");
                return;
            }
            switch (choice){
                case 1:
                    StoreConnection.Actions(StoreConnection.SHOW_LIST, "");
                    break;
                case 2:
                    inputProudct = readString();
                    StoreConnection.Actions(StoreConnection.ADD_TO_CART, inputProudct);
                    break;
                case 3:
                    inputProudct = readString();
                    StoreConnection.Actions(StoreConnection.REMOVE_FROM_CART, inputProudct);
                case 4:
                    StoreConnection.myCart.show();
                    System.out.println("Total: " + StoreConnection.sum());
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }

    private static int readInteger(){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return Integer.valueOf(input);
    }

    private static String readString(){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return input;
    }
}