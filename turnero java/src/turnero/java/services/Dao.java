package turnero.java.services;

public class Dao {


    public void getData() {

        personService.loadList();

        shiftService.loadList();
    }

    private final PersonService personService = new PersonService();
    private final ShiftService shiftService = new ShiftService();
}
