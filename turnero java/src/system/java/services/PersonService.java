package system.java.services;

import system.java.models.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PersonService {

    public List<Person>getPersons() {

        return peopleList;
    }
    public  void addPeople(boolean flag) {

        Person person = new Person();

        System.out.print("Enter the name: ");
        person.setName(sc.nextLine());

        System.out.print("Enter the lastname: ");
        person.setLastname(sc.nextLine());

        System.out.print("Enter the identification number: ");
        person.setId(sc.nextInt());

        System.out.print("Enter the phone number: ");
        person.setPhone(sc.nextLong());

        sc.nextLine();

        System.out.print("Enter the email: ");
        person.setEmail(sc.nextLine());

        // Llamado a la funcion para confirmar a la persona.
        confirmPeople(person);
        
        if(flag) {
            
            frontService.submenu();

        } else {
            
            shiftService.addShift();
        }
    }
    public  void updatePeople(){

        Person personToUpdate;

        System.out.print("Enter the identification number: ");
        int id = Integer.parseInt(sc.nextLine());

        List <Person> listForUpdates;


        listForUpdates = peopleList.stream()
                .filter(person -> person.getId() == id)
                .collect(Collectors.toList());

        frontService.clear();


        if(listForUpdates.size() > 1) {

            System.out.println("There's " + listForUpdates.size() + " people founded with the same id: ");

            listForUpdates.forEach(p -> {
                System.out.println("\n**************************************************");
                System.out.println("Name: " + p.getName());
                System.out.println("Lastname: " + p.getLastname());
                System.out.println("**************************************************");
            });

            System.out.println("Enter the actual name: ");
            String name = sc.nextLine();
            System.out.println("Enter the actual name: ");
            String lastname = sc.nextLine();

            personToUpdate = new Person(name, lastname, id);




        } else if(listForUpdates.isEmpty()) {
            System.out.println("there is no person with that id");
            frontService.submenu();

        }
        else{
        
            personToUpdate = listForUpdates.get(0);
            
                    System.out.print("Enter the new name: ");
        personToUpdate.setName(sc.nextLine());

        System.out.print("Enter the new lastname: ");
        personToUpdate.setLastname(sc.nextLine());

        System.out.print("Enter the new phone: ");
        personToUpdate.setPhone(Long.parseLong(sc.nextLine()));

        System.out.print("Enter the new email: ");
        personToUpdate.setEmail(sc.nextLine().toLowerCase());

        System.out.println("The person has been updated!");
        System.out.println("Press 1 to return to the principal menu.");
        System.out.println("Press 2 to exit.");
        int option = Integer.parseInt(sc.nextLine());

        if (option == 1)
            frontService.menu();
        }

    }
    public  void deletePeople() {

        Person personToRemove = new Person();

        System.out.print("Enter the name: ");
        personToRemove.setName(sc.nextLine());

        System.out.print("Enter the lastname: ");
        personToRemove.setLastname(sc.nextLine());

        System.out.print("Enter the identification number: ");
        personToRemove.setId(Integer.parseInt(sc.nextLine()));

        for (int i = 0; i < peopleList.size(); i++) {

            if (personToRemove.getName().equalsIgnoreCase(peopleList.get(i).getName()) && personToRemove.getLastname().equalsIgnoreCase(peopleList.get(i).getLastname()) && personToRemove.getId() == (peopleList.get(i).getId())) {
                
                int index = 1;
                
                for(int j=0; j< shiftService.getShifts().size();j++){
                    
                   if( personToRemove.getId() == shiftService.getShifts().get(j).getPerson().getId() ) {
                       
                       index = j;
                   }
                }
                
                shiftService.shiftList.remove(index);
                
                peopleList.remove(i);
                System.out.println();
                System.out.println("The person has been deleted!");
                break;


            } else if (i == peopleList.size()-1) {

                System.out.println("The person couldn't be found.");
                frontService.submenu2();
                int option = Integer.parseInt(sc.nextLine());

                switch (option) {

                case 1:
                    deletePeople();
                    break;

                case 2:
                    frontService.menu();
                    break;

                default:
                    break;
            }

            }
        }
        frontService.submenu();
    }
    public  void confirmPeople(Person person) {



        if(peopleList
                .stream()
                .anyMatch(
                             p->p.getId() == person.getId() &&
                                p.getName().equalsIgnoreCase(person.getName()) &&
                                p.getLastname().equalsIgnoreCase(person.getLastname()) &&
                                p.getEmail().equalsIgnoreCase(person.getEmail()) &&
                                p.getPhone() == person.getPhone())){

            System.out.println("The user already exists! \nTry again...");
            addPeople(false);
        }
        else {

            peopleList.add(person);
            System.out.println();
            System.out.println("The user has been added successfully!");
            System.out.println();
        }

    }
    public  void displayPeople(){

        if ( peopleList.isEmpty() ) {

            System.out.println("The database is empty.");

        }
        else {

            System.out.println("****************************************************************************************************");
            System.out.printf("%7s %15s %20s %20s %25s", "ID", "NAME", "LASTNAME", "PHONE", "EMAIL");
            System.out.println();
            System.out.println("****************************************************************************************************");
            for (Person p : peopleList) {
                System.out.format("%10s %15s %20s %20s %25s",
                        p.getId(), p.getName(), p.getLastname(), p.getPhone(), p.getEmail());
                System.out.println();
                System.out.println("****************************************************************************************************");
            }


        }
        frontService.submenu();
    }
    public  void searchPeople() {

        System.out.print("Enter the identification number: ");
        int id = Integer.parseInt(sc.nextLine());
        //sc.nextLine();
        Person foundedPerson;

        /*
        for (int i = 0; i < peopleList.size(); i++) {

            if (id == (peopleList.get(i).getId())) {

                foundedPerson = peopleList.get(i);

                System.out.println("************************************************************");
                System.out.println("Id: " + id);
                System.out.println("Name: " + foundedPerson.getName());
                System.out.println("Lastname: " + foundedPerson.getLastname());
                System.out.println("************************************************************");
                frontService.menu();
                break;

            }
            else if (i == peopleList.size()-1) {

                System.out.println("The person doesn't exist!.");
                System.out.println("Press 1 to try again.");
                System.out.println("Press 2 to return to the principal menu.");
                System.out.println("Press 3 to exit.");
                int option = Integer.parseInt(sc.nextLine());

                if (option == 1) {
                    searchPeople();
                } else if (option == 2) {
                    frontService.menu();
                }
                else {
                    break;
                }
            }
        }*/

        List<Person> foundedPeople = peopleList.stream().filter(p -> p.getId() == id).collect(Collectors.toList());

        if (!foundedPeople.isEmpty()) {

            System.out.println("****************************************************************************************************");
            System.out.printf("%7s %15s %20s %20s %25s", "ID", "NAME", "LASTNAME", "PHONE", "EMAIL");
            System.out.println();
            System.out.println("****************************************************************************************************");

            for (Person p : foundedPeople) {


                    System.out.format("%10s %15s %20s %20s %25s",
                            p.getId(), p.getName(), p.getLastname(), p.getPhone(), p.getEmail());
                    System.out.println();
                    System.out.println("****************************************************************************************************");

            }
        } else {
            
            System.out.println("The person hasn't been founded!");
            
            frontService.submenu2();
                
                int option = sc.nextInt();
                sc.nextLine();

                switch (option) {

                    case 1:
                        searchPeople();
                        break;

                    case 2:
                        frontService.menu();
                        break;

                    default:
                        break;
                }
        }

        frontService.submenu();



    }

    public static List<Person> peopleList = new ArrayList<>();
    public void loadList() {

        peopleList.add(new Person("Daenerys", "Targaryen", 22546124, 12027953213L, "daenerys@gmail.com"));
        peopleList.add(new Person("Jon", "Snow", 54213456, 12064563059L, "jonsnow@gmail.com"));
        peopleList.add(new Person("Catelyn", "Stark", 45645645, 12064512559L, "catelyn@gmail.com"));
        peopleList.add(new Person("Khal", "Drogo", 84651321, 12011111213L, "khal@gmail.com"));
        peopleList.add(new Person("Robb", "Stark", 5789457, 12064234239L,"robb@gmail.com"));
        peopleList.add(new Person("Eddard", "Stark", 89778989, 12064514569L, "eddard@gmail.com"));
        peopleList.add(new Person("Sansa", "Stark", 68465456, 12027953883L, "sansa@gmail.com"));
        peopleList.add(new Person("Aria", "Stark", 71235451, 14327953213L, "aria@gmail.com"));
        peopleList.add(new Person("Tyrion", "Lannister", 9, 19927953213L, "tyrion@gmail.com"));
        peopleList.add(new Person("Cersei", "Lannister", 8, 14327958888L, "cersei@gmail.com"));

    }
    private static final Scanner sc = new Scanner(System.in);
    private static final FrontService frontService = new FrontService();
    private static final ShiftService shiftService = new ShiftService();


}
