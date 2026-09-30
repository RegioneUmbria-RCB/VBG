using VBG.Backend.Protocollo.Verticalizzazioni.Core;

namespace VBG.Backend.Protocollo.AppLogic.Core.Kibernetes
{
    public class ParametriService : IParametriService
    {
        public ParametriService(VerticalizzazioneProtocolloKibernetes vertProtocolloKibernetes)
        {
            switch (vertProtocolloKibernetes.Versione)
            {
                case 2:
                    {
                        this.Versione = VersioneEnum.VERSIONE_2;
                        break;
                    }
                default:
                    {
                        this.Versione = VersioneEnum.VERSIONE_1;
                        break;
                    }
            }

            this.UserName = vertProtocolloKibernetes.Username;
            this.Url = vertProtocolloKibernetes.Url;
            this.Password = vertProtocolloKibernetes.Password;
            this.IstatEnte = vertProtocolloKibernetes.CodiceIstat;
            this.UfficioProtocollante = vertProtocolloKibernetes.UfficioProtocollante;
            this.UsaRuoloInEntrata = vertProtocolloKibernetes.UsaRuoloInEntrata;
            this.UsaRuoloInUscita = vertProtocolloKibernetes.UsaRuoloInUscita;
        }
        public VersioneEnum Versione { get; }

        public string UserName { get; }

        public string Url { get; }

        public string Password { get; }

        public long? IstatEnte { get; }

        public string UfficioProtocollante { get; }

        public bool UsaRuoloInEntrata { get; }

        public bool UsaRuoloInUscita { get; }
    }
}
