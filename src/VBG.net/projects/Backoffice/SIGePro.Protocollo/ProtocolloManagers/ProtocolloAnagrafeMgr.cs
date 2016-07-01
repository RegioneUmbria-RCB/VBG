using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ProtocolloManagers
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

        public void SetProtocolloAnagrafe(Anagrafe anagrafe, ProtocolloAnagrafe protoAnagrafe)
        {
            protoAnagrafe.AnagrafeDocumenti = anagrafe.AnagrafeDocumenti;
            protoAnagrafe.CAP = anagrafe.CAP;
            protoAnagrafe.CAPCORRISPONDENZA = anagrafe.CAPCORRISPONDENZA;
            protoAnagrafe.CITTA = anagrafe.CITTA;
            protoAnagrafe.CITTACORRISPONDENZA = anagrafe.CITTACORRISPONDENZA;

            protoAnagrafe.CODCOMNASCITA = anagrafe.CODCOMNASCITA;
            protoAnagrafe.CODCOMREGDITTE = anagrafe.CODCOMREGDITTE;
            protoAnagrafe.CODCOMREGTRIB = anagrafe.CODCOMREGTRIB;
            protoAnagrafe.CODICEANAGRAFE = anagrafe.CODICEANAGRAFE;
            protoAnagrafe.CODICECITTADINANZA = anagrafe.CODICECITTADINANZA;

            protoAnagrafe.CODICEFISCALE = anagrafe.CODICEFISCALE;
            protoAnagrafe.COMUNECORRISPONDENZA = anagrafe.COMUNECORRISPONDENZA;
            protoAnagrafe.COMUNERESIDENZA = anagrafe.COMUNERESIDENZA;
            protoAnagrafe.DATA_DISABILITATO = anagrafe.DATA_DISABILITATO;
            protoAnagrafe.DATAISCRREA = anagrafe.DATAISCRREA;

            protoAnagrafe.DATANASCITA = anagrafe.DATANASCITA;
            protoAnagrafe.DATANOMINATIVO = anagrafe.DATANOMINATIVO;
            protoAnagrafe.DATAREGDITTE = anagrafe.DATAREGDITTE;
            protoAnagrafe.DATAREGTRIB = anagrafe.DATAREGTRIB;
            protoAnagrafe.EMAIL = anagrafe.EMAIL;
            protoAnagrafe.Pec = anagrafe.Pec;

            protoAnagrafe.FAX = anagrafe.FAX;
            protoAnagrafe.FLAG_DISABILITATO = anagrafe.FLAG_DISABILITATO;
            protoAnagrafe.FLAG_NOPROFIT = anagrafe.FLAG_NOPROFIT;
            protoAnagrafe.FORMAGIURIDICA = anagrafe.FORMAGIURIDICA;
            protoAnagrafe.IDCOMUNE = anagrafe.IDCOMUNE;

            protoAnagrafe.INDIRIZZO = anagrafe.INDIRIZZO;
            protoAnagrafe.INDIRIZZOCORRISPONDENZA = anagrafe.INDIRIZZOCORRISPONDENZA;
            protoAnagrafe.INVIOEMAIL = anagrafe.INVIOEMAIL;
            protoAnagrafe.INVIOEMAILTEC = anagrafe.INVIOEMAILTEC;
            protoAnagrafe.NOME = anagrafe.NOME;

            protoAnagrafe.NOMINATIVO = anagrafe.NOMINATIVO;
            protoAnagrafe.NOTE = anagrafe.NOTE;
            protoAnagrafe.NUMISCRREA = anagrafe.NUMISCRREA;
            protoAnagrafe.PARTITAIVA = anagrafe.PARTITAIVA;
            protoAnagrafe.PASSWORD = anagrafe.PASSWORD;

            protoAnagrafe.PROVINCIA = anagrafe.PROVINCIA;
            protoAnagrafe.PROVINCIACORRISPONDENZA = anagrafe.PROVINCIACORRISPONDENZA;
            protoAnagrafe.PROVINCIAREA = anagrafe.PROVINCIAREA;
            protoAnagrafe.REFERENTE = anagrafe.REFERENTE;
            protoAnagrafe.REGDITTE = anagrafe.REGDITTE;

            protoAnagrafe.REGTRIB = anagrafe.REGTRIB;
            protoAnagrafe.SESSO = anagrafe.SESSO;
            protoAnagrafe.TELEFONO = anagrafe.TELEFONO;
            protoAnagrafe.TELEFONOCELLULARE = anagrafe.TELEFONOCELLULARE;
            protoAnagrafe.TIPOANAGRAFE = anagrafe.TIPOANAGRAFE;

            protoAnagrafe.TIPOLOGIA = anagrafe.TIPOLOGIA;
            protoAnagrafe.TITOLO = anagrafe.TITOLO;
        }
    }
}
