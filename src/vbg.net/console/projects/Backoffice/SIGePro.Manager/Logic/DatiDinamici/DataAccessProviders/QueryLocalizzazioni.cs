using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.GestioneLocalizzazioni;
using Init.SIGePro.DatiDinamici.GestioneLocalizzazioni.StringaFormattazioneIndirizzi;
using PersonalLib2.Data;
using System.Data;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.Manager;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.DataAccessProviders
{
	public class QueryLocalizzazioni : QueryLocalizzazioniBase
	{
		DataBase _db;
		int _codiceIstanza;
		string _idComune;

		public QueryLocalizzazioni(DataBase db, int codiceIstanza, string idComune)
		{
			this._db = db;
			this._codiceIstanza = codiceIstanza;
			this._idComune = idComune;
		}

		public override IEnumerable<LocalizzazioneIstanza> GetLocalizzazioni(string tipoLocalizzazione)
		{

			bool closeCnn = false;

			try
			{
				if (this._db.Connection.State == ConnectionState.Closed)
				{
					this._db.Connection.Open();
					closeCnn = true;
				}

				var sql = @"SELECT 
								istanzestradario.*,
								stradario.prefisso, 
								stradario.descrizione as NOMEVIA
							FROM
								istanzestradario 
									left join stradario ON
										istanzestradario.idcomune = stradario.idcomune AND
										istanzestradario.codicestradario = stradario.codicestradario
									left OUTER JOIN tipi_localizzazioni on
										istanzestradario.idcomune = tipi_localizzazioni.idcomune AND
										istanzestradario.tipolocalizzazione_id = tipi_localizzazioni.id
							WHERE
								istanzestradario.idcomune = {0} AND
								istanzestradario.codiceistanza = {1}";

				sql = String.Format(sql, this._db.Specifics.QueryParameterName("idcomune"), this._db.Specifics.QueryParameterName("codiceIstanza"));

				if (String.IsNullOrEmpty(tipoLocalizzazione))
					sql += " AND (tipi_localizzazioni.descrizione = '' OR tipi_localizzazioni.descrizione IS NULL)";
				else
					sql += " and tipi_localizzazioni.descrizione = " + this._db.Specifics.QueryParameterName("tipoLocalizzazione");

				using (var cmd = this._db.CreateCommand(sql))
				{
					cmd.Parameters.Add(this._db.CreateParameter("idcomune", this._idComune));
					cmd.Parameters.Add(this._db.CreateParameter("codiceIstanza", this._codiceIstanza));

					if (!String.IsNullOrEmpty(tipoLocalizzazione))
						cmd.Parameters.Add(this._db.CreateParameter("tipoLocalizzazione", tipoLocalizzazione));
					
					using(var dr = cmd.ExecuteReader())
					{
						var rVal = new List<LocalizzazioneIstanza>();

						while(dr.Read())
						{
							var id = Convert.ToInt32(dr["id"]);

							var localizzazione = new LocalizzazioneIstanza
							{
								Civico = dr["CIVICO"].ToString(),
								Esponente = dr["ESPONENTE"].ToString(),
								EsponenteInterno = dr["ESPONENTEINTERNO"].ToString(),
								Indirizzo = dr["PREFISSO"].ToString() + " " + dr["NOMEVIA"].ToString(),
								Interno = dr["INTERNO"].ToString(),
								Km = dr["Km"].ToString(),
								Note = dr["NOTE"].ToString(),
								Piano = dr["Piano"].ToString(),
								Scala = dr["SCALA"].ToString(),
								Mappali = MappaliDaIdStradario( id ),
								TipoLocalizzazione = dr["tipolocalizzazione_id"].ToString(),
								Uuid = dr["UUID"].ToString(),
								Coordinate = String.IsNullOrEmpty(dr["LONGITUDINE"].ToString()) ? null : new LocalizzazioneIstanza.Coordinata( dr["LONGITUDINE"].ToString() , dr["LATITUDINE"].ToString()),
							};

							rVal.Add( localizzazione );
						}

						return rVal;
					}
				}
			}
			finally
			{
				if (closeCnn)
					this._db.Connection.Close();
			}

		}

		private LocalizzazioneIstanza.RiferimentiCatastali MappaliDaIdStradario(int idStradario)
		{
			using (var db = new DataBase(this._db.ConnectionDetails.ConnectionString, this._db.ConnectionDetails.ProviderType))
			{
				var mappali = new IstanzeMappaliMgr(db).GetPrimarioByIdStradario(this._idComune, idStradario);

				if (mappali == null)
					return null;

				return new LocalizzazioneIstanza.RiferimentiCatastali
				{
					TipoCatasto = mappali.Codicecatasto,
					Foglio = mappali.Foglio,
					Particella = mappali.Particella,
					Sub = mappali.Sub
				};
			}
		}
	}
}
