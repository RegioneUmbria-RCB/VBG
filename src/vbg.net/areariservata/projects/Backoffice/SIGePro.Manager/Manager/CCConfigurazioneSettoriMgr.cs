using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using System.ComponentModel;
using Init.SIGePro.Authentication;
using System.Data;
using Init.SIGePro.Utils;
using Init.SIGePro.Exceptions;

namespace Init.SIGePro.Manager
{
	[DataObject(true)]
	public partial class CCConfigurazioneSettoriMgr
	{
		[DataObjectMethod( DataObjectMethodType.Select )]
		public static List<CCConfigurazioneSettori> Find(string token, string software)
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);
			
			CCConfigurazioneSettoriMgr mgr = new CCConfigurazioneSettoriMgr( authInfo.CreateDatabase() );

			CCConfigurazioneSettori filtro = new CCConfigurazioneSettori();
			filtro.Idcomune = authInfo.IdComune;
			filtro.Software = software;

			return mgr.GetList(filtro);
		}


		public DataTable GetAttivitaPerSettore(string idComune, int idCalcoloTot, string idSettore)
		{
            //TODO: query
			string sql = @"Select distinct 
								ATTIVITA.CODICEISTAT as Id, 
								ATTIVITA.ISTAT as Descrizione
							From
								CC_COEFFCONTRIB_ATTIVITA, 
								CC_CONDIZIONI_ATTIVITA, 
								ATTIVITA, 
								VW_CCICT_DESTINAZIONI, 
								CC_ICALCOLOTOT
							Where
								VW_CCICT_DESTINAZIONI.IDCOMUNE = CC_ICALCOLOTOT.IDCOMUNE and
								VW_CCICT_DESTINAZIONI.CCICT_ID = CC_ICALCOLOTOT.ID and
								CC_COEFFCONTRIB_ATTIVITA.IDCOMUNE = CC_ICALCOLOTOT.IDCOMUNE and
								CC_COEFFCONTRIB_ATTIVITA.FK_CCDE_ID = VW_CCICT_DESTINAZIONI.CCDE_ID and
								CC_COEFFCONTRIB_ATTIVITA.FK_CCVC_ID = CC_ICALCOLOTOT.FK_CCVC_ID and
								CC_CONDIZIONI_ATTIVITA.IDCOMUNE = CC_COEFFCONTRIB_ATTIVITA.IDCOMUNE and
								CC_CONDIZIONI_ATTIVITA.ID = CC_COEFFCONTRIB_ATTIVITA.FK_CCCA_ID and
								ATTIVITA.IDCOMUNE = CC_CONDIZIONI_ATTIVITA.IDCOMUNE and
								ATTIVITA.CODICEISTAT = CC_CONDIZIONI_ATTIVITA.FK_AT_CODICEISTAT and
								CC_ICALCOLOTOT.IDCOMUNE = {0} and
								CC_ICALCOLOTOT.ID = {1} and
								ATTIVITA.CODICESETTORE = {2} order by ATTIVITA.ISTAT asc";

			sql = String.Format(sql, db.Specifics.QueryParameterName("IDCOMUNE"),
										db.Specifics.QueryParameterName("IDCALCOLOTOT"),
										db.Specifics.QueryParameterName("IDSETTORE"));

			using (IDbCommand cmd = db.CreateCommand(sql))
			{
				cmd.Parameters.Add(db.CreateParameter("IDCOMUNE", idComune));
				cmd.Parameters.Add(db.CreateParameter("IDCALCOLOTOT", idCalcoloTot));
				cmd.Parameters.Add(db.CreateParameter("IDSETTORE", idSettore));

				IDataAdapter adp = db.CreateDataAdapter(cmd);

				DataSet dsRet = new DataSet();

				adp.Fill(dsRet);

				return dsRet.Tables[0];
			}
		}


		public double GetCoefficienteDContributoAttiv(string idComune, int idCalcoloTot,  string idAttivita)
		{
            //TODO: query
			string sql = @"Select 
								CC_COEFFCONTRIB_ATTIVITA.COEFFICIENTE
							From
								CC_COEFFCONTRIB_ATTIVITA, 
								CC_CONDIZIONI_ATTIVITA, 
								ATTIVITA, 
								VW_CCICT_DESTINAZIONI, 
								CC_ICALCOLOTOT
							Where
								VW_CCICT_DESTINAZIONI.IDCOMUNE = CC_ICALCOLOTOT.IDCOMUNE and
								VW_CCICT_DESTINAZIONI.CCICT_ID = CC_ICALCOLOTOT.ID and
								CC_COEFFCONTRIB_ATTIVITA.IDCOMUNE = CC_ICALCOLOTOT.IDCOMUNE and
								CC_COEFFCONTRIB_ATTIVITA.FK_CCDE_ID = VW_CCICT_DESTINAZIONI.CCDE_ID and
								CC_COEFFCONTRIB_ATTIVITA.FK_CCVC_ID = CC_ICALCOLOTOT.FK_CCVC_ID and
								CC_CONDIZIONI_ATTIVITA.IDCOMUNE = CC_COEFFCONTRIB_ATTIVITA.IDCOMUNE and
								CC_CONDIZIONI_ATTIVITA.ID = CC_COEFFCONTRIB_ATTIVITA.FK_CCCA_ID and
								ATTIVITA.IDCOMUNE = CC_CONDIZIONI_ATTIVITA.IDCOMUNE and
								ATTIVITA.CODICEISTAT = CC_CONDIZIONI_ATTIVITA.FK_AT_CODICEISTAT and
								CC_ICALCOLOTOT.IDCOMUNE = {0} and
								CC_ICALCOLOTOT.ID = {1} and
								ATTIVITA.CODICEISTAT = {2}";

			sql = String.Format(sql, db.Specifics.QueryParameterName("IDCOMUNE"),
										db.Specifics.QueryParameterName("IDCALCOLOTOT"),
										db.Specifics.QueryParameterName("IDATTIVITA"));

			bool closecnn = false;

			if (db.Connection.State == ConnectionState.Closed)
			{
				db.Connection.Open();
				closecnn = true;
			}

			try
			{
				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("IDCOMUNE", idComune));
					cmd.Parameters.Add(db.CreateParameter("IDCALCOLOTOT", idCalcoloTot));
					cmd.Parameters.Add(db.CreateParameter("IDATTIVITA", idAttivita));

					double? valore = null;

					using (IDataReader rd = cmd.ExecuteReader())
					{
						while (rd.Read())
						{
							// In teoria si dovrebbe leggere un solo valore
							if ( valore.HasValue )
							{
								string errmsg = "CCConfigurazioneSettoriMgr::GetCoefficienteDContributoAttiv ha restituito più di una riga. IDCOMUNE={0}, IDCALCOLOTOT={1},  IDATTIVITA={3}";
								Logger.LogEvent(db, idComune, "Calcolo oneri", String.Format(errmsg, idComune, idCalcoloTot,  idAttivita), "ERRORE");

								return valore.GetValueOrDefault(0.0d);
							}
							else
							{
								valore = Convert.ToDouble(rd[0]);
							}
						}
					}

					return valore.GetValueOrDefault( 0.0d );
				}
			}
			finally
			{
				if (closecnn)
					db.Connection.Close();
			}
		}

        private void VerificaRecordCollegati(CCConfigurazioneSettori cls)
        {
            if (recordCount("CC_CONDIZIONI_ATTIVITA, ATTIVITA", "ATTIVITA.CODICESETTORE", "WHERE CC_CONDIZIONI_ATTIVITA.IDCOMUNE = ATTIVITA.IDCOMUNE AND CC_CONDIZIONI_ATTIVITA.FK_AT_CODICEISTAT = ATTIVITA.CODICEISTAT AND ATTIVITA.IDCOMUNE = '" + cls.Idcomune + "' AND ATTIVITA.CODICESETTORE = '" + cls.FkSeCodicesettore + "'") > 0)
                throw new ReferentialIntegrityException("CC_CONDIZIONI_ATTIVITA");
        }
	}
}
