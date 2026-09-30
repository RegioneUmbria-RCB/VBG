using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Pagamenti.ENTRANEXT
{
    public class RiferimentiDomandaEntranext : RiferimentiDomanda
    {
        public readonly string DescrizioneIntervento;

        public RiferimentiDomandaEntranext(IRiferimentiDomandaPerPagamenti domanda, int stepId, string descrizioneIntervento) : base(domanda, stepId)
        {
            if (string.IsNullOrEmpty(descrizioneIntervento))
            {
                throw new ArgumentException($"'{nameof(descrizioneIntervento)}' cannot be null or empty.", nameof(descrizioneIntervento));
            }

            DescrizioneIntervento = descrizioneIntervento;
        }

        
    }
}
