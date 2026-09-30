using System;
using System.Linq;

namespace SIGePro.Manager.Verticalizzazioni
{
    public class VerticalizzazioneWsAnagrafeParixCloud : VerticalizzazioneWsanagrafeParix
    {
        private static class Constants
        {
            public const string NomeVerticalizzazione = "WSANAGRAFE_PARIX_CLOUD";
            public const string TargetNamespace = "TARGET_NAMESPACE";
        }

        public override string NomeVerticalizzazione => Constants.NomeVerticalizzazione;

        public VerticalizzazioneWsAnagrafeParixCloud() : base() { }
        public VerticalizzazioneWsAnagrafeParixCloud(string idComuneAlias, string software) : base(idComuneAlias, software) { }

        public bool UsaProxy => !String.IsNullOrEmpty(this.ProxyAddress);

        public bool UsaCertificatoClient => !String.IsNullOrEmpty(this.GetPathCertificatoClient());

        public string GetPathCertificatoClient() => this.PathCertificatoClient.IndexOf('|') != -1 ?
                                                    this.PathCertificatoClient.Split('|').First() :
                                                    this.PathCertificatoClient;

        public string GetPasswordCertificatoClient() => this.PathCertificatoClient.IndexOf('|') != -1 ?
                                            this.PathCertificatoClient.Split('|').ElementAt(1) :
                                            "";

        public string TargetNamespace => this.GetString(Constants.TargetNamespace);
    }
}
