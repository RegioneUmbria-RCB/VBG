// -----------------------------------------------------------------------
// <copyright file="RiepilogoMovimentoDaEffettuareViewModel.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.ReadInterface;
	using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.ReadInterface.Persistence;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
	using Init.Sigepro.FrontEnd.Infrastructure;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.Commands;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence;
using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class RiepilogoMovimentoDaEffettuareViewModel
	{
		public class RigaRiepilogoSchedeDinamiche
		{
			public string NomeScheda { get; protected set; }
			public int CodiceOggetto { get; protected set; }
			public string NomeFile { get; protected set; }

			public RigaRiepilogoSchedeDinamiche(string nomeScheda, int codiceOggetto, string nomeFile)
			{
				this.NomeScheda = nomeScheda;
				this.CodiceOggetto = codiceOggetto;
				this.NomeFile = nomeFile;
			}
		}


		ICommandSender _bus;
		IMovimentiReadRepository _readRepository;
		OggettiService _oggettiService;
		IAliasResolver _aliasResolver;
		IIdMovimentoResolver _idMovimentoResolver;
		IVerificaFirmaDigitaleService _verificaFirmaDigitaleService;

		public RiepilogoMovimentoDaEffettuareViewModel(IAliasResolver aliasResolver, IIdMovimentoResolver idMovimentoResolver, ICommandSender eventBus, IMovimentiReadRepository readRepository, OggettiService oggettiService, IVerificaFirmaDigitaleService verificaFirmaDigitaleService)
		{
			this._aliasResolver = aliasResolver;
			this._bus = eventBus;
			this._readRepository = readRepository;
			this._oggettiService = oggettiService;
			this._idMovimentoResolver = idMovimentoResolver;
			this._verificaFirmaDigitaleService = verificaFirmaDigitaleService;
		}

		public DatiMovimentoDaEffettuare GetMovimentoDaEffettuare()
		{
			return this._readRepository.GetById(this._idMovimentoResolver.IdMovimento);
		}


		public void CaricaAllegato( string descrizione, BinaryFile file)
		{
			if (String.IsNullOrEmpty(descrizione))
				throw new ValidationException("Specificare una descrizione per l'allegato");

			var esitoValidazione = _verificaFirmaDigitaleService.VerificaFirmaDigitale(file);

			var firmatoDigitalmente = new FirmaValidaSpecification().IsSatisfiedBy(esitoValidazione);

			var codiceOggetto = this._oggettiService.InserisciOggetto(file.FileName, file.MimeType, file.FileContent);

			var cmd = new AggiungiAllegatoAlMovimentoV2(this._aliasResolver.AliasComune, this._idMovimentoResolver.IdMovimento, codiceOggetto, file.FileName, descrizione, firmatoDigitalmente );

			this._bus.Send(cmd);
		}

		public IEnumerable<string> ValidaPerInvio()
		{
			return this.GetMovimentoDaEffettuare().Allegati.Where(x => !x.FirmatoDigitalmente).Select(x => "Il file \"" + x.Note + "\" non è stato firmato digitalmente oppure non è firmato digitalmente oppure non contiene firme digitali valide");
		}

		public void Invia()
		{
			var cmd = new TrasmettiMovimento(this._aliasResolver.AliasComune, this._idMovimentoResolver.IdMovimento);

			this._bus.Send(cmd);
		}

		public void EliminaAllegato( int idAllegato)
		{
			var cmd = new RimuoviAllegatoDalMovimento(this._aliasResolver.AliasComune, this._idMovimentoResolver.IdMovimento, idAllegato);

			this._bus.Send(cmd);
		}

		public void AggiornaNoteMovimento( string note)
		{
			var cmd = new ModificaNoteMovimento(this._aliasResolver.AliasComune, this._idMovimentoResolver.IdMovimento, note);

			this._bus.Send(cmd);
		}

		public IEnumerable<RigaRiepilogoSchedeDinamiche> GetListaSchedeCompilate()
		{
			var movimento = this.GetMovimentoDaEffettuare();

			return movimento.GetRiepiloghiSchedeDinamiche()
							.Where(x => x.CodiceOggetto.HasValue)
							.Select(x => new RigaRiepilogoSchedeDinamiche(x.NomeScheda, x.CodiceOggetto.Value, x.NomeFile));
		}
	}
}
