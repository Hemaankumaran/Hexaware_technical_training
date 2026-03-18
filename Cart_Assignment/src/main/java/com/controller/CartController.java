package com.controller;

import com.config.ProjConfig;
import com.enums.UserMembership;
import com.model.CartItem;
import com.model.User;
import com.service.CartService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Scanner;

public class CartController {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        var context = new AnnotationConfigApplicationContext(ProjConfig.class);
        CartService cartService = context.getBean(CartService.class);
        User user = context.getBean(User.class);
        CartItem cartItem = context.getBean(CartItem.class);

        while(true){
            System.out.println("\n1. Add Item and User Details");
            System.out.println("2. Fetch All Items");
            System.out.println("3. Fetch By User Name");
            System.out.println("4. Delete an Item");
            System.out.println("0. Exit");
            int ch = sc.nextInt();
            if(ch == 0)
                break;
            switch (ch){
                case 1:
                    System.out.println("1. Add User");
                    System.out.println("2. Add Item");
                    int ip = sc.nextInt();
                    if(ip == 1) {
                        sc.nextLine();
                        System.out.print("Enter User Name: ");
                        user.setName(sc.nextLine());
                        System.out.println("Select membership: ");
                        System.out.println("1.Regular 2.Premium");
                        int membership = sc.nextInt();
                        if(membership == 1)
                            user.setUserMembership(UserMembership.REGULAR);
                        else if(membership == 2)
                            user.setUserMembership(UserMembership.PREMIUM);
                        else
                            System.out.println("Invalid choice");
                        cartService.addUser(user);
                        System.out.println("User added Successfully");
                    }else if(ip == 2){
                        sc.nextLine();
                        System.out.println("Enter name: ");
                        cartItem.setName(sc.nextLine());
                        System.out.println("Enter price: ");
                        cartItem.setPrice(sc.nextBigDecimal());
                        System.out.println("Enter qty: ");
                        cartItem.setQty(sc.nextInt());
                        System.out.println("Enter user id: ");
                        user.setId(sc.nextInt());
                        cartItem.setUser(user);
                        cartService.addItem(cartItem);
                        System.out.println("Item added successfully");
                    }else{
                        System.out.println("Invalid Option");
                    }
                    break;
                case 2:
                    List<CartItem> list = cartService.fetchAllItems();
                    if(list.isEmpty())
                        System.out.println("No items present");
                    else
                        list.forEach(System.out :: println);
                    break;
                case 3:
                    sc.nextLine();
                    System.out.println("Enter user name: ");
                    List<CartItem> listByName = cartService.fetchByUserName(sc.nextLine());
                    if(listByName.isEmpty())
                        System.out.println("No items for the user provided");
                    else
                        listByName.forEach(System.out :: println);
                    break;
                case 4:
                    sc.nextLine();
                    System.out.println("Enter Cart Item Id to delete: ");
                    cartService.deleteById(sc.nextInt());
                    System.out.println("Deleted successfully");
                    break;
                default:
                    System.out.println("Invalid Option");
            }
        }
        sc.close();
    }
}
