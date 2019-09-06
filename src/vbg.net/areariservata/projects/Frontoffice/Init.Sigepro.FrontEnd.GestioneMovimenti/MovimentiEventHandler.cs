// -----------------------------------------------------------------------
// <copyright file="MovimentiEventHandler.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti
{
	using System.Linq;
	using CuttingEdge.Conditions;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.Events;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices;
	using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneSchedeDinamiche;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;
    using System;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class MovimentiEventHandler: IEventHandler,	Handles<MovimentoCreato>,
														Handles<NoteMovimentoModificate>,
														Handles<AllegatoAggiuntoAlMovimento>,
														Handles<AllegatoRimossoDalMovimento>,
														Handles<MovimentoTrasmesso>,
														Handles<ValoreDatoDinamicoAggiuntoAlMovimento>,
														Handles<ValoreDatoDinamicoDelMovimentoModificato>,
														Handles<ValoriCampoDinamicoEliminati>,
														Handles<CompilazioneSchedaDinamicaCompletata>,
														Handles<RiepilogoSchedaDinamicaAllegatoAlMovimento>,
														Handles<RiepilogoSchedaDinamicaRimossoDalMovimento>,
														Handles<RiepilogoSchedaDinamicaFirmatoDigitalmente>,
														Handles<AllegatoFirmatoDigitalmente>,
                                                        Handles<SostituzioneDocumentaleEffettuata>,
                                                        Handles<SostituzioneDocumentaleAnnullata>,
                                                        Handles<AllegatoRimossoDaSchedaDinamica>
	{
		IMovimentiBackofficeService _movimentiBackofficeService;
        IMovimentiDaEffettuareRepository _movimentiReadRepository;
		IScadenzeService _scadenzeService;

        public MovimentiEventHandler(IMovimentiBackofficeService movimentiBackofficeService, IMovimentiDaEffettuareRepository movimentiReadRepository, IScadenzeService scadenzeService)
		{
			Condition.Requires(movimentiBackofficeService, "movimentiBackofficeService").IsNotNull();
			Condition.Requires(movimentiReadRepository, "movimentiReadRepository").IsNotNull();
			Condition.Requires(scadenzeService, "scadenzeService").IsNotNull();

			this._movimentiBackofficeService = movimentiBackofficeService;
			this._movimentiReadRepository = movimentiReadRepository;
			this._scadenzeService = scadenzeService;
		}

		#region Handles<MovimentoCreato> Members

		public void Handle(MovimentoCreato message)
		{
			// Arricchisco i dati relativi al movimento da creare
			var scadenza = this._scadenzeService.GetById(message.IdMovimentoDaEffettuare);

			var movimento = new MovimentoDaEffettuare
			{
				Id = message.IdMovimentoDaEffettuare,
                IdMovimentoDiOrigine = message.IdMovimentoOrigine,
				NomeAttivita = scadenza.DescrMovimentoDaFare,
				IdTipoAttivita = scadenza.CodMovimentoDaFare,
				Note = string.Empty,
                Version = SerializationSettings.CurrentVersion,
                CodiceIstanza = Convert.ToInt32(scadenza.CodiceIstanza),
                AliasComune = scadenza.CodEnte
			};
			
			_movimentiReadRepository.Save(movimento);
		}

		#endregion

		#region Handles<NoteMovimentoModificate> Members

		public void Handle(NoteMovimentoModificate message)
		{
			var movimento = this._movimentiReadRepository.GetById(message.IdMovimento);

			movimento.Note = message.TestoNote;

			this._movimentiReadRepository.Save(movimento);
		}

		#endregion

		#region Handles<AllegatoAggiuntoAlMovimento> Members

		public void Handle(AllegatoAggiuntoAlMovimento message)
		{
			var movimento = this._movimentiReadRepository.GetById(message.IdMovimento);

			movimento.Allegati.Add(new DatiAllegatoMovimento { 
				IdAllegato = message.IdAllegato,
				Descrizione = message.Descrizione,
				Note = message.NomeFile,
				FirmatoDigitalmente = message.FirmatoDigitalmente
			});

			this._movimentiReadRepository.Save(movimento);
		}

		#endregion

		#region Handles<AllegatoRimossoDalMovimento> Members

		public void Handle(AllegatoRimossoDalMovimento message)
		{
			var movimento = this._movimentiReadRepository.GetById(message.IdMovimento);

			var allegato = movimento.Allegati.Where(all => all.IdAllegato == message.IdAllegato);

			movimento.Allegati.Remove(allegato.ElementAt(0));

			this._movimentiReadRepository.Save(movimento);
		}

		#endregion

		#region Handles<MovimentoTrasmesso> Members

		public void Handle(MovimentoTrasmesso message)
		{
			_movimentiBackofficeService.ImpostaComeTrasmesso(message.IdMovimento);
		}

		#endregion

		#region Handles<ValoreDatoDinamicoAggiuntoAlMovimento> Members

		public void Handle(ValoreDatoDinamicoAggiuntoAlMovimento message)
		{
			var movimento = this._movimentiReadRepository.GetById(message.IdMovimento);

			movimento.AggiungiValoreSchedaDinamica(new ValoreSchedaDinamicaMovimento
			{
				Id = message.IdCampoDinamico,
				IndiceMolteplicita = message.IndiceMolteplicita,
				Valore = message.Valore,
				ValoreDecodificato = message.ValoreDecodificato
			});

			this._movimentiReadRepository.Save(movimento);
		}

		#endregion

		#region Handles<ValoriCampoDinamicoEliminati> Members

		public void Handle(ValoriCampoDinamicoEliminati message)
		{
			var movimento = this._movimentiReadRepository.GetById(message.IdMovimento);

            movimento.EliminaValoriCampo(message.IdCampo);

			this._movimentiReadRepository.Save(movimento);
		}

		#endregion

		#region Handles<ValoreDatoDinamicoDelMovimentoModificato> Members

		public void Handle(ValoreDatoDinamicoDelMovimentoModificato message)
		{
			var movimento = this._movimentiReadRepository.GetById(message.IdMovimento);

			var campo = movimento.ValoriSchedeDinamiche.Where(x => message.IdCampoDinamico == x.Id && message.IndiceMolteplicita == x.IndiceMolteplicita).FirstOrDefault();

			campo.Valore = message.Valore;
			campo.ValoreDecodificato = message.ValoreDecodificato;

			this._movimentiReadRepository.Save(movimento);
		}

		#endregion


		#region Handles<CompilazioneSchedaDinamicaCompletata> Members

		public void Handle(CompilazioneSchedaDinamicaCompletata message)
		{
			var movimento = this._movimentiReadRepository.GetById(message.IdMovimento);

			movimento.SegnaSchedaComeCompilata(message.IdScheda);

			this._movimentiReadRepository.Save(movimento);
		}

		#endregion

		#region Handles<RiepilogoSchedaDinamicaAllegatoAlMovimento> Members

		public void Handle(RiepilogoSchedaDinamicaAllegatoAlMovimento message)
		{
			var movimento = this._movimentiReadRepository.GetById(message.IdMovimento);

			movimento.RiepiloghiSchedeDinamiche.Add(new DatiRiepilogoSchedaDinamica 
			{ 
				IdAllegato = message.IdAllegato,
				NomeFile = message.NomeFile,
				IdScheda = message.IdSchedaDinamica,
				FirmatoDigitalmente = message.FirmatoDigitalmente
			});

			this._movimentiReadRepository.Save(movimento);
		}

		#endregion

		#region Handles<RiepilogoSchedaDinamicaRimossoDalMovimento> Members

		public void Handle(RiepilogoSchedaDinamicaRimossoDalMovimento message)
		{
			var movimento = this._movimentiReadRepository.GetById(message.IdMovimento);

			movimento.RiepiloghiSchedeDinamiche.RemoveAll(x => x.IdScheda == message.IdSchedaDinamica);

			this._movimentiReadRepository.Save(movimento);
		}

		#endregion

		public void Handle(RiepilogoSchedaDinamicaFirmatoDigitalmente message)
		{
			var movimento = this._movimentiReadRepository.GetById(message.IdMovimento);

			movimento
				.RiepiloghiSchedeDinamiche
				.Where( x => x.IdAllegato == message.CodiceOggetto )
				.ToList()
				.ForEach( x => {
					x.NomeFile = message.NomeFile;
					x.FirmatoDigitalmente = true;
				});

			this._movimentiReadRepository.Save(movimento);
		}

		public void Handle(AllegatoFirmatoDigitalmente message)
		{
			var movimento = this._movimentiReadRepository.GetById(message.IdMovimento);

			movimento
				.Allegati
				.Where(x => x.IdAllegato == message.CodiceOggetto)
				.ToList()
				.ForEach(x => {
					x.FirmatoDigitalmente = true;
					x.Note = message.NomeFile;
				});

			this._movimentiReadRepository.Save(movimento);
		}

        public void Handle(SostituzioneDocumentaleEffettuata message)
        {
            Execute(message.IdMovimento, movimento =>
            {
                movimento.SostituzioniDocumentali.Add(new SostituzioneDocumentale 
                {
                    TipoDocumento = message.OrigineDocumento == Commands.OrigineDocumentoSostituzioneDocumentale.Endoprocedimento ? TipoSostituzioneDocumentaleEnum.Endo : TipoSostituzioneDocumentaleEnum.Intervento,
                    CodiceOggettoOrigine = message.CodiceOggettoOriginale,
                    NomeFileOrigine = message.NomeFileOriginale,
                    CodiceOggettoSostitutivo = message.CodiceOggettoSostitutivo,
                    NomeFileSostitutivo = message.NomeFileSostitutivo
                });
            });
        }

        private void Execute(int idMovimento, Action<MovimentoDaEffettuare> action)
        {
            var movimento = this._movimentiReadRepository.GetById(idMovimento);

            action(movimento);                

            this._movimentiReadRepository.Save(movimento);
        }

        public void Handle(SostituzioneDocumentaleAnnullata message)
        {
            Execute(message.IdMovimento, movimento => {
                movimento.AnnullaSostituzioneDocumentale(message.CodiceOggettoSostitutivo);
            });
        }

        public void Handle(AllegatoRimossoDaSchedaDinamica message)
        {
            Execute(message.IdMovimento, x =>
            {
                x.RimuoviAllegatoDaSchedaDinamica(message.IdCampoDinamico, message.IndiceMolteplicita, message.VecchioValore);
            });
        }
    }
}
