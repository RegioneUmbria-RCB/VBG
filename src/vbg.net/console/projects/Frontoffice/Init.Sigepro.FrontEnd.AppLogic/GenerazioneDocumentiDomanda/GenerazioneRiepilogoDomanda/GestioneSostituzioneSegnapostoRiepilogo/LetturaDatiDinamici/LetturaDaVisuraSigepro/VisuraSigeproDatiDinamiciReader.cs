using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.StrutturaModelli;
using Init.SIGePro.DatiDinamici.Interfaces;
using Init.SIGePro.DatiDinamici.VisibilitaCampi;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Visura;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici.LetturaDaVisuraSigepro
{
    public class VisuraSigeproDatiDinamiciReader : IDatiDinamiciRiepilogoReader
    {
        Istanze _istanza;
        IDatiDinamiciRepository _datiDinamiciRepository;
        VisuraDyn2ModelliManager _modelliManager;
        VisuraDyn2DataAccessProvider _dap;

        public VisuraSigeproDatiDinamiciReader(Istanze istanza, IDatiDinamiciRepository datiDinamiciRepository, VisuraDyn2DataAccessProvider dap)
        {
            this._istanza = istanza;
            this._datiDinamiciRepository = datiDinamiciRepository;
            this._dap = dap;
        }


        public IDyn2DataAccessProvider CreateDataAccessProvider(int idScheda, ITokenApplicazioneService tokenApplicazioneService)
        {
            return _dap;
        }

        public IDyn2DataAccessProvider CreateDataAccessProviderStampaMolteplicita(int idScheda, int indiceMolteplicita, ITokenApplicazioneService tokenApplicazioneService)
        {
            return _dap;
        }

        public IEnumerable<IdValoreCampo> GetCampiNonVisibili(int idModello)
        {
            return Enumerable.Empty<IdValoreCampo>();
        }

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

        public IEnumerable<int> GetIndiciSchede(int idModello, IStrutturaModelloReader strutturaReader)
        {
            var strutturaModello = strutturaReader.Read(idModello);

            var indiciCampi = strutturaModello.Campi.SelectMany(campo =>
            {
                return this._istanza.IstanzeDyn2Dati
                            .Where(x => x.FkD2cId == campo.Id)
                            .Select(x => x.Indice.Value);
            });

            return indiciCampi.DefaultIfEmpty().Distinct();
        }

        public IEnumerable<IModelloDinamicoRiepilogo> GetListaModelli()
        {
            var idIntervento = Convert.ToInt32(this._istanza.CODICEINTERVENTOPROC);
            var endoSelezionati = this._istanza.EndoProcedimenti.Select(x => Convert.ToInt32(x.CODICEINVENTARIO));
            
            var schedeDinamicheRichieste = _datiDinamiciRepository.GetSchedeDaInterventoEEndo(idIntervento, endoSelezionati, Enumerable.Empty<string>(), UsaTipiLocalizzazioniPerSelezionareSchedeDinamiche.No);
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

            var schedeDinamicheRichieste = _datiDinamiciRepository.GetSchedeDaInterventoEEndo(idIntervento, endoSelezionati, Enumerable.Empty<string>(), UsaTipiLocalizzazioniPerSelezionareSchedeDinamiche.No);

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

            var schedeDinamicheRichieste = _datiDinamiciRepository.GetSchedeDaInterventoEEndo(idIntervento, endoSelezionati, Enumerable.Empty<string>(), UsaTipiLocalizzazioniPerSelezionareSchedeDinamiche.No);

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
