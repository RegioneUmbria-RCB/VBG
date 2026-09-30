using ProtocolloInsielMercatoService2;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.InsielMercato2.LeggiProtocollo
{
    public static class LeggiMittentiDestinatariExtensions
    {
        public static MittDestOutType ToMittDestOutFromMittente(this sender mittente)
        {
            var res = new MittDestOutType { IdSoggetto = mittente.code, CognomeNome = mittente.description };
            if (!String.IsNullOrEmpty(mittente.transmissionMode))
                res.CognomeNome = String.Format("{0} Trasmesso per: {1}", mittente.description, mittente.transmissionMode);

            return res;

        }

        public static MittDestOutType ToMittDestOutFromDestinatario(this recipient destinatario)
        {
            var res = new MittDestOutType { IdSoggetto = destinatario.code, CognomeNome = destinatario.description };
            if (!String.IsNullOrEmpty(destinatario.transmissionMode))
                res.CognomeNome = String.Format("{0} Trasmesso per: {1}", destinatario.description, destinatario.transmissionMode);

            return res;

        }

    }
}
