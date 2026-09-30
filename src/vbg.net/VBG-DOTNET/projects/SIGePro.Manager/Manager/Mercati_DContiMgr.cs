using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.Validator;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{
    public partial class Mercati_DContiMgr
    {
        private void Validate(Mercati_DConti cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);

            if (cls.Contesto != "SPUNTISTI" && cls.Contesto != "CONCESSIONARI" && cls.Contesto != "TUTTI")
                throw new IncongruentDataException("MERCATI_D_CONTI.CONTESTO(" + cls.Contesto + ") è case sensitive e può accettare solamente i valori: SPUNTISTI, CONCESSIONARI o TUTTI");

            this.ForeignValidate(cls);
        }

        private void ForeignValidate(Mercati_DConti cls)
        {
            #region MERCATI_D_CONTI.FK_COID
            if (cls.FkCoId.GetValueOrDefault(int.MinValue) > int.MinValue)
            {
                var conditions = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune),
                    new KeyValuePair<string, string>("ID", cls.FkCoId.ToString())
                };

                if (this.recordCount("CONTI", "ID", conditions) == 0)
                {
                    throw (new RecordNotfoundException("MERCATI_D_CONTI.FK_COID (" + cls.FkCoId.ToString() + ") non trovato nella tabella CONTI"));
                }
            }
            #endregion

            #region MERCATI_D_CONTI.FK_MDID
            if (cls.FkMdId.GetValueOrDefault(int.MinValue) > int.MinValue)
            {
                var conditions = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune),
                    new KeyValuePair<string, string>("IDPOSTEGGIO", cls.FkMdId.ToString())
                };

                if (this.recordCount("MERCATI_D", "IDPOSTEGGIO", conditions) == 0)
                {
                    throw (new RecordNotfoundException("MERCATI_D_CONTI.FK_MDID (" + cls.FkMdId.ToString() + ") non trovato nella tabella MERCATI_D"));
                }
            }
            #endregion

        }
    }
}
