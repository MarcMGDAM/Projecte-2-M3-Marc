package P2_Gimnas;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

public class GimnasApp {

    public static void main(String[] args) throws SQLException, NoSuchAlgorithmException {

        // Creem el Gimnas
        Gimnas g = new Gimnas();

        g.gestionarGimnas();

        // this.dni > teclat.nextint();
    }
}
