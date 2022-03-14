package P2_Gimnas;

public class Dni {
    private String dni;
    private String numero;

    public Dni() {
    }

    
    // public Dni(String dni) {
    //     if (setDni(dni)) this.dni = dni;
    //     else this.dni = "incorrecte";
    // }

    public Dni(String dni) {
        this.dni = dni;
    }

    public boolean validarDni(String dni) {

        if ( dni.length() != 9 ) return false;
        
        char ultPos = dni.charAt(8);
        if (!(Character.isLetter(ultPos))) return false;

        char majuscula = Character.toUpperCase(ultPos);
        String dniNum = dni.substring(0,8);
        char[] lletresDni = {'T', 'R', 'W', 'A', 'G', 'M', 'Y' ,'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
        int dniNums = 0;

        try {
            dniNums = Integer.parseInt(dniNum);            
        } catch (Exception e) {
            return false;
        }

        int reste = dniNums % 23;
        
        if (majuscula == lletresDni[reste]) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Dni [dni=" + dni + "]";
    }

    // public boolean validarDni(String dni) {

    //     char lletraMajuscula;

    //     if (dni.length() != 9 ) {
    //         return false;
    //     }

    //     char ultPos = dni.charAt(8);

    //     if (!(Character.isLetter(ultPos))) {
    //         return false;
    //     }

    //     lletraMajuscula = Character.toUpperCase(ultPos);

    //     String dniNum = dni.substring(0, 8);

    //     if (solnumeros(dniNum) != true) {
    //         return false;
    //     }

    //     if (!(lletraDNI(dniNum) == lletraMajuscula)) {
    //         return false;
    //     }

    //     return true;

    // }

    // private boolean solnumeros(String numero) {

    //     for (int i = 0; 1 < numero.length() -1 ; i++){

    //         if (!Character.isDigit(numero.charAt(i))) {
    //             System.out.println("\nEls primers 8 caracters han de ser numeros");
    //             return false;
    //         }
    //     }
    //     return true;

    // }

    // private boolean solNumerosAmbExcepcio(String numero) {


    //     try {
    //         int dniNum = Integer.parseInt(numero);
    //     } catch (NumberFormatException e){
    //         return false;
    //     }

    //     return true;
    // }

    // private char lletraDNI(String dniNum) {

    //     // int miDNI = Integer.parseInt(dni.substring(0, 8));
    //     int resta = 0;
    //     char lletra = ' ';
    //     char[] assignacioLletra = { 'T', 'R', 'W', 'A', 'G', 'H', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S',
    //             'Q', 'V', 'H', 'L', 'C', 'K', 'E' };

    //     resta = Integer.parseInt(dniNum) % 23;

    //     lletra = assignacioLletra[resta];

    //     return lletra;

    // }

    public String getDni() {
        return dni;
    }

    public boolean setDni(String dni) {
        if (validarDni(dni)) {
			this.dni = dni;
			return true;
		} else {
            System.out.println("DNI introduit de manera incorrecta");
            return false;
        }
    }


    // public boolean validarDni(String dni) {

    //     String lletraMajuscula = "";

    //     if (dni.length() != 9 || Character.isLetter(dni.charAt(8)) == false) {
    //         return false;
    //     }

    //     lletraMajuscula = (dni.substring(8)).toUpperCase();

    //     if (solnumeros(lletraMajuscula) == true && lletraDNI(lletraMajuscula).equals(lletraMajuscula)) {
    //         return true;
    //     } else {
    //         return false;
    //     }
    // }

    // private boolean solnumeros(String dni) {

    //     int i, j = 0;
    //     String numero = "";
    //     String[] numeros = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

    //     for (i = 0; i < dni.length() - 1; i++) {
    //         numero = dni.substring(i, i + 1);

    //         for (j = 0; j < numeros.length; j++) {
    //             dni += numeros[j];
    //         }
    //     }

    //     if (dni.length() != 8) {
    //         return false;
    //     } else {
    //         return true;
    //     }

    // }

    // private String lletraDNI(String dni) {

    //     int miDNI = Integer.parseInt(dni.substring(0, 8));
    //     int resta = 0;
    //     String lletra = "";
    //     String[] assignacioLletra = { "T", "R", "W", "A", "G", "H", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S",
    //             "Q", "V", "H", "L", "C", "K", "E" };

    //     resta = miDNI % 23;

    //     lletra = assignacioLletra[resta];

    //     return lletra;

    // }

    

}
