using System;
using System.Collections.Generic;
using System.Text;
using System.ComponentModel;
using Init.SIGePro.Data;
using Init.SIGePro.Authentication;
using PersonalLib2.Sql;
using Init.SIGePro.Validator;

namespace Init.SIGePro.Manager
{
	[DataObject(true)]
	public partial class OICalcoloDettaglioRMgr
	{
		[DataObjectMethod( DataObjectMethodType.Select )]
		public static List<OICalcoloDettaglioR> Find(string token, int idTestata)
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

			OICalcoloDettaglioRMgr mgr = new OICalcoloDettaglioRMgr(authInfo.CreateDatabase());

			OICalcoloDettaglioR filtro = new OICalcoloDettaglioR();
			filtro.Idcomune = authInfo.IdComune;
			filtro.FkOicdtId = idTestata;
			filtro.OrderBy = "id asc";

			return mgr.GetList(filtro);
		}


		public OICalcoloDettaglioR Update(OICalcoloDettaglioR cls)
		{
			Validate(cls, AmbitoValidazione.Update);

			db.Update(cls);

			RicalcolaCubatura(cls);

			return cls;
		}

		private void RicalcolaCubatura(OICalcoloDettaglioR cls)
		{
			OICalcoloDettaglioTMgr tMgr = new OICalcoloDettaglioTMgr(db);
			tMgr.RicalcolaCubaturaTotale(cls.Idcomune, cls.FkOicdtId.Value);
		}

		public void Delete(OICalcoloDettaglioR cls)
		{
			VerificaRecordCollegati(cls);

			EffettuaCancellazioneACascata(cls);

			db.Delete(cls);

			RicalcolaCubatura(cls);
		}

		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			RicalcolaCubatura((OICalcoloDettaglioR)cls);

			return cls;
		}



	}
}
