namespace VBG.Backend.Protocollo.AppLogic.Core.Halley2.Protocollazione
{
    public class Halley2ProtocollazioneResolver : IProtocollazioneResolver
    {
        public string ProtocollazioneUrl { get; }
        public string CasellaEmail { get; }
        public string UserName { get; }
        public string Password { get; }

        public Halley2ProtocollazioneResolver( Parametri parametri )
        {
            this.ProtocollazioneUrl = parametri.UrlInserisciProtocollo;
            this.CasellaEmail = parametri.CasellaEmail;
            this.UserName = parametri.UserName;
            this.Password = parametri.Password;
        }
    }
}
