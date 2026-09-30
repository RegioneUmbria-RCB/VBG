namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.GetElencoFascicoli
{
    public class GetElencoFascicoliRequest
    {
        public string Token { get; set; }
        public int? Anno { get; set; }
        public string Classifica { get; set; }
        public string Numero { get; set; }
        public string Oggetto { get; set; }
    }
}
