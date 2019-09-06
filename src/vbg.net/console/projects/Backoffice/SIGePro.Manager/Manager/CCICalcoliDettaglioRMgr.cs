using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Authentication;
using Init.SIGePro.Validator;

namespace Init.SIGePro.Manager
{
	public partial class CCICalcoliDettaglioRMgr
	{
		public static  List<CCICalcoliDettaglioR> Find(string token, int idTestata)
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

			CCICalcoliDettaglioRMgr mgr = new CCICalcoliDettaglioRMgr(authInfo.CreateDatabase());

			CCICalcoliDettaglioR filtro = new CCICalcoliDettaglioR();
			filtro.Idcomune = authInfo.IdComune;
			filtro.FkCcicdtId = idTestata;

			filtro.OrderBy = "ID ASC";

			return mgr.GetList(filtro);
		}

		private CCICalcoliDettaglioR ChildInsert(CCICalcoliDettaglioR cls)
		{
            new CCICalcoliDettaglioTMgr(db).AggiornaSU(cls.Idcomune, cls.FkCcicdtId.GetValueOrDefault(int.MinValue));

			return cls;
		}

		public CCICalcoliDettaglioR Update(CCICalcoliDettaglioR cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

            new CCICalcoliDettaglioTMgr(db).AggiornaSU(cls.Idcomune, cls.FkCcicdtId.GetValueOrDefault(int.MinValue));

			return cls;
		}

		public void Delete(CCICalcoliDettaglioR cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);

            new CCICalcoliDettaglioTMgr(db).AggiornaSU(cls.Idcomune, cls.FkCcicdtId.GetValueOrDefault(int.MinValue));
		}
	}
}
