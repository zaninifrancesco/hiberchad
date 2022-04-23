/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controllers;

import org.hibernate.Session;
import com.mycompany.entities.Person;
import com.mycompany.entities.Products;
import com.mycompany.exceptions.DBBadParamaterException;
import com.mycompany.exceptions.DBUniqueViolationException;
import com.mysql.cj.x.protobuf.MysqlxCrud.Order;
import java.util.Arrays;
import java.util.List;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author Francesco Zanini
 */
public class DBManager {

    private static DBManager _instance = null;
    private SessionFactory sessionFactory;
    private boolean installed = false;

    private DBManager() {
        initConnection();
    }

    public static DBManager getInstance() {
        if (_instance == null) {
            _instance = new DBManager();
        }

        return _instance;
    }

    public List<Person> getAllPerson() {
        System.out.println("fetching data.. [select * from Person]");
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery query = criteriaBuilder.createQuery(Person.class);
        Root root = query.from(Person.class);
        root.fetch("products", JoinType.INNER); //gli dico di caricarmi anche i dati di products ( che sono LAZY) 
        query.select(root).distinct(true);

        List<Person> result = session.createQuery(query).list();
        
        System.out.println("result sizze = "+result.size());

//        List<Person> result = session.createQuery("from Person", Person.class).list();
        session.getTransaction().commit();
        session.close();

        return result;
    }

    private void initConnection() {
        // configures settings from hibernate.cfg.xml 
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            System.out.print(" checking if db [watsondb] is already created ..");
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            System.out.println(" YES");
            installed = true;
//             SessionFactory sessionFactory = new Configuration()
//    .configure("/org/nitish/caller/hibernate.cfg.xml").buildSessionFactory();
        } catch (Exception e) {
            // handle the exception
            System.out.println(" NO");
            e.printStackTrace();

        }
    }

    public Person createPerson(String name, String surname, int age, Products... products) throws DBUniqueViolationException, DBBadParamaterException {
        if (name == null) {
            throw new DBBadParamaterException("name", DBBadParamaterException.ErrorType.NULL);
        }
        if (name.isEmpty()) {
            throw new DBBadParamaterException("name", DBBadParamaterException.ErrorType.EMPTY);
        }
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Products> listaProdotti = Arrays.asList(products);

        Person person = new Person(name, surname, age);
        for (Products p : listaProdotti) {
            p.setPerson(person);
        }
        person.setProducts(listaProdotti);

        try {
            session.saveOrUpdate(person);
        } catch (PersistenceException ex) {
            if (ex.getCause() instanceof ConstraintViolationException) {
                throw new DBUniqueViolationException("Laboratory.name");
            }
            System.out.println("EXCEPTION CAUSE = " + ex.getCause().getClass().getCanonicalName());
            ex.printStackTrace();
        }
        session.getTransaction().commit();
        session.close();
        return person;
    }

}
