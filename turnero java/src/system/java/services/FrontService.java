package system.java.services;

import java.util.Scanner;

public class FrontService {

    public  void welcome() {

        // FRONT //
        System.out.println("""
                     ==
                   //_\\\\_
                 ."\\\\    ".
                /          \\
                |           \\_
                |       ,--.-.)
                 \\     /  o \\o\\
                 /\\/\\  \\    /_/
                  (_.   `--'__)
                   |     .-'  \\
                   |  .-'.     )
                   | (  _/--.-'
                   |  `.___.'
                         (""");

        System.out.println("****************************************************************************************************");
        System.out.println("\t\t\t\t\t\t\t\t\tWelcome to the shift system");
        System.out.println("****************************************************************************************************");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
        welcomeFlag = false;
        clear();

    }

    public  void menu(){

        if (welcomeFlag) {
            welcome();
        }

        do {

            System.out.println("****************************************************************************************************");
            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\tMenu");
            System.out.println("****************************************************************************************************");
            System.out.println();
            System.out.println("1. Display shifts.\t\t2. Display people. \t\t3. Add shifts. \t\t4. Add people.");
            System.out.println("5. Update shifts. \t\t6. Update people. \t\t7. Delete shifts.\t8. Delete people.");
            System.out.println("9. Search shifts. \t\t10. Search people. \t\t11. Exit.");
            System.out.println();
            System.out.print("Enter an option: ");
            option = sc.nextInt();

            clear();


        } while (option < 1 || option >= 12);

        switch (option){

            case 1: shiftService.displayShifts();
                break;

            case 2: personService.displayPeople();
                break;

            case 3: shiftService.addShift();
                break;

            case 4: personService.addPeople();
                break;
            case 5: shiftService.updateShift();
                break;

            case 6: personService.updatePeople();
                break;

            case 7: shiftService.deleteShift();
                break;

            case 8: personService.deletePeople();
                break;

            case 9: shiftService.searchShifts();
                break;

            case 10 : personService.searchPeople();
                break;

            case 11: break;

        }
    }

    public  void submenu() {
        sc.nextLine();
        //clear();

        do {
            System.out.println();
            System.out.println("\n1. Press 1 to return to the principal menu: ");
            System.out.println("2. Press 2 to exit");
            option = sc.nextInt();

        } while (option < 1 || option >= 3);

        switch (option){

            case 1: menu();
                break;

            case 2: break;

        }
    }

    public  void clear() {

        for (int i = 0; i < 25; i++) {

            System.out.println();

        }
    }

    private static int option;
    private static boolean welcomeFlag = true;
    private static final Scanner sc = new Scanner(System.in);
    private static final PersonService personService = new PersonService();
    private static final ShiftService shiftService = new ShiftService();

}
