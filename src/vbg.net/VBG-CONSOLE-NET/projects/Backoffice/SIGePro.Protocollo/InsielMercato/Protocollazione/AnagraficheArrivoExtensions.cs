using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielMercatoService;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.InsielMercato.Protocollazione
{
    public static class AnagraficheArrivoExtensions
    {
        public static sender ToSenderAnagrafe(this ProtocolloAnagrafe anagrafe)
        {
            var res = new sender
            {
                description = anagrafe.GetNomeCompleto(),
                referenceDate = DateTime.MinValue,
                referenceDateSpecified = true
            };

            if (!String.IsNullOrEmpty(anagrafe.ModalitaTrasmissione))
                res.transmissionMode = anagrafe.ModalitaTrasmissione;

            return res;
        }

        public static sender ToSenderAmministrazione(this Amministrazioni amm)
        {
            var res = new sender
            {
                description = amm.AMMINISTRAZIONE,
                referenceDate = DateTime.MinValue,
                referenceDateSpecified = true
            };

            if (!String.IsNullOrEmpty(amm.ModalitaTrasmissione))
                res.transmissionMode = amm.ModalitaTrasmissione;

            return res;
        }
    }
}
