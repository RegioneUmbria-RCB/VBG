namespace Sigepro.net.Api.NotificaFirma
{
    public class NotificaFirmaResponse
    {
        public string esito { get; internal set; }
        public string descrizione { get; internal set; }

        public static NotificaFirmaResponse OK()
        {
            return new NotificaFirmaResponse
            {
                esito = "OK",
                descrizione = string.Empty
            };
        }

        public static NotificaFirmaResponse KO(string message)
        {
            return new NotificaFirmaResponse
            {
                esito = "KO",
                descrizione = message
            };
        }
    }
}