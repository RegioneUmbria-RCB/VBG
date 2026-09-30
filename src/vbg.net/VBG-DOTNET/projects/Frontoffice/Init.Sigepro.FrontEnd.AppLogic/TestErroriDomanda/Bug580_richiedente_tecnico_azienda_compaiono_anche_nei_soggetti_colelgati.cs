using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda.Repositories;
using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;

namespace Init.Sigepro.FrontEnd.AppLogic.TestErroriDomanda
{
    public class Bug580_richiedente_tecnico_azienda_compaiono_anche_nei_soggetti_colelgati
    {
        private readonly IDatiDomandaFoRepository _datiDomandaFoRepository;
        private readonly IEventDispatcher _eventsDispatcher;

        public Bug580_richiedente_tecnico_azienda_compaiono_anche_nei_soggetti_colelgati(IDatiDomandaFoRepository datiDomandaFoRepository,
                                                                                          IEventDispatcher eventsDispatcher)
        {
            this._datiDomandaFoRepository = datiDomandaFoRepository;
            this._eventsDispatcher = eventsDispatcher;

        }

        public void Test(string idComune, string software, int idDomanda)
        {
            //var aliasSoftwareResolver = new TestAliasSoftwareResolver(idComune, software);

            //var adapter			= new IstanzaStcAdapter(idComune, software);
            //var domandaFoLoader	= new SalvataggioDirettoStrategy(aliasSoftwareResolver, _authenticationDataResolver,
            //                                                    _datiDomandaFoRepository,  
            //                                                    _eventsDispatcher);

            //var	domandaFo		= domandaFoLoader.GetById(idDomanda);

            //var result = adapter.Adatta(domandaFo);

            //Condition.Requires(result.richiedente).IsNotNull();
            //Condition.Requires(result.aziendaRichiedente).IsNotNull();
            //Condition.Requires(result.intermediario).IsNotNull();
            //Condition.Requires(result.altriSoggetti.Length).IsEqualTo( 1 );
        }
    }
}
