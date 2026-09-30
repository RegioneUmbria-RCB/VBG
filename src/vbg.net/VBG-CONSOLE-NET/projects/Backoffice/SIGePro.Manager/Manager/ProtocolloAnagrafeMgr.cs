/*using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using PersonalLib2.Sql.Collections;

namespace Init.SIGePro.Manager
{
    /// <summary>
    /// Descrizione di riepilogo per ProtocolloAnagrafeMgr.
    /// </summary>
    public class ProtocolloAnagrafeMgr : AnagrafeMgr
    {
        public ProtocolloAnagrafeMgr(DataBase dataBase) : base(dataBase) { }

        public string GetCodiceIstat(string sCodComune)
        {
            ComuniMgr pComuniMgr = new ComuniMgr(db);
            Comuni pComuni = pComuniMgr.GetById(sCodComune);

            //Tolgo il primo 0 dal codice istat
            string sCodiceIstat = string.Empty;
            if (pComuni != null)
            {
                if (!string.IsNullOrEmpty(pComuni.CODICEISTAT))
                {
                    sCodiceIstat = pComuni.CODICEISTAT;
                    if (sCodiceIstat.IndexOf('0') == 0)
                        sCodiceIstat = sCodiceIstat.TrimStart(new char[] { '0' });
                }
            }

            return sCodiceIstat;
        }

        public string GetCodiceStatoEstero(string sCodComune)
        {
            ComuniMgr pComuniMgr = new ComuniMgr(db);
            Comuni pComuni = pComuniMgr.GetById(sCodComune);

            string sCodiceStatoEstero = string.Empty;
            if (pComuni != null)
            {
                if (!string.IsNullOrEmpty(pComuni.CODICESTATOESTERO))
                    sCodiceStatoEstero = pComuni.CODICESTATOESTERO;
            }

            return sCodiceStatoEstero;
        }

        public void SetProtocolloAnagrafe(Anagrafe pAnagrafe, ProtocolloAnagrafe pProtAnagrafe)
        {
            pProtAnagrafe.AnagrafeDocumenti = pAnagrafe.AnagrafeDocumenti;
            pProtAnagrafe.CAP = pAnagrafe.CAP;
            pProtAnagrafe.CAPCORRISPONDENZA = pAnagrafe.CAPCORRISPONDENZA;
            pProtAnagrafe.CITTA = pAnagrafe.CITTA;
            pProtAnagrafe.CITTACORRISPONDENZA = pAnagrafe.CITTACORRISPONDENZA;

            pProtAnagrafe.CODCOMNASCITA = pAnagrafe.CODCOMNASCITA;
            pProtAnagrafe.CODCOMREGDITTE = pAnagrafe.CODCOMREGDITTE;
            pProtAnagrafe.CODCOMREGTRIB = pAnagrafe.CODCOMREGTRIB;
            pProtAnagrafe.CODICEANAGRAFE = pAnagrafe.CODICEANAGRAFE;
            pProtAnagrafe.CODICECITTADINANZA = pAnagrafe.CODICECITTADINANZA;

            pProtAnagrafe.CODICEFISCALE = pAnagrafe.CODICEFISCALE;
            pProtAnagrafe.COMUNECORRISPONDENZA = pAnagrafe.COMUNECORRISPONDENZA;
            pProtAnagrafe.COMUNERESIDENZA = pAnagrafe.COMUNERESIDENZA;
            pProtAnagrafe.DATA_DISABILITATO = pAnagrafe.DATA_DISABILITATO;
            pProtAnagrafe.DATAISCRREA = pAnagrafe.DATAISCRREA;

            pProtAnagrafe.DATANASCITA = pAnagrafe.DATANASCITA;
            pProtAnagrafe.DATANOMINATIVO = pAnagrafe.DATANOMINATIVO;
            pProtAnagrafe.DATAREGDITTE = pAnagrafe.DATAREGDITTE;
            pProtAnagrafe.DATAREGTRIB = pAnagrafe.DATAREGTRIB;
            pProtAnagrafe.EMAIL = pAnagrafe.EMAIL;

            pProtAnagrafe.FAX = pAnagrafe.FAX;
            pProtAnagrafe.FLAG_DISABILITATO = pAnagrafe.FLAG_DISABILITATO;
            pProtAnagrafe.FLAG_NOPROFIT = pAnagrafe.FLAG_NOPROFIT;
            pProtAnagrafe.FORMAGIURIDICA = pAnagrafe.FORMAGIURIDICA;
            pProtAnagrafe.IDCOMUNE = pAnagrafe.IDCOMUNE;

            pProtAnagrafe.INDIRIZZO = pAnagrafe.INDIRIZZO;
            pProtAnagrafe.INDIRIZZOCORRISPONDENZA = pAnagrafe.INDIRIZZOCORRISPONDENZA;
            pProtAnagrafe.INVIOEMAIL = pAnagrafe.INVIOEMAIL;
            pProtAnagrafe.INVIOEMAILTEC = pAnagrafe.INVIOEMAILTEC;
            pProtAnagrafe.NOME = pAnagrafe.NOME;

            pProtAnagrafe.NOMINATIVO = pAnagrafe.NOMINATIVO;
            pProtAnagrafe.NOTE = pAnagrafe.NOTE;
            pProtAnagrafe.NUMISCRREA = pAnagrafe.NUMISCRREA;
            pProtAnagrafe.PARTITAIVA = pAnagrafe.PARTITAIVA;
            pProtAnagrafe.PASSWORD = pAnagrafe.PASSWORD;

            pProtAnagrafe.PROVINCIA = pAnagrafe.PROVINCIA;
            pProtAnagrafe.PROVINCIACORRISPONDENZA = pAnagrafe.PROVINCIACORRISPONDENZA;
            pProtAnagrafe.PROVINCIAREA = pAnagrafe.PROVINCIAREA;
            pProtAnagrafe.REFERENTE = pAnagrafe.REFERENTE;
            pProtAnagrafe.REGDITTE = pAnagrafe.REGDITTE;

            pProtAnagrafe.REGTRIB = pAnagrafe.REGTRIB;
            pProtAnagrafe.SESSO = pAnagrafe.SESSO;
            pProtAnagrafe.TELEFONO = pAnagrafe.TELEFONO;
            pProtAnagrafe.TELEFONOCELLULARE = pAnagrafe.TELEFONOCELLULARE;
            pProtAnagrafe.TIPOANAGRAFE = pAnagrafe.TIPOANAGRAFE;

            pProtAnagrafe.TIPOLOGIA = pAnagrafe.TIPOLOGIA;
            pProtAnagrafe.TITOLO = pAnagrafe.TITOLO;
        }
    }
}*/
