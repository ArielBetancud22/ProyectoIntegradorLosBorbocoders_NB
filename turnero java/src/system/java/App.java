package system.java;

import system.java.services.Dao;
import system.java.services.FrontService;
public class App {

    public static void main(String[] args) {
        
        dao.getData(); // Este objeto se encarga de cargar los arrays en la base de datos.

        frontService.menu(); // Despliegue del menu.

    }
    
    private static final FrontService frontService = new FrontService(); 
    private static final Dao dao = new Dao();

}
