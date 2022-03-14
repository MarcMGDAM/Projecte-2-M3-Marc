package P2_Gimnas;

import javax.print.attribute.standard.RequestingUserName;

public class Telefon {

    private String telefon;

    public Telefon(String telefon) {
        
        this.telefon = telefon;
    }

    Telefon() {

    }

    private boolean validarTelefon(String telefon) {
        // Validem que el numero tingui 9 digits exactes
        if (telefon.length() != 9) {
            System.out.println("El telèfon ha de tenir 9 digits");
            return false;
        } else {
            // validem que tots els digits siguin numeros
            for (int i = 0; i < telefon.length(); i++) {
                if (!(telefon.charAt(i) >= '0' && telefon.charAt(i) <= '9')) {
                    System.out.println("Tots els digits han de ser numeros");
                    return false;
                }
            }

            return true;
        }

    }

    public String getTelefon() {
        return telefon;
    }

    public boolean setTelefon(String telefon) {
        if (validarTelefon(telefon)) {
            this.telefon = telefon;
            return true;
        } 
        return false;
    }

    @Override
    public String toString() {
        return "Telefon [telefon=" + telefon + "]";
    }
    

}
