
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
    public partial class OCausaliRiduzioniTMgr
    {
		public override void RegisterHandlers()
		{
			this.Deleting += new DeletingDelegate(OCausaliRiduzioniTMgr_Deleting);
		}

		void OCausaliRiduzioniTMgr_Deleting(OCausaliRiduzioniT cls)
		{
			DeleteChildRows(cls);
		}

		private void DeleteChildRows(OCausaliRiduzioniT cls)
		{
			OCausaliRiduzioniRMgr mgr = new OCausaliRiduzioniRMgr(db);

            foreach (OCausaliRiduzioniR r in mgr.GetListByCausaliRiduzioniT(cls.Idcomune, cls.Id.GetValueOrDefault(int.MinValue)))
				mgr.Delete(r);
		}


		[DataObjectMethod(DataObjectMethodType.Select)]
		public static List<OCausaliRiduzioniT> Find(string token, string software,string descrizione, string sortExpression)
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

			OCausaliRiduzioniTMgr mgr = new OCausaliRiduzioniTMgr(authInfo.CreateDatabase());

			OCausaliRiduzioniT filtro = new OCausaliRiduzioniT();
			OCausaliRiduzioniT filtroCompare = new OCausaliRiduzioniT();

			filtro.Software = software;
			filtro.Idcomune = authInfo.IdComune;

			if (!String.IsNullOrEmpty(descrizione))
				filtro.Descrizione = descrizione;

			filtroCompare.Descrizione = "like";

			List<OCausaliRiduzioniT> list = authInfo.CreateDatabase().GetClassList(filtro, filtroCompare, false, true).ToList<OCausaliRiduzioniT>();

			ListSortManager<OCausaliRiduzioniT>.Sort(list, sortExpression);

			return list;
		}

	}
}
				