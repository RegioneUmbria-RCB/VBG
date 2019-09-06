using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielMercatoService;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.InsielMercato.Protocollazione
{
    public static class AnagrafichePartenzaExtensions
    {
        public static recipient ToRecipientAnagrafe(this ProtocolloAnagrafe anagrafe)
        {
            var res = new recipient
            {
                description = anagrafe.GetNomeCompleto(),
                referenceDate = DateTime.MaxValue,
                referenceDateSpecified = true
            };

            if(!String.IsNullOrEmpty(anagrafe.ModalitaTrasmissione))
                res.transmissionMode = anagrafe.ModalitaTrasmissione;

            return res;

        }

        public static recipient ToRecipientAmministrazione(this Amministrazioni amm)
        {
            var res = new recipient
            {
                description = amm.AMMINISTRAZIONE,
                referenceDate = DateTime.MaxValue,
                referenceDateSpecified = true
            };

            if (!String.IsNullOrEmpty(amm.ModalitaTrasmissione))
                res.transmissionMode = amm.ModalitaTrasmissione;

            return res;
        }

    }
}
