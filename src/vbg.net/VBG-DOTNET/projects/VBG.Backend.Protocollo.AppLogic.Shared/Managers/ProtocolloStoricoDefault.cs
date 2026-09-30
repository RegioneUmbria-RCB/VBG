using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.Authentication;
using SIGePro.Manager.VerticalizzazioniBase;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.PerEvitareLaDependencyInjection;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.Verticalizzazioni.Shared;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Managers
{
    public class ProtocolloStoricoException : Exception
    {
        public ProtocolloStoricoException(string message) : base(message)
        {
        }
    }


    public class ProtocolloStoricoNonAttivo : IProtocolloStorico
    {
        public readonly static ProtocolloStoricoNonAttivo Instance = new ProtocolloStoricoNonAttivo();

        private ProtocolloStoricoNonAttivo() { }

        public bool ProtocollazioneStoricaAttiva => false;

        public DateTime DataUltimaProtocollazione => throw new ProtocolloStoricoException("Protocollazione storica non attiva");

        public AllegatoResponseType StoricoLeggiAllegato(string IdProtocollo, string numProtocollo, string annoProtocollo, string idAllegato)
        {
            throw new ProtocolloStoricoException("Protocollazione storica non attiva");
        }

        public DatiProtocolloLettoResponseType StoricoLeggiProtocolloConData(string idProtocollo, int annoProtocollo, string numeroProtocollo)
        {
            throw new ProtocolloStoricoException("Protocollazione storica non attiva");
        }
    }


    public class ProtocolloStoricoDefault : IProtocolloStorico
    {


        private VerticalizzazioneProtocolloAttivo? _verticalizzazioneProtocolloAttivo;
        private VerticalizzazioneProtocolloStorico? _verticalizzazioneProtocolloStorico;
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly IBindingFactory _bindingFactory;
        private ProtocolloBase? _protocolloStorico;
        private ProtocolloLogs? _log;

        public ProtocolloStoricoDefault(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._bindingFactory = bindingFactory;
        }

        public void Initialize(AuthenticationInfo authInfo, string software, string codiceComune = "", AmbitoProtocollazioneEnum ambito = AmbitoProtocollazioneEnum.NESSUNO, Istanze istanza = null, Movimenti movimento = null, PecInbox datiPec = null)
        {
            if (String.IsNullOrEmpty(software))
                throw new ArgumentNullException(nameof(software));

            if (authInfo == null)
                throw new ArgumentNullException(nameof(authInfo));

            if (String.IsNullOrEmpty(authInfo.IdComune))
                throw new ArgumentNullException("authInfo.IdComune");

            var dataBase = authInfo.CreateDatabase();

            var idComune = authInfo.IdComune;
            var idComuneAlias = authInfo.Alias;
            var codOperatore = authInfo.CodiceResponsabile;
            var token = authInfo.Token;

            var datiProtocollazione = new ResolveDatiProtocollazioneService(idComune, idComuneAlias, software, dataBase, istanza, movimento, codOperatore, ambito, token, codiceComune, datiPec);

            this.InizializzaVerticalizzazioneProtocolloAttivo(idComuneAlias, software, codiceComune);
            this.InizializzaVerticalizzazioneProtocolloStorico(idComuneAlias, software, codiceComune);

            var factory = ProtocolloFactoryProvider.Factory
                ?? throw new InvalidOperationException("ProtocolloFactory non inizializzata");

            this._log = factory.CreateLogs(datiProtocollazione, this.GetType());

            var attivazioneProtoService = new AttivazioneProtocolloService(this._log, this._verticalizzazioniFactory, this._bindingFactory);
            this._protocolloStorico = attivazioneProtoService.AttivaProtocolloStorico(this._verticalizzazioneProtocolloStorico, datiProtocollazione);
        }

        private void InizializzaVerticalizzazioneProtocolloAttivo(string idComuneAlias, string software, string codiceComune)
        {
            this._verticalizzazioneProtocolloAttivo = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloAttivo>(idComuneAlias, software, codiceComune);
        }

        private void InizializzaVerticalizzazioneProtocolloStorico(string idComuneAlias, string software, string codiceComune)
        {
            this._verticalizzazioneProtocolloStorico = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloStorico>(idComuneAlias, software, codiceComune);
        }


        public bool ProtocollazioneStoricaAttiva => this._verticalizzazioneProtocolloStorico?.Attiva ?? throw new ProtocolloStoricoException("Il protocollo storico default non è inizializzato");
        public DateTime DataUltimaProtocollazione
        {
            get
            {
                return this.GetDataUltimaProtocollazione();
            }
        }

        private DateTime GetDataUltimaProtocollazione()
        {
            if (this._verticalizzazioneProtocolloStorico == null)
            {
                throw new ProtocolloStoricoException("Il protocollo storico non è inizializzato");
            }

            if (!this._verticalizzazioneProtocolloStorico.DataUltimaProtocollazione.HasValue)
            {
                throw new ProtocolloStoricoException("E' ATTIVA LA VERTICALIZZAZIONE DEL PROTOCOLLO STORICO MA NON E' IMPOSTATO IL PARAMETRO DATAULTIMAPROTOCOLLAZIONE. CONTROLLARE LA CONFIGURAZIONE!!");
            }
            return this._verticalizzazioneProtocolloStorico.DataUltimaProtocollazione.Value;
        }

        public AllegatoResponseType StoricoLeggiAllegato(string IdProtocollo, string numProtocollo, string annoProtocollo, string idAllegato)
        {
            if (this._protocolloStorico == null)
            {
                throw new ProtocolloStoricoException("Il protocollo storico non è inizializzato");
            }

            this._protocolloStorico.IdProtocollo = IdProtocollo;
            this._protocolloStorico.NumProtocollo = numProtocollo;
            this._protocolloStorico.AnnoProtocollo = annoProtocollo;
            this._protocolloStorico.IdAllegato = idAllegato;

            this._protocolloStorico.EstraiEml = this._verticalizzazioneProtocolloAttivo.EstraiEml;
            this._protocolloStorico.EstraiZip = this._verticalizzazioneProtocolloAttivo.EstraiZip;
            this._protocolloStorico.EscludiFileDaEml = this._verticalizzazioneProtocolloAttivo.EscludiFilesDaEml.Split('|');
            this._protocolloStorico.ZipExtensions = this._verticalizzazioneProtocolloAttivo.ExtFileZip.Split(',');

            this._log.DebugFormat("Lettura dell'allegato dal protocollo storico, ID Protocollo: {0}, Numero Protocollo: {1}, Anno Protocollo: {2}, Id Allegato: {3}", IdProtocollo, numProtocollo, annoProtocollo, idAllegato);
            var res = this._protocolloStorico.LeggiAllegatoStorico();

            return res;
        }

        public DatiProtocolloLettoResponseType StoricoLeggiProtocolloConData(string idProtocollo, int annoProtocollo, string numeroProtocollo)
        {
            if (this._protocolloStorico == null)
            {
                throw new ProtocolloStoricoException("Il protocollo storico non è inizializzato");
            }

            return this._protocolloStorico.LeggiProtocolloStorico(idProtocollo, annoProtocollo.ToString(), numeroProtocollo);
        }
    }
}
