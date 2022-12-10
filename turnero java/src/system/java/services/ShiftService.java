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
    
    // Esta función se encarga de desplegar los turnos de la base de datos.
    public void displayShifts() {

        if (shiftList.isEmpty()) {

            frontService.clear();
            
            System.out.println("The database is empty.\n");
            
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

    // Esta función se encarga de agregar turnos a la base de datos.
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


                    LocalDateTime dateTime = inputDate(); // En esta línea se solicita la fecha y hora.
                    
                    Shift shift = new Shift(dateTime,personService.getPersons().get(index));

                    confirmShift(shift, dateTime); // Aqui se envia un turno y una fecha para validar la disponibilidad.

                    frontService.submenu();
                           
                
            } else {
                
                System.out.println("The identification number hasn't been founded!");
                personService.addPeople(false); // Si el ID no existe se solicita los datos del usuario y luego la carga del turno.
                
            }
                  
            
        
    }
    
    // Esta función se encarga de reprogramar un turno
    public void updateShift() { // Esta función se encarga de reprogramar un turno.

        System.out.print("Enter the identification number: ");
        int id = Integer.parseInt(sc.nextLine());

        // Aca comprobamos que el turno exista en la base de datos.
        if (getShift(id) != null) {
            
            Shift shiftFounded = getShift(id); // Aquí asignamos el objeto Shift obtenido.
            
            // Solicitud de fecha, hora y minuto del turno.
            LocalDateTime dateTime = inputDate();
            
            //Validación de disponibilidad.
            if (validateDate(dateTime, shiftList)) { 
                shiftFounded.setDate(dateTime);
                System.out.println("The shift has been changed!");
                System.out.println("Press 1 to return to the principal menu.");
                System.out.println("Press 2 to exit.");
                option = sc.nextInt();

                if (option == 1) {
                    frontService.menu();
                }
            } else {

                System.out.println("Sorry, the shift can't be assigned.");
                System.out.print("Would you like to try with another date? y/n");

                String answer = sc.nextLine();

                if (answer.equalsIgnoreCase("y")) {

                    updateShift();
                }

            }

        } else {

            frontService.submenu2(4);
        }

    }

    // Esta función se encarga de eliminar un turno de la base de datos.
    public void deleteShift() {

        System.out.print("Enter the identification number: ");
        int id = Integer.parseInt(sc.nextLine());     
       
        
        if (getShift(id) != null) {
            
            Shift shiftFounded = getShift(id);
            frontService.clear();
            System.out.println("The shift has been deleted!");
            shiftList.remove(shiftFounded);
            frontService.submenu();
            
        } else {
    
            frontService.submenu2(5);
        }
       
    }
    
    // Esta función se encarga de mostrar los turnos en una tabla de la base de datos.
    public void searchShifts() {

        System.out.print("Enter the identification number: ");
        int id = sc.nextInt();
        //sc.nextLine();

        
        if (getShift(id) != null) {

            Shift shiftFounded = getShift(id);
            
            System.out.println("****************************************************************************************************");
            System.out.printf("%10s %25s %30s %25s", "ID", "NAME", "LASTNAME", "SHIFT");
            System.out.println();
            System.out.println("****************************************************************************************************");
            System.out.format("%10s %25s %30s %30s",
                    shiftFounded.getPerson().getId(), shiftFounded.getPerson().getName(), shiftFounded.getPerson().getLastname(), shiftFounded.getDate().format(formatter));
            System.out.println();
            System.out.println("****************************************************************************************************");

            frontService.submenu();

        } else {
            
            frontService.submenu2(6);
        }

    }
    
    // Esta función se encarga de solicitar los datos del turno.
    private LocalDateTime inputDate(){ // Esta función se encarga de solicitar la fecha y hora del turno.
        
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

    // Esta función se encarga de verificar los datos del turno antes de confirmarlo.
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
    
    // Esta función se encarga de validar la disponibilidad del turno.
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
        
    // Esta función se encarga de obtener un turno específico.
    private Shift getShift(int id) {

        Person personShift = new Person();

        Shift shiftFounded = null;

        // En este ciclo obtenemos el objeto persona a modificar comparando los ID.
        for (int i = 0; i < personService.getPersons().size(); i++) {

            if (id == (personService.getPersons().get(i).getId())) {

                personShift = personService.getPersons().get(i);

            }

        }

        // En  ciclo comparamos nuestro objeto persona con el objeto turno y su atributo persona para hallar el turno a modificar.
        for (int i = 0; i < shiftList.size(); i++) {

            if (shiftList.get(i).getPerson().equals(personShift)) {

                shiftFounded = shiftList.get(i);
                break;
                
            } else if (shiftList.size() - 1 == i) { // Aqui nos aseguramos de recorrer toda la base de datos hasta que el indice sea igual a su tamaño.
                
                frontService.clear();
                System.out.println("the shift hasn't been founded!\n");

            }
            

        }
        return shiftFounded;
    }

    // Esta función se encarga de cargar la información en la base de datos.
    public void loadList() {

        shiftList.add(new Shift(LocalDateTime.of(2022, 12,10, 10, 00 ), personService.getPersons().get(1)));
        shiftList.add(new Shift(LocalDateTime.of(2022, 11, 10, 10, 15), personService.getPersons().get(2)));
        shiftList.add(new Shift(LocalDateTime.of(2022, 10, 10, 10, 30), personService.getPersons().get(3)));
        shiftList.add(new Shift(LocalDateTime.of(2022, 11, 10, 11, 15), personService.getPersons().get(4)));
        shiftList.add(new Shift(LocalDateTime.of(2022, 12, 10, 10, 15), personService.getPersons().get(5)));
        shiftList.add(new Shift(LocalDateTime.of(2022, 10, 10, 10, 20), personService.getPersons().get(6)));
        shiftList.add(new Shift(LocalDateTime.of(2023, 1, 10, 10, 15), personService.getPersons().get(7)));

    }
    
    // Esta función se encarga de ordenar los turnos según dia, mes, año, hora  y minuto.
    private void sortShifts() {
        // Por cada turno que se itera se compara con el siguiente y se va ordenando.
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
