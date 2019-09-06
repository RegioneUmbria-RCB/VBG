using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;

namespace Init.SIGePro.Manager
{
    public partial class CdsMgr
    {
        public void Delete(Cds cls)
        {
			VerificaRecordCollegati(cls);

			EffettuaCancellazioneACascata(cls);

			db.Delete(cls);
        }

		private void EffettuaCancellazioneACascata(Cds cls)
		{
			#region CDSATTI

			CdsAttiMgr cdsAttiMgr = new CdsAttiMgr(db);

			CdsAtti cdsAttiFiltro = new CdsAtti();
			cdsAttiFiltro.Idcomune = cls.Idcomune;
			cdsAttiFiltro.Fkidtestata = cls.Idtestata;

			List<CdsAtti> cdsAttiList = cdsAttiMgr.GetList(cdsAttiFiltro);

			for (int i = 0; i < cdsAttiList.Count; i++)
			{
				cdsAttiMgr.Delete(cdsAttiList[i]); 
			}
			#endregion

			#region CDSCONVOCAZIONI
			CdsConvocazioni cds_con = new CdsConvocazioni();
			cds_con.Idcomune = cls.Idcomune;
			cds_con.Idtestata = cls.Idtestata;

			List<CdsConvocazioni> lCdsconvocazioni = new CdsConvocazioniMgr(db).GetList(cds_con);
			foreach (CdsConvocazioni cdsconvocazioni in lCdsconvocazioni)
			{
				CdsConvocazioniMgr mgr = new CdsConvocazioniMgr(db);
				mgr.Delete(cdsconvocazioni);
			}
			#endregion

			#region CDSINVITATI
			CdsInvitati cds_inv = new CdsInvitati();
			cds_inv.Idcomune = cls.Idcomune;
			cds_inv.Fkidtestata = cls.Idtestata;

			List<CdsInvitati> lInvitati = new CdsInvitatiMgr(db).GetList(cds_inv);
			foreach (CdsInvitati invitati in lInvitati)
			{
				CdsInvitatiMgr mgr = new CdsInvitatiMgr(db);
				mgr.Delete(invitati);
			}
			#endregion

			#region CDSINVITATI2
			CdsInvitati2 cds_inv2 = new CdsInvitati2();
			cds_inv2.Idcomune = cls.Idcomune;
			cds_inv2.Fkidtestata = cls.Idtestata;

			List<CdsInvitati2> lInvitati2 = new CdsInvitati2Mgr(db).GetList(cds_inv2);
			foreach (CdsInvitati2 invitati2 in lInvitati2)
			{
				CdsInvitati2Mgr mgr = new CdsInvitati2Mgr(db);
				mgr.Delete(invitati2);
			}
			#endregion
		}

    }
}
