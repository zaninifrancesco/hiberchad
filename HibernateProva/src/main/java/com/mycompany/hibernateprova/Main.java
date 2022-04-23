/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hibernateprova;

import com.mycompany.controllers.DBManager;
import com.mycompany.entities.Person;
import com.mycompany.entities.Products;
import com.mycompany.exceptions.DBBadParamaterException;
import com.mycompany.exceptions.DBUniqueViolationException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francesco Zanini
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("dsadsad");

        try {
            Products p1 = new Products("Cipolla", 2.3f);
            Products p2 = new Products("Carota", 4.0f);
            Products p3 = new Products("Fiorella", 1.0f);
            Products p4 = new Products("Gino", 1.0f);
            Person createPerson = DBManager.getInstance().createPerson("Gianvi", "ncenzo", 10, p1, p2);
            System.out.println("ID of P1 = " + p1.getId());
            System.out.println("ID of P2 = " + p2.getId());
            Person createPerson1 = DBManager.getInstance().createPerson("Donato", "Franca", 50, p3, p4);
            System.out.println("ID of P3 = " + p3.getId());

            System.out.println(createPerson.getId());

            List<Person> persons = DBManager.getInstance().getAllPerson();
            System.out.println("................");
            for (Person person : persons) {
                System.out.println("person products size: " + person.getName() + " ha " + person.getProducts().size() + " prodotti");

            }
        } catch (DBUniqueViolationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DBBadParamaterException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
