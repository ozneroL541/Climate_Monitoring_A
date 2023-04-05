/**************************************
 * Matricola    Cognome     Nome
 * 754530       Galimberti  Riccardo
 * 753747       Masolo      Carlos
 * 755152       Paredi      Giacomo
 * 753252       Radice      Lorenzo
 * Sede: Como
***************************************/

package src.users;
import src.monitoringcentre.MonitoringCentre;

public class AutorizedOperator extends User {
    private String nome;
    private String cognome;
    private String codice_fiscale;
    private String email_address;
    private int userid;
    private String passwd;
    private MonitoringCentre centre;

    public AutorizedOperator() {
        //TODO
    }
}
