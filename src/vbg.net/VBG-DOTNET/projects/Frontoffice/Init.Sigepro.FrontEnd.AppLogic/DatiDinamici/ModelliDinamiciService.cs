using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.DataAccess;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.SchedeCollegate;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici.Sincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.SIGePro.Manager.DTO.DatiDinamici;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Standard.Scripts;
using VBG.DatiDinamici.Standard.Utils.CreazioneModelli;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici
{

    public class ModelliDinamiciService : IModelliDinamiciService
    {
        private readonly IAliasResolver _aliasResolver;
        private readonly IConfigurazione<ParametriSchedaCittadiniExtracomunitari> _configurazioneSchedaCittadiniEC;
        private readonly IDatiDinamiciRepository _datiDinamiciRepository;
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
        private readonly ITokenApplicazioneService _tokenApplicazioneService;
        private readonly IIstanzaSigeproAdapterService _istanzaSigeproAdapterService;
        private readonly IModelliDinamiciFactory _modelliDinamiciFactory;
        private readonly IStrutturaModelloDinamicoRepository _strutturaModelloDinamicoRepository;
        private readonly ISchedeCollegateService _schedeCollegateService;

        public ModelliDinamiciService(ISalvataggioDomandaStrategy salvataggioDomandaStrategy, IAliasResolver aliasResolver,
                                        IDatiDinamiciRepository datiDinamiciRepository,
                                        IConfigurazione<ParametriSchedaCittadiniExtracomunitari> configurazioneSchedaCittadiniEC,
                                        ITokenApplicazioneService tokenApplicazioneService,
                                        IIstanzaSigeproAdapterService istanzaSigeproAdapterService, IModelliDinamiciFactory modelliDinamiciFactory,
                                        IStrutturaModelloDinamicoRepository strutturaModelloDinamicoRepository, ISchedeCollegateService schedeCollegateService)
        {
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
            this._aliasResolver = aliasResolver;
            this._datiDinamiciRepository = datiDinamiciRepository;
            this._configurazioneSchedaCittadiniEC = configurazioneSchedaCittadiniEC;
            this._tokenApplicazioneService = tokenApplicazioneService;
            this._istanzaSigeproAdapterService = istanzaSigeproAdapterService;
            this._modelliDinamiciFactory = modelliDinamiciFactory;
            this._strutturaModelloDinamicoRepository = strutturaModelloDinamicoRepository;
            this._schedeCollegateService = schedeCollegateService;
        }



        public void EliminaModello(int idDomanda, int idModello, int indice)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            var modello = this.GetModelloDinamico(domanda, idModello, indice);

            modello.Elimina();

            domanda.WriteInterface.RiepiloghiSchedeDinamiche.EliminaByIdModello(idModello);

            this._salvataggioDomandaStrategy.Salva(domanda);
        }



        public IEnumerable<int> GetIndiciScheda(int idDomanda, int idScheda)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            var strutturaModello = this._strutturaModelloDinamicoRepository.GetStrutturaModelloDinamico(idScheda);

            return domanda.ReadInterface.DatiDinamici.GetIndiciSchede(strutturaModello);
        }

        public ModelloDinamicoIstanza GetModelloDinamico(int idDomanda, int idScheda, int indiceScheda)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            return this.GetModelloDinamico(domanda, idScheda, indiceScheda);
        }

        public async Task<ModelloDinamicoIstanza> GetModelloDinamicoAsync(int idDomanda, int idScheda, int indiceScheda)
        {
            var domanda = await this._salvataggioDomandaStrategy.GetByIdAsync(idDomanda);

            return this.GetModelloDinamico(domanda, idScheda, indiceScheda);
        }

        private ModelloDinamicoIstanza GetModelloDinamico(DomandaOnline domanda, int idScheda, int indiceScheda)
        {
            var loader = this.CreaLoader(domanda, idScheda);
            var scheda = this._modelliDinamiciFactory.CreaModelloIstanza(loader, idScheda, indiceScheda, false);

            return scheda;
        }

        private ModelloDinamicoLoader CreaLoader(DomandaOnline domanda, int idScheda)
        {
            var istanza = this._istanzaSigeproAdapterService.ToIstanzaBackoffice(domanda.ReadInterface, new IstanzaSigeproAdapterFlags
            {
                AggiungiPdfSchedeAListaAllegati = false,
                CalcolaMD5Oggetti = false
            });
            var cache = this._strutturaModelloDinamicoRepository.GetStrutturaModelloDinamico(idScheda);
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


        public void InvalidaRiepiloghi(int idDomanda, int idModello)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            // Elimino eventuali riepiloghi delle schede
            domanda.WriteInterface.RiepiloghiSchedeDinamiche.EliminaByIdModello(idModello);

            // Faccio persistere i dati della domanda
            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        public EsitoSalvataggioModelloDinamico Salva(int idDomanda, ModelloDinamicoBase modelloDinamico)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            var idModello = modelloDinamico.IdModello;

            //var cache = this._datiDinamiciRepository.GetCacheModelloDinamico(idModello);
            //var istanza = this._istanzaSigeproAdapterService.ToIstanzaBackoffice(domanda.ReadInterface);
            //var dap = new DomandaOnlineDataAccessFactory(cache, domanda, this._tokenApplicazioneService, istanza);
            //var loader = new ModelloDinamicoLoader(dap, domanda.DataKey.IdComune, ContestoScriptEnum.Frontoffice);
            var loader = this.CreaLoader(domanda, idModello);

            modelloDinamico.LegacySetLoader(loader);
            modelloDinamico.Salva();

            if (modelloDinamico.ErroriScript.Any())
                throw new SalvataggioModelloDinamicoException(modelloDinamico.ErroriScript);

            // Salvo lo stato dei campi eventualmente non visibili
            modelloDinamico.SalvaCampiNonVisibili();

            // Elimino eventuali riepiloghi delle schede
            domanda.WriteInterface.RiepiloghiSchedeDinamiche.EliminaByIdModello(idModello);

            // Marco eventuali schede collegate come non compilate
            var schedeDaRicompilare = this._schedeCollegateService.MarcaSchedeCollegateComeNonCompilate(domanda, idModello);

            // Faccio persistere i dati della domanda
            this._salvataggioDomandaStrategy.Salva(domanda);

            return new EsitoSalvataggioModelloDinamico(schedeDaRicompilare);
        }

        public void SincronizzaModelliDinamici(int idDomanda, bool ignoraSchedaCittadinoExtracomunitario)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            var alias = domanda.DataKey.IdComune;
            var idIntervento = domanda.ReadInterface.AltriDati.Intervento.Codice;
            var endoSelezionati = domanda.ReadInterface.Endoprocedimenti.NonAcquisiti.Select(x => x.Codice).ToList();
            var tipiLocalizzazioni = domanda.ReadInterface.Localizzazioni.Indirizzi.Select(x => x.TipoLocalizzazione).Distinct();

            var schedeDinamicheRichieste = this._datiDinamiciRepository.GetSchedeDaInterventoEEndo(idIntervento, endoSelezionati, tipiLocalizzazioni, UsaTipiLocalizzazioniPerSelezionareSchedeDinamiche.Si);

            var schedeintervento = schedeDinamicheRichieste.SchedeIntervento.Select(x => new ModelloDinamicoInterventoDaSincronizzare(x.CodiceIntervento, x.Id, x.Descrizione, x.TipoFirma, x.Facoltativa, x.Ordine.GetValueOrDefault(999)));

            var schedeEndo = schedeDinamicheRichieste.SchedeEndoprocedimenti.Select(x => new ModelloDinamicoEndoprocedimentoDaSincronizzare(x.CodiceEndo, x.Id, x.Descrizione, x.TipoFirma, x.Facoltativa, x.Ordine.GetValueOrDefault(999)));



            var richiedenteIsExtracomunitario = false;

            if (domanda.ReadInterface.Anagrafiche != null && domanda.ReadInterface.Anagrafiche.GetRichiedente() != null)
            {
                richiedenteIsExtracomunitario = domanda.ReadInterface.Anagrafiche.GetRichiedente().IsCittadinoExtracomunitario;
            }

            ModelloDinamicoPerCittadiniExtracomunitariDaSincronizzare modelloCittadiniExtracomunitari = null;

            if (!ignoraSchedaCittadinoExtracomunitario && richiedenteIsExtracomunitario && this._configurazioneSchedaCittadiniEC.Parametri.EsisteSchedaDinamicaPerCittadiniExtracomunitari)
            {
                modelloCittadiniExtracomunitari = new ModelloDinamicoPerCittadiniExtracomunitariDaSincronizzare(
                    this._configurazioneSchedaCittadiniEC.Parametri.IdSchedaDinamica.Value,
                    this._configurazioneSchedaCittadiniEC.Parametri.NomeScheda,
                    this._configurazioneSchedaCittadiniEC.Parametri.RichiedeFirma ? TipoFirmaEnum.SingoliBlocchi : TipoFirmaEnum.NessunaFirma,
                    !this._configurazioneSchedaCittadiniEC.Parametri.RichiedeFirma
                );
            }

            var cmd = new SincronizzaModelliDinamiciCommand(schedeintervento, schedeEndo, modelloCittadiniExtracomunitari);

            domanda.WriteInterface.DatiDinamici.SincronizzaModelliDinamici(cmd);

            this._salvataggioDomandaStrategy.Salva(domanda);
        }


    }
}
