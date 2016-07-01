// -----------------------------------------------------------------------
// <copyright file="CaricamentoRiepiloghiSchedeViewModel.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.ReadInterface.Persistence;
	using Init.Sigepro.FrontEnd.Infrastructure;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.Commands;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ReadInterface;
using Init.Sigepro.FrontEnd.AppLogic.Services.ConversioneDomanda;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.DatiDinamici;
	using Init.SIGePro.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
	using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneSostituzioneSegnapostoRiepilogo;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GenerazioneRiepiloghiSchedeDinamiche;
using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class CaricamentoRiepiloghiSchedeViewModel
	{
		IIdMovimentoResolver _idMovimentoResolver;
		IMovimentiReadRepository _readRepository;
		IOggettiService _oggettiService;
		IAliasResolver _aliasResolver;
		ICommandSender _commandSender;
		IGenerazioneRiepilogoSchedeDinamicheService _generazioneRiepilogoSchedeDinamicheService;
		IVerificaFirmaDigitaleService _verificaFirmaDigitaleService;

		public CaricamentoRiepiloghiSchedeViewModel(IAliasResolver aliasResolver, IIdMovimentoResolver idMovimentoResolver, IMovimentiReadRepository readRepository, IOggettiService oggettiService, ICommandSender commandSender, IGenerazioneRiepilogoSchedeDinamicheService generazioneRiepilogoSchedeDinamicheService, IVerificaFirmaDigitaleService verificaFirmaDigitaleService)
		{
			this._idMovimentoResolver = idMovimentoResolver;
			this._readRepository = readRepository;
			this._oggettiService = oggettiService;
			this._aliasResolver = aliasResolver;
			this._commandSender = commandSender;
			this._generazioneRiepilogoSchedeDinamicheService = generazioneRiepilogoSchedeDinamicheService;
			this._verificaFirmaDigitaleService = verificaFirmaDigitaleService;
		}

		public IEnumerable<DatiMovimentoDaEffettuare.RiepilogoSchedaDinamica> GetListaRiepiloghi()
		{
			var movimento = this._readRepository.GetById(this._idMovimentoResolver.IdMovimento);

			return movimento.GetRiepiloghiSchedeDinamiche();
		}

		public void CaricaRiepilogoScheda(int idScheda, BinaryFile file)
		{
			var esitoVerificaFirma = _verificaFirmaDigitaleService.VerificaFirmaDigitale(file);

			var firmatoDigitalemnte = new FirmaValidaSpecification().IsSatisfiedBy(esitoVerificaFirma);

			var codiceOggetto = this._oggettiService.InserisciOggetto(file.FileName, file.MimeType, file.FileContent);

			var cmd = new AllegaRiepilogoSchedaDinamicaAMovimentoV2(this._aliasResolver.AliasComune,
																	this._idMovimentoResolver.IdMovimento,
																	idScheda,
																	codiceOggetto,
																	file.FileName,
																	firmatoDigitalemnte);

			this._commandSender.Send(cmd);
		}

		public void EliminaRiepilogoScheda(int idScheda)
		{
			var cmd = new RimuoviRiepilogoSchedaDinamicaDalMovimento(this._aliasResolver.AliasComune,
																	  this._idMovimentoResolver.IdMovimento,
																	  idScheda);

			this._commandSender.Send(cmd);
		}

		public BinaryFile GeneraHtmlScheda(int idScheda)
		{
			var movimento = this._readRepository.GetById(this._idMovimentoResolver.IdMovimento);

			return this._generazioneRiepilogoSchedeDinamicheService.GeneraRiepilogoScheda(movimento, idScheda);
		}

		public string GetNomeAttivitaDaEffettuare()
		{
			var movimento = this._readRepository.GetById(this._idMovimentoResolver.IdMovimento);

			return movimento.NomeAttivita;
		}
	}
}
