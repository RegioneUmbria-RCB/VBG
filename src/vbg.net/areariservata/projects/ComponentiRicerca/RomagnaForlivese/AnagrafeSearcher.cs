using System;
using System.Collections.Generic;
using System.Data;
using System.Text;
using System.IO;
using System.Globalization;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche;
using System.Xml.Serialization;
using Init.Utils;
using System.Xml;
using System.Xml.Schema;
using System.Web;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche.Adrier;

namespace RomagnaForlivese
{
    public class AnagrafeSearcher : AnagrafeSearcherAdrierBase
    {
        public AnagrafeSearcher()
            : base("FORLIV")
        {
            
        }

        public override Anagrafe ByCodiceFiscaleImp(string codiceFiscale)
        {
            LogMessage("Il codice fiscale è " + codiceFiscale);
            DataBase db = new DataBase(Configuration["CONNECTIONSTRING"], (ProviderType)Enum.Parse(typeof(ProviderType), Configuration["PROVIDER"], true));

            Anagrafe anagrafe = null;

            DataSet ds = new DataSet();
            string sql;
            string table = (string.IsNullOrEmpty(Configuration["OWNER"]) ? Configuration["VIEW"] : Configuration["OWNER"] + "." + Configuration["VIEW"]);

            sql = "SELECT * " +
                  "FROM " +
                          table +
                //"COMUNE.CED_V_AN_RESID_MAGGIOLI " +
                        " WHERE " +
                           "COD_FISCALE = {0}";


            sql = String.Format(sql, db.Specifics.QueryParameterName("COD_FISCALE"));

            using (IDbCommand cmd = db.CreateCommand(sql))
            {
                cmd.Parameters.Add(db.CreateParameter("COD_FISCALE", codiceFiscale));

                IDataAdapter da = db.CreateDataAdapter(cmd);
                da.Fill(ds);
            }

            if (ds.Tables[0].Rows.Count == 0)
                throw new Exception("Non è stato trovato nessun soggetto anagrafico per il codice fiscale: " + codiceFiscale);
            else
            {
                if (ds.Tables[0].Rows.Count == 1)
                    anagrafe = GetAnagrafe(ds.Tables[0].Rows[0]);
                else
                    throw new Exception("Per il CF " + codiceFiscale + " il metodo ha restituito " + ds.Tables[0].Rows.Count + " record");
            }

            return anagrafe;
        }

        public override Anagrafe ByCodiceFiscaleImp(TipoPersona tipoPersona, string codiceFiscale)
        {
            if (tipoPersona == TipoPersona.PersonaFisica)
            {
                return ByCodiceFiscaleImp(codiceFiscale);
            }
            else
            {
                //Persona giuridica
                return base.ByPartitaIvaImp(codiceFiscale);
            }
        }

        public override List<Anagrafe> ByNomeCognomeImp(string nome, string cognome)
        {
            DataBase db = new DataBase(Configuration["CONNECTIONSTRING"], (ProviderType)Enum.Parse(typeof(ProviderType), Configuration["PROVIDER"], true));
            List<Anagrafe> list = new List<Anagrafe>();

            DataSet ds = new DataSet();
            string sql;
            string table = (string.IsNullOrEmpty(Configuration["OWNER"]) ? Configuration["VIEW"] : Configuration["OWNER"] + "." + Configuration["VIEW"]);

            sql = "SELECT * " +
                  "FROM " +
                          table +
                //"COMUNE.CED_V_AN_RESID_MAGGIOLI " +
                        " WHERE " +
                           "NOME = {0} AND " +
                           "COGNOME = {1}";


            sql = String.Format(sql, db.Specifics.QueryParameterName("NOME"), db.Specifics.QueryParameterName("COGNOME"));

            using (IDbCommand cmd = db.CreateCommand(sql))
            {
                cmd.Parameters.Add(db.CreateParameter("NOME", nome));
                cmd.Parameters.Add(db.CreateParameter("COGNOME", cognome));

                IDataAdapter da = db.CreateDataAdapter(cmd);
                da.Fill(ds);
            }

            foreach (DataRow dr in ds.Tables[0].Rows)
                list.Add(GetAnagrafe(dr));

            return list;
        }

        private Anagrafe GetAnagrafe(DataRow dr)
        {
            
            Anagrafe anagrafe = new Anagrafe();
            //Setto idcomune
            anagrafe.IDCOMUNE = IdComune;
            LogMessage("IDCOMUNE " + anagrafe.IDCOMUNE);
            //Setto CF
            anagrafe.CODICEFISCALE = dr["COD_FISCALE"].ToString().Trim().ToUpper();
            LogMessage("CODICEFISCALE " + anagrafe.CODICEFISCALE);
            //Setto il flag disabilitato
            switch (dr["POSIZIONE_ANAG"].ToString().Trim().ToUpper())
            {
                case "AIRE":
                case "EMIG":
                case "RESI":
                    anagrafe.FLAG_DISABILITATO = "0";
                    break;
                case "IRRE":
                case "ERR?":
                case "DECE":
                    anagrafe.FLAG_DISABILITATO = "1";
                    anagrafe.DATA_DISABILITATO = string.IsNullOrEmpty(dr["DATA_DECES"].ToString().Trim()) ? (DateTime?)null : (DateTime)dr["DATA_DECES"];
                    break;
                default:
                    throw new Exception("Il valore riportato dal campo POSIZIONE_ANAG non rientra nei casi gestiti: " + dr["POSIZIONE_ANAG"].ToString());
            }
            LogMessage("FLAG_DISABILITATO " + anagrafe.FLAG_DISABILITATO);

            //Setto il cognome
            anagrafe.NOMINATIVO = dr["COGNOME"].ToString().Trim().ToUpper();
            LogMessage("NOMINATIVO " + anagrafe.NOMINATIVO);
            //Setto il nome
            anagrafe.NOME = dr["NOME"].ToString().Trim().ToUpper();
            LogMessage("NOME " + anagrafe.NOME);
            //Setto il sesso
            anagrafe.SESSO = dr["SESSO"].ToString().Trim().ToUpper();
            LogMessage("SESSO " + anagrafe.SESSO);
            //Setto la data di nascita
            anagrafe.DATANASCITA = string.IsNullOrEmpty(dr["DATA_NASCITA"].ToString().Trim()) ? (DateTime?)null : (DateTime)dr["DATA_NASCITA"];
            LogMessage("DATANASCITA " + anagrafe.DATANASCITA);
            //Setto il codice del comune di nascita
            if (!string.IsNullOrEmpty(dr["COD_COMUNE_NASC"].ToString().Trim()))
            {
                ComuniMgr pComuniMgr = new ComuniMgr(SigeproDb);
                Comuni pComuni = new Comuni();
                string codiceIstat = dr["COD_COMUNE_NASC"].ToString().Trim();
                pComuni.CODICEISTAT = codiceIstat.PadLeft(6, '0');
                pComuni = pComuniMgr.GetByClass(pComuni);
                if (pComuni != null)
                {
                    anagrafe.CODCOMNASCITA = pComuni.CODICECOMUNE;
                    LogMessage("CODCOMNASCITA " + anagrafe.CODCOMNASCITA);
                }
                else
                {
                    //Uso il COD_COMUNE_NASC come codice stato estero
                    pComuni = new Comuni();
                    string codiceStatoEstero = dr["COD_COMUNE_NASC"].ToString().Trim();
                    pComuni.CODICESTATOESTERO = codiceStatoEstero.TrimStart(new char[] { '0' });
                    pComuni = pComuniMgr.GetByClass(pComuni);
                    if (pComuni != null)
                    {
                        anagrafe.CODCOMNASCITA = pComuni.CODICECOMUNE;
                        LogMessage("CODCOMNASCITA " + anagrafe.CODCOMNASCITA);
                    }
                }
            }

            switch (dr["POSIZIONE_ANAG"].ToString().Trim().ToUpper())
            {
                case "EMIG":
                case "AIRE":
                case "RESI":
                    //Setto l'indirizzo 
                    SetIndirizzo(dr, anagrafe, dr["POSIZIONE_ANAG"].ToString().Trim().ToUpper());
                    break;
                case "DECE":
                case "ERR?":
                case "IRRE":
                    if (string.IsNullOrEmpty(dr["DATA_EMIGR"].ToString().Trim()))
                    {
                        SetIndirizzo(dr, anagrafe, "RESI");
                    }
                    else
                    {
                        if (!string.IsNullOrEmpty(dr["DATA_IMMIGR"].ToString().Trim()))
                        {
                            if (((DateTime)dr["DATA_IMMIGR"]).CompareTo((DateTime)dr["DATA_EMIGR"]) >= 0)
                                SetIndirizzo(dr, anagrafe, "RESI");
                            else
                                SetIndirizzo(dr, anagrafe, "EMIG");
                        }
                        else
                            SetIndirizzo(dr, anagrafe, "EMIG");
                    }
                    break;
                default:
                    throw new Exception("Il valore riportato dal campo POSIZIONE_ANAG non rientra nei casi gestiti: " + dr["POSIZIONE_ANAG"].ToString());
            }

            return anagrafe;
        }

        private void SetIndirizzo(DataRow dr, Anagrafe anagrafe, string posAnag)
        {
            ComuniMgr pComuniMgr = new ComuniMgr(SigeproDb);
            Comuni pComune = new Comuni();

            switch (posAnag)
            {
                case "AIRE":
                    anagrafe.INDIRIZZO = dr["VIA"].ToString().Trim().ToUpper();
                    if (!string.IsNullOrEmpty(dr["NUM_CIVICO"].ToString().Trim()))
                        anagrafe.INDIRIZZO += " " + dr["NUM_CIVICO"].ToString().Trim().ToUpper();
                    if (!string.IsNullOrEmpty(dr["BIS"].ToString().Trim()))
                        anagrafe.INDIRIZZO += dr["BIS"].ToString().Trim().ToUpper();
                    if (!string.IsNullOrEmpty(dr["INTERNO"].ToString().Trim()))
                        anagrafe.INDIRIZZO += " INT.: " + dr["INTERNO"].ToString().Trim().ToUpper();
                    LogMessage("INDIRIZZO " + anagrafe.INDIRIZZO);
                    anagrafe.CAP = dr["CAP"].ToString().Trim();
                    LogMessage("CAP " + anagrafe.CAP);
                    if (!string.IsNullOrEmpty(dr["COD_COMUNE_RESID"].ToString().Trim()))
                    {
                        string codiceStatoEstero = dr["COD_COMUNE_RESID"].ToString().Trim();
                        pComune.CODICESTATOESTERO = codiceStatoEstero.TrimStart(new char[] { '0' });
                        pComune = pComuniMgr.GetByClass(pComune);
                        if (pComune != null)
                        {
                            anagrafe.COMUNERESIDENZA = pComune.CODICECOMUNE;
                            LogMessage("COMUNERESIDENZA " + anagrafe.COMUNERESIDENZA);
                            //Setto la provincia
                            anagrafe.PROVINCIA = pComune.SIGLAPROVINCIA;
                            LogMessage("PROVINCIA " + anagrafe.PROVINCIA);
                        }
                    }
                    break;
                case "RESI":
                    anagrafe.INDIRIZZO = dr["VIA"].ToString().Trim().ToUpper();
                    if (!string.IsNullOrEmpty(dr["NUM_CIVICO"].ToString().Trim()))
                        anagrafe.INDIRIZZO += " " + dr["NUM_CIVICO"].ToString().Trim().ToUpper();
                    if (!string.IsNullOrEmpty(dr["BIS"].ToString().Trim()))
                        anagrafe.INDIRIZZO += dr["BIS"].ToString().Trim().ToUpper();
                    if (!string.IsNullOrEmpty(dr["INTERNO"].ToString().Trim()))
                        anagrafe.INDIRIZZO += " INT.: " + dr["INTERNO"].ToString().Trim().ToUpper();
                    LogMessage("INDIRIZZO " + anagrafe.INDIRIZZO);
                    anagrafe.CAP = dr["CAP"].ToString().Trim();
                    LogMessage("CAP " + anagrafe.CAP);
                    //Setto il codice del comune di residenza
                    pComune = pComuniMgr.GetByComune($"0{dr["CAP"].ToString()}");
                    if (pComune != null)
                    {
                        anagrafe.COMUNERESIDENZA = pComune.CODICECOMUNE;
                        LogMessage("COMUNERESIDENZA " + anagrafe.COMUNERESIDENZA);
                        //Setto la provincia
                        anagrafe.PROVINCIA = pComune.SIGLAPROVINCIA;
                        LogMessage("PROVINCIA " + anagrafe.PROVINCIA);
                    }
                    break;
                case "EMIG":
                    //Non ho informazioni aggiornate
                    break;
            }
        } 


    }
}
