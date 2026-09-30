namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft
{
    public class Parametri
    {
        public string UrlProtocollazione { get; set; }
        public string Token { get; set; }
        public string CodiceUfficio { get; set; }
        public bool IndirizziEmailAbilitati { get; set; }
        public string CorpoMail { get; set; }
        public string OggettoMail { get; set; }
        public string DomainCode { get; set; }
        public string UrlFascicolazione { get; internal set; }
    }
}
