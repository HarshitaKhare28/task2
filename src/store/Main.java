package store;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Choose option:");
        System.out.println("1. Add new item");
        System.out.println("2. Show all items");
        System.out.print("Enter choice: ");
        
        int choice = 0;
        if(sc.hasNextInt()) {
            choice = sc.nextInt();
            sc.nextLine(); // consume newline
        } else {
            System.out.println("Invalid input!");
            sc.close();
            return;
        }

        if (choice == 1) {
            System.out.print("Enter item name: ");
            String name = sc.nextLine();

            System.out.print("Enter price: ");
            while (!sc.hasNextDouble()) {
                System.out.println("Please enter a valid number for price:");
                sc.next(); // discard invalid input
            }
            double price = sc.nextDouble();

            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();

            Item item = new Item();
            item.setName(name);
            item.setPrice(price);

            session.save(item);
            tx.commit();
            session.close();

            System.out.println("Item saved successfully!");
        } else if (choice == 2) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                List<Item> items = session.createQuery("FROM Item", Item.class).list();

                System.out.println("Items in database:");
                for (Item i : items) {
                    System.out.println("ID: " + i.getId() + ", Name: " + i.getName() + ", Price: " + i.getPrice());
                }
            } catch (Exception e) {
                System.err.println("Error fetching items: " + e.getMessage());
                e.printStackTrace();
            } finally {
                session.close();
            }
        } else {
            System.out.println("Invalid choice!");
        }

        sc.close();
    }
}
