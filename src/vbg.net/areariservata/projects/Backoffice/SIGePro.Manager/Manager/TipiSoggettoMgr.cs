using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using System.Collections.Generic;
using System.Data;
using log4net;
using System.Linq;

namespace Init.SIGePro.Manager 
 { 	///<summary>
	/// Descrizione di riepilogo per TipiSoggettoMgr.\n	/// </summary>
	public class TipiSoggettoMgr: BaseManager
	{
		ILog _log = LogManager.GetLogger(typeof(TipiSoggettoMgr));
		string _idComune;

		public TipiSoggettoMgr( DataBase dataBase, string idComune ) : base( dataBase ) 
		{
			this._idComune = idComune;
		}

		public TipiSoggetto GetById(int codice)
		{
			TipiSoggetto retVal = new TipiSoggetto();
			retVal.CODICETIPOSOGGETTO = codice.ToString();
			retVal.IDCOMUNE = this._idComune;
		
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as TipiSoggetto;
			
			return null;
		}

		public List<TipiSoggetto> GetList(TipiSoggetto filtro)
		{
			return db.GetClassList(filtro).ToList<TipiSoggetto>();
		}

		public IEnumerable<TipiSoggetto> GetTipiSoggettoDaCodiceIntervento(string software, string tipoAnagrafe, int? codiceIntervento)
		{
			if (tipoAnagrafe != "F" && tipoAnagrafe != "G")
			{
				throw new ArgumentException("Il tipo anagrafe " + tipoAnagrafe + " non è valido", "tipoAnagrafe");
			}

			if (!codiceIntervento.HasValue)
			{
				return GetTipiSoggettoDaSoftwareETipoAnagrafe(software, tipoAnagrafe);
			}


			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var listaScCodice = new AlberoProcMgr(this.db).GetById(codiceIntervento.Value, this._idComune).GetListaScCodice().ToString();

				var sql = @"SELECT 
								tipisoggetto.* 
							FROM 
								tipisoggetto,
								alberoproc_tipisoggetto,
								alberoproc
							WHERE 
								alberoproc_tipisoggetto.idcomune = alberoproc.idcomune AND
								alberoproc_tipisoggetto.fk_scid = alberoproc.sc_id AND
								tipisoggetto.idcomune = alberoproc_tipisoggetto.idcomune AND
								tipisoggetto.codicetiposoggetto = alberoproc_tipisoggetto.fk_codicetiposoggetto AND
								alberoproc.software = {0} AND 
								alberoproc.sc_codice IN (" + listaScCodice + @") and 
								(tipisoggetto.tipoanagrafe = {1} or tipisoggetto.tipoanagrafe ='' or tipisoggetto.tipoanagrafe is null) and
								alberoproc.idComune = {2}";

				sql = PreparaQueryParametrica(sql, "software", "tipoAnagrafe", "idComune");



				using (var cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("software", software));
					cmd.Parameters.Add(db.CreateParameter("tipoAnagrafe", tipoAnagrafe));
					cmd.Parameters.Add(db.CreateParameter("idComune", _idComune));

					var list = db.GetClassList<TipiSoggetto>(cmd);

					if (list.Count > 0)
					{
						return list;
					}
                }

                // Se ci sono comunque tipi soggetto collegati all'albero (indipendentemente dal tipo anagrafe)
                // restituisco una lista vuota
                sql = $@"select 
                            count(*) 
                        from 
                            alberoproc
                                inner join alberoproc_tipisoggetto on
			                        alberoproc_tipisoggetto.idcomune = alberoproc.idcomune AND
			                        alberoproc_tipisoggetto.fk_scid = alberoproc.sc_id
                        where 
                            alberoproc.idcomune={db.Specifics.QueryParameterName("idcomune")} and 
                            alberoproc.software = {db.Specifics.QueryParameterName("software")} AND 
                            alberoproc.sc_codice IN (" + listaScCodice + @")";

                var count = db.ExecuteScalar(sql, 0, x => {
                    x.AddParameter("idcomune", _idComune);
                    x.AddParameter("software", software);
                });

                if (count>0)
                {
                    return Enumerable.Empty<TipiSoggetto>();
                }

                return GetTipiSoggettoDaSoftwareETipoAnagrafe(software, tipoAnagrafe);

			}
			catch (Exception ex)
			{
				_log.ErrorFormat("TipiSoggettoManager.GetTipiSoggettoDaCodiceIntervento: {0}", ex.ToString());

				throw;
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}
		}

		private IEnumerable<TipiSoggetto> GetTipiSoggettoDaSoftwareETipoAnagrafe(string software, string tipoAnagrafe)
		{
			var filtro = new TipiSoggetto
			{
				IDCOMUNE = this._idComune,
				SOFTWARE = software
			};

			filtro.OthersWhereClause.Add("(utilizzo ='F' or utilizzo is null or utilizzo = '')");
			filtro.OthersWhereClause.Add("(tipoanagrafe ='" + tipoAnagrafe + "' or tipoanagrafe is null or tipoanagrafe = '')");
			filtro.OrderBy = "ordine asc, tiposoggetto asc";

			return GetList(filtro);
		}
	}
}
