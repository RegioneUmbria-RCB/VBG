using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;

namespace Init.SIGePro.Manager
{
    public partial class IstanzeLavoriTMgr : BaseManager
    {
        private void EffettuaCancellazioneACascata(IstanzeLavoriT cls)
        {
            #region ISTANZELAVORI_D
            IstanzeLavoriD ist_lav = new IstanzeLavoriD();
            ist_lav.Idcomune = cls.Idcomune;
            ist_lav.FkIltid = cls.Id;

            List<IstanzeLavoriD> lLavori = new IstanzeLavoriDMgr(db).GetList(ist_lav);
            foreach (IstanzeLavoriD lavoro in lLavori)
            {
                IstanzeLavoriDMgr mgr = new IstanzeLavoriDMgr(db);
                mgr.Delete(lavoro);
            }
            #endregion
        }
    }
}
