
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using System.Data;
using System.ComponentModel;
using Init.SIGePro.Authentication;

using PersonalLib2.Sql;
using Init.Utils.Sorting;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class StiliFrontOfficeMgr
    {
		/// <summary>
		/// Ottiene il codice oggetto associato ad una risorsa del frontoffice.
		/// Restituisce null se
		/// a) la risorsa non esiste
		/// b) il codice oggetto non è valorizzato
		/// </summary>
		/// <param name="idComune"></param>
		/// <param name="idRisorsa"></param>
		/// <returns></returns>
		public int? GetCodiceOggettoRisorsaFrontoffice(string idComune, string idRisorsa)
		{
			string sql = "select " + idRisorsa + " from stilifrontoffice where idcomune = {0}";

			sql = String.Format( sql  , db.Specifics.QueryParameterName( "idComune" ) );

			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));

					object objCodOggetto = cmd.ExecuteScalar();

					if (objCodOggetto == null)
						return null;

					return Convert.ToInt32(objCodOggetto);
				}
			}
			catch (Exception ex)
			{
				return null;
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}

		}
	}
}
				