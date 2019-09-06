using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Authentication;
using PersonalLib2.Sql;
using Init.SIGePro.Validator;
using PersonalLib2.Data;
using Init.Utils.Math;

namespace Init.SIGePro.Manager
{
	public partial class CCICalcoliDettaglioTMgr
	{


		#region Classi helper per il calcolo delle superfici

		#endregion


		public static List<CCICalcoliDettaglioT> Find(string token, int idCalcolo)
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

			CCICalcoliDettaglioTMgr mgr = new CCICalcoliDettaglioTMgr(authInfo.CreateDatabase());

			CCICalcoliDettaglioT filtro = new CCICalcoliDettaglioT();
			filtro.Idcomune = authInfo.IdComune;
			filtro.FkCcicId = idCalcolo;

			filtro.OrderBy = "ORDINE ASC";

			return mgr.GetList(filtro);
		}

		private CCICalcoliDettaglioT DataIntegrations(CCICalcoliDettaglioT cls)
		{
            if (cls.Ordine.GetValueOrDefault(int.MinValue) == int.MinValue)
			{
				var where = new List<KeyValuePair<string, object>>{
																	new KeyValuePair<string,object>( "FK_CCIC_ID", cls.FkCcicId )
					 											};


				int ordine = FindMax("ORDINE", cls.DataTableName, cls.Idcomune, where);

				cls.Ordine = ordine;
			}


			return cls;
		}

		public void AggiornaSU(string idComune, int idTestata)
		{
			CCICalcoliDettaglioRMgr mgr = new CCICalcoliDettaglioRMgr(db);
			CCICalcoliDettaglioR filtro = new CCICalcoliDettaglioR();
			filtro.Idcomune = idComune;
			filtro.FkCcicdtId = idTestata;

			List<CCICalcoliDettaglioR> lista = mgr.GetList(filtro);

			double totale = 0;

			lista.ForEach(delegate(CCICalcoliDettaglioR r)
			{
                double tmp = r.Qta.GetValueOrDefault(int.MinValue) * r.Lung.GetValueOrDefault(double.MinValue) * r.Larg.GetValueOrDefault(double.MinValue);

				if (tmp > 0.0f)
					totale += tmp;
			});

			CCICalcoliDettaglioT cls = GetById(idComune, idTestata);
			cls.Su = totale;

			Update(cls);
		}

		public override DataClass ChildDataIntegrations(DataClass cls)
		{

			return cls;
		}

        private void EffettuaCancellazioneACascata(CCICalcoliDettaglioT cls)
        {
            CCICalcoliDettaglioR a = new CCICalcoliDettaglioR();
            a.Idcomune = cls.Idcomune;
            a.FkCcicdtId = cls.Id;

            List<CCICalcoliDettaglioR> lCalcoloR = new CCICalcoliDettaglioRMgr(db).GetList(a);
            foreach (CCICalcoliDettaglioR calcoloR in lCalcoloR)
            {
                CCICalcoliDettaglioRMgr mgr = new CCICalcoliDettaglioRMgr(db);
                mgr.Delete(calcoloR);
            }
        }
	}
}
