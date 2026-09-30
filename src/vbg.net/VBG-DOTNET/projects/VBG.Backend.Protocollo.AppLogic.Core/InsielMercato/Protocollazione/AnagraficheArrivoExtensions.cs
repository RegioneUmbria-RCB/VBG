using ProtocolloInsielMercatoService;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Core.InsielMercato.Protocollazione
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

        public static sender ToSenderAmministrazione(this ProtocolloAmministrazioni amm)
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
