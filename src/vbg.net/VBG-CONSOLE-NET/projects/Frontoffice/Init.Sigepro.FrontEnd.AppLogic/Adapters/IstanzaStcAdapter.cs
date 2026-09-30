using Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.StrutturaModelli;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.StcService;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters
{
    public interface IIstanzaStcAdapter
    {
        DettaglioPraticaType Adatta(DomandaOnline domandaFo);
    }


    internal class IstanzaStcAdapter : IIstanzaStcAdapter
    {
        private readonly ITipiSoggettoService _tipiSoggettoService;
        private readonly ICodiceAccreditamentoHelper _codiceAccreditamentoHelper;
        private readonly IStrutturaModelloReader _strutturaModelloReader;
        private readonly IConfigurazione<ParametriStc> _parametriStc;
        private readonly IFormeGiuridicheRepository _formeGiuridicheRepository;
        private readonly IAliasResolver _aliasResolver;

        public IstanzaStcAdapter(IAliasResolver aliasResolver, ITipiSoggettoService tipiSoggettoRepository, ICodiceAccreditamentoHelper codiceAccreditamentoHelper, IStrutturaModelloReader strutturaModelloReader, IConfigurazione<ParametriStc> parametriStc, IFormeGiuridicheRepository formeGiuridicheRepository)
        {
            this._aliasResolver = aliasResolver;
            this._tipiSoggettoService = tipiSoggettoRepository;
            this._codiceAccreditamentoHelper = codiceAccreditamentoHelper;
            this._strutturaModelloReader = strutturaModelloReader;
            this._parametriStc = parametriStc;
            this._formeGiuridicheRepository = formeGiuridicheRepository;
        }


        public DettaglioPraticaType Adatta(DomandaOnline domandaFo)
        {
            var readInterface = domandaFo.ReadInterface;
            var dettaglioPratica = new DettaglioPraticaType();


            var adapters = new IStcPartialAdapter[]
            {
                new DatiPraticaAdapter(),
                new ComuniAssociatiAdapter(),
                new RichiedenteAdapter(),
                new AziendaAdapter(this._tipiSoggettoService, this._formeGiuridicheRepository),
                new TecnicoAdapter(this._formeGiuridicheRepository),
                new AltriSoggettiAdapter(this._parametriStc,this._formeGiuridicheRepository),
                new ProcureAdapter(this._aliasResolver),
                new LocalizzazioneAdapter(),
                new DocumentiAdapter(this._aliasResolver),
                new AltriDatiAdapter(this._codiceAccreditamentoHelper),
                new ProcedimentiAdapter(this._aliasResolver),
                new OneriAdapter(),
                new DatiDinamiciAdapter(this._strutturaModelloReader)
            };

            foreach (var adapter in adapters)
                adapter.Adapt(readInterface, dettaglioPratica);

            return dettaglioPratica;
        }


    }
}
