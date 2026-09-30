
namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft
{
    public class Esito
    {
        public string Descrizione { get; set; }
        public string TipoRisultato { get; set; }

        public bool Ok => this.TipoRisultato != "Error";

        public static Esito FromFascicoloMessageResult(FascicolazioneItalSoftService.messageResult messageResult)
        {
            if (messageResult == null)
            {
                return new Esito();
            }

            return new Esito
            {
                Descrizione = messageResult.descrizione,
                TipoRisultato = messageResult.tipoRisultato,
            };
        }

        public static Esito FromProtocolloMessageResult(ProtocolloItalSoftService.messageResult messageResult)
        {
            if (messageResult == null)
            {
                return new Esito();
            }

            return new Esito
            {
                Descrizione = messageResult.descrizione,
                TipoRisultato = messageResult.tipoRisultato,
            };
        }
    }
}
