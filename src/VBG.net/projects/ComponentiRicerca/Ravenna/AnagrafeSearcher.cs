using System;
using System.Collections.Generic;
using System.Data;
using System.Globalization;
using System.IO;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche;
using PersonalLib2.Data;

namespace Ravenna
{
    public class AnagrafeSearcher : AnagrafeSearcherBase
    {
		private static class Constants
		{
			public const string OwnerTabelle = "OWNER";
			public const string NomeVista = "VIEW";
		}

        public AnagrafeSearcher(): base("RAVENNA")
        {
        }


		private string OwnerTabelle
		{
			get 
			{
				if (!Configuration.ContainsKey(Constants.OwnerTabelle))
					return String.Empty;

				return Configuration[Constants.OwnerTabelle];
			}
		}

		private string NomeVista
		{
			get
			{
				if (!Configuration.ContainsKey(Constants.NomeVista))
					return String.Empty;

				return Configuration[Constants.NomeVista];
			}
		}

		private ProviderType ConnectionProvider
		{
			get
			{
				return (ProviderType)Enum.Parse(typeof(ProviderType), Configuration["PROVIDER"], true);
			}
		}

		private string ConnectionString
		{
			get
			{
				return Configuration["CONNECTIONSTRING"];
			}
		}

		


        public override Anagrafe ByCodiceFiscaleImp(string codiceFiscale)
        {
            LogMessage("Il codice fiscale è " + codiceFiscale);
			using (DataBase db = CreateDatabase())
			{


				Anagrafe anagrafe = null;

				DataSet ds = new DataSet();
				string sql;
                string table = (string.IsNullOrEmpty(OwnerTabelle) ? NomeVista : OwnerTabelle + "." + NomeVista);

				sql = "SELECT * " +
					  "FROM " +
                              table +
							  //"vwAnagrafePerSIGePro " +
							" WHERE " +
							   "scodicefiscale = {0}";


				sql = String.Format(sql, db.Specifics.QueryParameterName("scodicefiscale"));


				using (IDbCommand cmd = db.CreateCommand(sql))
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
						anagrafe = GetAnagrafe(ds.Tables[0].Rows[0]);
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
                return ByCodiceFiscaleImp(codiceFiscale);
            }
            else
            {
                //Persona giuridica
                return ByPartitaIvaImp(codiceFiscale);
            }
        }

        public override Anagrafe ByPartitaIvaImp(string partitaIva)
        {
            return null;
        }

        public override List<Anagrafe> ByNomeCognomeImp(string nome, string cognome)
        {
			using (var db = CreateDatabase())
			{

				List<Anagrafe> list = new List<Anagrafe>();

				DataSet ds = new DataSet();
				string sql;
                string table = (string.IsNullOrEmpty(OwnerTabelle) ? NomeVista : this.OwnerTabelle + "." + this.NomeVista);

				sql = "SELECT * " +
					  "FROM " +
                              table +
							  //"vwAnagrafePerSIGePro " +
							" WHERE " +
							   "snome = {0} AND " +
							   "scognome = {1}";


				sql = String.Format(sql, db.Specifics.QueryParameterName("snome"), db.Specifics.QueryParameterName("scognome"));

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("snome", nome));
					cmd.Parameters.Add(db.CreateParameter("scognome", cognome));

					IDataAdapter da = db.CreateDataAdapter(cmd);
					da.Fill(ds);
				}

				foreach (DataRow dr in ds.Tables[0].Rows)
					list.Add(GetAnagrafe(dr));

				return list;
			}
        }

        private Anagrafe GetAnagrafe(DataRow dr)
        {
            Anagrafe anagrafe = new Anagrafe();

            //Setto idcomune
            anagrafe.IDCOMUNE = IdComune;
            LogMessage("IDCOMUNE " + anagrafe.IDCOMUNE);
            //Setto CF
            anagrafe.CODICEFISCALE = dr["scodicefiscale"].ToString().Trim().ToUpper();
            LogMessage("CODICEFISCALE " + anagrafe.CODICEFISCALE);
            //Setto il flag disabilitato
            if (dr["sinvita"].ToString().Trim().ToUpper() == "S")
                anagrafe.FLAG_DISABILITATO = "0";
            else
                anagrafe.FLAG_DISABILITATO = "1";
            LogMessage("FLAG_DISABILITATO " + anagrafe.FLAG_DISABILITATO);

            //Setto il cognome
            anagrafe.NOMINATIVO = dr["scognome"].ToString().Trim().ToUpper();
            LogMessage("NOMINATIVO " + anagrafe.NOMINATIVO);
            //Setto il nome
            anagrafe.NOME = dr["snome"].ToString().Trim().ToUpper();
            LogMessage("NOME " + anagrafe.NOME);
            //Setto il sesso
            anagrafe.SESSO = dr["ssesso"].ToString().Trim().ToUpper();
            LogMessage("SESSO " + anagrafe.SESSO);
            //Setto la data di nascita
            anagrafe.DATANASCITA = string.IsNullOrEmpty(dr["dtdatanascita"].ToString().Trim()) ? (DateTime?)null : (DateTime)dr["dtdatanascita"];
            LogMessage("DATANASCITA " + anagrafe.DATANASCITA);
            //Setto il codice del comune di nascita
            if (!string.IsNullOrEmpty(dr["scodiceistat"].ToString().Trim()))
            {
                ComuniMgr pComuniMgr = new ComuniMgr(SigeproDb);
                Comuni pComuni = new Comuni();
                pComuni.CODICEISTAT = dr["scodiceistat"].ToString().Trim().ToUpper();
                pComuni = pComuniMgr.GetByClass(pComuni);
                if (pComuni != null)
                {
                    anagrafe.CODCOMNASCITA = pComuni.CODICECOMUNE;
                    LogMessage("CODCOMNASCITA " + anagrafe.CODCOMNASCITA);
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
                LogMessage("INDIRIZZO " + anagrafe.INDIRIZZO);
                //Setto il cap
                anagrafe.CAP = dr["scap"].ToString().Trim();
                LogMessage("CAP " + anagrafe.CAP);
                //Setto la provincia
                anagrafe.PROVINCIA = dr["sprovincia"].ToString();
                LogMessage("PROVINCIA " + anagrafe.PROVINCIA);
                //Setto il codice del comune di residenza
                Comuni pComune = null;
                ComuniMgr pComuniMgr = new ComuniMgr(SigeproDb);
                pComune = pComuniMgr.GetByComune("RAVENNA");
                if (pComune != null)
                {
                    anagrafe.COMUNERESIDENZA = pComune.CODICECOMUNE;
                    LogMessage("COMUNERESIDENZA " + anagrafe.COMUNERESIDENZA);
                }
            }
            //Setto la cittadinanza
            if (dr["scodiceistatstatocittadinanza"].ToString().Trim().ToUpper() == "I")
            {
                Cittadinanza citt = new Cittadinanza();
                CittadinanzaMgr pCittMgr = new CittadinanzaMgr(SigeproDb);
                citt.Descrizione = "ITALIA";
                citt = pCittMgr.GetByClass(citt);
                if (citt != null)
                {
                    anagrafe.CODICECITTADINANZA = citt.Codice.ToString();
                    LogMessage("CITTADINANZA " + anagrafe.CODICECITTADINANZA);
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
