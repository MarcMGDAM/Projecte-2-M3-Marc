package P2_Gimnas;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

public class Activitats {
    
    private int id;
    private String nomActivitat;
    private String descripcio;
    private Time hora;
    private int reserves_totals;
    private int aforament;


    public Activitats(int id, String nomActivitat, String descripcio, Time hora, int reserves_totals, int aforament) {
        this.id = id;
        this.nomActivitat = nomActivitat;
        this.descripcio = descripcio;
        this.hora = hora;
        this.reserves_totals = reserves_totals;
        this.aforament = aforament;
    }

    @Override
    public String toString() {
        return "Activitats [ID=" + id + ", nomActivitat=" + nomActivitat + 
        ", descripcio=" + descripcio + ", hora=" + hora + ", reserves_totals=" + reserves_totals + ", aforament=" + aforament + "]";
    }

    public Activitats() {

    }

    public void cargarDadesActivtats(PreparedStatement ps) throws SQLException {

        ps.setInt(1, this.id);
        ps.setString(2, this.nomActivitat);
        ps.setString(3, this.descripcio);
        ps.setTime(4, this.hora);
        ps.setInt(5, this.reserves_totals);
        ps.setInt(6, this.aforament);

    }

    public void cargarDadesActivitatsSentencia(ResultSet rs) throws SQLException {

        this.setID(rs.getInt("ID"));
        this.setNomActivitat(rs.getString("nomActivitat"));
        this.setDescricpio(rs.getString("descripció"));
        this.setHora(rs.getTime("hora"));
        this.setReservesTotals(rs.getInt("reserves_totals"));
        this.setAforament(rs.getInt("aforament"));

    }


    private void setAforament(int aforament) {
        this.aforament = aforament;
    }

    private void setReservesTotals(int reserves_totals) {
        this.reserves_totals = reserves_totals;
    }


    private void setHora(Time hora) {
        this.hora = hora;
    }


    private void setDescricpio(String descripcio) {
        this.descripcio = descripcio;
    }


    private void setNomActivitat(String nomActivitat) {
        this.nomActivitat = nomActivitat;
    }


    private void setID(int id) {
        this.id = id;
    }


    public Activitats consultaActivitatsLliuresBD() throws SQLException {

        ArrayList<Activitats> activitats = new ArrayList<>();

        // Fem connexió a la BD
        ConnexioBD con = new ConnexioBD();
        con.conectarBD();

        String reserves = "select A.*, count(L.ID) as reserves_totals, S.aforament from reserva_lliure L, activitats A, activitat_lliure AL, sales S where A.numero_sala = S.num_sala and A.ID = AL.IDL and AL.IDL = L.ID group by L.ID order by count(L.ID) desc;";

        PreparedStatement ps = con.connexioBD.prepareStatement(reserves);
        
        ps.executeQuery();

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Activitats a1 = new Activitats();
            a1.cargarDadesActivitatsSentencia(rs);

            activitats.add(a1);

        }

        for (Activitats activitat : activitats) {
            System.out.println(activitat);
        }
        con.desconectarBD();

        return null;
    }

    public Activitats consultaActivitatsColectivesBD() throws SQLException {

        ArrayList<Activitats> activitats = new ArrayList<>();

        // Fem connexió a la BD
        ConnexioBD con = new ConnexioBD();
        con.conectarBD();

        String reserves = "select A.*, count(C.ID) as reserves_totals, S.aforament from reserva_colectiva C, activitats A, activitat_colectiva AC, sales S where A.numero_sala = S.num_sala and A.ID = AC.IDC and AC.IDC = C.ID group by C.ID order by count(C.ID) desc;";

        PreparedStatement ps = con.connexioBD.prepareStatement(reserves);
        
        ps.executeQuery();

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Activitats a1 = new Activitats();
            a1.cargarDadesActivitatsSentencia(rs);

            activitats.add(a1);

        }

        for (Activitats activitat : activitats) {
            System.out.println(activitat);
        }
        con.desconectarBD();

        return null;
    }


    
    


}
