using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;

namespace Init.SIGePro.Manager
{
    public partial class RiTipoProcedimentoMgr
    {

        public RiTipoProcedimento GetRiProcedimentoByProcedura(int codiceProcedura, string idComune)
        {
            var tpMgr = new TipiProcedureMgr(db);
            var tipoProcedura = tpMgr.GetById(idComune, codiceProcedura);

            if (tipoProcedura == null || String.IsNullOrEmpty(tipoProcedura.FkRitpCodice))
                return null;

            return this.GetById(tipoProcedura.FkRitpCodice);
        }

    }
}
