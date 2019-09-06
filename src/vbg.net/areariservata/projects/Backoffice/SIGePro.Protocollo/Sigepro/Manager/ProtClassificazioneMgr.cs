using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Manager
{
    public partial class ProtClassificazioneMgr
    {

        public Anagrafe GetResponsabileProcedimento(int idClassifica, string idComune)
        {
            Anagrafe anag = null;

            var clMgr = new ProtClassificazioneMgr(db);

            var classifica = clMgr.GetById(idClassifica, idComune);

            if (classifica != null)
            {
                if (classifica.Cl_Fkidresponsabile.HasValue)
                    anag = new AnagrafeMgr(db).GetById(idComune, classifica.Cl_Fkidresponsabile.Value);
                else
                {
                    if (classifica.Cl_Padre.HasValue)
                        anag = GetResponsabileProcedimento(classifica.Cl_Padre.Value, idComune);
                }
            }

            return anag;
        }
    }
}
