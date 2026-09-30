package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen;


public class QueriesConstants {
    
    public static final String MERCATISELECT = "select mercati_massive_t.fkid_testata " +
		"from mercati_massive_t " +
		"inner join massive_testata on massive_testata.idcomune = mercati_massive_t.idcomune and massive_testata.id = mercati_massive_t.fkid_testata " +
		"where mercati_massive_t.idcomune = ? " +
		"and massive_testata.data_cancellazione is null " +
		"and mercati_massive_t.software = ? " +
		"order by massive_testata.data_comunicazione desc, massive_testata.id desc ";
    
    public static final String MERCATITESTATECHCK = "select count(id) as conteggio from mercati_massive_t where idcomune = ? and fkid_testata = ?";
    
    public static Object[] toArray(Object... params) {
	return params;
    }
    
    
    public static final String COMUNICAZIONIMERCATISPUNTISTISELECT = "SELECT (:campi) FROM "
	    +"AUTORIZZAZIONI A JOIN SPUNTISTI_MERCATI B ON A.IDCOMUNE = B.IDCOMUNE AND A.ID = B.FK_AUTORIZZAZIONE "
	    +"JOIN MERCATI C ON B.IDCOMUNE = C.IDCOMUNE AND C.CODICEMERCATO = B.FK_MERCATO "
	    +"WHERE A.IDCOMUNE = :idcomune AND C.SOFTWARE = :software AND B.FLG_ATTIVO = 1 AND A.FLAG_ATTIVA = 1 ";
    
    public static final String COMUNICAZIONIMERCATICONCSTISELECT = "SELECT (:campi) FROM "
	    +"AUTORIZZAZIONI A JOIN AUTORIZZAZIONI_CONCESSIONI B ON A.IDCOMUNE = B.IDCOMUNE AND A.ID = B.FK_IDAUT_ATTUALE "
	    +"JOIN MERCATI C ON B.IDCOMUNE = C.IDCOMUNE AND C.CODICEMERCATO = B.FK_CODICEMERCATO "
	    +"WHERE A.IDCOMUNE = :idcomune AND A.FLAG_ATTIVA = 1 AND C.SOFTWARE = :software ";
    
    public static final String COMUNICAZIONIMERCATICONCSPUNTSELECT = "SELECT (:campi) FROM "
	    +"AUTORIZZAZIONI A "
	    +"JOIN MERCATIPRESENZE_D B ON A.IDCOMUNE = B.IDCOMUNE AND A.ID = B.FK_AUTORIZZAZIONI_ID "
	    +"JOIN MERCATIPRESENZE_T T ON T.IDCOMUNE = B.IDCOMUNE AND T.ID = B.FKIDTESTATA "
	    +"JOIN MERCATI C ON C.IDCOMUNE = T.IDCOMUNE AND C.CODICEMERCATO = T.FKCODICEMERCATO "
	    +"WHERE A.IDCOMUNE = :idcomune AND C.SOFTWARE = :software ";

    
    public static final String SELECTSOFTWAREANDCODICECOMUNEMERCATI = "SELECT DISTINCT D.SOFTWARE, D.CODICECOMUNE FROM MASSIVE_DETTAGLIO A JOIN MERCATI_MASSIVE_D B ON A.IDCOMUNE = B.IDCOMUNE AND A.ID = B.FKID_MASSIVE_D "
	    +"JOIN MERCATI_MASSIVE_D_AUT C ON B.ID = C.FK_MERCATI_MASSIVE_D JOIN MERCATI D ON D.CODICEMERCATO = C.FK_CODICEMERCATO "
	    +"WHERE A.IDCOMUNE = ? AND A.ID = ? ";
    
    public static final String SELECTMERCATOBYTESTATA = "SELECT FK_CODICEMERCATO AS fkcodicemercato FROM MERCATI_MASSIVE_T WHERE IDCOMUNE = ? AND FKID_TESTATA = ?";
    
    public static final String ISTANZECOMSELECT = "select istanze_massive_t.fkid_testata " +
		"from istanze_massive_t " +
		"inner join massive_testata on massive_testata.idcomune = istanze_massive_t.idcomune and massive_testata.id = istanze_massive_t.fkid_testata " +
		"where istanze_massive_t.idcomune = ? " +
		"and massive_testata.data_cancellazione is null " +
		"and istanze_massive_t.software = ? " +
		"order by massive_testata.data_comunicazione desc, massive_testata.id desc ";
    public static final String ISTANZETESTATECHCK = "select count(id) as conteggio from istanze_massive_t where idcomune = ? and fkid_testata = ?";
    
    public static final String SELECTSOFTWAREANDCODICECOMUNEISTANZE = "SELECT DISTINCT SOFTWARE, CODICECOMUNE FROM ISTANZE WHERE IDCOMUNE = ? AND CODICEISTANZA = ?";
    
    public static final String SELECTAUTORIZZAZIONIFROMMERCATID = "SELECT B.ID FROM MERCATI_MASSIVE_D A JOIN AUTORIZZAZIONI B ON A.IDCOMUNE = B.IDCOMUNE AND B.ID = A.FK_AUTORIZZAZIONE WHERE A.IDCOMUNE = ? AND A.FKID_MASSIVE_D = ?";
    
    public static final String COMUNICAZIONISELECTISTANZEFORMOVIMENTI = "SELECT B.* FROM ISTANZE_MASSIVE_D A JOIN ISTANZE_MASSIVE_D_ISTANZE B ON A.IDCOMUNE = B.IDCOMUNE AND A.ID = B.FKID_ISTANZEMD "
	    + "WHERE A.IDCOMUNE = ? AND A.FKID_MASSIVE_D = ? AND B.FKMOVIMENTO IS NULL";
    
    public static final String SELECTSOFTWAREANDCODICECOMUNEISTANZEAPPIO = "SELECT DISTINCT D.SOFTWARE, D.CODICECOMUNE FROM MASSIVE_DETTAGLIO A JOIN ISTANZE_MASSIVE_D B ON A.IDCOMUNE = B.IDCOMUNE AND A.ID = B.FKID_MASSIVE_D "
	    +"JOIN ISTANZE_MASSIVE_D_ISTANZE C ON B.IDCOMUNE = C.IDCOMUNE AND B.ID = C.FKID_ISTANZEMD "
	    +"JOIN ISTANZE D ON D.IDCOMUNE = C.IDCOMUNE AND D.CODICEISTANZA = C.FKCODICEISTANZA "
	    +"WHERE A.IDCOMUNE = ? AND A.ID = ? AND D.SOFTWARE IS NOT NULL AND D.CODICECOMUNE IS NOT NULL";
    
    public static final String SELECTGRUPPOTYPEISTANZE = "SELECT GRUPPO_TYPE as gruppotype FROM ISTANZE_MASSIVE_T WHERE IDCOMUNE = ? AND FKID_TESTATA = ?";
    
    public static final String SELECTISTANZABYIDDETTAGLIO = "SELECT D.* FROM MASSIVE_DETTAGLIO A JOIN ISTANZE_MASSIVE_D B ON A.IDCOMUNE = B.IDCOMUNE AND A.ID = B.FKID_MASSIVE_D "
	    +"JOIN ISTANZE_MASSIVE_D_ISTANZE C ON B.IDCOMUNE = C.IDCOMUNE AND B.ID = C.FKID_ISTANZEMD "
	    +"JOIN ISTANZE D ON D.IDCOMUNE = C.IDCOMUNE AND D.CODICEISTANZA = C.FKCODICEISTANZA "
	    +"WHERE A.IDCOMUNE = ? AND A.ID = ?";

}
