package store;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\nChoose option:");
            System.out.println("1. Add new item");
            System.out.println("2. Show all items");
            System.out.println("3. Update an item");
            System.out.println("4. Delete an item");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = 0;
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine(); // consume newline
            } else {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine(); // discard invalid input
                continue;
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
                sc.nextLine(); // consume newline

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
                    if (items.isEmpty()) {
                        System.out.println("No items found in the database.");
                    } else {
                        System.out.println("Items in database:");
                        for (Item i : items) {
                            System.out.println("ID: " + i.getId() + ", Name: " + i.getName() + ", Price: " + i.getPrice());
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error fetching items: " + e.getMessage());
                    e.printStackTrace();
                } finally {
                    session.close();
                }

            } else if (choice == 3) {
                System.out.print("Enter ID of item to update: ");
                int id = sc.nextInt();
                sc.nextLine(); 

                Session session = HibernateUtil.getSessionFactory().openSession();
                Transaction tx = session.beginTransaction();

                Item item = session.get(Item.class, id);
                if (item == null) {
                    System.out.println("Item with ID " + id + " not found.");
                    tx.rollback();
                    session.close();
                    continue;
                }

                System.out.println("Current name: " + item.getName());
                System.out.print("Enter new name (or press Enter to keep): ");
                String newName = sc.nextLine();
                if (!newName.trim().isEmpty()) {
                    item.setName(newName);
                }

                System.out.println("Current price: " + item.getPrice());
                System.out.print("Enter new price (or press Enter to keep): ");
                String priceInput = sc.nextLine();
                if (!priceInput.trim().isEmpty()) {
                    try {
                        double newPrice = Double.parseDouble(priceInput);
                        item.setPrice(newPrice);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid price input. Keeping previous price.");
                    }
                }

                session.update(item);
                tx.commit();
                session.close();

                System.out.println("Item updated successfully!");

            } else if (choice == 4) {
                System.out.print("Enter ID of item to delete: ");
                int id = sc.nextInt();
                sc.nextLine();

                Session session = HibernateUtil.getSessionFactory().openSession();
                Transaction tx = session.beginTransaction();

                Item item = session.get(Item.class, id);
                if (item == null) {
                    System.out.println("Item with ID " + id + " not found.");
                    tx.rollback();
                    session.close();
                    continue;
                }

                session.delete(item);
                tx.commit();
                session.close();

                System.out.println("Item deleted successfully!");

            } else if (choice == 5) {
                System.out.println("Exiting program.");
                break;
            } else {
                System.out.println("Invalid choice! Please select 1-5.");
            }
        }

        sc.close();
    }
}
