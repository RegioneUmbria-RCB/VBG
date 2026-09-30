using VBG.Backend.Protocollo.AppLogic.Core.DocEr.ProtocollazioneRegistrazione;
using VBG.Backend.Protocollo.AppLogic.Core.DocEr.GestioneDocumentale.LeggiDocumento.Persone;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.GestioneDocumentale.LeggiDocumento
{
    public static class MittenteDestinatarioExtensions
    {
        public static MittDestOutType ToMittenteDestinatario(this MittDestType mittDest)
        {
            var factory = PersonaFisicaGiuridicaFactory.Create(mittDest.Items[0]);
            return factory.GetPersona();
        }
    }
}
