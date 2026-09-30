using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Data;
using Init.SIGePro.Validator;

namespace Init.SIGePro.Manager
{
    public partial class Mercati_DContiMgr
    {
        private void Validate(Mercati_DConti cls, AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(cls, ambitoValidazione);

            if (cls.Contesto != "SPUNTISTI" && cls.Contesto != "CONCESSIONARI" && cls.Contesto != "TUTTI")
                throw new IncongruentDataException("MERCATI_D_CONTI.CONTESTO(" + cls.Contesto + ") è case sensitive e può accettare solamente i valori: SPUNTISTI, CONCESSIONARI o TUTTI");

            ForeignValidate(cls);
        }

        private void ForeignValidate(Mercati_DConti cls)
        {
            #region MERCATI_D_CONTI.FK_COID
            if (cls.FkCoId.GetValueOrDefault(int.MinValue) > int.MinValue)
            {
                if (this.recordCount("CONTI", "ID", "WHERE IDCOMUNE = '" + cls.Idcomune + "' AND ID = " + cls.FkCoId.ToString() ) == 0)
                {
                    throw (new RecordNotfoundException("MERCATI_D_CONTI.FK_COID (" + cls.FkCoId.ToString() + ") non trovato nella tabella CONTI"));
                }
            }
            #endregion

            #region MERCATI_D_CONTI.FK_MDID
            if (cls.FkMdId.GetValueOrDefault(int.MinValue) > int.MinValue)
            {
                if (this.recordCount("MERCATI_D", "IDPOSTEGGIO", "WHERE IDCOMUNE = '" + cls.Idcomune + "' AND IDPOSTEGGIO = " + cls.FkMdId.ToString()) == 0)
                {
                    throw (new RecordNotfoundException("MERCATI_D_CONTI.FK_MDID (" + cls.FkMdId.ToString() + ") non trovato nella tabella MERCATI_D"));
                }
            }
            #endregion

        }
    }
}
