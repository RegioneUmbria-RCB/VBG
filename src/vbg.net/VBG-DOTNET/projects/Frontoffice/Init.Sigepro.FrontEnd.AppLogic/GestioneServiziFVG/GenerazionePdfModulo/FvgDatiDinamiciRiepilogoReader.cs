using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.Database;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using System.Collections.Generic;
using System.Linq;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Standard.Scripts;
using VBG.DatiDinamici.Standard.Utils.CreazioneModelli;
using VBG.DatiDinamici.WebControls.MaschereCampiNonVisibili;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.GenerazionePdfModulo
{
    public class FvgDatiDinamiciRiepilogoReader : ISchedeDinamicheDomandaAlRiepilogoService
    {
        public class ModelloDinamicoRiepilogo : IModelloDinamicoRiepilogo
        {
            public int IdModello { get; set; }
            public bool Compilato { get; set; }
            public ModelloDinamico.TipoFirmaEnum TipoFirma { get; set; }
            public string Descrizione { get; set; }
        }

        private readonly FvgDatabase _database;
        private readonly IAliasResolver _aliasResolver;
        private readonly IEnumerable<ServiziFVGService.SchedaDinamicaEndoprocedimento> _listaSchede;
        private readonly IStrutturaModelloDinamicoRepository _strutturaModelloDinamicoRepository;
        private readonly IDatiDinamiciRepository _datiDinamiciRepository;

        public bool PuoCaricareSchedeNonPresenti => true;
        public bool SupportaCachingCampiNonVisibili => true;

        public FvgDatiDinamiciRiepilogoReader(IAliasResolver aliasResolver, IDatiDinamiciRepository datiDinamiciRepository, FvgDatabase database,
                                              IEnumerable<ServiziFVGService.SchedaDinamicaEndoprocedimento> listaSchede, IStrutturaModelloDinamicoRepository strutturaModelloDinamicoRepository)
        {
            this._datiDinamiciRepository = datiDinamiciRepository;
            this._database = database;
            this._listaSchede = listaSchede;
            this._strutturaModelloDinamicoRepository = strutturaModelloDinamicoRepository;
            this._aliasResolver = aliasResolver;
        }


        public CampiNonVisibili GetCampiNonVisibili(int idModello)
        {
            return new CampiNonVisibili(this._database.GetCampiNonVisibili(idModello));
        }

        public IValoreDatoDinamicoRiepilogo GetCampoDinamico(int idCampoDinamico, int indiceMolteplicita = 0)
        {
            return this._database.GetValoreSingoloCampo(idCampoDinamico, indiceMolteplicita);
        }

        public int GetCodiceIstanza()
        {
            return -1;
        }

        public string GetIdComune()
        {
            return this._aliasResolver.AliasComune;
        }

        public IEnumerable<int> GetIndiciSchede(int idModello)
        {
            return new[] { 0 };
        }

        public IEnumerable<IModelloDinamicoRiepilogo> GetListaModelli()
        {
            return this._listaSchede.Select(x => new ModelloDinamicoRiepilogo
            {
                IdModello = x.Id,
                Compilato = true,
                Descrizione = x.Descrizione,
                TipoFirma = ModelloDinamico.TipoFirmaEnum.Nessuna
            });
        }

        public IEnumerable<IModelloDinamicoRiepilogo> GetListaModelliEndo(int idEndo)
        {
            return Enumerable.Empty<IModelloDinamicoRiepilogo>();
            /*return this._listaSchede.Select(x => new ModelloDinamicoRiepilogo
            {
                IdModello = x.Id,
                Compilato = x.Compilata,
                Descrizione = x.Descrizione,
                TipoFirma = ModelloDinamico.TipoFirmaEnum.Nessuna
            });*/
        }

        public IEnumerable<IModelloDinamicoRiepilogo> GetListaModelliIntervento()
        {
            return Enumerable.Empty<IModelloDinamicoRiepilogo>();
        }

        public class ModelloDinamicoRiepilogoFvg : IModelloDinamicoRiepilogo
        {
            public int IdModello { get; private set; }

            public bool Compilato { get; private set; }

            public ModelloDinamico.TipoFirmaEnum TipoFirma { get; private set; }

            public string Descrizione { get; private set; }

            public ModelloDinamicoRiepilogoFvg(int idModello)
            {
                this.IdModello = idModello;
                this.Compilato = true;
                this.TipoFirma = ModelloDinamico.TipoFirmaEnum.Nessuna;
                this.Descrizione = "Modello riepilogo fvg";
            }
        }

        private class EmptyClassLoader : IClasseContestoLoader
        {
            public IClasseContestoModelloDinamico LoadClass()
            {
                return new Istanze();
            }
        }

        public ModelloDinamicoLoader CreateLoader(int idScheda, int indiceMolteplicita, ITokenApplicazioneService tokenApplicazioneService)
        {
            var cache = this._strutturaModelloDinamicoRepository.GetStrutturaModelloDinamico(idScheda);
            var classeContestoLoader = new EmptyClassLoader();
            var repository = new FvgDyn2DatiRepository(this._database);

            var builder = new ModelloDinamicoLoaderBuilder();

            var loader = builder.UsaStruttura(cache)
                           .UsaLoaderClasseContesto(classeContestoLoader)
                           // .UsaQueryLocalizzazioni(classeContestoLoader) // Usa la classe di default che non restituisce localizzazioni
                           .UsaRepository(repository)
                           .UsaToken(tokenApplicazioneService.GetToken())
                           .Build(this._aliasResolver.AliasComune, ContestoScriptEnum.Frontoffice);

            return loader;
        }
    }
}
