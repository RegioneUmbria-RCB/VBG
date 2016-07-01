using System;
using System.Linq;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using System.ComponentModel;
using Init.SIGePro.Manager.DTO.Configurazione;
using System.Data;
using Init.SIGePro.Verticalizzazioni;
using System.Collections.Generic;
using PersonalLib2.Sql;

namespace Init.SIGePro.Manager 
 { 	///<summary>
	/// Descrizione di riepilogo per ConfigurazioneMgr.\n	/// </summary>
	public class ConfigurazioneMgr: BaseManager
{

		public ConfigurazioneMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB

		public Configurazione GetById(String idComune, String software)
		{
			var retVal = new Configurazione{IDCOMUNE = idComune, SOFTWARE = software};

			var mydc = db.GetClassList(retVal,true,false);

			if (mydc.Count!=0)
				return (mydc[0]) as Configurazione;
			
			return null;
		}
		
		public Configurazione GetByIdComuneESoftwareSovrascrivendoTT(string idComune, string software)
		{
			// Leggo prima la configurazione di TT
			var configTT = (Configurazione)db.GetClass(new Configurazione{IDCOMUNE = idComune, SOFTWARE = "TT", UseForeign = useForeignEnum.Yes });

			if (software == "TT")
				return configTT;

			var configSW = (Configurazione)db.GetClass(new Configurazione { IDCOMUNE = idComune, SOFTWARE = software, UseForeign = useForeignEnum.Yes });

			if(configSW == null)
				return configTT;

			var props = TypeDescriptor.GetProperties(configSW);

			for (int i = 0; i < props.Count; i++)
			{
				object val = props[i].GetValue(configSW);

				if (val == null || String.IsNullOrEmpty(val.ToString()))
				{
					object valTT = props[i].GetValue( configTT );
					props[i].SetValue( configSW , valTT);
				}
			}

			return configSW;

		}
 
		[Obsolete]
		public Configurazione Update( Configurazione p_class )
		{		
			int rowCount = db.Update( p_class );

			return p_class;
		}

		#endregion


		public ConfigurazioneContenutiDto GetConfigurazioneContenutiFrontoffice(string idComune, string idComuneAlias, string software)
		{
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}
				// Leggo i dati relativi a TT
				var ttScrittaRegione = String.Empty;
				int? ttCodiceOggettoLogo = null;
				var ttDenominazione = String.Empty;

				string sqlvaloriTT = @"select 
										scrittaregione,
										codiceoggetto4,
										denominazione
									FROM 
										configurazione 
									WHERE 
										idcomune = {0} AND
										software = 'TT'";

				sqlvaloriTT = PreparaQueryParametrica(sqlvaloriTT, "idComune");

				using (IDbCommand cmd = db.CreateCommand(sqlvaloriTT))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));

					using (var dr = cmd.ExecuteReader())
					{
						if (dr.Read())
						{
							ttScrittaRegione = dr["scrittaregione"].ToString();
							ttDenominazione = dr["denominazione"].ToString();
							ttCodiceOggettoLogo = String.IsNullOrEmpty(dr["codiceoggetto4"].ToString()) ? (int?)null : Convert.ToInt32(dr["codiceoggetto4"]);
						}
					}

				}

				int? swCodiceResponsabile = null;
				var swNomeResponsabile = String.Empty;
				var swTelefono = String.Empty;
				var swEmailResponsabilePec = String.Empty;
				var swEmailResponsabile = String.Empty;
				var swDenominazione = String.Empty;

				// Leggo i dati del software
				var sqlSoftware = @"select 
										codiceresponsabile,
										telefono,
										emailresponsabilepec,
										emailresponsabile,
										denominazione
									FROM 
										configurazione 
									WHERE 
										idcomune = {0} AND
										software = {1}";

				sqlSoftware = PreparaQueryParametrica(sqlSoftware, "idComune", "software");

				using (IDbCommand cmd = db.CreateCommand(sqlSoftware))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("software", software));

					using (var dr = cmd.ExecuteReader())
					{
						if (dr.Read())
						{
							swCodiceResponsabile = String.IsNullOrEmpty(dr["codiceresponsabile"].ToString()) ? (int?)null : Convert.ToInt32(dr["codiceresponsabile"]);
							swTelefono = dr["telefono"].ToString();
							swEmailResponsabilePec = dr["emailresponsabilepec"].ToString();
							swEmailResponsabile = dr["emailresponsabile"].ToString();
							swDenominazione = dr["denominazione"].ToString();
						}
					}
				}
				var codiciAccreditamento = GetCodiciAccreditamento(idComune, software);

				if (swCodiceResponsabile.HasValue)
					swNomeResponsabile = new ResponsabiliMgr(db).GetById(idComune, swCodiceResponsabile.Value).RESPONSABILE;

				var rVal = new ConfigurazioneContenutiDto
				{
					CodiceOggettoLogo = ttCodiceOggettoLogo,
					IndirizzoPec = swEmailResponsabilePec,
					IndirizzoEmail = swEmailResponsabile,
					NomeComune = ttDenominazione,
					NomeComuneSottotitolo = swDenominazione ,
					NomeRegione = ttScrittaRegione,
					ResponsabileSportello = swNomeResponsabile,
					Telefono = swTelefono,
					ListaCodiciAccreditamento = codiciAccreditamento.ToArray()
				};

                if (new VerticalizzazioneAreaRiservata(idComuneAlias, software).Attiva)
					rVal.AreaRiservataAttiva = true;

				return rVal;

			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}

		}

		private IEnumerable<CodiceAccreditamentoDto> GetCodiciAccreditamento(string idcomune, string software)
		{
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var sql = PreparaQueryParametrica(@"SELECT 
								comuni.comune AS comune,
								comuniassociatisoftware.codice_accreditamento as codiceAccreditamento
							FROM 
								comuniassociatisoftware,
								comuni
							WHERE 
								comuniassociatisoftware.codicecomune = comuni.codicecomune and

								idcomune = {0} AND
								software = {1}", "idcomune", "software");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idcomune", idcomune));
					cmd.Parameters.Add(db.CreateParameter("software", software));

					using (var dr = cmd.ExecuteReader())
					{
						var rVal = new List<CodiceAccreditamentoDto>();

						while (dr.Read())
						{
							if (dr["codiceAccreditamento"] == DBNull.Value || dr["codiceAccreditamento"].ToString().Length == 0)
								continue;

							rVal.Add(new CodiceAccreditamentoDto
							{
								NomeComune = dr["comune"].ToString(),
								CodiceAccreditamento = dr["codiceAccreditamento"].ToString()
							});
						}

						return rVal;
					}
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}

		}

	}
}