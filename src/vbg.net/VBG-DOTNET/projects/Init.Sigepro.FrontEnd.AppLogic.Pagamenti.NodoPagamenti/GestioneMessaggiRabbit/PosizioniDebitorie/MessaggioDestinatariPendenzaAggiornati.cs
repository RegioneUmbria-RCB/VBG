using Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi.Infrastructure;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti.GestioneMessaggiRabbit.PosizioniDebitorie
{
    public class DestinatariPendenzaAggiornatiMessageBody
    {
        public string CfEnteCreditore { get; set; } = "";
        public string Uuid { get; set; } = "";
        public string RiferimentoClient { get; set; } = "";
        public string Provenienza { get; set; } = "";
        public string PartitaIVA { get; set; } = "";
        public List<string> CodiciFiscaliDestinatari { get; set; } = new List<string>();
    }

    public class MessaggioDestinatariPendenzaAggiornati : RabbitMessage<DestinatariPendenzaAggiornatiMessageBody>
    {
        public static readonly string TopicKey = "domande-on-line.destinatari-pendenza-aggiornati";
    }
}
