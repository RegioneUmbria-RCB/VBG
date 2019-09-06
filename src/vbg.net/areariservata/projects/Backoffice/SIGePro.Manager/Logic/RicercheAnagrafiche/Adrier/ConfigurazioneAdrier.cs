namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.Adrier
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using log4net;
    using PersonalLib2.Data;
    using Init.SIGePro.Verticalizzazioni;
    using Init.SIGePro.Exceptions.Protocollo;
    using System.Configuration;

    public class ConfigurazioneAdrier
    {
        ILog _log = LogManager.GetLogger(typeof(ConfigurazioneAdrier));

        public class ParametriInizializzazione
        {
            public string IdComune { get; set; }
            public string IdComuneAlias { get; set; }
            public DataBase Database { get; set; }
        }
        Func<ParametriInizializzazione, ParametriInizializzazione> _initFunc;


        internal ConfigurazioneAdrier(Func<ParametriInizializzazione, ParametriInizializzazione> initFunc)
        {
            this._initFunc = initFunc;
        }

        VerticalizzazioneWsanagrafeAdrier _verticalizzazioneAdrier;

        public VerticalizzazioneWsanagrafeAdrier Get
        {
            get
            {
                if (_verticalizzazioneAdrier == null)
                {
                    _verticalizzazioneAdrier = GetVerticalizzazioneWsAnagrafeAdrier();
                    //_xmlValidator = new ParixXmlValidator(_verticalizzazioneParix.Xsd);
                }

                return _verticalizzazioneAdrier;
            }
        }

        public bool IsVerticalizzazioneAttiva
        {
            get
            {
                try
                {
                    var parametri = this._initFunc(new ParametriInizializzazione());

                    var vert = new VerticalizzazioneWsanagrafeAdrier(parametri.IdComuneAlias, "TT");

                    return vert.Attiva;
                }
                catch (Exception ex)
                {
                    var errore = String.Format("Errore durante la lettura della verticalizzazione WSANAGRAFE_PARIX: {0}", ex.ToString());
                    _log.Error(errore);
                    throw new Exception(errore, ex);
                }
            }
        }

        private VerticalizzazioneWsanagrafeAdrier GetVerticalizzazioneWsAnagrafeAdrier()
        {
            try
            {
                var parametri = this._initFunc(new ParametriInizializzazione());

                var vert = new VerticalizzazioneWsanagrafeAdrier(parametri.IdComuneAlias, "TT");

                if (!vert.Attiva)
                {
                    throw new ConfigurationException("La verticalizzazione ADRIER non è attiva.\r\n");
                }

                return vert;
            }
            catch (Exception ex)
            {
                var errore = String.Format("Errore durante la lettura della verticalizzazione WSANAGRAFE_PARIX: {0}", ex.ToString());
                _log.Error(errore);
                throw new Exception(errore, ex);
            }
        }
    }
}
