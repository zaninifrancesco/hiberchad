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
        
        
        
        
        List<Person> persons = DBManager.getInstance().getAllPerson();
        
        try {
            Products p1 = new Products("Cipolla", 2.3f);
            Products p2 = new Products("Carota", 4.0f);
            Products p3 = new Products("Fiorella", 1.0f);
            Person createPerson = DBManager.getInstance().createPerson("Gianvi", "ncenzo", 10,p1,p2);
            Person createPerson1 = DBManager.getInstance().createPerson("Donato", "Franca", 50,p1,p2,p3);
            System.out.println(createPerson.getId());
        } catch (DBUniqueViolationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DBBadParamaterException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        System.out.println(persons.size());
    }
    
    
}
