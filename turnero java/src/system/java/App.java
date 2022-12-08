package system.java;

import system.java.services.Dao;
import system.java.services.FrontService;
public class App {

    public static void main(String[] args) {
        
        dao.getData(); // Data load.

        frontService.menu(); // Show menu.

    }
        private static final FrontService frontService = new FrontService();
    private static final Dao dao = new Dao();

    
}
