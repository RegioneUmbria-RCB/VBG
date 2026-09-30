namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.PutAllegato
{
    public class PutAllegatoRequest : AllegatoProtocollo
    {
        public string Token { get; set; }
        public string AnnoProtocollo { get; set; }
        public string NumeroProtocollo { get; set; }
        public string TipoProtocollo { get; set; }
    }
}
