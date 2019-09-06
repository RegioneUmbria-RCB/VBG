using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.StrutturaModelli;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.Database;
using Init.SIGePro.DatiDinamici.Interfaces;
using Init.SIGePro.DatiDinamici.VisibilitaCampi;
using Init.SIGePro.DatiDinamici.WebControls.MaschereCampiNonVisibili;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.GenerazionePdfModulo
{
    public class FvgDatiDinamiciRiepilogoReader : IDatiDinamiciRiepilogoReader
    {
        public class ModelloDinamicoRiepilogo : IModelloDinamicoRiepilogo
        {
            public int IdModello { get; set; }
            public bool Compilato { get; set; }
            public ModelloDinamico.TipoFirmaEnum TipoFirma { get; set; }
            public string Descrizione { get; set; }
        }

        FvgDatabase _database;
        IAliasResolver _aliasResolver;
        IEnumerable<ServiziFVGService.SchedaDinamicaEndoprocedimento> _listaSchede;
        IDatiDinamiciRepository _datiDinamiciRepository;

        public bool PuoCaricareSchedeNonPresenti => true;

        public FvgDatiDinamiciRiepilogoReader(IAliasResolver aliasResolver, IDatiDinamiciRepository datiDinamiciRepository, FvgDatabase database, 
                                              IEnumerable<ServiziFVGService.SchedaDinamicaEndoprocedimento> listaSchede)
        {
            this._datiDinamiciRepository = datiDinamiciRepository;
            this._database = database;
            this._listaSchede = listaSchede;
            this._aliasResolver = aliasResolver;
        }

        public IDyn2DataAccessProvider CreateDataAccessProvider(int idScheda, ITokenApplicazioneService tokenApplicazioneService)
        {
            var dyn2DatiRepository = new FvgDyn2DatiRepository(this._database);

            return new FvgDyn2DataAccessProvider(idScheda, this._datiDinamiciRepository, dyn2DatiRepository, tokenApplicazioneService);
        }

        public IDyn2DataAccessProvider CreateDataAccessProviderStampaMolteplicita(int idScheda, int indiceMolteplicita, ITokenApplicazioneService tokenApplicazioneService)
        {
            var dyn2DatiRepository = new FvgDyn2DatiRepository(this._database);

            return new FvgDyn2DataAccessProvider(idScheda, this._datiDinamiciRepository, dyn2DatiRepository, tokenApplicazioneService);
        }

        public CampiNonVisibili GetCampiNonVisibili(int idModello)
        {
            return new CampiNonVisibili(Enumerable.Empty<IdValoreCampo>());
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

        public IEnumerable<int> GetIndiciSchede(int idModello, IStrutturaModelloReader strutturaReader)
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

        public IModelloDinamicoRiepilogo CaricaSchedaNonPresenteDaId(int idScheda)
        {
            return new ModelloDinamicoRiepilogoFvg(idScheda);

        }
    }
}
