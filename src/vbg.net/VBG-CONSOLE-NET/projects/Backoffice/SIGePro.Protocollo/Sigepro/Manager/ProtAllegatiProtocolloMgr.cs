using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using Init.SIGePro.Data;

using Init.SIGePro.Validator;
using PersonalLib2.Sql;
using Init.SIGePro.Exceptions;

namespace Init.SIGePro.Manager
{
    public partial class ProtAllegatiProtocolloMgr
    {
        private void ForeignValidate(ProtAllegatiProtocollo cls)
        {
            #region Oggetto
            if (cls.Ad_Ogid.GetValueOrDefault(int.MinValue) != int.MinValue)
            {
                if (this.recordCount("PROT_OGGETTI", "CODICEOGGETTO", "WHERE CODICEOGGETTO = " + cls.Ad_Ogid + " AND IDCOMUNE = '" + cls.Idcomune + "'") == 0)
                    throw (new RecordNotfoundException("PROT_OGGETTI.CODICEOGGETTO (" + cls.Ad_Ogid + ") non trovato nella tabella PROT_AOO"));
            }
            else
                throw new RequiredFieldException("PROT_ALLEGATIPROTOCOLLO.AD_OGID obbligatorio");
            #endregion

            #region Protocollo
            if (this.recordCount("PROT_GENERALE", "PG_ID", "WHERE PG_ID = " + cls.Ad_Dlid + " AND IDCOMUNE = '" + cls.Idcomune + "'") == 0)
                throw (new RecordNotfoundException("PROT_GENERALE.PG_ID (" + cls.Ad_Dlid + ") non trovato nella tabella PROT_AOO"));
            #endregion
        }	
    }
}
