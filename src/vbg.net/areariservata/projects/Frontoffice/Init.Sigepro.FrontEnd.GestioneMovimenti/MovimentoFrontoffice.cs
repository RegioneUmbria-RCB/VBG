using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.Infrastructure.ModelBase;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Events;
using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneSchedeDinamiche;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Commands;

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

		string _idComune;
		int _id;
		int _idMovimentoOrigine;
		string _testoNote = String.Empty;
		//bool _trasmesso = false;

		// Dati dinamici
		Dictionary<int, CampoDinamico> _campiDinamici = new Dictionary<int, CampoDinamico>();
		Dictionary<int, RiepilogoSchedaDinamica> _riepiloghiSchede = new Dictionary<int, RiepilogoSchedaDinamica>();
		List<AllegatoDelMovimento> _allegatiPresenti = new List<AllegatoDelMovimento>();

		public override int Id
		{
			get { return _id; }
		}

		private MovimentoFrontoffice(IEnumerable<Event> events):base(events)
		{
		}

		private MovimentoFrontoffice( string idComune , int id , int idMovimentoOrigine )
		{
			Condition.Requires( idComune, "idComune").IsNotNullOrEmpty();
			Condition.Requires(id, "id").IsGreaterThan(0);
			Condition.Requires( idMovimentoOrigine, "idMovimentoOrigine").IsGreaterThan(0);

			ApplyChange( new MovimentoCreato{
				IdComune = idComune,
				IdMovimentoDaEffettuare = id,
				IdMovimentoOrigine = idMovimentoOrigine
			});
		}

		private void Apply(MovimentoCreato @event)
		{
			this._idComune = @event.IdComune;
			this._id = @event.IdMovimentoDaEffettuare;
			this._idMovimentoOrigine = @event.IdMovimentoOrigine;
		}


		internal void ModificaNote(string testoNote)
		{
			if (this._testoNote == testoNote)
				return;

			ApplyChange(new NoteMovimentoModificate { 
				IdComune = this._idComune,
				IdMovimento = this.Id,
				TestoNote = testoNote
			});
		}

		private void Apply(NoteMovimentoModificate @event)
		{
			this._testoNote = @event.TestoNote;
		}

		internal void ModificaValoreDatoDinamico(int idCampoDinamico, int indiceMolteplicita, string valore, string valoreDecodificato)
		{
			Condition.Requires(idCampoDinamico, "idCampoDinamico").IsNotLessOrEqual(0);
			Condition.Requires(indiceMolteplicita, "indiceMolteplicita").IsNotLessThan(0);



			var valoreDatoDinamico = new ValoreDatoDinamico(valore, valoreDecodificato);

			var campo = _campiDinamici.ContainsKey(idCampoDinamico) ? _campiDinamici[idCampoDinamico] : (CampoDinamico)null;

			var campoNonHaUnValore = new CampoNonHaUnValoreAllIndiceSpecification(indiceMolteplicita);

			// Se il campo non contiene un valore lo aggiungo
			if (campoNonHaUnValore.IsSatisfiedBy(campo))
			{
				ApplyChange(new ValoreDatoDinamicoAggiuntoAlMovimento
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

			if ( campoHaUnValoreDiverso.IsSatisfiedBy( campo ))
			{
				ApplyChange(new ValoreDatoDinamicoDelMovimentoModificato
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

		private void Apply(ValoreDatoDinamicoAggiuntoAlMovimento @event)
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

		private void Apply(ValoreDatoDinamicoDelMovimentoModificato @event)
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

				ApplyChange(new RiepilogoSchedaDinamicaRimossoDalMovimento {
					IdComune = this._idComune,
					IdMovimento = this._id,
					IdSchedaDinamica = idSchedaDinamica,
					IdAllegato = riepilogo.IdAllegato					
				});
			}

			ApplyChange(new RiepilogoSchedaDinamicaAllegatoAlMovimento { 
				IdComune = this._idComune,
				IdMovimento = this._id,
				IdSchedaDinamica = idSchedaDinamica,
				IdAllegato =idAllegato,
				NomeFile = nomeFile,
				FirmatoDigitalmente = firmatoDigitalmente
			});
		}

		private void Apply(RiepilogoSchedaDinamicaAllegatoAlMovimento @event)
		{
			this._riepiloghiSchede.Add(@event.IdSchedaDinamica, new RiepilogoSchedaDinamica(@event.IdAllegato, @event.NomeFile, @event.FirmatoDigitalmente));
		}

		private void Apply(RiepilogoSchedaDinamicaRimossoDalMovimento @event)
		{
			this._riepiloghiSchede.Remove(@event.IdSchedaDinamica);
		}

		internal void RimuoviRiepilogoSchedaDinamica(int idSchedaDinamica)
		{
			if (!this._riepiloghiSchede.ContainsKey(idSchedaDinamica))
				return;

			var riepilogo = this._riepiloghiSchede[idSchedaDinamica];


			ApplyChange(new RiepilogoSchedaDinamicaRimossoDalMovimento { 
				IdComune = this._idComune,
				IdMovimento = this._id,
				IdSchedaDinamica = idSchedaDinamica,
				IdAllegato = riepilogo.IdAllegato
			});
		}

		internal void AggiungiAllegato(int idAllegato, string nomeFile, string descrizione, bool firmatoDigitalmente = true)
		{
			Condition.Requires(idAllegato, "idAllegato").IsGreaterThan(0);
			Condition.Requires(nomeFile, "nomeFile").IsNotNullOrEmpty();
			Condition.Requires(nomeFile, "descrizione").IsNotNullOrEmpty();

			ApplyChange(new AllegatoAggiuntoAlMovimento { 
				IdComune = this._idComune,
				IdMovimento = this._id,
				IdAllegato = idAllegato,
				Descrizione = descrizione,
				NomeFile = nomeFile,
				FirmatoDigitalmente = firmatoDigitalmente
			});
		}

		private void Apply(AllegatoAggiuntoAlMovimento @event)
		{
			this._allegatiPresenti.Add( new AllegatoDelMovimento( @event.IdAllegato, @event.FirmatoDigitalmente ) );
		}

		internal void RimuoviAllegato(int idAllegato)
		{
			Condition.Requires(idAllegato, "idAllegato").IsGreaterThan(0);

			if (this._allegatiPresenti.Where( x => x.IdAllegato == idAllegato ).Count() == 0 )
				throw new ModelValidationException("L'id allegato " + idAllegato + " non è presente nel movimento con id " + this._id);

			ApplyChange(new AllegatoRimossoDalMovimento { 
				IdComune = this._idComune,
				IdMovimento = this._id,
				IdAllegato = idAllegato
			});
		}

		private void Apply(AllegatoRimossoDalMovimento @event)
		{
			var allegati = this._allegatiPresenti.Where( x => x.IdAllegato == @event.IdAllegato );

			allegati.ToList().ForEach( x => {
				this._allegatiPresenti.Remove(x);
			});
			
		}

		internal void Trasmetti(ITrasmissioneMovimentoService trasmissioneMovimentoService)
		{
			trasmissioneMovimentoService.Trasmetti(this._id);

			ApplyChange(new MovimentoTrasmesso
			{ 
				Data = DateTime.Now,
				IdComune = this._idComune,
				IdMovimento = this._id
			});
		}

		private void Apply(MovimentoTrasmesso @event)
		{
			//this._trasmesso = true;
		}


		internal void EliminaValoriCampo(int idCampo)
		{
			if (!_campiDinamici.ContainsKey(idCampo))
				return;

			if (!_campiDinamici[idCampo].ContieneValori())
				return;

			ApplyChange(new ValoriCampoDinamicoEliminati { 
				IdCampo = idCampo,
				IdComune = this._idComune,
				IdMovimento = this._id
			});
		}

		private void Apply(ValoriCampoDinamicoEliminati @event)
		{
			this._campiDinamici[@event.IdCampo].EliminaValori();
		}

		internal void ComlpetaCompilazioneScheda(int idScheda)
		{
			ApplyChange(new CompilazioneSchedaDinamicaCompletata { 
				IdComune = this._idComune,
				IdMovimento = this._id,
				IdScheda = idScheda
			});
		}

        internal void RimuoviAllegatoDellaSchedaDinamica(RimuoviAllegatoDellaSchedaDinamica cmd)
        {
            ApplyChange(new AllegatoRimossoDaSchedaDinamica(
                cmd.IdComune, 
                cmd.IdMovimento, 
                cmd.IdCampoDinamico, 
                cmd.IndiceMolteplicita, 
                cmd.VecchioValore));
        }

        private void Apply(AllegatoRimossoDaSchedaDinamica @event)
        {

        }

        private void Apply(CompilazioneSchedaDinamicaCompletata @event) { }

		internal void FirmaRiepilogoSchedaDinamica(int codiceOggetto, string nomeFile)
		{
			ApplyChange(new RiepilogoSchedaDinamicaFirmatoDigitalmente
			{
				IdComune = this._idComune,
				CodiceOggetto = codiceOggetto,
				IdMovimento = this._id,
				NomeFile = nomeFile
			});
		}


		private void Apply(RiepilogoSchedaDinamicaFirmatoDigitalmente @event)
		{
			this._riepiloghiSchede
					.Where( x => x.Value.IdAllegato == @event.CodiceOggetto)
					.Select( x => x.Value )
					.ToList()
					.ForEach( x => x.FirmaDigitalmente() );
		}

		internal void FirmaAllegato(int codiceOggetto, string nomeFile)
		{
			ApplyChange(new AllegatoFirmatoDigitalmente
			{
				IdComune = this._idComune,
				CodiceOggetto = codiceOggetto,
				IdMovimento = this._id,
				NomeFile = nomeFile
			});
		}

		private void Apply(AllegatoFirmatoDigitalmente @event)
		{
			this._allegatiPresenti
					.Where(x => x.IdAllegato == @event.CodiceOggetto)
					.ToList()
					.ForEach(x => x.FirmaDigitalmente());
		}

        internal void SostituisciDocumento(OrigineDocumentoSostituzioneDocumentale origine, int codiceOggettoOriginale, string nomeFileOriginale, int codiceOggettoSostitutivo, string nomeFileSostitutivo)
        {
            ApplyChange(new SostituzioneDocumentaleEffettuata 
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

        private void Apply(SostituzioneDocumentaleEffettuata @event)
        {

        }

        internal void AnnullaSostituzioneDocumentale(int codiceOggettoSostitutivo)
        {
            ApplyChange(new SostituzioneDocumentaleAnnullata
            {
                IdComune = this._idComune,
                IdMovimento = this._id,
                CodiceOggettoSostitutivo = codiceOggettoSostitutivo
            });
        }

        private void Apply(SostituzioneDocumentaleAnnullata @event)
        {

        }
    }
}
