using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielMercatoService2;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.InsielMercato2.Protocollazione
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

            if (!String.IsNullOrEmpty(anagrafe.Pec))
            {
                res.description = String.Format("{0} ({1})", anagrafe.GetNomeCompleto(), anagrafe.Pec);
                res.emailPec = anagrafe.Pec;
            }
            

            return res;
        }

        public static sender ToSenderAmministrazione(this Amministrazioni amm)
        {
            var res = new sender
            {
                description = amm.AMMINISTRAZIONE,
                referenceDate = DateTime.MinValue,
                referenceDateSpecified = true,
                transmissionMode = amm.ModalitaTrasmissione
            };

            if (!String.IsNullOrEmpty(amm.ModalitaTrasmissione))
                res.transmissionMode = amm.ModalitaTrasmissione;

            if (!String.IsNullOrEmpty(amm.PEC))
            {
                res.description = String.Format("{0} ({1})", amm.AMMINISTRAZIONE, amm.PEC);
                res.emailPec = amm.PEC;
            }

            return res;
        }
    }
}
