
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
    public partial class OICalcoloContribRRiduzMgr
	{
		#region gestione dei callback per gli eventi del database
		public override void RegisterHandlers()
		{
			this.Deleted += new DeletedDelegate(OnDeleted);
			this.Inserted += new InsertedDelegate(OnInserted);
			this.Updated += new UpdatedDelegate(OnUpdated);
		}

		void OnUpdated(OICalcoloContribRRiduz cls)
		{
			RicalcolaTotaleContribR(cls);
		}

		void OnInserted(OICalcoloContribRRiduz cls)
		{
			RicalcolaTotaleContribR(cls);
		}

		void OnDeleted(OICalcoloContribRRiduz cls)
		{
			RicalcolaTotaleContribR(cls);
		}

		private void RicalcolaTotaleContribR(OICalcoloContribRRiduz cls)
		{
			OICalcoloContribRMgr contribrMgr = new OICalcoloContribRMgr(db);
            contribrMgr.RicalcolaRiduzioniECostoTotale(contribrMgr.GetById(cls.Idcomune, cls.FkOiccrId.GetValueOrDefault(int.MinValue)), true);
		}

		#endregion 


		internal double CalcolaTotaleRiduzioni(OICalcoloContribR contribR)
		{
			List<OICalcoloContribRRiduz> l = GetListaRiduzioniDaContribR(contribR);

			double totale = 0;

			foreach (OICalcoloContribRRiduz rid in l)
                totale += rid.Riduzioneperc.GetValueOrDefault(0);

			return totale;
		}

		public DataSet GetRiduzioniPerTipoCausale(string idComune, int idContribT, int idTipoDestinazione , int idTipoCausale)
		{
			// Preparo il dataset
			DataSet ds = new DataSet();
			DataTable dt = new DataTable();

			dt.Columns.Add(new DataColumn("idCausale", typeof(int)));
			dt.Columns.Add(new DataColumn("descrizioneCausale", typeof(string)));
			dt.Columns.Add(new DataColumn("percentuale", typeof(double)));

			List<int> tipiOnereTmp = new OICalcoloContribTMgr(db).GetListaTipiOnere(idComune, idContribT);
			List<int> tipiOnere = new List<int>();

			foreach (int idTipoOnere in tipiOnereTmp)
			{
				OICalcoloContribR rigaRiduzione = new OICalcoloContribRMgr(db).GetByContribTTipoOnereDestinazione(idComune, idContribT, idTipoOnere, idTipoDestinazione);

				if (rigaRiduzione.Riduzioneperc == 0 && rigaRiduzione.Riduzione != 0.0d) continue;

				tipiOnere.Add(idTipoOnere);
			}

			

			foreach (int idTipoOnere in tipiOnere)
			{
				dt.Columns.Add(new DataColumn(idTipoOnere.ToString() + "_selezionato", typeof(bool)));
				dt.Columns.Add(new DataColumn(idTipoOnere.ToString() + "_percentuale", typeof(double)));
				dt.Columns.Add(new DataColumn(idTipoOnere.ToString() + "_note", typeof(string)));
			}

			ds.Tables.Add(dt);

			// Leggo la lista delle riduzioni presenti per il tipo causale passato
			OCausaliRiduzioniR filtro = new OCausaliRiduzioniR();
			filtro.Idcomune = idComune;
			filtro.FkOcrtId = idTipoCausale;

			List<OCausaliRiduzioniR> listaRiduzioni = new OCausaliRiduzioniRMgr(db).GetList(filtro);

			OICalcoloContribRMgr contribRMgr = new OICalcoloContribRMgr(db);

			foreach (OCausaliRiduzioniR riduzione in listaRiduzioni)
			{
				DataRow dr = dt.NewRow();

				dr["idCausale"] = riduzione.Id;
				dr["descrizioneCausale"] = riduzione.Descrizione;
				dr["percentuale"] = riduzione.Riduzioneperc;

				foreach (int idTipoOnere in tipiOnere)
				{
					OICalcoloContribR contribR = contribRMgr.GetByContribTTipoOnereDestinazione( idComune , idContribT , idTipoOnere , idTipoDestinazione );
                    OICalcoloContribRRiduz contribRRiduz = GetByIdContribRTipoCausale(idComune, contribR.Id.GetValueOrDefault(int.MinValue), riduzione.Id.GetValueOrDefault(int.MinValue));

					dr[idTipoOnere.ToString() + "_selezionato"] = contribRRiduz != null;
					dr[idTipoOnere.ToString() + "_percentuale"] = contribRRiduz != null ? contribRRiduz.Riduzioneperc : 0.0d;
					dr[idTipoOnere.ToString() + "_note"] = contribRRiduz != null ? contribRRiduz.Note : String.Empty;
				}

				dt.Rows.Add(dr);
			}


			return ds;
		}

		public OICalcoloContribRRiduz GetByIdContribRTipoCausale(string idComune, int idContribr, int idCausale)
		{
			OICalcoloContribRRiduz filtro = new OICalcoloContribRRiduz();
			filtro.Idcomune = idComune;
			filtro.FkOiccrId = idContribr;
			filtro.FkOcrrId = idCausale;

			return db.GetClass(filtro) as OICalcoloContribRRiduz;
		}

		public List<OICalcoloContribRRiduz> GetRiduzioniDaContribtDestinazioneTipoOnere(string idComune, int idContribt, int idDestinazione, int idTipoOnere)
		{
			string sql = @"SELECT  o_icalcolocontribr_riduz.*
							FROM    o_icalcolocontribr,
									o_icalcolocontribr_riduz
							WHERE   o_icalcolocontribr_riduz.idcomune    = o_icalcolocontribr.idcomune
								AND o_icalcolocontribr_riduz.fk_oiccr_id = o_icalcolocontribr.id
								AND o_icalcolocontribr.idcomune			 = {0}
								AND o_icalcolocontribr.fk_oto_id         = {1}
								AND o_icalcolocontribr.fk_ode_id         = {2}
								AND o_icalcolocontribr.fk_oicct_id       = {3}";

			sql = string.Format(sql, db.Specifics.QueryParameterName("IdComune"),
										db.Specifics.QueryParameterName("idTipoOnere"),
										db.Specifics.QueryParameterName("idDestinazione"),
										db.Specifics.QueryParameterName("idContribT"));


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
					cmd.Parameters.Add(db.CreateParameter("IdComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("idTipoOnere", idTipoOnere));
					cmd.Parameters.Add(db.CreateParameter("idDestinazione", idDestinazione));
					cmd.Parameters.Add(db.CreateParameter("idContribT", idContribt));

					OICalcoloContribRRiduz cls = new OICalcoloContribRRiduz();
					cls.UseForeign = useForeignEnum.Yes;

					return db.GetClassList(cmd, cls, false, true).ToList < OICalcoloContribRRiduz>();

				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}

		}

		public List<OICalcoloContribRRiduz> GetListaRiduzioniDaContribR(OICalcoloContribR contribR)
		{
			OICalcoloContribRRiduz filtro = new OICalcoloContribRRiduz();
			filtro.Idcomune = contribR.Idcomune;
			filtro.FkOiccrId = contribR.Id;

			return GetList(filtro);
		}
	}
}
				