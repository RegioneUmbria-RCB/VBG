using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;

namespace Init.SIGePro.Manager
{
    public partial class RiInterventoMgr
    {
        public RiIntervento GetByIntervento(string idComune, int codiceIntervento)
        {
            var mgr = new AlberoProcMgr(db);
            var intervento = mgr.GetById(codiceIntervento, idComune);

            if (intervento == null || String.IsNullOrEmpty(intervento.FkRitiCodice))
                return null;

            return this.GetById(intervento.FkRitiCodice);
            
        }
    }
}
