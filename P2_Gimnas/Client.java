package P2_Gimnas;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import javax.print.event.PrintServiceAttributeListener;

import IntroPOO.CompteBancari;

public class Client {

    private Dni dni;
    private String nom;
    private String cognoms;
    private String sexe;
    private LocalDate data_naix;
    private int edat;
    private Telefon telefon;
    private Email email;
    private String usuari;
    private String contrasenya;
    private String condicio_fisica;
    private boolean comunicacio_comercial;
    private Iban compte_bancari;

    public Client(Dni dni, String nom, String cognoms, String sexe, LocalDate data_naix, int edat, Telefon telefon,
            Email email, String usuari, String contrasenya, String condicio_fisica, boolean comunicacio_comercial,
            Iban compte_bancari) {
        this.dni = dni;
        this.nom = nom;
        this.cognoms = cognoms;
        this.sexe = sexe;
        this.data_naix = data_naix;
        this.edat = edat;
        this.telefon = telefon;
        this.email = email;
        this.usuari = usuari;
        this.contrasenya = contrasenya;
        this.condicio_fisica = condicio_fisica;
        this.comunicacio_comercial = comunicacio_comercial;
        this.compte_bancari = compte_bancari;
    }

    public Client() {

    }

    @Override
    public String toString() {
        return "Client [DNI=" + dni + ", \nNom=" + nom + ",     Cognom=" + cognoms + ", \nSexe=" + sexe +
                ", \ndata_naix=" + data_naix + ", \ntelefon=" + telefon + ",    email=" + email + ", \nusuari=" + usuari
                +
                ", \ncondicio_fisica=" + condicio_fisica + ", \ncomunicacio_comercial=" + comunicacio_comercial + "]\n";
    }

    private void calcularEdad() {
        LocalDate ara = LocalDate.now();
        this.edat = Period.between(this.data_naix, ara).getYears();
    }

    public void cargarDadesDelClient(PreparedStatement ps) throws SQLException {

        ps.setString(1, this.dni.getDni());
        ps.setString(2, this.nom);
        ps.setString(3, this.cognoms);
        ps.setString(4, this.sexe);
        ps.setString(5, data_naix.toString());
        ps.setString(6, this.telefon.getTelefon());
        ps.setString(7, this.email.getEmail());
        ps.setString(8, this.usuari);
        ps.setString(9, this.contrasenya);
        ps.setString(10, this.condicio_fisica);
        ps.setBoolean(11, this.comunicacio_comercial);
        ps.setString(12, this.compte_bancari.getCompte());

    }

    public void cargarDadesDelClientSentencia(ResultSet rs) throws SQLException {

        this.setDni(new Dni(rs.getString("DNI")));
        this.setNom(rs.getString("nom"));
        this.setCognoms(rs.getString("cognom"));
        this.setSexe(rs.getString("sexe"));
        this.setTelefon(new Telefon(rs.getString("mobil")));
        this.setEmail(new Email(rs.getString("email")));
        this.setUsuari(rs.getString("usuari"));
        this.setContrasenya(rs.getString("contrasenya"));
        this.setCondicio_fisica(rs.getString("condicio_fisica"));
        this.setComunicacio_comercial(rs.getBoolean("comunicacio_comercial"));
        this.setCompte(new Iban(rs.getString("compte_bancari")));

        this.setdata_naix(rs.getDate("data_naix").toLocalDate());
        calcularEdad();

    }

    private void setdata_naix(LocalDate data_naix) {
        this.data_naix = data_naix;
    }

    private void setCompte(Iban compte_bancari) {
        this.compte_bancari = compte_bancari;
    }

    private void setComunicacio_comercial(Boolean comunicacio_comercial) {
        this.comunicacio_comercial = comunicacio_comercial;
    }

    private void setCondicio_fisica(String condicio_fisica) {
        this.condicio_fisica = condicio_fisica;
    }

    private void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    private void setUsuari(String usuari) {
        this.usuari = usuari;
    }

    private void setEmail(Email email) {
        this.email = email;
    }

    private void setTelefon(Telefon telefon) {
        this.telefon = telefon;
    }

    private void setSexe(String sexe) {
        this.sexe = sexe;
    }

    private void setCognoms(String cognoms) {
        this.cognoms = cognoms;
    }

    private void setNom(String nom) {
        this.nom = nom;
    }

    private void setDni(Dni dni) {
        this.dni = dni;
    }

    public Client consultaClientBD(String dni) throws SQLException {

        // Fem connexió a la BD
        ConnexioBD con = new ConnexioBD();
        con.conectarBD();

        // Demanem el DNI
        String consulta = "SELECT * FROM socis WHERE DNI =?";
        PreparedStatement ps = con.connexioBD.prepareStatement(consulta);

        ps.setString(1, dni);
        ps.executeQuery();

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            // mapeig del Client BD a client 00
            cargarDadesDelClientSentencia(rs);

            return this;

        }
        con.desconectarBD();

        return null;
    }

    public void consultaClient() throws SQLException {

        Scanner teclat = new Scanner(System.in);
        System.out.println("Introdueix el DNI del cleint que vols consultar: ");
        String dni = teclat.next();

        Client client = consultaClientBD(dni);
        if (client != null) {
            System.out.println("****** DADES DEL CLIENT ******");
            System.out.println(client);
        } else {
            System.out.println("Client no trobat");
        }
    }

    public void altaClient() throws SQLException, NoSuchAlgorithmException {

        Scanner teclat = new Scanner(System.in);
        // solicitem el dni a donar d'alta fins que sigui correcte

        String dni;
        Dni d = new Dni();
        do {

            System.out.print("Introdueix el dni del client que vols donar d'alta:");
            dni = teclat.next();

        } while (!d.validarDni(dni));
        d.setDni(dni);

        setDni(d);
        // d.setDni(dni);

        if (consultaClientBD(d.getDni()) != null) {

            System.out.println("El client amb aquest DNI ja existeix");
        } else {

            System.out.println("Introdueix el Nom: ");
            this.nom = teclat.next();

            System.out.println("Introdueix el teu Primer Cognom: ");
            this.cognoms = teclat.next();

            System.out.println("Introdueix el teu sexe: (Home/Dona) ");
            this.sexe = teclat.next();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            boolean dataCorrecta;

            do {
                dataCorrecta = true;
                System.out.println("Introdueix la seva data de naixement en el següent format (DD.MM.AAA) ");
                try {
                    this.data_naix = LocalDate.parse(teclat.next(), formatter);
                } catch (Exception ex) {
                    dataCorrecta = false;
                }

            } while (!dataCorrecta);

            Telefon telefon = new Telefon();
            do {
                System.out.println("Introdueix el numerò de telèfon:");
            } while (!telefon.setTelefon(teclat.next()));
            setTelefon(telefon);

            Email email = new Email();
            do {
                System.out.println("Introdueix el email:");
            } while (!email.setEmail(teclat.next()));
            setEmail(email);

            System.out.println("Introdueix el teu nom d'usuari: (serà el nom amb que s'iniciara a la Web) ");
            this.usuari = teclat.next();

            System.out.println("Introdueix la contrasenya del teu usuari: ");
            this.contrasenya = encriptarContrasenya(teclat.next());

            System.out.println("Introdueix si tens alguna condició fisica: ");
            this.condicio_fisica = teclat.next();

            System.out.println("Introdueix true(per si vols spam) o false(per si no vols spam): ");
            this.comunicacio_comercial = teclat.nextBoolean();

            Iban compte_bancari = new Iban();
            do {
                System.out.println("Introdueix el numerò del Compte Bancari: (IBAN complet sense espais)");
            } while (!compte_bancari.setCompte(teclat.next()));
            setCompte(compte_bancari);

            altaClientBD();

            System.out.println("Client donat d'alta: " + this);
        }

    }

    public Client altaClientBD() throws SQLException {

        // Fem connexió a la BD
        ConnexioBD con = new ConnexioBD();
        con.conectarBD();

        // Demanem el DNI
        String inserció = "INSERT INTO socis values (?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement ps = con.connexioBD.prepareStatement(inserció);
            cargarDadesDelClient(ps);
            ps.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Error al crear l'usuari" + this.nom);
        }

        con.desconectarBD();
        return null;
    }

    private String encriptarContrasenya(String contrasenya) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(contrasenya.getBytes());
        BigInteger bigInt = new BigInteger(1, messageDigest);
        return bigInt.toString(16);

    }

    public void eliminarClient() throws SQLException {

        Scanner teclat = new Scanner(System.in);
        // solicitem el dni a donar d'alta fins que sigui correcte

        String dni;
        Dni d = new Dni();
        do {

            System.out.print("Introdueix el dni del client que vols donar d'alta:");
            dni = teclat.next();

        } while (!d.validarDni(dni));
        d.setDni(dni);

        setDni(d);

        if (consultaClientBD(d.getDni()) == null) {

            System.out.println("El client amb aquest DNI no existeix");
        } else {

            eliminarClientBD();

        }

    }

    public Client eliminarClientBD() throws SQLException {

        // Fem connexió a la BD
        ConnexioBD con = new ConnexioBD();
        con.conectarBD();

        String eliminar = "DELETE from socis where DNI=? ";

        try {
            PreparedStatement ps = con.connexioBD.prepareStatement(eliminar);
            // Aqui solament demanen el dni ja que per eliminar un usuari amb el DNI es
            // suficient
            ps.setString(1, this.dni.getDni());
            ps.executeUpdate();
            System.out.println("Client eliminat correctament");
        } catch (Exception ex) {
            System.out.println("Error al eliminar aquest usuari: " + this.nom);
        }

        con.desconectarBD();
        return null;
    }

}
