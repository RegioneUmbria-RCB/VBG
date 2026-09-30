using Init.Sigepro.FrontEnd.GestioneMovimenti.Commands;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Events;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneSchedeDinamiche;
using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
using Init.Sigepro.FrontEnd.Infrastructure.ModelBase;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti
{
    public class MovimentoFrontoffice : AggregateRoot
    {
        private class RiepilogoSchedaDinamica
        {
            public readonly int IdAllegato;
            public readonly string NomeFile;
            public bool FirmatoDigitalmente { get; private set; }

            internal RiepilogoSchedaDinamica(int idAllegato, string nomeFile, bool firmatoDigitalemente)
            {
                this.IdAllegato = idAllegato;
                this.NomeFile = nomeFile;
                this.FirmatoDigitalmente = firmatoDigitalemente;
            }

            internal void FirmaDigitalmente()
            {
                this.FirmatoDigitalmente = true;
            }
        }

        private class AllegatoDelMovimento
        {
            public int IdAllegato { get; private set; }
            public bool FirmatoDigitalmente { get; private set; }

            public AllegatoDelMovimento(int idAllegato, bool firmatoDigitalmente)
            {
                this.IdAllegato = idAllegato;
                this.FirmatoDigitalmente = firmatoDigitalmente;
            }

            public void FirmaDigitalmente()
            {
                this.FirmatoDigitalmente = true;
            }
        }

        #region factory e configurazione
        internal class Factory
        {
            public MovimentoFrontoffice Crea(Action<ConfigurazioneMovimento> configura)
            {
                var configurazione = new ConfigurazioneMovimento();

                configura(configurazione);

                var movimento = new MovimentoFrontoffice(configurazione.IdComune, configurazione.IdMovimentoDaEffettuare, configurazione.IdMovimentoOrigine);

                foreach (var valore in configurazione.valori)
                    movimento.ModificaValoreDatoDinamico(valore.Id, valore.IndiceMolteplicita, valore.Valore, valore.ValoreDecodificato);

                return movimento;
            }
        }

        internal class ConfigurazioneMovimento
        {
            internal string IdComune { get; private set; }
            internal int IdMovimentoOrigine { get; private set; }
            internal int IdMovimentoDaEffettuare { get; private set; }
            internal IEnumerable<ValoreSchedaDinamicaMovimento> valori { get; private set; }

            public ConfigurazioneMovimento()
            {
                this.valori = new List<ValoreSchedaDinamicaMovimento>();
            }

            internal ConfigurazioneMovimento ConIdComune(string idComune)
            {
                this.IdComune = idComune;

                return this;
            }

            internal ConfigurazioneMovimento ConIdMovimentoDaEffettuare(int idMovimentoDaEffettuare)
            {
                this.IdMovimentoDaEffettuare = idMovimentoDaEffettuare;

                return this;
            }

            internal ConfigurazioneMovimento ConIdMovimentoOrigine(int idMovimentoOrigine)
            {
                this.IdMovimentoOrigine = idMovimentoOrigine;

                return this;
            }

            internal ConfigurazioneMovimento ConValoriDatiDinamici(IEnumerable<ValoreSchedaDinamicaMovimento> valori)
            {
                this.valori = valori;

                return this;
            }
        }
        #endregion

        private string _idComune;
        private int _id;
        private int _idMovimentoOrigine;
        private string _testoNote = String.Empty;

        //bool _trasmesso = false;

        // Dati dinamici
        private readonly Dictionary<int, CampoDinamico> _campiDinamici = new Dictionary<int, CampoDinamico>();
        private readonly Dictionary<int, RiepilogoSchedaDinamica> _riepiloghiSchede = new Dictionary<int, RiepilogoSchedaDinamica>();
        private readonly List<AllegatoDelMovimento> _allegatiPresenti = new List<AllegatoDelMovimento>();

        public override int Id
        {
            get { return this._id; }
        }

        private MovimentoFrontoffice(IEnumerable<Event> events) : base(events)
        {
        }

        private MovimentoFrontoffice(string idComune, int id, int idMovimentoOrigine)
        {
            if (string.IsNullOrEmpty(idComune))
                throw new ArgumentNullException(nameof(idComune));
            if (id <= 0)
                throw new ArgumentNullException(nameof(id));
            if (idMovimentoOrigine <= 0)
                throw new ArgumentNullException(nameof(idMovimentoOrigine));

            //Condition.Requires(idComune, "idComune").IsNotNullOrEmpty();
            //Condition.Requires(id, "id").IsGreaterThan(0);
            //Condition.Requires(idMovimentoOrigine, "idMovimentoOrigine").IsGreaterThan(0);

            this.ApplyChange(new MovimentoCreato
            {
                IdComune = idComune,
                IdMovimentoDaEffettuare = id,
                IdMovimentoOrigine = idMovimentoOrigine
            });
        }

        public void Apply(MovimentoCreato @event)
        {
            this._idComune = @event.IdComune;
            this._id = @event.IdMovimentoDaEffettuare;
            this._idMovimentoOrigine = @event.IdMovimentoOrigine;
        }


        internal void ModificaNote(string testoNote)
        {
            if (this._testoNote == testoNote)
                return;

            this.ApplyChange(new NoteMovimentoModificate
            {
                IdComune = this._idComune,
                IdMovimento = this.Id,
                TestoNote = testoNote
            });
        }

        public void Apply(NoteMovimentoModificate @event)
        {
            this._testoNote = @event.TestoNote;
        }

        internal void ModificaValoreDatoDinamico(int idCampoDinamico, int indiceMolteplicita, string valore, string valoreDecodificato)
        {
            if (idCampoDinamico < 0)
                throw new ArgumentNullException(nameof(idCampoDinamico));
            if (indiceMolteplicita < 0)
                throw new ArgumentNullException(nameof(indiceMolteplicita));

            //Condition.Requires(idCampoDinamico, "idCampoDinamico").IsNotLessOrEqual(0);
            //Condition.Requires(indiceMolteplicita, "indiceMolteplicita").IsNotLessThan(0);



            var valoreDatoDinamico = new ValoreDatoDinamico(valore, valoreDecodificato);

            var campo = this._campiDinamici.ContainsKey(idCampoDinamico) ? this._campiDinamici[idCampoDinamico] : (CampoDinamico)null;

            var campoNonHaUnValore = new CampoNonHaUnValoreAllIndiceSpecification(indiceMolteplicita);

            // Se il campo non contiene un valore lo aggiungo
            if (campoNonHaUnValore.IsSatisfiedBy(campo))
            {
                this.ApplyChange(new ValoreDatoDinamicoAggiuntoAlMovimento
                {
                    IdCampoDinamico = idCampoDinamico,
                    IdComune = this._idComune,
                    IdMovimento = this._id,
                    IndiceMolteplicita = indiceMolteplicita,
                    Valore = valore,
                    ValoreDecodificato = valoreDecodificato
                });

                return;
            }

            var campoHaUnValoreDiverso = new CampoHaUnValoreDiversoAllIndiceSpecification(indiceMolteplicita, valoreDatoDinamico);

            if (campoHaUnValoreDiverso.IsSatisfiedBy(campo))
            {
                this.ApplyChange(new ValoreDatoDinamicoDelMovimentoModificato
                {
                    IdCampoDinamico = idCampoDinamico,
                    IdComune = this._idComune,
                    IdMovimento = this._id,
                    IndiceMolteplicita = indiceMolteplicita,
                    Valore = valore,
                    ValoreDecodificato = valoreDecodificato
                });

                return;
            }
        }

        public void Apply(ValoreDatoDinamicoAggiuntoAlMovimento @event)
        {
            CampoDinamico campo = null;

            var idCampoDinamico = @event.IdCampoDinamico;

            if (!this._campiDinamici.TryGetValue(idCampoDinamico, out campo))
            {
                campo = new CampoDinamico(idCampoDinamico);

                this._campiDinamici.Add(idCampoDinamico, campo);
            }

            var valore = new ValoreDatoDinamico(@event.Valore, @event.ValoreDecodificato);

            this._campiDinamici[idCampoDinamico].ImpostaValore(@event.IndiceMolteplicita, valore);
        }

        public void Apply(ValoreDatoDinamicoDelMovimentoModificato @event)
        {
            var valore = new ValoreDatoDinamico(@event.Valore, @event.ValoreDecodificato);

            this._campiDinamici[@event.IdCampoDinamico].ImpostaValore(@event.IndiceMolteplicita, valore);
        }

        internal void AllegaRiepilogoSchedaDinamica(int idSchedaDinamica, int idAllegato, string nomeFile, bool firmatoDigitalmente = true)
        {
            // Se esiste già un riepilogo per la scheda dinamica rimuovo il file esistente e ne allego un altro
            if (this._riepiloghiSchede.ContainsKey(idSchedaDinamica))
            {
                var riepilogo = this._riepiloghiSchede[idSchedaDinamica];

                this.ApplyChange(new RiepilogoSchedaDinamicaRimossoDalMovimento
                {
                    IdComune = this._idComune,
                    IdMovimento = this._id,
                    IdSchedaDinamica = idSchedaDinamica,
                    IdAllegato = riepilogo.IdAllegato
                });
            }

            this.ApplyChange(new RiepilogoSchedaDinamicaAllegatoAlMovimento
            {
                IdComune = this._idComune,
                IdMovimento = this._id,
                IdSchedaDinamica = idSchedaDinamica,
                IdAllegato = idAllegato,
                NomeFile = nomeFile,
                FirmatoDigitalmente = firmatoDigitalmente
            });
        }

        public void Apply(RiepilogoSchedaDinamicaAllegatoAlMovimento @event)
        {
            this._riepiloghiSchede.Add(@event.IdSchedaDinamica, new RiepilogoSchedaDinamica(@event.IdAllegato, @event.NomeFile, @event.FirmatoDigitalmente));
        }

        public void Apply(RiepilogoSchedaDinamicaRimossoDalMovimento @event)
        {
            this._riepiloghiSchede.Remove(@event.IdSchedaDinamica);
        }

        internal void RimuoviRiepilogoSchedaDinamica(int idSchedaDinamica)
        {
            if (!this._riepiloghiSchede.ContainsKey(idSchedaDinamica))
                return;

            var riepilogo = this._riepiloghiSchede[idSchedaDinamica];


            this.ApplyChange(new RiepilogoSchedaDinamicaRimossoDalMovimento
            {
                IdComune = this._idComune,
                IdMovimento = this._id,
                IdSchedaDinamica = idSchedaDinamica,
                IdAllegato = riepilogo.IdAllegato
            });
        }

        internal void AggiungiAllegato(int idAllegato, string nomeFile, string descrizione, bool firmatoDigitalmente = true)
        {
            if (string.IsNullOrEmpty(nomeFile))
                throw new ArgumentNullException(nameof(nomeFile));
            if (string.IsNullOrEmpty(descrizione))
                throw new ArgumentNullException(nameof(descrizione));
            if (idAllegato <= 0)
                throw new ArgumentNullException(nameof(idAllegato));

            //Condition.Requires(idAllegato, "idAllegato").IsGreaterThan(0);
            //Condition.Requires(nomeFile, "nomeFile").IsNotNullOrEmpty();
            //Condition.Requires(nomeFile, "descrizione").IsNotNullOrEmpty();

            this.ApplyChange(new AllegatoAggiuntoAlMovimento
            {
                IdComune = this._idComune,
                IdMovimento = this._id,
                IdAllegato = idAllegato,
                Descrizione = descrizione,
                NomeFile = nomeFile,
                FirmatoDigitalmente = firmatoDigitalmente
            });
        }

        public void Apply(AllegatoAggiuntoAlMovimento @event)
        {
            this._allegatiPresenti.Add(new AllegatoDelMovimento(@event.IdAllegato, @event.FirmatoDigitalmente));
        }

        internal void RimuoviAllegato(int idAllegato)
        {
            if (idAllegato <= 0)
                throw new ArgumentNullException(nameof(idAllegato));
            //Condition.Requires(idAllegato, "idAllegato").IsGreaterThan(0);

            if (this._allegatiPresenti.Where(x => x.IdAllegato == idAllegato).Count() == 0)
                throw new ModelValidationException("L'id allegato " + idAllegato + " non è presente nel movimento con id " + this._id);

            this.ApplyChange(new AllegatoRimossoDalMovimento
            {
                IdComune = this._idComune,
                IdMovimento = this._id,
                IdAllegato = idAllegato
            });
        }

        public void Apply(AllegatoRimossoDalMovimento @event)
        {
            var allegati = this._allegatiPresenti.Where(x => x.IdAllegato == @event.IdAllegato);

            allegati.ToList().ForEach(x =>
            {
                this._allegatiPresenti.Remove(x);
            });

        }

        internal void Trasmetti(ITrasmissioneMovimentoService trasmissioneMovimentoService)
        {
            trasmissioneMovimentoService.Trasmetti(this._id);

            this.ApplyChange(new MovimentoTrasmesso
            {
                Data = DateTime.Now,
                IdComune = this._idComune,
                IdMovimento = this._id
            });
        }

        public void Apply(MovimentoTrasmesso @event)
        {
            //this._trasmesso = true;
        }


        internal void EliminaValoriCampo(int idCampo)
        {
            if (!this._campiDinamici.ContainsKey(idCampo))
                return;

            if (!this._campiDinamici[idCampo].ContieneValori())
                return;

            this.ApplyChange(new ValoriCampoDinamicoEliminati
            {
                IdCampo = idCampo,
                IdComune = this._idComune,
                IdMovimento = this._id
            });
        }

        public void Apply(ValoriCampoDinamicoEliminati @event)
        {
            this._campiDinamici[@event.IdCampo].EliminaValori();
        }

        internal void ComlpetaCompilazioneScheda(int idScheda)
        {
            this.ApplyChange(new CompilazioneSchedaDinamicaCompletata
            {
                IdComune = this._idComune,
                IdMovimento = this._id,
                IdScheda = idScheda
            });
        }

        internal void RimuoviAllegatoDellaSchedaDinamica(RimuoviAllegatoDellaSchedaDinamica cmd)
        {
            this.ApplyChange(new AllegatoRimossoDaSchedaDinamica(
                cmd.IdComune,
                cmd.IdMovimento,
                cmd.IdCampoDinamico,
                cmd.IndiceMolteplicita,
                cmd.VecchioValore));
        }

        public void Apply(AllegatoRimossoDaSchedaDinamica @event)
        {

        }

        public void Apply(CompilazioneSchedaDinamicaCompletata @event) { }

        internal void FirmaRiepilogoSchedaDinamica(int codiceOggetto, string nomeFile)
        {
            this.ApplyChange(new RiepilogoSchedaDinamicaFirmatoDigitalmente
            {
                IdComune = this._idComune,
                CodiceOggetto = codiceOggetto,
                IdMovimento = this._id,
                NomeFile = nomeFile
            });
        }


        public void Apply(RiepilogoSchedaDinamicaFirmatoDigitalmente @event)
        {
            this._riepiloghiSchede
                    .Where(x => x.Value.IdAllegato == @event.CodiceOggetto)
                    .Select(x => x.Value)
                    .ToList()
                    .ForEach(x => x.FirmaDigitalmente());
        }

        internal void FirmaAllegato(int codiceOggetto, string nomeFile)
        {
            this.ApplyChange(new AllegatoFirmatoDigitalmente
            {
                IdComune = this._idComune,
                CodiceOggetto = codiceOggetto,
                IdMovimento = this._id,
                NomeFile = nomeFile
            });
        }

        public void Apply(AllegatoFirmatoDigitalmente @event)
        {
            this._allegatiPresenti
                    .Where(x => x.IdAllegato == @event.CodiceOggetto)
                    .ToList()
                    .ForEach(x => x.FirmaDigitalmente());
        }

        internal void SostituisciDocumento(OrigineDocumentoSostituzioneDocumentale origine, int codiceOggettoOriginale, string nomeFileOriginale, int codiceOggettoSostitutivo, string nomeFileSostitutivo)
        {
            this.ApplyChange(new SostituzioneDocumentaleEffettuata
            {
                IdComune = this._idComune,
                IdMovimento = this._id,
                OrigineDocumento = origine,
                CodiceOggettoOriginale = codiceOggettoOriginale,
                NomeFileOriginale = nomeFileOriginale,
                CodiceOggettoSostitutivo = codiceOggettoSostitutivo,
                NomeFileSostitutivo = nomeFileSostitutivo
            });
        }

        public void Apply(SostituzioneDocumentaleEffettuata @event)
        {

        }

        internal void AnnullaSostituzioneDocumentale(int codiceOggettoSostitutivo)
        {
            this.ApplyChange(new SostituzioneDocumentaleAnnullata
            {
                IdComune = this._idComune,
                IdMovimento = this._id,
                CodiceOggettoSostitutivo = codiceOggettoSostitutivo
            });
        }

        public void Apply(SostituzioneDocumentaleAnnullata @event)
        {

        }
    }
}
