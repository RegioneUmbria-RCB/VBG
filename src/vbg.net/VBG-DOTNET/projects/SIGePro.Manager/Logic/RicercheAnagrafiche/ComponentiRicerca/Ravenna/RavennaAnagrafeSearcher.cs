using Init.SIGePro.Data;
using PersonalLib2.Data;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Data;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.ComponentiRicerca.Ravenna
{
    public class RavennaAnagrafeSearcher : AnagrafeSearcherBase
    {
        private static class Constants
        {
            public const string OwnerTabelle = "OWNER";
            public const string NomeVista = "VIEW";
        }

        public RavennaAnagrafeSearcher(IVerticalizzazioniFactory verticalizzazioniFactory) : base(verticalizzazioniFactory, "RAVENNA")
        {
        }


        private string OwnerTabelle
        {
            get
            {
                if (!this.Configuration.ContainsKey(Constants.OwnerTabelle))
                    return String.Empty;

                return this.Configuration[Constants.OwnerTabelle];
            }
        }

        private string NomeVista
        {
            get
            {
                if (!this.Configuration.ContainsKey(Constants.NomeVista))
                    return String.Empty;

                return this.Configuration[Constants.NomeVista];
            }
        }

        private ProviderType ConnectionProvider
        {
            get
            {
                return (ProviderType)Enum.Parse(typeof(ProviderType), this.Configuration["PROVIDER"], true);
            }
        }

        private string ConnectionString
        {
            get
            {
                return this.Configuration["CONNECTIONSTRING"];
            }
        }




        public override Anagrafe ByCodiceFiscaleImp(string codiceFiscale)
        {
            this.LogMessage("Il codice fiscale è " + codiceFiscale);
            using (var db = this.CreateDatabase())
            {


                Anagrafe anagrafe = null;

                DataSet ds = new DataSet();
                string sql;
                string table = (string.IsNullOrEmpty(this.OwnerTabelle) ? this.NomeVista : this.OwnerTabelle + "." + this.NomeVista);

                sql = "SELECT * " +
                      "FROM " +
                              table +
                            //"vwAnagrafePerSIGePro " +
                            " WHERE " +
                               "scodicefiscale = {0}";


                sql = String.Format(sql, db.Specifics.QueryParameterName("scodicefiscale"));


                using (var cmd = db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(db.CreateParameter("scodicefiscale", codiceFiscale));

                    IDataAdapter da = db.CreateDataAdapter(cmd);
                    da.Fill(ds);
                }

                if (ds.Tables[0].Rows.Count == 0)
                    return new Anagrafe();
                else
                {
                    if (ds.Tables[0].Rows.Count == 1)
                        anagrafe = this.GetAnagrafe(ds.Tables[0].Rows[0]);
                    else
                        throw new Exception("Per il CF " + codiceFiscale + " il metodo ha restituito " + ds.Tables[0].Rows.Count + " record");
                }


                return anagrafe;
            }
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
                return this.ByPartitaIvaImp(codiceFiscale);
            }
        }

        public override Anagrafe ByPartitaIvaImp(string partitaIva)
        {
            return null;
        }

        public override List<Anagrafe> ByNomeCognomeImp(string nome, string cognome)
        {
            using (var db = this.CreateDatabase())
            {

                List<Anagrafe> list = new List<Anagrafe>();

                DataSet ds = new DataSet();
                string sql;
                string table = (string.IsNullOrEmpty(this.OwnerTabelle) ? this.NomeVista : this.OwnerTabelle + "." + this.NomeVista);

                sql = "SELECT * " +
                      "FROM " +
                              table +
                            //"vwAnagrafePerSIGePro " +
                            " WHERE " +
                               "snome = {0} AND " +
                               "scognome = {1}";


                sql = String.Format(sql, db.Specifics.QueryParameterName("snome"), db.Specifics.QueryParameterName("scognome"));

                using (var cmd = db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(db.CreateParameter("snome", nome));
                    cmd.Parameters.Add(db.CreateParameter("scognome", cognome));

                    IDataAdapter da = db.CreateDataAdapter(cmd);
                    da.Fill(ds);
                }

                foreach (DataRow dr in ds.Tables[0].Rows)
                    list.Add(this.GetAnagrafe(dr));

                return list;
            }
        }

        private Anagrafe GetAnagrafe(DataRow dr)
        {
            Anagrafe anagrafe = new Anagrafe();

            //Setto idcomune
            anagrafe.IDCOMUNE = this.IdComune;
            this.LogMessage("IDCOMUNE " + anagrafe.IDCOMUNE);
            //Setto CF
            anagrafe.CODICEFISCALE = dr["scodicefiscale"].ToString().Trim().ToUpper();
            this.LogMessage("CODICEFISCALE " + anagrafe.CODICEFISCALE);
            //Setto il flag disabilitato
            if (dr["sinvita"].ToString().Trim().ToUpper() == "S")
                anagrafe.FLAG_DISABILITATO = "0";
            else
                anagrafe.FLAG_DISABILITATO = "1";
            this.LogMessage("FLAG_DISABILITATO " + anagrafe.FLAG_DISABILITATO);

            //Setto il cognome
            anagrafe.NOMINATIVO = dr["scognome"].ToString().Trim().ToUpper();
            this.LogMessage("NOMINATIVO " + anagrafe.NOMINATIVO);
            //Setto il nome
            anagrafe.NOME = dr["snome"].ToString().Trim().ToUpper();
            this.LogMessage("NOME " + anagrafe.NOME);
            //Setto il sesso
            anagrafe.SESSO = dr["ssesso"].ToString().Trim().ToUpper();
            this.LogMessage("SESSO " + anagrafe.SESSO);
            //Setto la data di nascita
            anagrafe.DATANASCITA = string.IsNullOrEmpty(dr["dtdatanascita"].ToString().Trim()) ? (DateTime?)null : (DateTime)dr["dtdatanascita"];
            this.LogMessage("DATANASCITA " + anagrafe.DATANASCITA);
            //Setto il codice del comune di nascita
            if (!string.IsNullOrEmpty(dr["scodiceistat"].ToString().Trim()))
            {
                ComuniMgr pComuniMgr = new ComuniMgr(this.SigeproDb);
                Comuni pComuni = new Comuni();
                pComuni.CODICEISTAT = dr["scodiceistat"].ToString().Trim().ToUpper();
                pComuni = pComuniMgr.GetByClass(pComuni);
                if (pComuni != null)
                {
                    anagrafe.CODCOMNASCITA = pComuni.CODICECOMUNE;
                    this.LogMessage("CODCOMNASCITA " + anagrafe.CODCOMNASCITA);
                }
            }
            if (dr["sresidente"].ToString().Trim().ToUpper() == "S")
            {
                //Setto l'indirizzo
                anagrafe.INDIRIZZO = dr["lprefisso"].ToString().Trim().ToUpper();
                if (!string.IsNullOrEmpty(dr["ldescrizione"].ToString().Trim()))
                    anagrafe.INDIRIZZO += " " + dr["ldescrizione"].ToString().Trim().ToUpper();
                if (!string.IsNullOrEmpty(dr["icivico"].ToString().Trim()))
                    anagrafe.INDIRIZZO += " " + dr["icivico"].ToString().Trim().ToUpper();
                if (!string.IsNullOrEmpty(dr["sbarrato"].ToString().Trim()))
                    anagrafe.INDIRIZZO += "/" + dr["sbarrato"].ToString().Trim().ToUpper();
                this.LogMessage("INDIRIZZO " + anagrafe.INDIRIZZO);
                //Setto il cap
                anagrafe.CAP = dr["scap"].ToString().Trim();
                this.LogMessage("CAP " + anagrafe.CAP);
                //Setto la provincia
                anagrafe.PROVINCIA = dr["sprovincia"].ToString();
                this.LogMessage("PROVINCIA " + anagrafe.PROVINCIA);
                //Setto il codice del comune di residenza
                Comuni pComune = null;
                ComuniMgr pComuniMgr = new ComuniMgr(this.SigeproDb);
                pComune = pComuniMgr.GetByComune("RAVENNA");
                if (pComune != null)
                {
                    anagrafe.COMUNERESIDENZA = pComune.CODICECOMUNE;
                    this.LogMessage("COMUNERESIDENZA " + anagrafe.COMUNERESIDENZA);
                }
            }
            //Setto la cittadinanza
            if (dr["scodiceistatstatocittadinanza"].ToString().Trim().ToUpper() == "I")
            {
                Cittadinanza citt = new Cittadinanza();
                CittadinanzaMgr pCittMgr = new CittadinanzaMgr(this.SigeproDb);
                citt.Descrizione = "ITALIA";
                citt = pCittMgr.GetByClass(citt);
                if (citt != null)
                {
                    anagrafe.CODICECITTADINANZA = citt.Codice.ToString();
                    this.LogMessage("CITTADINANZA " + anagrafe.CODICECITTADINANZA);
                }
            }

            return anagrafe;
        }



        protected DataBase CreateDatabase()
        {
            return new DataBase(this.ConnectionString, this.ConnectionProvider);
        }
    }
}
