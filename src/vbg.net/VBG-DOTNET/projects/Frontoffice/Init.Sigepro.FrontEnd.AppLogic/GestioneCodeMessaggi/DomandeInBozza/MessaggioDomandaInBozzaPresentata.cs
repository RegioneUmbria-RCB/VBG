using Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi.Infrastructure;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi.DomandeInBozza
{
    public class DomandaInBozzaPresentataMessageBody
    {
        public string Provenienza { get; set; } = "DomandaOnLine";
        public int IdDomanda { get; set; }
        public string IdentificativoDomanda { get; set; } = "";
    }

    public class MessaggioDomandaInBozzaPresentata : RabbitMessage<DomandaInBozzaPresentataMessageBody>
    {
        public static readonly string TopicKey = "domande-on-line.domande-in-bozza.presentata";
    }

}
