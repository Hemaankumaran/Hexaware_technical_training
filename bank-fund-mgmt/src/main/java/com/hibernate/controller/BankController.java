package com.hibernate.controller;

import com.hibernate.config.ProjConfig;
import com.hibernate.model.Fund;
import com.hibernate.model.Manager;
import com.hibernate.service.BankService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.Instant;
import java.util.List;
import java.util.Scanner;

public class BankController {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjConfig.class);
        BankService bankService = context.getBean(BankService.class);
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("1. Add Manager");
            System.out.println("2. Add Fund");
            System.out.println("3. Display All Funds for Specific Manager");
            System.out.println("0. Exit");
            System.out.println("Enter choice:");
            int ch = sc.nextInt();sc.nextLine();
            if(ch == 0) break;
            switch (ch){
                case 1:
                    Manager manager = new Manager();
                    System.out.println("Enter Name: ");
                    manager.setName(sc.nextLine());
                    System.out.println("Enter email: ");
                    manager.setEmail(sc.nextLine());
                    bankService.insertManager(manager);
                    System.out.println("Manager added.!");
                    break;
                case 2:
                    Fund fund = new Fund();
                    System.out.println("Enter Manager ID: "); // keeping at first to handle exception first
                    int managerID = sc.nextInt();sc.nextLine();
                    try{
                        Manager manager1 = bankService.getManagerById(managerID);
                        System.out.println("Enter Name: ");
                        fund.setName(sc.nextLine());
                        System.out.println("Enter Asset Under Mgmt Amount: ");
                        fund.setAumAmount(sc.nextBigDecimal());
                        System.out.println("Enter Expense Ratio (in %): ");
                        fund.setExpenseRatio(sc.nextBigDecimal());
                        fund.setCreatedAt(Instant.now());
                        fund.setManager(manager1);
                        bankService.setFund(fund);
                        System.out.println("Fund added..!");
                    } catch (RuntimeException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    List<?> list = bankService.getAllFundWithManager(); // wildcard
                    list.forEach(System.out :: println);
                    break;
                default:
                    System.out.println("Invalid Option");
            }
        }
        sc.close();
        context.close();
    }
}
