using VBG.Backend.Protocollo.Verticalizzazioni.Core;

namespace VBG.Backend.Protocollo.AppLogic.Core.Halley2
{
    public class Parametri
    {
        public string UrlInserisciProtocollo { get; internal set; }
        public string CasellaEmail { get; internal set; }
        public string UserName { get; private set; }
        public string Password { get; private set; }
        public string UsernameAuri { get; private set; }
        public string PasswordAuri { get; private set; }
        public string UrlEstraiProto { get; private set; }
        public string UrlConsultaDoc { get; private set; }
        public string UrlFascicolaProtocollo { get; private set; }
        public string UrlServiziAggiuntivi { get; private set; }


        public static Parametri FromVerticalizzazione(VerticalizzazioneProtocolloHalley2 verticalizzazione) 
        {
            if (verticalizzazione is null)
            {
                throw new ArgumentNullException(nameof(verticalizzazione));
            }

            return new Parametri
            {
                UrlInserisciProtocollo = verticalizzazione.Url,
                CasellaEmail = verticalizzazione.CasellaEmail,
                UserName = verticalizzazione.UserName,
                Password = verticalizzazione.Password,
                UsernameAuri = verticalizzazione.UsernameAuri,
                PasswordAuri = verticalizzazione.PasswordAuri,
                UrlEstraiProto = verticalizzazione.UrlEstraiProto,
                UrlConsultaDoc = verticalizzazione.UrlConsultaDoc,
                UrlFascicolaProtocollo = verticalizzazione.UrlFascicolaProtocollo,
                UrlServiziAggiuntivi = verticalizzazione.UrlServiziAggiuntivi
            };
        }

 
    }
}
