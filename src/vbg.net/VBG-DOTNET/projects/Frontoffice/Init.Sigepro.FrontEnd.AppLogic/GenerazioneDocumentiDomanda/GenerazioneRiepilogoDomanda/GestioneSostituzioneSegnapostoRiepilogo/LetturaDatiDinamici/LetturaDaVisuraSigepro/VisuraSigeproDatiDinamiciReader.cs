using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.DataAccess.Visura;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Standard.Scripts;
using VBG.DatiDinamici.Standard.Utils.CreazioneModelli;
using VBG.DatiDinamici.VisibilitaCampi;
using VBG.DatiDinamici.WebControls.MaschereCampiNonVisibili;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici.LetturaDaVisuraSigepro
{
    public class VisuraSigeproDatiDinamiciReader : ISchedeDinamicheDomandaAlRiepilogoService
    {
        private readonly Istanze _istanza;
        private readonly IDatiDinamiciRepository _datiDinamiciRepository;
        private readonly IStrutturaModelloDinamicoRepository _strutturaModelloDinamicoRepository;

        public VisuraSigeproDatiDinamiciReader(Istanze istanza, IDatiDinamiciRepository datiDinamiciRepository, IStrutturaModelloDinamicoRepository strutturaModelloDinamicoRepository)
        {
            this._istanza = istanza;
            this._datiDinamiciRepository = datiDinamiciRepository;
            this._strutturaModelloDinamicoRepository = strutturaModelloDinamicoRepository;
        }

        public bool PuoCaricareSchedeNonPresenti => false;
        public bool SupportaCachingCampiNonVisibili => false;


        private class IstanzaLoader : IClasseContestoLoader
        {
            private readonly Istanze _istanza;

            public IstanzaLoader(Istanze istanza)
            {
                this._istanza = istanza;
            }

            public IClasseContestoModelloDinamico LoadClass()
            {
                return JsonConvert.DeserializeObject<Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService.Istanze>(JsonConvert.SerializeObject(this._istanza));
            }
        }

        public ModelloDinamicoLoader CreateLoader(int idScheda, int indiceMolteplicita, ITokenApplicazioneService tokenApplicazioneService)
        {
            var cache = this._strutturaModelloDinamicoRepository.GetStrutturaModelloDinamico(idScheda);
            var classeContestoLoader = new IstanzaLoader(this._istanza);
            var repository = new VisuraDatiRepository(this._istanza);

            var builder = new ModelloDinamicoLoaderBuilder();

            var loader = builder.UsaStruttura(cache)
                           .UsaLoaderClasseContesto(classeContestoLoader)
                           // .UsaQueryLocalizzazioni(classeContestoLoader) // Usa la classe di default che non restituisce localizzazioni
                           .UsaRepository(repository)
                           .UsaToken(tokenApplicazioneService.GetToken())
                           .Build(this.GetIdComune(), ContestoScriptEnum.Frontoffice);

            return loader;
        }

        public CampiNonVisibili GetCampiNonVisibili(int idModello) => new CampiNonVisibili(Enumerable.Empty<IdValoreCampo>());

        public IValoreDatoDinamicoRiepilogo GetCampoDinamico(int idCampoDinamico, int indiceMolteplicita = 0)
        {
            return this._istanza.IstanzeDyn2Dati.Where(x =>
                                        x.FkD2cId.GetValueOrDefault(-1) == idCampoDinamico &&
                                        x.IndiceMolteplicita == indiceMolteplicita)
                                .Select(x => new VisuraValoreDatoDinamicoRiepilogo(x.Valoredecodificato))
                                .FirstOrDefault();
        }

        public int GetCodiceIstanza()
        {
            return Convert.ToInt32(this._istanza.CODICEISTANZA);
        }

        public string GetIdComune()
        {
            return this._istanza.IDCOMUNE;
        }

        public IEnumerable<int> GetIndiciSchede(int idModello)
        {
            var strutturaModello = this._strutturaModelloDinamicoRepository.GetStrutturaModelloDinamico(idModello);

            var indiciCampi = strutturaModello.ListaCampiDinamici.SelectMany(campo =>
            {
                return this._istanza.IstanzeDyn2Dati
                            .Where(x => x.FkD2cId == campo.Key)
                            .Select(x => x.Indice.Value);
            });

            return indiciCampi.DefaultIfEmpty().Distinct();
        }

        public IEnumerable<IModelloDinamicoRiepilogo> GetListaModelli()
        {
            var idIntervento = Convert.ToInt32(this._istanza.CODICEINTERVENTOPROC);
            var endoSelezionati = this._istanza.EndoProcedimenti.Select(x => Convert.ToInt32(x.CODICEINVENTARIO));

            var schedeDinamicheRichieste = this._datiDinamiciRepository.GetSchedeDaInterventoEEndo(idIntervento, endoSelezionati, Enumerable.Empty<string>(), UsaTipiLocalizzazioniPerSelezionareSchedeDinamiche.No);
            var listaSchedeEndo = schedeDinamicheRichieste.SchedeEndoprocedimenti.Select(x => new VisuraModelloDinamicoRiepilogo
            {
                IdModello = x.Id,
                Compilato = true,
                Descrizione = x.Descrizione,
                TipoFirma = (ModelloDinamico.TipoFirmaEnum)(int)x.TipoFirma,
                Ordine = x.Ordine.GetValueOrDefault(9999)
            });

            var listaSchedeintervento = schedeDinamicheRichieste.SchedeIntervento.Select(x => new VisuraModelloDinamicoRiepilogo
            {
                IdModello = x.Id,
                Compilato = true,
                Descrizione = x.Descrizione,
                TipoFirma = (ModelloDinamico.TipoFirmaEnum)(int)x.TipoFirma,
                Ordine = x.Ordine.GetValueOrDefault(9999)
            });

            return listaSchedeintervento.Union(listaSchedeEndo)
                                        .OrderBy(x => x.Ordine)
                                        .ThenBy(x => x.Descrizione);
        }

        public IEnumerable<IModelloDinamicoRiepilogo> GetListaModelliEndo(int idEndo)
        {
            var idIntervento = -1;
            var endoSelezionati = new[] { idEndo };

            var schedeDinamicheRichieste = this._datiDinamiciRepository.GetSchedeDaInterventoEEndo(idIntervento, endoSelezionati, Enumerable.Empty<string>(), UsaTipiLocalizzazioniPerSelezionareSchedeDinamiche.No);

            var listaSchedeintervento = schedeDinamicheRichieste.SchedeEndoprocedimenti.Select(x => new VisuraModelloDinamicoRiepilogo
            {
                IdModello = x.Id,
                Compilato = true,
                Descrizione = x.Descrizione,
                TipoFirma = (ModelloDinamico.TipoFirmaEnum)(int)x.TipoFirma,
                Ordine = x.Ordine.GetValueOrDefault(9999)
            });

            return listaSchedeintervento.OrderBy(x => x.Ordine)
                                        .ThenBy(x => x.Descrizione);
        }

        public IEnumerable<IModelloDinamicoRiepilogo> GetListaModelliIntervento()
        {
            var idIntervento = Convert.ToInt32(this._istanza.CODICEINTERVENTOPROC);
            var endoSelezionati = Enumerable.Empty<int>();

            var schedeDinamicheRichieste = this._datiDinamiciRepository.GetSchedeDaInterventoEEndo(idIntervento, endoSelezionati, Enumerable.Empty<string>(), UsaTipiLocalizzazioniPerSelezionareSchedeDinamiche.No);

            var listaSchedeintervento = schedeDinamicheRichieste.SchedeIntervento.Select(x => new VisuraModelloDinamicoRiepilogo
            {
                IdModello = x.Id,
                Compilato = true,
                Descrizione = x.Descrizione,
                TipoFirma = (ModelloDinamico.TipoFirmaEnum)(int)x.TipoFirma,
                Ordine = x.Ordine.GetValueOrDefault(9999)
            });

            return listaSchedeintervento.OrderBy(x => x.Ordine)
                                        .ThenBy(x => x.Descrizione);
        }
    }
}
