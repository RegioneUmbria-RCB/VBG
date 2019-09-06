using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using System.Data;
using PersonalLib2.Sql.Collections;

namespace Init.SIGePro.Manager
{
	public partial class CCICalcoloDContribAttivMgr
	{
		public void DeleteByIdTContributo(string idComune, int idTContributo)
		{
			CCICalcoloDContribAttiv filtro = new CCICalcoloDContribAttiv();
			filtro.Idcomune = idComune;
			filtro.FkCcictcId = idTContributo;

			List<CCICalcoloDContribAttiv> lst = GetList(filtro);

			foreach (CCICalcoloDContribAttiv dca in lst)
				Delete(dca);
		}

		public CCICalcoloDContribAttiv GetByIdTContributoIdAttivita(string idComune, int idTContributo,  int idAttivita )
		{
			CCICalcoloDContribAttiv filtro = new CCICalcoloDContribAttiv();
			filtro.Idcomune = idComune;
			filtro.FkCcictcId = idTContributo;
			filtro.FkCcccaId = idAttivita;

			return (CCICalcoloDContribAttiv)db.GetClass(filtro);
		}

		public CCICalcoloDContribAttiv GetByIdSettore(string idComune, int idTContributo, string codiceSettore)
		{
			string sql = @"SELECT 
							  CC_ICALCOLO_DCONTRIBATTIV.* 
							FROM
							  CC_ICALCOLO_DCONTRIBATTIV,
							  CC_CONDIZIONI_ATTIVITA,
							  attivita
							WHERE
							  CC_ICALCOLO_DCONTRIBATTIV.IDCOMUNE    = CC_CONDIZIONI_ATTIVITA.IDCOMUNE AND
							  CC_ICALCOLO_DCONTRIBATTIV.FK_CCCCA_ID = CC_CONDIZIONI_ATTIVITA.ID AND
							  CC_CONDIZIONI_ATTIVITA.IDCOMUNE       = ATTIVITA.IDCOMUNE AND
							  CC_CONDIZIONI_ATTIVITA.FK_AT_CODICEISTAT = ATTIVITA.CODICEISTAT and
							  attivita.idcomune = {0} AND
							  attivita.codicesettore = {1} AND
							  CC_ICALCOLO_DCONTRIBATTIV.fk_ccictc_id = {2}";

			sql = String.Format( sql , db.Specifics.QueryParameterName( "IdComune" ) ,
										db.Specifics.QueryParameterName( "CodiceSettore" ) ,
										db.Specifics.QueryParameterName( "IdTContributo" ) );


			bool closeCnn = false;

			if (db.Connection.State == ConnectionState.Closed)
			{
				closeCnn = true;
				
				db.Connection.Open();
			}

			try
			{
				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("IdComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("CodiceSettore", codiceSettore));
					cmd.Parameters.Add(db.CreateParameter("IdTContributo", idTContributo));

					DataClassCollection coll = db.GetClassList(cmd, new CCICalcoloDContribAttiv(), true, true);

					if (coll.Count == 0) return null;

					return (CCICalcoloDContribAttiv)coll[0];
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
