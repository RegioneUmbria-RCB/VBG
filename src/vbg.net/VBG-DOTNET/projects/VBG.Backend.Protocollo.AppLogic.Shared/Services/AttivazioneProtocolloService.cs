using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using SIGePro.Manager.VerticalizzazioniBase;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.Verticalizzazioni.Shared;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Services
{
    public class AttivazioneProtocolloService
    {
        public class AttivazioneProtocolloException : Exception
        {
            public AttivazioneProtocolloException(string message) : base(message)
            {
            }
            public AttivazioneProtocolloException(string message, Exception innerException) : base(message, innerException)
            {
            }
        }

        private readonly ILog _log;
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly IBindingFactory _bindingFactory;

        public AttivazioneProtocolloService(ILog log, IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory)
        {
            this._log = log ?? throw new ArgumentNullException(nameof(log));
            this._verticalizzazioniFactory = verticalizzazioniFactory ?? throw new ArgumentNullException(nameof(verticalizzazioniFactory));
            this._bindingFactory = bindingFactory;
        }

        public bool IsProtocolloSupportato(IVerticalizzazioneProtocollo verticalizzazione)
        {
            try
            {
                var tipoProtocollo = this.GetTipoProtocollo(verticalizzazione);
                return tipoProtocollo == TipiProtocollo.NESSUNO || TipiProtocolloRegistry.Istance.IsProtocolloSupportato(tipoProtocollo);
            }
            catch (AttivazioneProtocolloException ex)
            {
                this._log.ErrorFormat("ERRORE DURANTE LA VERIFICA DEL PROTOCOLLO SUPPORTATO: {0}", ex.Message);
                throw;
            }
        }

        private ProtocolloBase AttivaProtocolloInternal(IVerticalizzazioneProtocollo verticalizzazione, ResolveDatiProtocollazioneService datiProtocollazione)
        {
            var tipoProtocollo = this.GetTipoProtocollo(verticalizzazione);

            if (tipoProtocollo == TipiProtocollo.NESSUNO)
            {
                throw new AttivazioneProtocolloException("NON E' IMPOSTATO UN PROTOCOLLO ATTIVO");
            }

            var istanzaProtocollo = TipiProtocolloRegistry.Istance.GetProtocolloIstance(this._verticalizzazioniFactory, _bindingFactory, tipoProtocollo.ToString());

            if (istanzaProtocollo == null)
            {
                throw new AttivazioneProtocolloException($"Non è stato possibile trovare il protocollo {tipoProtocollo}");
            }

            istanzaProtocollo.InizializzaProtocolloBase(datiProtocollazione);

            return istanzaProtocollo;
        }

        public ProtocolloBase AttivaProtocollo(VerticalizzazioneProtocolloAttivo verticalizzazione, ResolveDatiProtocollazioneService datiProtocollazione)
        {
            return this.AttivaProtocolloInternal(verticalizzazione, datiProtocollazione);
        }

        public ProtocolloBase AttivaProtocolloStorico(VerticalizzazioneProtocolloStorico verticalizzazione, ResolveDatiProtocollazioneService datiProtocollazione)
        {
            return this.AttivaProtocolloInternal(verticalizzazione, datiProtocollazione);
        }

        private TipiProtocollo GetTipoProtocollo(IVerticalizzazioneProtocollo verticalizzazione)
        {
            try
            {
                this._log.DebugFormat("VERTICALIZZAZIONE ATTIVA: {0} ({1})", verticalizzazione.Attiva, verticalizzazione.NomeVerticalizzazione);

                if (!verticalizzazione.Attiva)
                {
                    throw new AttivazioneProtocolloException($"LA VERTICALIZZAZIONE {verticalizzazione.NomeVerticalizzazione} NON E' ATTIVA");
                }

                if (String.IsNullOrEmpty(verticalizzazione.Tipoprotocollo))
                {
                    throw new AttivazioneProtocolloException($"LA VERTICALIZZAZIONE {verticalizzazione.NomeVerticalizzazione} NON HA IL PARAMETRO TIPOPROTOCOLLO SPECIFICATO!");
                }

                this._log.DebugFormat("VERTICALIZZAZIONE {0} ATTIVA {0}", verticalizzazione.NomeVerticalizzazione, verticalizzazione.Attiva);

                return Enum.TryParse<TipiProtocollo>(verticalizzazione.Tipoprotocollo, false, out var result) ? result : throw new AttivazioneProtocolloException($"Tipo protocollo {verticalizzazione.Tipoprotocollo} non supportato");
            }
            catch (Exception ex)
            {
                throw new AttivazioneProtocolloException("ERRORE GENERATO DURANTE L'ATTIVAZIONE DEL PROTOCOLLO", ex);
            }
        }
    }
}
