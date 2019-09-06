using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ProtoInf.DatiConfigurazioneProtocollo
{
    public static class InventarioProcedimentiExtensions
    {
        public static AlberoProc GetInterventoDaProcedimentoPrincipale(this InventarioProcedimenti inventarioProcedimenti, DataBase db, string software, string codiceComune)
        {
            var mgr = new AlberoProcEndoMgr(db);
            var list = mgr.GetBySoftwareeInventario(inventarioProcedimenti.Idcomune, software, inventarioProcedimenti.Codiceinventario.Value);

            if (list == null || list.Count == 0 || list.Count > 1)
            {
                return null;
            }

            if (list[0].FlagPrincipale == 1)
            {
                var mgrAlberoProc = new AlberoProcMgr(db);
                var alberoProc = mgrAlberoProc.GetById(list[0].FkScid.Value, list[0].Idcomune, codiceComune);

                alberoProc.ClassificaProtocollazione = mgrAlberoProc.GetClassificaProtocolloFromAlberoProcProtocollo(list[0].FkScid.Value, list[0].Idcomune, software, codiceComune);
                alberoProc.TipoDocumentoProtocollazione = mgrAlberoProc.GetTipoDocumentoProtocolloFromAlberoProcProtocollo(list[0].FkScid.Value, list[0].Idcomune, software, codiceComune);
                alberoProc.CodiceAmministrazione = mgrAlberoProc.GetAmministrazioneFromAlberoProcProtocollo(list[0].FkScid.Value, list[0].Idcomune, software, codiceComune);

                return alberoProc;
            }

            return null;
        }

        public static Amministrazioni GetAmministrazionePerSmistamento(this InventarioProcedimenti inventarioProcedimenti, string[] endoProcedimentiSmistabili, DataBase db, string idComune, string software, string codiceComune)
        {
            if (endoProcedimentiSmistabili.Contains(inventarioProcedimenti.Codiceinventario.Value.ToString()))
            {
                if (!inventarioProcedimenti.Amministrazione.HasValue)
                {
                    return null;
                }

                var ammMgr = new AmministrazioniMgr(db);
                var amm = ammMgr.GetByIdProtocollo(idComune, inventarioProcedimenti.Amministrazione.Value, software, codiceComune);

                if (amm == null)
                {
                    return null;
                }

                return amm;
            }

            return null;

        }

        public static bool IsPrincipale(this InventarioProcedimenti inventarioProcedimenti, DataBase db, string software)
        {
            var mgr = new AlberoProcEndoMgr(db);
            var list = mgr.GetBySoftwareeInventario(inventarioProcedimenti.Idcomune, software, inventarioProcedimenti.Codiceinventario.Value);

            if (list == null || list.Count == 0 || list.Count > 1)
            {
                return false;
            }

            return list[0].FlagPrincipale == 1;

        }
    }
}
