// -----------------------------------------------------------------------
// <copyright file="MovimentiEventHandler.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti
{
	using System.Linq;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.Commands;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices;
	using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
	using Init.Sigepro.FrontEnd.Infrastructure.Repositories;
	using System.Collections.Generic;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneSchedeDinamiche;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine;
using System;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class MovimentiCommandHandler : IEventHandler, Handles<CreaMovimento>,
														Handles<ModificaNoteMovimento>,
														Handles<ModificaValoreDatoDinamicoDelMovimento>,
														Handles<AllegaRiepilogoSchedaDinamicaAMovimento>,
														Handles<AllegaRiepilogoSchedaDinamicaAMovimentoV2>,
														Handles<RimuoviRiepilogoSchedaDinamicaDalMovimento>,
														Handles<AggiungiAllegatoAlMovimento>,
														Handles<AggiungiAllegatoAlMovimentoV2>,
														Handles<RimuoviAllegatoDalMovimento>,
														Handles<FirmaDigitalmenteAllegatoMovimento>,
														Handles<TrasmettiMovimento>,
														Handles<EliminaValoriCampo>,
														Handles<CompletaCompilazioneSchedaDinamica>,
														Handles<FirmaDigitalmenteRiepilogo>,
                                                        Handles<SostituisciDocumento>,
                                                        Handles<AnnullaSostituzioneDocumentale>,
                                                        Handles<RimuoviAllegatoDellaSchedaDinamica>
	{
		IRepository<MovimentoFrontoffice> _repository;
		ITrasmissioneMovimentoService _trasmissioneService;
        IMovimentiDiOrigineRepository _movimentoDiOrigineRepository;

		public MovimentiCommandHandler(IRepository<MovimentoFrontoffice> repository, ITrasmissioneMovimentoService trasmissioneService,
                                        IMovimentiDiOrigineRepository movimentoDiOrigineRepository)
		{
			this._repository = repository;
			this._trasmissioneService = trasmissioneService;
            this._movimentoDiOrigineRepository = movimentoDiOrigineRepository;
		}

		#region Handles<CreaMovimento> Members

		public void Handle(CreaMovimento message)
		{
            var movimentoOrigine = this._movimentoDiOrigineRepository.GetByIdHackUsaSoloPerCreazioneMovimento(message.IdMovimentoOrigine);
            var movimentoDaEffettuare = this._movimentoDiOrigineRepository.GetByIdHackUsaSoloPerCreazioneMovimento(message.IdMovimentoDaEffettuare);

            if (movimentoDaEffettuare.PubblicaSchede)
            {
                movimentoOrigine.SchedeDinamiche = movimentoDaEffettuare.SchedeDinamiche;
            }

			var valoriSchedeDinamiche = movimentoOrigine.SchedeDinamiche != null ? movimentoOrigine.SchedeDinamiche.SelectMany(x => x.Valori) : new List<ValoreSchedaDinamicaMovimento>(); 

			var movimento = new MovimentoFrontoffice.Factory()
													.Crea( cfg =>
														   cfg.ConIdComune(message.IdComune)
															  .ConIdMovimentoDaEffettuare( message.IdMovimentoDaEffettuare )
															  .ConValoriDatiDinamici( valoriSchedeDinamiche )
															  .ConIdMovimentoOrigine( message.IdMovimentoOrigine ));

			this._repository.Save(movimento);
		}

		#endregion

		#region Handles<ModificaNoteMovimento> Members

		public void Handle(ModificaNoteMovimento message)
		{
			var movimento = this._repository.GetById(message.IdMovimento);

			if (movimento == null)
				throw new AggregateRootNotFoundException("Il movimento con id " + message.IdMovimento + " non è stato trovato");

			movimento.ModificaNote(message.TestoNote);

			this._repository.Save(movimento);

		}

		#endregion

		#region Handles<ModificaValoreDatoDinamico> Members

		public void Handle(ModificaValoreDatoDinamicoDelMovimento message)
		{
			var movimento = this._repository.GetById(message.IdMovimento);

			if (movimento == null)
				throw new AggregateRootNotFoundException("Il movimento con id " + message.IdMovimento + " non è stato trovato");

			movimento.ModificaValoreDatoDinamico(message.IdCampoDinamico, message.IndiceMolteplicita, message.Valore, message.ValoreDecodificato);

			this._repository.Save(movimento);
		}

		#endregion

		#region Handles<AllegaRiepilogoSchedaDinamicaAMovimento> Members

		public void Handle(AllegaRiepilogoSchedaDinamicaAMovimento message)
		{
			var movimento = this._repository.GetById(message.IdMovimento);

			if (movimento == null)
				throw new AggregateRootNotFoundException("Il movimento con id " + message.IdMovimento + " non è stato trovato");

			movimento.AllegaRiepilogoSchedaDinamica( message.IdSchedaDinamica , message.IdAllegato , message.NomeFile );

			this._repository.Save(movimento);
		}

		#endregion

		#region Handles<AllegaRiepilogoSchedaDinamicaAMovimentoV2> Members

		public void Handle(AllegaRiepilogoSchedaDinamicaAMovimentoV2 message)
		{
			var movimento = this._repository.GetById(message.IdMovimento);

			if (movimento == null)
				throw new AggregateRootNotFoundException("Il movimento con id " + message.IdMovimento + " non è stato trovato");

			movimento.AllegaRiepilogoSchedaDinamica(message.IdSchedaDinamica, message.IdAllegato, message.NomeFile, message.FirmatoDigitalmente);

			this._repository.Save(movimento);
		}

		#endregion

		#region Handles<RimuoviRiepilogoSchedaDinamicaDalMovimento> Members

		public void Handle(RimuoviRiepilogoSchedaDinamicaDalMovimento message)
		{
			var movimento = this._repository.GetById(message.IdMovimento);

			if (movimento == null)
				throw new AggregateRootNotFoundException("Il movimento con id " + message.IdMovimento + " non è stato trovato");

			movimento.RimuoviRiepilogoSchedaDinamica(message.IdSchedaDinamica);

			this._repository.Save(movimento);
		}

		#endregion

		#region Handles<AggiungiAllegatoAlMovimento> Members

		public void Handle(AggiungiAllegatoAlMovimento message)
		{
			var movimento = this._repository.GetById(message.IdMovimento);

			if (movimento == null)
				throw new AggregateRootNotFoundException("Il movimento con id " + message.IdMovimento + " non è stato trovato");

			movimento.AggiungiAllegato(message.IdAllegato , message.NomeFile, message.Descrizione);

			this._repository.Save(movimento);
		}

		#endregion

		#region Handles<AggiungiAllegatoAlMovimentoV2> Members

		public void Handle(AggiungiAllegatoAlMovimentoV2 message)
		{
			var movimento = this._repository.GetById(message.IdMovimento);

			if (movimento == null)
				throw new AggregateRootNotFoundException("Il movimento con id " + message.IdMovimento + " non è stato trovato");

			movimento.AggiungiAllegato(message.IdAllegato, message.NomeFile, message.Descrizione, message.FirmatoDigitalmente);

			this._repository.Save(movimento);
		}

		#endregion

		#region Handles<RimuoviAllegatoDalMovimento> Members

		public void Handle(RimuoviAllegatoDalMovimento message)
		{
			var movimento = this._repository.GetById(message.IdMovimento);

			if (movimento == null)
				throw new AggregateRootNotFoundException("Il movimento con id " + message.IdMovimento + " non è stato trovato");

			movimento.RimuoviAllegato(message.IdAllegato);

			this._repository.Save(movimento);
		}

		#endregion

		#region Handles<TrasmettiMovimento> Members

		public void Handle(TrasmettiMovimento message)
		{
			var movimento = this._repository.GetById(message.IdMovimento);

			if (movimento == null)
				throw new AggregateRootNotFoundException("Il movimento con id " + message.IdMovimento + " non è stato trovato");

			movimento.Trasmetti( this._trasmissioneService );

			this._repository.Save(movimento);
		}

		#endregion

		#region Handles<EliminaValoriCampo> Members

		public void Handle(EliminaValoriCampo message)
		{
			var movimento = this._repository.GetById(message.IdMovimento);

			if (movimento == null)
				throw new AggregateRootNotFoundException("Il movimento con id " + message.IdMovimento + " non è stato trovato");

			movimento.EliminaValoriCampo( message.IdCampoDinamico );

			this._repository.Save(movimento);
		}

		#endregion

		#region Handles<CompletaCompilazioneSchedaDinamica> Members

		public void Handle(CompletaCompilazioneSchedaDinamica message)
		{
			var movimento = this._repository.GetById(message.IdMovimento);

			if (movimento == null)
				throw new AggregateRootNotFoundException("Il movimento con id " + message.IdMovimento + " non è stato trovato");

			movimento.ComlpetaCompilazioneScheda(message.IdScheda);

			this._repository.Save(movimento);
		}

		#endregion


		public void Handle(FirmaDigitalmenteRiepilogo message)
		{
			var movimento = this._repository.GetById(message.IdMovimento);

			if (movimento == null)
				throw new AggregateRootNotFoundException("Il movimento con id " + message.IdMovimento + " non è stato trovato");

			movimento.FirmaRiepilogoSchedaDinamica(message.CodiceOggetto, message.NomeFile);

			this._repository.Save(movimento);
		}

		public void Handle(FirmaDigitalmenteAllegatoMovimento message)
		{
			var movimento = this._repository.GetById(message.IdMovimento);

			if (movimento == null)
				throw new AggregateRootNotFoundException("Il movimento con id " + message.IdMovimento + " non è stato trovato");

			movimento.FirmaAllegato(message.CodiceOggetto, message.NomeFile);

			this._repository.Save(movimento);
		}

        public void Handle(SostituisciDocumento message)
        {
            Execute(message.IdMovimento, movimento =>
            {
                movimento.SostituisciDocumento(
                    message.OrigineDocumento, 
                    message.CodiceOggettoOriginale,
                    message.NomeFileOriginale,
                    message.CodiceOggettoSostitutivo,
                    message.NomeFileSostitutivo
                );
            });
        }


        private void Execute(int idMovimento, Action<MovimentoFrontoffice> action)
        {
            var movimento = this._repository.GetById(idMovimento);

            if (movimento == null)
				throw new AggregateRootNotFoundException("Il movimento con id " + idMovimento + " non è stato trovato");

            action(movimento);

            this._repository.Save(movimento);
        }

        public void Handle(AnnullaSostituzioneDocumentale message)
        {
            Execute(message.IdMovimento, movimento =>
            {
                movimento.AnnullaSostituzioneDocumentale(message.CodiceOggettoSostitutivo);
            });
        }

        public void Handle(RimuoviAllegatoDellaSchedaDinamica message)
        {
            Execute(message.IdMovimento, movimento => {
                movimento.RimuoviAllegatoDellaSchedaDinamica(message);
            });
        }
    }
}
