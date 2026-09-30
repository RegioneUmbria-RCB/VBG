using IntegrazioneCUnicoWS.CUnicoWS;
using System;

namespace IntegrazioneCUnicoWS
{
    public class AnnullaConcessioneRequest
    {
        public int MatricolaDichiarazione { get; set; }

        internal annullaConcessioneRichiesta ToannullaConcessioneRequest(CUnicoConfigurazione configurazione)
        {
            if (configurazione is null)
            {
                throw new ArgumentNullException(nameof(configurazione));
            }

            return new annullaConcessioneRichiesta
            {
                codBel = configurazione.CodBel,
                codUte = configurazione.CodUte,
                DIC_MTR = this.MatricolaDichiarazione,
                DIC_MTRSpecified = true
            };
        }
    }
}