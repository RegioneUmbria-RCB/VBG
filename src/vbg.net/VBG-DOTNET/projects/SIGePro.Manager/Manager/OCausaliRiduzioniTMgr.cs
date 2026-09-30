
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.IOC;
using Init.Utils.Sorting;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.ComponentModel;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class OCausaliRiduzioniTMgr
    {
        public override void RegisterHandlers()
        {
            this.Deleting += new DeletingDelegate(this.OCausaliRiduzioniTMgr_Deleting);
        }

        private void OCausaliRiduzioniTMgr_Deleting(OCausaliRiduzioniT cls)
        {
            this.DeleteChildRows(cls);
        }

        private void DeleteChildRows(OCausaliRiduzioniT cls)
        {
            OCausaliRiduzioniRMgr mgr = new OCausaliRiduzioniRMgr(this.db);

            foreach (OCausaliRiduzioniR r in mgr.GetListByCausaliRiduzioniT(cls.Idcomune, cls.Id.GetValueOrDefault(int.MinValue)))
                mgr.Delete(r);
        }


        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<OCausaliRiduzioniT> Find(string token, string software, string descrizione, string sortExpression)
        {
            AuthenticationInfo authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

            OCausaliRiduzioniTMgr mgr = new OCausaliRiduzioniTMgr(authInfo.CreateDatabase());

            OCausaliRiduzioniT filtro = new OCausaliRiduzioniT();
            OCausaliRiduzioniT filtroCompare = new OCausaliRiduzioniT();

            filtro.Software = software;
            filtro.Idcomune = authInfo.IdComune;

            if (!String.IsNullOrEmpty(descrizione))
                filtro.Descrizione = descrizione;

            filtroCompare.Descrizione = "like";

            List<OCausaliRiduzioniT> list = authInfo.CreateDatabase().GetClassList(filtro, filtroCompare).ToList<OCausaliRiduzioniT>();

            ListSortManager<OCausaliRiduzioniT>.Sort(list, sortExpression);

            return list;
        }

    }
}
