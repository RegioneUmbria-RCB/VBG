using log4net;
using PersonalLib2.Data;
using SIGePro.Manager.Verticalizzazioni;
using SIGePro.Manager.VerticalizzazioniBase;
using System;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.Adrier
{
    public class ConfigurazioneAdrier
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(ConfigurazioneAdrier));

        public class ParametriInizializzazione
        {
            public string IdComune { get; set; }
            public string IdComuneAlias { get; set; }
            public DataBase Database { get; set; }
        }

        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly Func<ParametriInizializzazione, ParametriInizializzazione> _initFunc;


        internal ConfigurazioneAdrier(IVerticalizzazioniFactory verticalizzazioniFactory, Func<ParametriInizializzazione, ParametriInizializzazione> initFunc)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._initFunc = initFunc;
        }

        private VerticalizzazioneWsanagrafeAdrier _verticalizzazioneAdrier;

        public VerticalizzazioneWsanagrafeAdrier Get
        {
            get
            {
                if (this._verticalizzazioneAdrier == null)
                {
                    this._verticalizzazioneAdrier = this.GetVerticalizzazioneWsAnagrafeAdrier();
                }

                return this._verticalizzazioneAdrier;
            }
        }

        public bool IsVerticalizzazioneAttiva
        {
            get
            {
                try
                {
                    var parametri = this._initFunc(new ParametriInizializzazione());

                    var vert = this._verticalizzazioniFactory.Create<VerticalizzazioneWsanagrafeAdrier>(parametri.IdComuneAlias, "TT");

                    return vert.Attiva;
                }
                catch (Exception ex)
                {
                    var errore = String.Format("Errore durante la lettura della verticalizzazione WSANAGRAFE_PARIX: {0}", ex.ToString());
                    this._log.Error(errore, ex);
                    throw;
                }
            }
        }

        private VerticalizzazioneWsanagrafeAdrier GetVerticalizzazioneWsAnagrafeAdrier()
        {
            try
            {
                var parametri = this._initFunc(new ParametriInizializzazione());

                var vert = this._verticalizzazioniFactory.Create<VerticalizzazioneWsanagrafeAdrier>(parametri.IdComuneAlias, "TT");

                if (!vert.Attiva)
                {
                    throw new InvalidOperationException("La verticalizzazione ADRIER non è attiva.\r\n");
                }

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
