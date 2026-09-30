namespace VBG.AspnetCore.Agent.AppLogic.GestioneMessaggi.DomandeInBozza.Contracts
{
    public class DomandaInBozzaEliminataMessageBody
    {
        public const string ListeningRoutingKey = $"*.{RoutingKeySuffix}";
        public const string RoutingKeySuffix = "domande-in-bozza.eliminata";

        public string Provenienza { get; set; } = "DomandaOnLine";
        public int? IdDomanda { get; set; }
        public string IdentificativoDomanda { get; set; } = "";
    }
}
