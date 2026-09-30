namespace VBG.Backend.Protocollo.AppLogic.Core.Halley2.Fascicolazione
{
    public class Halley2FascicolazioneResolver : IFascicolazioneResolver
    {
        public string UrlFascicolaProtocollo { get; }
        public string UrlServiziAggiuntivi {  get; }
        public string Username { get; }
        public string Password { get; }

        public Halley2FascicolazioneResolver(Parametri parametri)
        {
            this.UrlFascicolaProtocollo = parametri.UrlFascicolaProtocollo;
            this.UrlServiziAggiuntivi = parametri.UrlServiziAggiuntivi;
            this.Username = parametri.UserName;
            this.Password = parametri.Password;
        }
    }
}
