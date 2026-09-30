// -----------------------------------------------------------------------
// <copyright file="IriepilogoModelloInHtml.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli
{
    using Init.Sigepro.FrontEnd.AppLogic.Adapters;
    using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
    using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
    using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF;
    using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli.GestioneTemplateSingolaScheda.Configurazione;
    using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo;
    using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;
    using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici.LetturaDaDomandaOnline;
    using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
    using Init.Sigepro.FrontEnd.Infrastructure.Server;

    public class RiepilogoModelloInHtmlFactory : IRiepilogoModelloInHtmlFactory
    {
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
        private readonly IGeneratoreHtmlSchedeDinamiche _generatoreHtml;
        private readonly IDatiDinamiciRepository _datiDinamiciRepository;
        private readonly IIstanzaSigeproAdapterService _istanzaSigeproAdapterService;
        private readonly IPathMapper _pathMapper;
        private readonly IAppConfigurationReader _appConfigurationReader;
        private readonly IConfigurazione<ParametriRiepilogoSingolaScheda> _configurazioneRiepilogo;
        private readonly IHtmlToPdfAsyncFileConverter _fileConverter;
        private readonly IStrutturaModelloDinamicoRepository _strutturaModelloDinamicoRepository;

        public RiepilogoModelloInHtmlFactory(ISalvataggioDomandaStrategy salvataggioDomandaStrategy, IGeneratoreHtmlSchedeDinamiche generatoreHtml,
            IDatiDinamiciRepository datiDinamiciRepository, IIstanzaSigeproAdapterService istanzaSigeproAdapterService, IPathMapper pathMapper,
            IAppConfigurationReader appConfigurationReader, IConfigurazione<ParametriRiepilogoSingolaScheda> configurazioneRiepilogo,
            IHtmlToPdfAsyncFileConverter fileConverter, IStrutturaModelloDinamicoRepository strutturaModelloDinamicoRepository)
        {
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
            this._generatoreHtml = generatoreHtml;
            this._datiDinamiciRepository = datiDinamiciRepository;
            this._istanzaSigeproAdapterService = istanzaSigeproAdapterService;
            this._pathMapper = pathMapper;
            this._appConfigurationReader = appConfigurationReader;
            this._configurazioneRiepilogo = configurazioneRiepilogo;
            this._fileConverter = fileConverter;
            this._strutturaModelloDinamicoRepository = strutturaModelloDinamicoRepository;
        }

        public IRiepilogoModelloInHtml FromDomanda(ISchedeDinamicheDomandaAlRiepilogoService reader, int idModello, int indiceMolteplicita = -1)
        {
            return new RiepilogoModelloInHtmlDaDomanda(this._generatoreHtml, reader, this._pathMapper, this._appConfigurationReader, this._configurazioneRiepilogo, this._fileConverter, idModello, indiceMolteplicita);
        }

        public IRiepilogoModelloInHtml FromIdDomandaOnline(int idDomanda, int idModello, int indiceMolteplicita = -1)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            var reader = new DomandaOnlineDatiDinamiciReader(domanda, this._datiDinamiciRepository, this._strutturaModelloDinamicoRepository, this._istanzaSigeproAdapterService);


            return this.FromDomanda(reader, idModello, indiceMolteplicita);
        }

    }
}
