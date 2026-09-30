using System;
using VBG.Pagamenti.NodoPagamenti.Shared;

namespace VBG.Pagamenti.Legacy.ENTRANEXT
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

            this.DescrizioneIntervento = descrizioneIntervento;
        }


    }
}
