using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.DataAccess;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using VBG.AppLogic.SSU.APICatalogoServizi.Client;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Standard.Scripts;
using VBG.DatiDinamici.Standard.Utils.CreazioneModelli;

namespace VBG.AppLogic.SSU.GestioneDatiDinamici
{

    public class SsuDatiDinamiciLoader
    {
        private readonly ISalvataggioDomandaStrategy _persistenzaStrategy;
        private readonly IIstanzaSigeproAdapterService _istanzaSigeproAdapterService;
        private readonly ITokenApplicazioneService _tokenApplicazioneService;
        private readonly IAliasResolver _aliasResolver;
        private readonly IModelliDinamiciFactory _modelliDinamiciFactory;

        public SsuDatiDinamiciLoader(ISalvataggioDomandaStrategy persistenzaStrategy, IIstanzaSigeproAdapterService istanzaSigeproAdapterService,
                                    ITokenApplicazioneService tokenApplicazioneService, IAliasResolver aliasResolver,
                                    IModelliDinamiciFactory modelliDinamiciFactory)
        {
            this._persistenzaStrategy = persistenzaStrategy;
            this._istanzaSigeproAdapterService = istanzaSigeproAdapterService;
            this._tokenApplicazioneService = tokenApplicazioneService;
            this._aliasResolver = aliasResolver;
            this._modelliDinamiciFactory = modelliDinamiciFactory;
        }

        public ModelloDinamicoIstanza Load(int idDomanda, SchedaDinamica scheda)
        {
            var domanda = this._persistenzaStrategy.GetById(idDomanda);

            var loader = this.CreaLoader(domanda, scheda);

            var modello = this._modelliDinamiciFactory.CreaModelloIstanza(loader, scheda.Id!.Value, 0, false);

            return modello;
        }


        private ModelloDinamicoLoader CreaLoader(DomandaOnline domanda, SchedaDinamica scheda)
        {
            var istanza = this._istanzaSigeproAdapterService.ToIstanzaBackoffice(domanda.ReadInterface, new IstanzaSigeproAdapterFlags
            {
                AggiungiPdfSchedeAListaAllegati = false,
                CalcolaMD5Oggetti = false
            });
            var cache = this.GetStrutturaModelloDinamico(scheda);
            var classeContestoLoader = new DomandaOnLineClasseContestoLoader(istanza);
            var repository = new DatiRepository(domanda);

            var builder = new ModelloDinamicoLoaderBuilder();

            return builder.UsaStruttura(cache)
                           .UsaLoaderClasseContesto(classeContestoLoader)
                           .UsaQueryLocalizzazioni(classeContestoLoader)
                           .UsaRepository(repository)
                           .UsaToken(this._tokenApplicazioneService.GetToken())
                           .Build(this._aliasResolver.AliasComune, ContestoScriptEnum.Frontoffice);
        }

        private IStrutturaModelloDinamico GetStrutturaModelloDinamico(SchedaDinamica scheda)
        {
            return scheda.ToStrutturaModelloDinamico();
        }



    }
}
