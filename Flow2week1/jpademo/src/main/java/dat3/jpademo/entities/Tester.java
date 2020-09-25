/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dat3.jpademo.entities;

import dto.*;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author angry
 */
public class Tester {
    public static void main(String[] args) {
        System.out.println("");
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        
        Person p1 = new Person("Jønke", 1963);
        Person p2 = new Person("Blondie", 1959);
        
        Address a1 = new Address("Store Torv 1", 2323, "Nr. Snede");
        Address a2 = new Address("Langgade 32", 1212 , "Valby");
        
        Fee f1 = new Fee(100);
        Fee f2 = new Fee(200);
        Fee f3 = new Fee(300);
        
        SwimStyle s1 = new SwimStyle("Crawl");
        SwimStyle s2 = new SwimStyle("ButterFly");
        SwimStyle s3 = new SwimStyle("Breast stroke");
        
        
        
        p1.AddFee(f1);
        p1.AddFee(f3);
        p2.AddFee(f2);
        
        p1.setAddress(a1);
        p2.setAddress(a2);
        
       p1.AddSwimStyle(s1);
       p1.AddSwimStyle(s3);
       p2.AddSwimStyle(s2);
        
        
        em.getTransaction().begin();
       
        em.persist(p1);
        em.persist(p2);
        em.getTransaction().commit();
        
        em.getTransaction().begin();
           // p1.removeSwimStyle(s3);
        em.getTransaction().commit();
        
        
        
        
        
        System.out.println("p1:"+p1.getP_id() +p1.getName());
        System.out.println("p2:"+p2.getP_id());
        
        System.out.println("Jønkes gade: "+ p1.getAddress().getStreet());
        
        System.out.println("Lad os se om to vejs virker: "+ a1.getPerson().getName());
        
        System.out.println("Hvem har betalt f2? Det har: " + f2.getPerson().getName());
        
        System.out.println("Hvad blev betalt i alt?");
        
        TypedQuery<Fee> q1 = em.createQuery("SELECT f FROM Fee f", Fee.class);
        List<Fee> fees = q1.getResultList();
        
        for(Fee f: fees){
            System.out.println(f.getPerson().getName() +": " + f.getAmout() + "kr. Den: "+f.getPayDate()+ " Adr: " + f.getPerson().getAddress().getCity());
        }
        
        TypedQuery<Person> q2 = em.createQuery("SELECT p FROM Person p", Person.class);
        List<Person> persons = q2.getResultList();
            
            for(Person p: persons){
                System.out.println("Navn: "+ p.getName());
                System.out.println("--Fees: ");
                for(Fee f: p.getFees()){
                    System.out.println("---Beløb: " + f.getAmout() + ", " + f.getPayDate().toString());
                }
                System.out.println("--------styles");
                for(SwimStyle ss: p.styles){
                    System.out.println("--------Style: "+ ss.getStyleName());
                }
            }
    
            
        System.out.println("******* JPQL joins");
        
        //create JPQL with constructor projections       
        Query q3 = em.createQuery("SELECT new dto.PersonStyleDTO(p.name, p.year, s.styleName) FROM Person p JOIN p.styles s");
        
        List<PersonStyleDTO> personDetails = q3.getResultList();
        
        for(PersonStyleDTO ps: personDetails){
            System.out.println("Navn:" + ps.getName() + "," + ps.getYear() + ", " + ps.getSwimStyle());
        }
        
        //Eksmepriment virker ikke
       /* System.out.println("EKSPERIMENT");
        TypedQuery<PersonStyleDTO> q4 = em.createQuery("SELECT p FROM Person p", PersonStyleDTO.class);
        List<PersonStyleDTO> personsDTOs = q4.getResultList();
        
         for(PersonStyleDTO ps: personsDTOs){
            System.out.println("Navn:" + ps.getName() + "," + ps.getYear() + ", " + ps.getSwimStyle());
         }*/
    }   
    
}
