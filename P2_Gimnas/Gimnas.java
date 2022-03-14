package P2_Gimnas;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Gimnas {

    // private String nom;
    private String CIF;
    // private String telefon;
    ArrayList<Client> clients;
    ArrayList<Client> activitats;
    ArrayList<Client> reserves;

    // ConnexioBD bd = new ConnexioBD();

    private Scanner teclat = new Scanner(System.in);

    public Gimnas() {
        this.clients = new ArrayList<>();
        this.activitats = new ArrayList<>();
        this.reserves = new ArrayList<>();
    }

    // onnection connexioBD)
    public void gestionarGimnas() throws SQLException, NoSuchAlgorithmException {

        boolean sortir = false;
        // bd.connectarBD();
        do {

            System.out.println("***********___MENU GIMNÀS___***********");
            System.out.println("1. Gestió del client");
            System.out.println("2. Visualització dels Clients");
            System.out.println("3. Visualitzar Actvitat del dia");
            System.out.println("4. Sortir de l'aplicació");
            System.out.println("***************************************");

            int opció = teclat.nextInt();
            // Et demana posar un valor a la terminal per executar X comanda
            switch (opció) {
                case 1:
                    gestióDelClient();
                    break;
                case 2:
                    visualitzacioClients();
                    break;
                case 3:
                    activitatDia();
                    break;
                case 4:
                    sortir = true;
                    break;
                default:
                    System.out.println("L'opció " + opció + " no existeix.");
            }

        } while (!sortir);

    }

    private void gestióDelClient() throws SQLException, NoSuchAlgorithmException {

        boolean sortir = false;

        do {
            System.out.println("**************GESTIÓ DELS CLIENTS**************");
            System.out.println("1. Consulta un usuari");
            System.out.println("2. Donar alta un client");
            System.out.println("3. Donar de baixa un client");
            System.out.println("4. Tornar al primer menú");
            System.out.println("***********************************************");

            int opció = teclat.nextInt();
            teclat.nextLine();

            // Et demana posar un valor a la terminal per executar X comanda
            switch (opció) {
                case 1:
                    consultaClient();
                    break;
                case 2:
                    altaClient();
                    break;
                case 3:
                    eliminarClient();
                    break;
                case 4:
                    sortir = true;
                    break;
                default:
                    System.out.println("L'opció " + opció + " no existeix.");
            }
        } while (!sortir);

    }

    private void consultaClient() throws SQLException {
        Client c = new Client();
        c.consultaClient();
    }

    private void altaClient() throws SQLException, NoSuchAlgorithmException {
        Client c = new Client();
        c.altaClient();
    }

    private void eliminarClient() throws SQLException {
        Client c = new Client();
        c.eliminarClient();
    }

    private void visualitzacioClients() throws SQLException {

        boolean sortir = false;

        do {
            System.out.println("**********VISUALITZACIÓ DELS CLIENTS**********");
            System.out.println("1. Clients ordenats per cognoms");
            System.out.println("2. Clients ordenats per edat");
            System.out.println("3. Clients ordenats per reserves fetes");
            System.out.println("4. Tornar al primer menú");
            System.out.println("***********************************************");

            int opció = teclat.nextInt();
            teclat.nextLine();

            // Et demana posar un valor a la terminal per executar X comanda
            switch (opció) {
                case 1:
                    this.clients = ClientsOrdenatsCognoms();
                    break;
                case 2:
                    this.clients = ClientsOrdenatsPerEdat();
                    break;
                case 3:
                    this.clients = ClientsOrdenatsPerReserves();
                    break;
                case 4:
                    sortir = true;
                    break;
                default:
                    System.out.println("L'opció " + opció + " no existeix.");
            }
        } while (!sortir);

    }

    public ArrayList<Client> ClientsOrdenatsCognoms() throws SQLException {
        ArrayList<Client> clients = new ArrayList<>();

        ConnexioBD con = new ConnexioBD();
        con.conectarBD();

        String consulta = "SELECT * FROM socis order by cognom";
        PreparedStatement ps = con.connexioBD.prepareStatement(consulta);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            Client c1 = new Client();
            c1.cargarDadesDelClientSentencia(rs);

            clients.add(c1);

        }

        for (Client client : clients) {
            System.out.println(client);
        }
        con.desconectarBD();

        return clients;

    }

    public ArrayList<Client> ClientsOrdenatsPerEdat() throws SQLException {

        ArrayList<Client> clients = new ArrayList<>();

        ConnexioBD con = new ConnexioBD();
        con.conectarBD();

        String consulta = "SELECT distinct * FROM socis order by data_naix";
        PreparedStatement ps = con.connexioBD.prepareStatement(consulta);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Client c1 = new Client();
            c1.cargarDadesDelClientSentencia(rs);

            clients.add(c1);

        }

        for (Client client : clients) {
            System.out.println(client);
        }

        con.desconectarBD();

        return clients;
    }

    public ArrayList<Client> ClientsOrdenatsPerReserves() throws SQLException {

        ArrayList<Client> clients = new ArrayList<>();

        ConnexioBD con = new ConnexioBD();
        con.conectarBD();

        String consulta = "SELECT socis.*,count(reserva_colectiva.DNI) + (SELECT count(reserva_lliure.DNI) FROM reserva_lliure WHERE reserva_lliure.DNI = socis.DNI)as Reserves FROM reserva_colectiva, socis WHERE reserva_colectiva.DNI = socis.DNI GROUP BY reserva_colectiva.DNI ORDER BY count(reserva_colectiva.DNI) + (SELECT count(reserva_lliure.DNI) FROM reserva_lliure WHERE reserva_lliure.DNI = socis.DNI) desc";
        PreparedStatement ps = con.connexioBD.prepareStatement(consulta);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Client c1 = new Client();
            c1.cargarDadesDelClientSentencia(rs);

            clients.add(c1);

        }

        for (Client client : clients) {
            System.out.println(client);
        }

        con.desconectarBD();

        return clients;

    }

    private void activitatDia() throws SQLException {

        boolean sortir = false;

        do {
            System.out.println("**********VISUALITZACIÓ DELS CLIENTS**********");
            System.out.println("1. Activitats Lliures més reservades");
            System.out.println("2. Activitats Col·lectives més reservades");
            System.out.println("3. Activitat del dia");
            System.out.println("4. Tornar al primer menú");
            System.out.println("***********************************************");

            int opció = teclat.nextInt();
            teclat.nextLine();

            // Et demana posar un valor a la terminal per executar X comanda
            switch (opció) {
                case 1:
                    this.clients = ActivitatsReservesLliures();
                    break;
                case 2:
                    this.clients = ActivitatsReservesColectives();
                    break;
                case 3:
                    this.clients = ClientsOrdenatsPerReserves();
                    break;
                case 4:
                    sortir = true;
                    break;
                default:
                    System.out.println("L'opció " + opció + " no existeix.");
            }
        } while (!sortir);

    }

    public ArrayList<Client> ActivitatsReservesLliures() throws SQLException {

        ArrayList<Client> clients = new ArrayList<>();

        ConnexioBD con = new ConnexioBD();
        con.conectarBD();

        String consulta = "select A.nomActivitat as Nom_activitat, count(L.ID) as ReservesTotals from reserva_lliure L, activitats A, activitat_lliure AL where A.ID = AL.IDL and AL.IDL = L.ID group by L.ID order by count(L.ID) desc";
        PreparedStatement ps = con.connexioBD.prepareStatement(consulta);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Client c1 = new Client();
            this.setNomActivitat(rs.getString("nomActivitat"));

            clients.add(c1);

        }

        for (Client client : clients) {
            System.out.println(client);
        }

        con.desconectarBD();

        return clients;

    }

    private void setNomActivitat(String string) {
    }

    public ArrayList<Client> ActivitatsReservesColectives() throws SQLException {

        ArrayList<Client> clients = new ArrayList<>();

        ConnexioBD con = new ConnexioBD();
        con.conectarBD();

        String consulta = "select A.nomActivitat as Nom_activitat, count(C.ID) as ReservesTotals from reserva_colectiva C, activitats A, activitat_colectiva AC where A.ID = AC.IDC and AC.IDC = C.ID group by C.ID order by count(C.ID) desc";
        PreparedStatement ps = con.connexioBD.prepareStatement(consulta);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Client c1 = new Client();
            c1.cargarDadesDelClientSentencia(rs);

            clients.add(c1);

        }

        for (Client client : clients) {
            System.out.println(client);
        }

        con.desconectarBD();

        return clients;

    }

}
