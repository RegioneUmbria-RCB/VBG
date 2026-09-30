namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.GetFascicoliProtocollo
{
    public class GetFascicoliProtocolloRequest
    {
        public string Token { get; set; }
        public string AnnoProtocollo { get; set; }
        public string NumeroProtocollo { get; set; }
        public string TipoProtocollo { get; set; }
    }
}
