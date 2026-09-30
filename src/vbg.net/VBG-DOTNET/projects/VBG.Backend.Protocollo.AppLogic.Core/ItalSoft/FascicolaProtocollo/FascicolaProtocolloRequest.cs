namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.FascicolaProtocollo
{
    public class FascicolaProtocolloRequest
    {
        public string Token { get; set; }
        public string AnnoProtocollo { get; set; }
        public string NumeroProtocollo { get; set; }
        public string TipoProtocollo { get; set; }
        public string Fascicolo { get; set; }
        public string SottoFascicolo { get; set; }
    }
}
