using System.Collections.Generic;
using Init.SIGePro.Exceptions;
using VBG.Backend.Protocollo.AppLogic.Legacy.Sigepro.Data;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Sigepro.Manager
{
    public partial class ProtAllegatiProtocolloMgr
    {
        private void ForeignValidate(ProtAllegatiProtocollo cls)
        {
            #region Oggetto
            if (cls.Ad_Ogid.GetValueOrDefault(int.MinValue) != int.MinValue)
            {
                var conditions = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("CODICEOGGETTO", cls.Ad_Ogid.ToString()),
                    new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune)
                };
                if (this.recordCount("PROT_OGGETTI", "CODICEOGGETTO", conditions) == 0)
                    throw (new RecordNotfoundException($"PROT_OGGETTI.CODICEOGGETTO ({cls.Ad_Ogid}) non trovato nella tabella PROT_AOO"));
            }
            else
                throw new RequiredFieldException("PROT_ALLEGATIPROTOCOLLO.AD_OGID obbligatorio");
            #endregion

            #region Protocollo
            var protocolloConditions = new List<KeyValuePair<string, string>>
            {
                new KeyValuePair<string, string>("PG_ID", cls.Ad_Dlid.ToString()),
                new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune)
            };
            if (this.recordCount("PROT_GENERALE", "PG_ID", protocolloConditions) == 0)
                throw (new RecordNotfoundException($"PROT_GENERALE.PG_ID ({cls.Ad_Dlid}) non trovato nella tabella PROT_AOO"));
            #endregion
        }	
    }
}
