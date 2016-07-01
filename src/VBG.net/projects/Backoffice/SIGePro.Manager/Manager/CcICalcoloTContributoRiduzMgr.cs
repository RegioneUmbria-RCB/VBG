
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
using Init.SIGePro.Validator;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class CcICalcoloTContributoRiduzMgr
    {
		public DataSet GetImportiRiduzioni(string idComune, int idTipoCausale, int idTContributo)
		{
			DataSet dsRet = new DataSet();
			
			DataTable dtRiduzioni = new DataTable();
			dtRiduzioni.Columns.Add(new DataColumn("IdCausale", typeof(int)));
			dtRiduzioni.Columns.Add(new DataColumn("Descrizione", typeof(string)));
			dtRiduzioni.Columns.Add(new DataColumn("Selezionato", typeof(bool)));
			dtRiduzioni.Columns.Add(new DataColumn("ImportoCausale", typeof(double)));
			dtRiduzioni.Columns.Add(new DataColumn("Importo", typeof(double)));
			dtRiduzioni.Columns.Add(new DataColumn("Note", typeof(string)));

			dsRet.Tables.Add(dtRiduzioni);

			string sql = "select * from CC_CAUSALIRIDUZIONIR where idcomune = {0} and FK_CCCRT_ID = {1} order by descrizione asc";

			sql = String.Format(sql, db.Specifics.QueryParameterName("idComune"), db.Specifics.QueryParameterName("idTipoCausale"));


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
					cmd.Parameters.Add(db.CreateParameter("idTipoCausale", idTipoCausale));

					using (IDataReader dr = cmd.ExecuteReader())
					{
						while (dr.Read())
						{
							DataRow row = dtRiduzioni.NewRow();
							row["IdCausale"] = dr["id"];
							row["Descrizione"] = dr["Descrizione"];
							row["Selezionato"] = false;
							row["ImportoCausale"] = Convert.ToDouble(dr["RiduzionePerc"]);
							row["Importo"] = Convert.ToDouble(dr["RiduzionePerc"]);
							row["Note"] = "";

							dtRiduzioni.Rows.Add(row);
						}
					}
				}

				sql = @"SELECT
							riduzioneperc,
							note
						FROM
							cc_icalcoloTcontributo_riduz
						WHERE
							cc_icalcoloTcontributo_riduz.idcomune = {0} AND
							cc_icalcoloTcontributo_riduz.fk_ccictc_id = {1} AND
							cc_icalcoloTcontributo_riduz.fk_cccrr_id = {2}";

				sql = String.Format(sql, db.Specifics.QueryParameterName("idComune"),
											db.Specifics.QueryParameterName("idTContributo"),
											db.Specifics.QueryParameterName("idCausale"));

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("idTContributo", idTContributo));

					foreach (DataRow row in dtRiduzioni.Rows)
					{
						int tipoCausale = Convert.ToInt32(row["IdCausale"]);

						cmd.Parameters.Add(db.CreateParameter("idCausale", tipoCausale));

						using (IDataReader dr = cmd.ExecuteReader())
						{
							if (dr.Read())
							{
								row["Selezionato"] = true;
								row["Importo"] = Convert.ToDouble(dr["riduzioneperc"]);
								row["Note"] = dr["note"].ToString();
							}
						}

					}
				}

				return dsRet;
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}




			/*
SELECT
	riduzioneperc,
	note
FROM
	cc_icalcoloTcontributo_riduz
WHERE
	cc_icalcoloTcontributo_riduz.idcomune = :idcomune AND
	cc_icalcoloTcontributo_riduz.fk_ccictc_id = :idTContributo AND
	cc_icalcoloTcontributo_riduz.fk_cccrr_id = :idCausale
*/
		}

		public List<CcICalcoloTContributoRiduz> GetListByIdTContributo(string idComune, int idTContributo)
		{
			CcICalcoloTContributoRiduz filtro = new CcICalcoloTContributoRiduz();
			filtro.Idcomune = idComune;
			filtro.FkCcictcId = idTContributo;
			filtro.UseForeign = useForeignEnum.Yes;

			return GetList(filtro);
		}

		public double GetRiduzioneDaIdContributo(string idComune, int idTContributo)
		{
			List<CcICalcoloTContributoRiduz> riduzioni = GetListByIdTContributo(idComune, idTContributo);

			double ret = 0.0d;

			foreach (CcICalcoloTContributoRiduz r in riduzioni)
                ret += r.Riduzioneperc.GetValueOrDefault(0);

			return ret;
		}

		public void Delete(CcICalcoloTContributoRiduz cls)
		{
			VerificaRecordCollegati(cls);

			EffettuaCancellazioneACascata(cls);

			db.Delete(cls);

			OnPostDelete(cls);
			
		}

		public CcICalcoloTContributoRiduz Insert(CcICalcoloTContributoRiduz cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);

			db.Insert(cls);

			cls = (CcICalcoloTContributoRiduz)ChildDataIntegrations(cls);

			OnPostInsert(cls);

			return cls;
		}

		public CcICalcoloTContributoRiduz Update(CcICalcoloTContributoRiduz cls)
		{
			Validate(cls, AmbitoValidazione.Update);

			db.Update(cls);

			OnPostUpdate(cls);

			return cls;
		}

		private void OnPostInsert(CcICalcoloTContributoRiduz cls)
		{
			new CCICalcoloTContributoMgr(db).RicalcolaRiduzioni(cls.Idcomune, cls.FkCcictcId.GetValueOrDefault(int.MinValue));
		}

		private void OnPostDelete(CcICalcoloTContributoRiduz cls)
		{
            new CCICalcoloTContributoMgr(db).RicalcolaRiduzioni(cls.Idcomune, cls.FkCcictcId.GetValueOrDefault(int.MinValue));
		}

		private void OnPostUpdate(CcICalcoloTContributoRiduz cls)
		{
            new CCICalcoloTContributoMgr(db).RicalcolaRiduzioni(cls.Idcomune, cls.FkCcictcId.GetValueOrDefault(int.MinValue));
		}

	}
}
				