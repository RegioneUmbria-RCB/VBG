namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.NotificaMailProtocollo
{
    public class NotificaMailProtocolloRequest
    {
        public string Token { get; set; }
        public string Numero { get; set; }
        public string Anno { get; set; }
        public string Tipo { get; set; }
        public string Oggetto { get; set; }
        public string Corpo { get; set; }
    }
}