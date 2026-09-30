using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche.Adrier;
using log4net;
using PersonalLib2.Data;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Data;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.ComponentiRicerca.RomagnaForlivese
{
    public class RomagnaForliveseAnagrafeSearcher : AnagrafeSearcherAdrierBase
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(RomagnaForliveseAnagrafeSearcher));
        public RomagnaForliveseAnagrafeSearcher(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory)
            : base(verticalizzazioniFactory, bindingFactory, "FORLIV")
        {

        }

        public override Anagrafe ByCodiceFiscaleImp(string codiceFiscale)
        {
            this.LogMessage("Il codice fiscale è " + codiceFiscale);
            var db = new DataBase(this.Configuration["CONNECTIONSTRING"], (ProviderType)Enum.Parse(typeof(ProviderType), this.Configuration["PROVIDER"], true));

            var ds = new DataSet();

            var owner = this.Configuration["OWNER"];
            var view = this.Configuration["VIEW"];
            var table = String.IsNullOrEmpty(owner) ? view : $"{owner}.{view}";

            var sql = $@"SELECT * 
                         FROM {table}
                        WHERE 
                        COD_FISCALE = {db.Specifics.QueryParameterName("COD_FISCALE")}";

            using (var cmd = db.CreateCommand(sql))
            {
                cmd.Parameters.Add(db.CreateParameter("COD_FISCALE", codiceFiscale));

                IDataAdapter da = db.CreateDataAdapter(cmd);
                da.Fill(ds);
            }

            if (ds.Tables[0].Rows.Count == 0)
            {
                this._log.Error($"Non è stato trovato nessun soggetto anagrafico per il codice fiscale: {codiceFiscale}");

                return null;
            }

            if (ds.Tables[0].Rows.Count > 1)
            {
                var err = $"La ricerca del codice fiscale {codiceFiscale} ha restituito {ds.Tables[0].Rows.Count} risultati";

                this._log.Error(err);
                throw new Exception(err);
            }

            return this.GetAnagrafe(ds.Tables[0].Rows[0]); ;
        }

        public override Anagrafe ByCodiceFiscaleImp(TipoPersona tipoPersona, string codiceFiscale)
        {
            if (tipoPersona == TipoPersona.PersonaFisica)
            {
                return this.ByCodiceFiscaleImp(codiceFiscale);
            }
            else
            {
                //Persona giuridica
                return base.ByPartitaIvaImp(codiceFiscale);
            }
        }

        public override List<Anagrafe> ByNomeCognomeImp(string nome, string cognome)
        {
            var db = new DataBase(this.Configuration["CONNECTIONSTRING"], (ProviderType)Enum.Parse(typeof(ProviderType), this.Configuration["PROVIDER"], true));
            var list = new List<Anagrafe>();

            var ds = new DataSet();
            string sql;
            var table = (string.IsNullOrEmpty(this.Configuration["OWNER"]) ? this.Configuration["VIEW"] : this.Configuration["OWNER"] + "." + this.Configuration["VIEW"]);

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
                list.Add(this.GetAnagrafe(dr));

            return list;
        }

        private Anagrafe GetAnagrafe(DataRow dr)
        {

            var anagrafe = new Anagrafe();
            //Setto idcomune
            anagrafe.IDCOMUNE = this.IdComune;
            this.LogMessage("IDCOMUNE " + anagrafe.IDCOMUNE);
            //Setto CF
            anagrafe.CODICEFISCALE = dr["COD_FISCALE"].ToString().Trim().ToUpper();
            this.LogMessage("CODICEFISCALE " + anagrafe.CODICEFISCALE);
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
            this.LogMessage("FLAG_DISABILITATO " + anagrafe.FLAG_DISABILITATO);

            //Setto il cognome
            anagrafe.NOMINATIVO = dr["COGNOME"].ToString().Trim().ToUpper();
            this.LogMessage("NOMINATIVO " + anagrafe.NOMINATIVO);
            //Setto il nome
            anagrafe.NOME = dr["NOME"].ToString().Trim().ToUpper();
            this.LogMessage("NOME " + anagrafe.NOME);
            //Setto il sesso
            anagrafe.SESSO = dr["SESSO"].ToString().Trim().ToUpper();
            this.LogMessage("SESSO " + anagrafe.SESSO);
            //Setto la data di nascita
            anagrafe.DATANASCITA = string.IsNullOrEmpty(dr["DATA_NASCITA"].ToString().Trim()) ? (DateTime?)null : (DateTime)dr["DATA_NASCITA"];
            this.LogMessage("DATANASCITA " + anagrafe.DATANASCITA);
            //Setto il codice del comune di nascita
            if (!string.IsNullOrEmpty(dr["COD_COMUNE_NASC"].ToString().Trim()))
            {
                var pComuniMgr = new ComuniMgr(this.SigeproDb);
                var pComuni = new Comuni();
                var codiceIstat = dr["COD_COMUNE_NASC"].ToString().Trim();
                pComuni.CODICEISTAT = codiceIstat.PadLeft(6, '0');
                pComuni = pComuniMgr.GetByClass(pComuni);
                if (pComuni != null)
                {
                    anagrafe.CODCOMNASCITA = pComuni.CODICECOMUNE;
                    this.LogMessage("CODCOMNASCITA " + anagrafe.CODCOMNASCITA);
                }
                else
                {
                    //Uso il COD_COMUNE_NASC come codice stato estero
                    pComuni = new Comuni();
                    var codiceStatoEstero = dr["COD_COMUNE_NASC"].ToString().Trim();
                    pComuni.CODICESTATOESTERO = codiceStatoEstero.TrimStart(new char[] { '0' });
                    pComuni = pComuniMgr.GetByClass(pComuni);
                    if (pComuni != null)
                    {
                        anagrafe.CODCOMNASCITA = pComuni.CODICECOMUNE;
                        this.LogMessage("CODCOMNASCITA " + anagrafe.CODCOMNASCITA);
                    }
                }
            }

            switch (dr["POSIZIONE_ANAG"].ToString().Trim().ToUpper())
            {
                case "EMIG":
                case "AIRE":
                case "RESI":
                    //Setto l'indirizzo 
                    this.SetIndirizzo(dr, anagrafe, dr["POSIZIONE_ANAG"].ToString().Trim().ToUpper());
                    break;
                case "DECE":
                case "ERR?":
                case "IRRE":
                    if (string.IsNullOrEmpty(dr["DATA_EMIGR"].ToString().Trim()))
                    {
                        this.SetIndirizzo(dr, anagrafe, "RESI");
                    }
                    else
                    {
                        if (!string.IsNullOrEmpty(dr["DATA_IMMIGR"].ToString().Trim()))
                        {
                            if (((DateTime)dr["DATA_IMMIGR"]).CompareTo((DateTime)dr["DATA_EMIGR"]) >= 0)
                                this.SetIndirizzo(dr, anagrafe, "RESI");
                            else
                                this.SetIndirizzo(dr, anagrafe, "EMIG");
                        }
                        else
                            this.SetIndirizzo(dr, anagrafe, "EMIG");
                    }
                    break;
                default:
                    throw new Exception("Il valore riportato dal campo POSIZIONE_ANAG non rientra nei casi gestiti: " + dr["POSIZIONE_ANAG"].ToString());
            }

            return anagrafe;
        }

        private void SetIndirizzo(DataRow dr, Anagrafe anagrafe, string posAnag)
        {
            var pComuniMgr = new ComuniMgr(this.SigeproDb);
            var pComune = new Comuni();

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
                    this.LogMessage("INDIRIZZO " + anagrafe.INDIRIZZO);
                    anagrafe.CAP = dr["CAP"].ToString().Trim();
                    this.LogMessage("CAP " + anagrafe.CAP);
                    if (!string.IsNullOrEmpty(dr["COD_COMUNE_RESID"].ToString().Trim()))
                    {
                        var codiceStatoEstero = dr["COD_COMUNE_RESID"].ToString().Trim();
                        pComune.CODICESTATOESTERO = codiceStatoEstero.TrimStart(new char[] { '0' });
                        pComune = pComuniMgr.GetByClass(pComune);
                        if (pComune != null)
                        {
                            anagrafe.COMUNERESIDENZA = pComune.CODICECOMUNE;
                            this.LogMessage("COMUNERESIDENZA " + anagrafe.COMUNERESIDENZA);
                            //Setto la provincia
                            anagrafe.PROVINCIA = pComune.SIGLAPROVINCIA;
                            this.LogMessage("PROVINCIA " + anagrafe.PROVINCIA);
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
                    this.LogMessage("INDIRIZZO " + anagrafe.INDIRIZZO);
                    anagrafe.CAP = dr["CAP"].ToString().Trim();
                    this.LogMessage("CAP " + anagrafe.CAP);
                    //Setto il codice del comune di residenza
                    pComune = pComuniMgr.GetByComune($"0{dr["CAP"].ToString()}");
                    if (pComune != null)
                    {
                        anagrafe.COMUNERESIDENZA = pComune.CODICECOMUNE;
                        this.LogMessage("COMUNERESIDENZA " + anagrafe.COMUNERESIDENZA);
                        //Setto la provincia
                        anagrafe.PROVINCIA = pComune.SIGLAPROVINCIA;
                        this.LogMessage("PROVINCIA " + anagrafe.PROVINCIA);
                    }
                    break;
                case "EMIG":
                    //Non ho informazioni aggiornate
                    break;
            }
        }


    }
}
