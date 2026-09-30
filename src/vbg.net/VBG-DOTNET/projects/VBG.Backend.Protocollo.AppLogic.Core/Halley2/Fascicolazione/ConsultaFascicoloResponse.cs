using VBG.Backend.Protocollo.AppLogic.Core.Halley2.Fascicolazione.Enum;

namespace VBG.Backend.Protocollo.AppLogic.Core.Halley2.Fascicolazione
{
    public class ConsultaFascicoloResponse
    {
        public bool Esito { get; set; }

        public Errore Errore { get; set; }

        public ServiziAggiuntiviFaultCode? FaultCodeEnum
        { 
            get
            {
                if (Errore == null)
                    return null;

                return ServiziAggiuntiviFaultCodeExt.ToFaultCode(Errore.codice);
            }
        }

        public HalleyUtilityService.Fascicolo[] fascicoli { get; set; }
    }
}
