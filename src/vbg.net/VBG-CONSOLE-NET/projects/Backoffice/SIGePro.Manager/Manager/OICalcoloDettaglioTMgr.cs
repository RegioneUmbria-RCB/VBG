using System;
using System.Collections.Generic;
using System.Text;
using System.ComponentModel;
using Init.SIGePro.Data;
using Init.SIGePro.Authentication;

namespace Init.SIGePro.Manager
{
	[DataObject(true )]
	public partial class OICalcoloDettaglioTMgr
	{

		public static List<OICalcoloDettaglioT> Find( string token , int idCalcolo)
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

			OICalcoloDettaglioTMgr mgr = new OICalcoloDettaglioTMgr(authInfo.CreateDatabase());

			OICalcoloDettaglioT filtro = new OICalcoloDettaglioT();
			filtro.Idcomune = authInfo.IdComune;
			filtro.FkOicId = idCalcolo;
			filtro.OrderBy = "id asc";

			return mgr.GetList(filtro);
		}

		private OICalcoloDettaglioT DataIntegrations(OICalcoloDettaglioT cls)
		{
            if (cls.Ordine.GetValueOrDefault(int.MinValue) == int.MinValue)
			{
				var where = new List<KeyValuePair<string,object>>{
																	new KeyValuePair<string,object>( "FK_OIC_ID", cls.FkOicId )
					 											};

				int ordine = FindMax("ORDINE", cls.DataTableName, cls.Idcomune, where);

				cls.Ordine = ordine;
			}


			return cls;
		}


		public bool AltezzaRichiesta(string idComune, int idTCalcolo)
		{
			OICalcoloDettaglioT t = GetById(idComune, idTCalcolo);
            ODestinazioni dest = new ODestinazioniMgr(db).GetById(idComune, t.FkOdeId.GetValueOrDefault(int.MinValue));

			Istanze istanza = new IstanzeMgr( db ).GetById( idComune, t.Codiceistanza.Value);

			OConfigurazione cfg = new OConfigurazioneMgr(db).GetById(idComune, istanza.SOFTWARE);

			return cfg.FkTumUmidMc == dest.FkTumUmid;
		}


		private List<OICalcoloDettaglioR> GetDettagli(OICalcoloDettaglioT cls)
		{
			OICalcoloDettaglioRMgr mgr = new OICalcoloDettaglioRMgr(db);

			OICalcoloDettaglioR filtro = new OICalcoloDettaglioR();

			filtro.Idcomune = cls.Idcomune;
			filtro.FkOicdtId = cls.Id;

			return mgr.GetList(filtro);
		}

		private void EffettuaCancellazioneACascata(OICalcoloDettaglioT cls)
		{
			OICalcoloDettaglioRMgr mgr = new OICalcoloDettaglioRMgr(db);
			
			List<OICalcoloDettaglioR> l = GetDettagli( cls );

			l.ForEach(delegate(OICalcoloDettaglioR dett) { mgr.Delete(dett); });
		}

		public void RicalcolaCubaturaTotale(string idComune, int id)
		{
			OICalcoloDettaglioT cls = GetById(idComune, id);
			List<OICalcoloDettaglioR> l = GetDettagli(cls);

			double totale = 0.0d;

            l.ForEach(delegate(OICalcoloDettaglioR dett) { totale += dett.Totale.GetValueOrDefault(double.MinValue); });

			cls.Totale = totale;

			Update(cls);
		}



	}
}
