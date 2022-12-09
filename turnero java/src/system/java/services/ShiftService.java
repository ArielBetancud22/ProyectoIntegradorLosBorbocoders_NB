package system.java.services;

import system.java.models.Person;
import system.java.models.Shift;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ShiftService {
    
    public List<Shift> getShifts() {
        
        return shiftList;
    }

    public void displayShifts() {

        if (shiftList.isEmpty()) {

            System.out.println("The database is empty.");

            frontService.submenu();

        } else {

            sortShifts();

            System.out.println("****************************************************************************************************");
            System.out.printf("%10s %25s %30s %25s", "ID", "NAME", "LASTNAME", "SHIFT");
            System.out.println();
            System.out.println("****************************************************************************************************");
            for (Shift s : shiftList) {

                System.out.format("%10s %25s %30s %30s",
                        s.getPerson().getId(), s.getPerson().getName(), s.getPerson().getLastname(), s.getDate().format(formatter));
                System.out.println();
                System.out.println("****************************************************************************************************");
            }

            frontService.submenu();
        }

    }

    public void addShift() {

       

            System.out.print("Please enter the id: ");
            int id = sc.nextInt();
            
            
            if((personService
                .getPersons()
                .stream()
                .anyMatch(p -> p.getId() == id))) {
                
                    int i, index = 0;

                    for (i = 0; i < personService.getPersons().size(); i++) {

                        if (id == personService.getPersons().get(i).getId()) {

                            index = i;
                        }
                    }


                    LocalDateTime dateTime = inputDate();
                    
                    Shift shift = new Shift(dateTime,personService.getPersons().get(index));

                    confirmShift(shift, dateTime);

                    frontService.submenu();
                           
                
            } else {
                
                System.out.println("The identification number hasn't been founded!");
                personService.addPeople(false);
                
            }
                  
            
        
    }
    
    private LocalDateTime inputDate(){
        
            System.out.print("Please enter the year: ");
            int year = sc.nextInt();
            System.out.print("Please enter the Month: ");
            int month = sc.nextInt();
            System.out.print("Please enter the day: ");
            int day = sc.nextInt();
            System.out.print("Please enter the hour: ");
            int hour = sc.nextInt();
            System.out.print("Please enter the minutes: ");
            int minutes = sc.nextInt();

        return LocalDateTime.of(year, month, day, hour, minutes);
    }

    public void updateShift() {

        
        System.out.print("Enter the identification number: ");
        int id = Integer.parseInt(sc.nextLine());

        Person personShift = new Person();

        Shift shiftFounded = null;

        for (int i = 0; i < personService.getPersons().size(); i++) {

            if (id == (personService.getPersons().get(i).getId())) {

                personShift = personService.getPersons().get(i);

            }

        }

        for (int i = 0; i < shiftList.size(); i++) {

            if (shiftList.get(i).getPerson().equals(personShift)) {

                shiftFounded = shiftList.get(i);
                break;
            } else if (shiftList.size() - 1 == i) {

                System.out.println("the shift hasn't been founded!");
                frontService.submenu();
            }

        }

        if (shiftFounded != null) {

            System.out.print("Please enter the year: ");
            int year = sc.nextInt();
            System.out.print("Please enter the Month: ");
            int month = sc.nextInt();
            System.out.print("Please enter the day: ");
            int day = sc.nextInt();
            System.out.print("Please enter the hour: ");
            int hour = sc.nextInt();
            System.out.print("Please enter the minutes: ");
            int minutes = sc.nextInt();

            dateTime = LocalDateTime.of(year, month, day, hour, minutes);
            LocalDateTime dateTimePlus = dateTime.plusHours(5);

            if (dateTime.getYear() >= dateTimePlus.getYear() && dateTime.getMonthValue() >= dateTimePlus.getMonthValue() && dateTime.getDayOfMonth() >= dateTimePlus.getDayOfMonth()) {
// && dateTime.getHour() >= dateTimePlus.getHour() 
                shiftFounded.setDate(dateTime);
                System.out.println("The shift has been changed!");
                System.out.println("Press 1 to return to the principal menu.");
                System.out.println("Press 2 to exit.");
                option = sc.nextInt();

                if (option == 1) {
                    frontService.menu();
                }
            } else {
                System.out.println("Sorry, the shift is busy...");
                frontService.submenu2();
                option = sc.nextInt();

                switch (option) {

                    case 1:
                        frontService.menu();
                        break;

                    case 2:
                        updateShift();
                        break;

                    default:
                        break;
                }
            }

        }

    }

    public void deleteShift() {

        System.out.print("Enter the identification number: ");
        int id = Integer.parseInt(sc.nextLine());

        Person personShift = new Person();

        Shift shiftFounded = null;

        for (int i = 0; i < personService.getPersons().size(); i++) {

            if (id == (personService.getPersons().get(i).getId())) {

                personShift = personService.getPersons().get(i);
                break;

            }

        }

        for (int i = 0; i < shiftList.size(); i++) {

            if (shiftList.get(i).getPerson().equals(personShift)) {

                shiftFounded = shiftList.get(i);
                break;
            } else if (shiftList.size() - 1 == i) {

                System.out.println("the shift hasn't been founded!");

            }
            
        }
        
       

        if (shiftFounded != null) {
            System.out.println("The shift has been deleted!");
            shiftList.remove(shiftFounded);
            frontService.submenu();
        } else {
            frontService.submenu2();
            
            option = sc.nextInt();
            sc.nextLine();

            switch (option) {

                case 1:
                    deleteShift();
                    break;

                case 2:
                    frontService.menu();
                    break;

                default:
                    break;
            }
        }

        
       
    }

    public void searchShifts() {

        System.out.print("Enter the identification number: ");
        int id = sc.nextInt();
        //sc.nextLine();

        Person personShift = new Person();

        Shift shiftFounded = null;

        for (int i = 0; i < personService.getPersons().size(); i++) {

            if (id == (personService.getPersons().get(i).getId())) {

                personShift = personService.getPersons().get(i);

            }

        }

        for (int i = 0; i < shiftList.size(); i++) {

            if (shiftList.get(i).getPerson().equals(personShift)) {

                shiftFounded = shiftList.get(i);
                break;
            } else if (shiftList.size() - 1 == i) {

                System.out.println("the shift hasn't been founded!");
                
                frontService.submenu2();
                
                option = sc.nextInt();
                sc.nextLine();

                switch (option) {

                    case 1:
                        searchShifts();
                        break;

                    case 2:
                        frontService.menu();
                        break;

                    default:
                        break;
                }
            }

        }

        if (shiftFounded != null) {

            System.out.println("****************************************************************************************************");
            System.out.printf("%10s %25s %30s %25s", "ID", "NAME", "LASTNAME", "SHIFT");
            System.out.println();
            System.out.println("****************************************************************************************************");
            System.out.format("%10s %25s %30s %30s",
                    shiftFounded.getPerson().getId(), shiftFounded.getPerson().getName(), shiftFounded.getPerson().getLastname(), shiftFounded.getDate().format(formatter));
            System.out.println();
            System.out.println("****************************************************************************************************");

            frontService.submenu();

        }

    }

    private void confirmShift(Shift shift, LocalDateTime dateTime) {

        if (validateDate(dateTime, shiftList)) {

            shiftList.add(shift);
            System.out.println();
            System.out.println("The shift has been added successfully!");
            System.out.println();

        } else {

            System.out.println("The shift is not available.\nWould you like to try with another shift ? y/n ");
            
            String answer = sc.next();
            
            if(answer.equalsIgnoreCase("y")) {
                
                addShift();
            }else {
                
                frontService.menu();
            }
            

        }



    }

    private boolean validateDate(LocalDateTime dateTime, List<Shift> shifts) {

        LocalDateTime datePlus = LocalDateTime.now().plusHours(5);
        
        
        if ((shifts
            .stream()
            .anyMatch(s -> s.getDate()
            .isEqual(dateTime))) 
            || (dateTime.isBefore(datePlus)) ) {
            
            
            return false;
            
        } else {

            return true;
        }

    }

    public void loadList() {

        shiftList.add(new Shift(LocalDateTime.of(2022, 12,10, 10, 00 ), personService.getPersons().get(1)));
        shiftList.add(new Shift(LocalDateTime.of(2022, 11, 10, 10, 15), personService.getPersons().get(2)));
        shiftList.add(new Shift(LocalDateTime.of(2022, 10, 10, 10, 30), personService.getPersons().get(3)));
        shiftList.add(new Shift(LocalDateTime.of(2022, 11, 10, 11, 15), personService.getPersons().get(4)));
        shiftList.add(new Shift(LocalDateTime.of(2022, 12, 10, 10, 15), personService.getPersons().get(5)));
        shiftList.add(new Shift(LocalDateTime.of(2022, 10, 10, 10, 20), personService.getPersons().get(6)));
        shiftList.add(new Shift(LocalDateTime.of(2023, 1, 10, 10, 15), personService.getPersons().get(7)));

    }

    private void sortShifts() {

        shiftList.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));
    }

    public static List<Shift> shiftList = new ArrayList<>();
    private static final Scanner sc = new Scanner(System.in);
    private static int option;
    public LocalDateTime dateTime = LocalDateTime.now();
    private static final FrontService frontService = new FrontService();
    private static final PersonService personService = new PersonService();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy'.'MM'.'dd  HH:mm");

}
