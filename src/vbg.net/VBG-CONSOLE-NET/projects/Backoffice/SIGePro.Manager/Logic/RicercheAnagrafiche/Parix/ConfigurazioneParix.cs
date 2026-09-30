// -----------------------------------------------------------------------
// <copyright file="ConfigurazioneParix.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.Parix
{
    using Init.SIGePro.Verticalizzazioni;
    using log4net;
    using PersonalLib2.Data;
    using System;
    using System.Linq;

    internal class ConfigurazioneParix
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(ConfigurazioneParix));

        public class ParametriInizializzazione
        {
            public string IdComune { get; set; }
            public string IdComuneAlias { get; set; }
            public DataBase Database { get; set; }
        }

        private readonly Lazy<VerticalizzazioneWsanagrafeParix> _verticalizzazioneParix;

        internal ConfigurazioneParix(Func<ParametriInizializzazione, ParametriInizializzazione> initFunc)
        {
            this._verticalizzazioneParix = new Lazy<VerticalizzazioneWsanagrafeParix>(() => this.GetVerticalizzazioneWsAnagrafeParix(initFunc));
        }

        public bool IsVerticalizzazioneAttiva => this._verticalizzazioneParix.Value.Attiva;

        public string Xsd => this._verticalizzazioneParix.Value.Xsd;

        public bool CercaSoloCf => this._verticalizzazioneParix.Value.CercaSoloCf;

        public string BasicAuthUser => this._verticalizzazioneParix.Value.BasicAuthUser;
        public string BasicAuthPassword => this._verticalizzazioneParix.Value.BasicAuthPassword;

        public string Switchcontrol => this._verticalizzazioneParix.Value.Switchcontrol;

        public string User => this._verticalizzazioneParix.Value.User;

        public string Password => this._verticalizzazioneParix.Value.Password;

        public string Url => this._verticalizzazioneParix.Value.Url;

        public string ProxyAddress => this._verticalizzazioneParix.Value.ProxyAddress;

        public bool UsaProxy => !String.IsNullOrEmpty(this.ProxyAddress);

        public bool UsaCertificatoClient => !String.IsNullOrEmpty(this.PathCertificatoClient);
        public string PathCertificatoClient => this._verticalizzazioneParix.Value.PathCertificatoClient.IndexOf('|') != -1 ?
                                                    this._verticalizzazioneParix.Value.PathCertificatoClient.Split('|').First() :
                                                    this._verticalizzazioneParix.Value.PathCertificatoClient;

        public string PasswordCertificatoClient => this._verticalizzazioneParix.Value.PathCertificatoClient.IndexOf('|') != -1 ?
                                                    this._verticalizzazioneParix.Value.PathCertificatoClient.Split('|').ElementAt(1) :
                                                    "";


        private VerticalizzazioneWsanagrafeParix GetVerticalizzazioneWsAnagrafeParix(Func<ParametriInizializzazione, ParametriInizializzazione> initFunc)
        {
            try
            {
                ParametriInizializzazione parametri = initFunc(new ParametriInizializzazione());

                VerticalizzazioneWsanagrafeParix vert = new VerticalizzazioneWsanagrafeParix(parametri.IdComuneAlias, "TT");

                return vert;
            }
            catch (Exception ex)
            {
                var errore = String.Format("Errore durante la lettura della verticalizzazione WSANAGRAFE_PARIX: {0}", ex.ToString());
                this._log.Error(errore, ex);
                throw;
            }
        }
    }
}
