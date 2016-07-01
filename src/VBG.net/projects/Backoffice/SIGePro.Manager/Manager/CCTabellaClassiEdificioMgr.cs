using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using System.Data;
using System.ComponentModel;
using Init.SIGePro.Authentication;
using Init.Utils.Sorting;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class CCTabellaClassiEdificioMgr
    {
        private void VerificaRecordCollegati(CCTabellaClassiEdificio cls)
        {
            if (recordCount("CC_ICALCOLI", "FK_CCTCE_ID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_CCTCE_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("CC_ICALCOLI");
        }

		public CCTabellaClassiEdificio GetClasseEdificio(string idComune, string software, double percentuale)
		{
            //TODO: query
			string sql = @"SELECT
								Id
							FROM 
								cc_tabella_classiedificio
							WHERE
								cc_tabella_classiedificio.IdComune = {0} AND
								cc_tabella_classiedificio.Software = {1} AND
								cc_tabella_classiedificio.Da < {2} AND
								cc_tabella_classiedificio.A >= {3} ";

			sql = String.Format(sql, db.Specifics.QueryParameterName("IDCOMUNE"),
							db.Specifics.QueryParameterName("SOFTWARE"),
							db.Specifics.QueryParameterName("PERCENTUALE"),
							db.Specifics.QueryParameterName("PERCENTUALE1"));

			bool closeCnn = db.Connection.State == ConnectionState.Closed;

			try
			{
				using (IDbCommand cmd = db.CreateCommand())
				{
					if (closeCnn)
						db.Connection.Open();

					cmd.CommandText = sql;

					IDbDataParameter parIdComune = cmd.CreateParameter();
					parIdComune.ParameterName = db.Specifics.ParameterName("IDCOMUNE");
					parIdComune.Value = idComune;
					cmd.Parameters.Add(parIdComune);

					IDbDataParameter parSoftware = cmd.CreateParameter();
					parSoftware.ParameterName = db.Specifics.ParameterName("SOFTWARE");
					parSoftware.Value = software;
					cmd.Parameters.Add(parSoftware);

					IDbDataParameter parPercentuale = cmd.CreateParameter();
					parPercentuale.ParameterName = db.Specifics.ParameterName("PERCENTUALE");
					parPercentuale.Value = percentuale;
					cmd.Parameters.Add(parPercentuale);

					IDbDataParameter parPercentuale1 = cmd.CreateParameter();
					parPercentuale1.ParameterName = db.Specifics.ParameterName("PERCENTUALE1");
					parPercentuale1.Value = percentuale;
					cmd.Parameters.Add(parPercentuale1);

					object ret = cmd.ExecuteScalar();

					if (ret == null || ret == DBNull.Value)
						throw new InvalidOperationException("Non è stato possibile determinare la classe dificio per la percentuale " + percentuale.ToString() );

					return GetById(idComune, Convert.ToInt32(ret));
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}
		}

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<CCTabellaClassiEdificio> Find(string token, int? id, string descrizione, string software, string sortExpression)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            CCTabellaClassiEdificio filtro = new CCTabellaClassiEdificio();
            CCTabellaClassiEdificio filtroCompare = new CCTabellaClassiEdificio();

            filtro.Idcomune = authInfo.IdComune;
            filtro.Descrizione = descrizione;
            filtro.Software = software;
			filtro.Id = id;

            filtroCompare.Descrizione = "LIKE";

            List<CCTabellaClassiEdificio> list = authInfo.CreateDatabase().GetClassList(filtro, filtroCompare, false, true).ToList<CCTabellaClassiEdificio>();

            ListSortManager<CCTabellaClassiEdificio>.Sort(list, sortExpression);

            return list;

        }
    }
}
