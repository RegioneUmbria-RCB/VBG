using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici.LetturaDaDomandaOnline;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Utils;
using log4net;
using Microsoft.VisualStudio.Threading;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici
{
    public class RiepilogoRichiesto
    {
        public int IdDomanda { get; set; }
        public int IdModello { get; set; }
        public int IndiceMolteplicita { get; set; }
        public bool Richiesto { get; set; }
        public bool RichiedeFirmaDigitale { get; set; }
        public string Titolo { get; set; } = "";
        public int? CodiceOggetto { get; set; }
        public int Ordine { get; set; }
        public bool Compilato => this.CodiceOggetto != null;

    }

    internal class RiepiloghiDatiDinamicService : IRiepiloghiDatiDinamiciService, IRiepiloghiDatiDinamiciAsyncService
    {
        public class IdRiepilogoRichiesto
        {
            public readonly int IdModello;
            public readonly string Descrizione;
            public readonly int IndiceMolteplicita;

            public IdRiepilogoRichiesto(int idModello, string descrizione, int indiceMolteplicita = -1)
            {
                this.IdModello = idModello;
                this.Descrizione = descrizione;
                this.IndiceMolteplicita = indiceMolteplicita;
            }
        }

        private readonly ILog _log = LogManager.GetLogger(typeof(RiepiloghiDatiDinamicService));

        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
        private readonly IDatiDinamiciRepository _datiDinamiciRepository;
        private readonly IIstanzaSigeproAdapterService _istanzaSigeproAdapterService;
        private readonly IRiepilogoModelloInHtmlFactory _riepilogoModelloInHtmlFactory;
        private readonly IAllegatiDomandaFoRepository _allegatiDomandaFoRepository;
        private readonly IConfigurazione<ParametriWorkflow> _configurazione;
        private readonly IOggettiService _oggettiService;
        private readonly IStrutturaModelloDinamicoRepository _strutturaModelloDinamicoRepository;

        public RiepiloghiDatiDinamicService(ISalvataggioDomandaStrategy salvataggioDomandaStrategy, IDatiDinamiciRepository datiDinamiciRepository,
            IIstanzaSigeproAdapterService istanzaSigeproAdapterService, IRiepilogoModelloInHtmlFactory riepilogoModelloInHtmlFactory,
            IAllegatiDomandaFoRepository allegatiDomandaFoRepository, IConfigurazione<ParametriWorkflow> configurazione,
            IOggettiService oggettiService, IStrutturaModelloDinamicoRepository strutturaModelloDinamicoRepository)
        {
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
            this._datiDinamiciRepository = datiDinamiciRepository;
            this._istanzaSigeproAdapterService = istanzaSigeproAdapterService;
            this._riepilogoModelloInHtmlFactory = riepilogoModelloInHtmlFactory;
            this._allegatiDomandaFoRepository = allegatiDomandaFoRepository;
            this._configurazione = configurazione;
            this._oggettiService = oggettiService;
            this._strutturaModelloDinamicoRepository = strutturaModelloDinamicoRepository;
        }

        private void EliminaModelliNonCompilati(DomandaOnline domanda)
        {
            var modelliOld = domanda.ReadInterface.DatiDinamici.Modelli.Where(x => !x.Compilato).ToList();

            foreach (var modello in modelliOld)
            {
                domanda.WriteInterface.RiepiloghiSchedeDinamiche.EliminaByIdModello(modello.IdModello);
            }
        }

        public async Task GeneraRiepiloghiEAllegaADomandaAsync(int idDomanda, IEnumerable<RiepilogoRichiesto> listaRiepiloghi, IStrutturaModelloDinamicoRepository? strutturaModelloDinamicoRepository = null)
        {
            var domanda = await this._salvataggioDomandaStrategy.GetByIdAsync(idDomanda);

            strutturaModelloDinamicoRepository ??= this._strutturaModelloDinamicoRepository;

            var reader = new DomandaOnlineDatiDinamiciReader(domanda, this._datiDinamiciRepository, strutturaModelloDinamicoRepository, this._istanzaSigeproAdapterService);
            var generatoreRiepilogo = new GeneratoreRiepilogoModelloDinamicoAsync(reader, this._riepilogoModelloInHtmlFactory);

            foreach (var riepilogo in listaRiepiloghi)
            {
                var modello = domanda.ReadInterface.DatiDinamici.Modelli.FirstOrDefault(x => x.IdModello == riepilogo.IdModello);
                var descrizioneModello = modello.Descrizione ?? "Descrizione non disponibile";

                var fileRiepilogo = await generatoreRiepilogo.GeneraRiepilogoAsync(riepilogo.IdModello, descrizioneModello, riepilogo.IndiceMolteplicita);

                var esitoSalvataggio = await this._allegatiDomandaFoRepository.SalvaAllegatoAsync(idDomanda, fileRiepilogo, false);
                var md5 = new Hasher().ComputeHash(fileRiepilogo.FileContent);
                var idAllegato = domanda.WriteInterface.Allegati.Allega(esitoSalvataggio.CodiceOggetto, esitoSalvataggio.NomeFile, md5, false, String.Empty);

                domanda.WriteInterface.RiepiloghiSchedeDinamiche.SetIdAllegato(riepilogo.IdModello, riepilogo.IndiceMolteplicita, idAllegato, md5);
            }

            await this._salvataggioDomandaStrategy.SalvaAsync(domanda, true);
        }


        public async Task<RiepilogoRichiesto[]> GetRiepiloghiRichiestiAsync(int idDomanda, bool generaRiepilogoSchedeCheNonRichiedonoFirma, bool ignoraObbligoFirmaDigitale)
        {
            var domanda = await this._salvataggioDomandaStrategy.GetByIdAsync(idDomanda);
            var readInterface = domanda.ReadInterface;

            var listaRiepiloghi = this.GetListaRiepiloghiRichiestiAggiornaDomanda(domanda, generaRiepilogoSchedeCheNonRichiedonoFirma);

            await this._salvataggioDomandaStrategy.SalvaAsync(domanda);

            return listaRiepiloghi.Select(x =>
            {
                var modello = readInterface.DatiDinamici.GetModelloById(x.IdModello);
                var riepilogoCompilato = readInterface.RiepiloghiSchedeDinamiche.GetByIdModelloIndiceMolteplicita(x.IdModello, x.IndiceMolteplicita);

                return new RiepilogoRichiesto
                {
                    IdDomanda = idDomanda,
                    IdModello = x.IdModello,
                    IndiceMolteplicita = x.IndiceMolteplicita,
                    Richiesto = modello.TipoFirma != ModelloDinamico.TipoFirmaEnum.Nessuna,
                    RichiedeFirmaDigitale = !ignoraObbligoFirmaDigitale && modello.TipoFirma != ModelloDinamico.TipoFirmaEnum.Nessuna,
                    Titolo = x.Descrizione,
                    CodiceOggetto = riepilogoCompilato?.AllegatoDellUtente?.CodiceOggetto,
                    //Compilato = modello.Compilato
                };
            }).ToArray();
        }

        private List<IdRiepilogoRichiesto> GetListaRiepiloghiRichiestiAggiornaDomanda(DomandaOnline domanda, bool generaRiepilogoSchedeCheNonRichiedonoFirma)
        {
            var listaRiepiloghiRichiesti = new List<IdRiepilogoRichiesto>();

            var modelliConFirmaABlocchi = domanda.ReadInterface.DatiDinamici.Modelli.Where(x => x.Compilato && x.TipoFirma == ModelloDinamico.TipoFirmaEnum.ABlocchi);

            foreach (var modello in modelliConFirmaABlocchi)
            {
                for (var indiceMolteplicita = 0; indiceMolteplicita < modello.MaxMolteplicita + 1; indiceMolteplicita++)
                {
                    listaRiepiloghiRichiesti.Add(new IdRiepilogoRichiesto(modello.IdModello, modello.Descrizione, indiceMolteplicita));
                }
            }

            // 3. Genero i modelli che richiedono firma singola
            var modelliConFirmaSingola = domanda.ReadInterface.DatiDinamici.Modelli.Where(x => x.Compilato && x.TipoFirma == ModelloDinamico.TipoFirmaEnum.InteroDocumento);

            foreach (var modello in modelliConFirmaSingola)
            {
                listaRiepiloghiRichiesti.Add(new IdRiepilogoRichiesto(modello.IdModello, modello.Descrizione));
            }

            // 4. Se è richiesto anche il riepilogo delle schede che non richiedono firma rigenero e allego anche questi
            if (generaRiepilogoSchedeCheNonRichiedonoFirma)
            {
                var modelliCheNonRichiedonoFirma = domanda.ReadInterface.DatiDinamici.Modelli.Where(x => x.Compilato && x.TipoFirma == ModelloDinamico.TipoFirmaEnum.Nessuna);

                foreach (var modello in modelliCheNonRichiedonoFirma)
                {
                    listaRiepiloghiRichiesti.Add(new IdRiepilogoRichiesto(modello.IdModello, modello.Descrizione));
                }
            }

            foreach (var riepilogo in listaRiepiloghiRichiesti)
            {
                domanda.WriteInterface.RiepiloghiSchedeDinamiche.AggiungiOAggiorna(riepilogo.IdModello, riepilogo.IndiceMolteplicita, riepilogo.Descrizione);
            }

            return listaRiepiloghiRichiesti;
        }

        public async Task<BinaryFile> GeneraRiepilogoAsync(int idDomanda, int idModello, int indiceMolteplicita, IStrutturaModelloDinamicoRepository? strutturaModelloDinamicoRepository = null)
        {
            var domanda = await this._salvataggioDomandaStrategy.GetByIdAsync(idDomanda);

            strutturaModelloDinamicoRepository ??= this._strutturaModelloDinamicoRepository;

            var reader = new DomandaOnlineDatiDinamiciReader(domanda, this._datiDinamiciRepository, strutturaModelloDinamicoRepository, this._istanzaSigeproAdapterService);
            var generatoreRiepilogo = new GeneratoreRiepilogoModelloDinamicoAsync(reader, this._riepilogoModelloInHtmlFactory);
            var modello = domanda.ReadInterface.DatiDinamici.Modelli.Where(x => x.IdModello == idModello).FirstOrDefault();
            var descrizioneModello = modello.Descrizione ?? "Descrizione non disponibile";

            domanda.WriteInterface.RiepiloghiSchedeDinamiche.AggiungiOAggiorna(idModello, indiceMolteplicita, descrizioneModello);

            var riepilogo = await generatoreRiepilogo.GeneraRiepilogoAsync(idModello, descrizioneModello, indiceMolteplicita);
            var md5 = new Hasher().ComputeHash(riepilogo.FileContent);

            domanda.WriteInterface.RiepiloghiSchedeDinamiche.SetHashConfronto(idModello, indiceMolteplicita, md5);

            await this._salvataggioDomandaStrategy.SalvaAsync(domanda, true);

            return riepilogo;
        }

        public void RigeneraRiepiloghi(int idDomanda, bool generaRiepilogoSchedeCheNonRichiedonoFirma)
        {
            var jtf = new JoinableTaskFactory(new JoinableTaskContext());
            jtf.Run(() => this.RigeneraRiepiloghiAsync(idDomanda, generaRiepilogoSchedeCheNonRichiedonoFirma));
        }

        /// <summary>
        /// TODO: Se chiamato solo internamente rendere il metodo sincrono
        /// </summary>
        /// <param name="idDomanda"></param>
        /// <param name="generaRiepilogoSchedeCheNonRichiedonoFirma"></param>
        /// <returns></returns>
        private async Task RigeneraRiepiloghiAsync(int idDomanda, bool generaRiepilogoSchedeCheNonRichiedonoFirma)
        {
            var domanda = await this._salvataggioDomandaStrategy.GetByIdAsync(idDomanda);
            var reader = new DomandaOnlineDatiDinamiciReader(domanda, this._datiDinamiciRepository, this._strutturaModelloDinamicoRepository, this._istanzaSigeproAdapterService);
            var generatoreRiepilogo = new GeneratoreRiepilogoModelloDinamicoAsync(reader, this._riepilogoModelloInHtmlFactory);

            // 1. Elimino i modelli non compilati
            this.EliminaModelliNonCompilati(domanda);

            // 2. Genero i modelli che richiedono firma a blocchi
            var listaRiepiloghiRichiesti = this.GetListaRiepiloghiRichiestiAggiornaDomanda(domanda, generaRiepilogoSchedeCheNonRichiedonoFirma);

            // 3. Salvo i documenti generati
            foreach (var idRiepilogo in listaRiepiloghiRichiesti)
            {
                domanda.WriteInterface.RiepiloghiSchedeDinamiche.AggiungiOAggiorna(idRiepilogo.IdModello, idRiepilogo.IndiceMolteplicita, idRiepilogo.Descrizione);

                if (domanda.WriteInterface.RiepiloghiSchedeDinamiche.HaRiepilogo(idRiepilogo.IdModello, idRiepilogo.IndiceMolteplicita))
                {
                    continue;
                }

                var riepilogo = await generatoreRiepilogo.GeneraRiepilogoAsync(idRiepilogo.IdModello, idRiepilogo.Descrizione, idRiepilogo.IndiceMolteplicita);

                var esitoSalvataggio = await this._allegatiDomandaFoRepository.SalvaAllegatoAsync(idDomanda, riepilogo, false);

                var md5 = new Hasher().ComputeHash(riepilogo.FileContent);
                var idAllegato = domanda.WriteInterface.Allegati.Allega(esitoSalvataggio.CodiceOggetto, esitoSalvataggio.NomeFile, md5, false, String.Empty);

                domanda.WriteInterface.RiepiloghiSchedeDinamiche.SetIdAllegato(idRiepilogo.IdModello, idRiepilogo.IndiceMolteplicita, idAllegato, md5);
            }

            await this._salvataggioDomandaStrategy.SalvaAsync(domanda);
        }

        public void EliminaOggettoRiepilogo(int idDomanda, int idModello, int indiceMolteplicita)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            domanda.WriteInterface.RiepiloghiSchedeDinamiche.EliminaOggettoRiepilogo(idModello, indiceMolteplicita);

            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        public void AggiungiOggettoRiepilogo(int idDomanda, int idModello, int indiceMolteplicita, BinaryFile file, bool ignoraVerificaFirma = false)
        {
            try
            {
                var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

                var datiModello = domanda.ReadInterface.DatiDinamici.Modelli.Where(x => x.IdModello == idModello).FirstOrDefault();
                var rigaRiepilogo = domanda.ReadInterface.RiepiloghiSchedeDinamiche.GetByIdModelloIndiceMolteplicita(idModello, indiceMolteplicita);

                var verificaFirma = datiModello.TipoFirma != ModelloDinamico.TipoFirmaEnum.Nessuna;
                SalvataggioAllegatoResult esitoSalvataggio = null;

                if (ignoraVerificaFirma)
                {
                    verificaFirma = false;
                }

                if (verificaFirma && this._configurazione.Parametri.VerificaHashFilesFirmati)
                {
                    var md5Confronto = rigaRiepilogo.HashConfronto;

                    esitoSalvataggio = this._allegatiDomandaFoRepository.SalvaAllegatoConfrontaHash(idDomanda, file, md5Confronto);
                }
                else
                {
                    esitoSalvataggio = this._allegatiDomandaFoRepository.SalvaAllegato(idDomanda, file, verificaFirma);
                }

                domanda.WriteInterface.RiepiloghiSchedeDinamiche.SalvaOggettoRiepilogo(idModello, indiceMolteplicita, esitoSalvataggio.CodiceOggetto, esitoSalvataggio.NomeFile, esitoSalvataggio.FirmatoDigitalmente);

                this._salvataggioDomandaStrategy.Salva(domanda);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore in AggiungiOggettoRiepilogo: {0}", ex.ToString());

                throw;
            }
        }

        public async Task AggiungiOggettoRiepilogoAsync(int idDomanda, int idModello, int indiceMolteplicita, int codiceOggetto)
        {
            try
            {
                var domanda = await this._salvataggioDomandaStrategy.GetByIdAsync(idDomanda);

                var datiModello = domanda.ReadInterface.DatiDinamici.Modelli.FirstOrDefault(x => x.IdModello == idModello);
                var rigaRiepilogo = domanda.ReadInterface.RiepiloghiSchedeDinamiche.GetByIdModelloIndiceMolteplicita(idModello, indiceMolteplicita);

                if (this._configurazione.Parametri.VerificaHashFilesFirmati)
                {
                    var md5Confronto = rigaRiepilogo.HashConfronto;
                    var file = await this._oggettiService.GetByIdAsync(codiceOggetto);

                    if (!this._allegatiDomandaFoRepository.ConfrontaHash(file, md5Confronto))
                    {
                        throw new HashCheckFailedException();
                    }
                }

                var esitoSalvataggio = await this._allegatiDomandaFoRepository.SalvaAllegatoAsync(idDomanda, codiceOggetto);

                domanda.WriteInterface.RiepiloghiSchedeDinamiche.SalvaOggettoRiepilogo(idModello, indiceMolteplicita, esitoSalvataggio.CodiceOggetto, esitoSalvataggio.NomeFile, esitoSalvataggio.FirmatoDigitalmente);

                await this._salvataggioDomandaStrategy.SalvaAsync(domanda);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore in AggiungiOggettoRiepilogo: {0}", ex.ToString());

                throw;
            }
        }

        public async Task EliminaOggettoRiepilogoAsync(int idDomanda, int idModello, int indiceMolteplicita)
        {
            var domanda = await this._salvataggioDomandaStrategy.GetByIdAsync(idDomanda);

            domanda.WriteInterface.RiepiloghiSchedeDinamiche.EliminaOggettoRiepilogo(idModello, indiceMolteplicita);

            await this._salvataggioDomandaStrategy.SalvaAsync(domanda);
        }


    }
}
