using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersonalLib2.Data;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;

namespace Init.SIGePro.Protocollo.ProtocolloServices.OperatoreProtocollo
{
    public class BaseOperatoreProtocollo
    {
        protected Responsabili Responsabile;

        public BaseOperatoreProtocollo(DataBase db, int codOperatore, string idComune)
        {
            var responsabile = new ResponsabiliMgr(db).GetById(idComune, codOperatore);
            if (responsabile == null)
                throw new Exception(String.Format("RESPONSABILE NON TROVATO PER IL CODICE {0}", codOperatore.ToString()));

            Responsabile = responsabile;
        }
    }
}
