using Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi.Infrastructure;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi.DomandeInBozza
{
    public class DomandaInBozzaTag
    {
        public string Tipo { get; set; }
        public string Messaggio { get; set; }
    }

    public class DomandaInBozzaModificataMessageBody
    {
        public string Provenienza { get; set; } = "DomandaOnLine";
        public int IdDomanda { get; set; }
        public string IdentificativoDomanda { get; set; } = "";
        public string CodiceFiscaleUtente { get; set; } = "";
        public DateTime UltimaModifica { get; set; } = DateTime.Now;
        public string Richiedente { get; set; } = "";
        public string TipoIntervento { get; set; } = "";
        public string Oggetto { get; set; } = "";
        public bool Eliminabile { get; set; } = true;
        public DomandaInBozzaTag[] Tags { get; set; } = Array.Empty<DomandaInBozzaTag>();
    }

    public class MessaggioDomandaInBozzaModificata : RabbitMessage<DomandaInBozzaModificataMessageBody>
    {
        public static readonly string TopicKey = "domande-on-line.domande-in-bozza.modificata";
    }
}
