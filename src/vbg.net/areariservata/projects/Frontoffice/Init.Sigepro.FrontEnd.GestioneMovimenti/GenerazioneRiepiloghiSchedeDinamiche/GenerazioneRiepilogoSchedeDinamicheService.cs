using System;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.Services.ConversioneDomanda;
using Init.Sigepro.FrontEnd.GestioneMovimenti.DatiDinamici;
using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
using Init.SIGePro.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.StrutturaModelli;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine;
using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.GenerazioneRiepiloghiSchedeDinamiche
{
	public class GenerazioneRiepilogoSchedeDinamicheService : IGenerazioneRiepilogoSchedeDinamicheService
	{
		IAliasResolver _aliasResolver;
		ICommandSender _commandSender;
		IDatiDinamiciRepository _datiDinamiciRepository;
		ITokenApplicazioneService _tokenService;
		FileConverterService _fileConverter;
        IHtmlToPdfFileConverter _htmlToPdf;
		MovimentiIstanzeManager _istanzeManager;
		IStrutturaModelloReader _strutturaModelloReader;
        IMovimentiDiOrigineRepository _movimentiDiOrigineRepository;

		private static class Constants
		{
			public const string HtmlWrapper = "<html><head>{0}</head><body>{1}</body></html>";
			public const string NomeFiledaConvertire = "riepilogoScheda.htm";
			public const string Tipoconversione = "PDF";
		}

		public GenerazioneRiepilogoSchedeDinamicheService(	IAliasResolver aliasResolver, 
															ICommandSender commandSender, 
															IDatiDinamiciRepository datiDinamiciRepository, 
															ITokenApplicazioneService tokenService, 
															FileConverterService fileConverter, 
                                                            IHtmlToPdfFileConverter htmlToPdf,
															MovimentiIstanzeManager istanzeManager,
															IStrutturaModelloReader strutturaModelloReader,
                                                            IMovimentiDiOrigineRepository movimentiDiOrigineRepository)
		{
			this._aliasResolver = aliasResolver;
			this._commandSender = commandSender;
			this._datiDinamiciRepository = datiDinamiciRepository;
			this._tokenService = tokenService;
			this._fileConverter = fileConverter;
			this._istanzeManager = istanzeManager;
			this._strutturaModelloReader = strutturaModelloReader;
            this._movimentiDiOrigineRepository = movimentiDiOrigineRepository;
            this._htmlToPdf = htmlToPdf;
		}

		#region IGenerazioneRiepilogoSchedeDinamicheService Members

		public AppLogic.GestioneOggetti.BinaryFile GeneraRiepilogoScheda(MovimentoDaEffettuare movimento, int idScheda)
		{
            var movimentoDiOrigine = this._movimentiDiOrigineRepository.GetById(movimento);
			var generatoreScheda = new GeneratoreHtmlSchedeDinamiche(this._tokenService, this._strutturaModelloReader);

            var codiceIstanza = movimento.CodiceIstanza;
            var cacheModello = this._datiDinamiciRepository.GetCacheModelloDinamico(idScheda);

            var dap = new Dyn2DataAccessProvider(cacheModello, this._aliasResolver.AliasComune, this._tokenService, movimentoDiOrigine, movimento, this._commandSender, this._istanzeManager);
			var loader = new ModelloDinamicoLoader(dap, this._aliasResolver.AliasComune, ModelloDinamicoLoader.TipoModelloDinamicoEnum.Frontoffice);
			var scheda = new ModelloDinamicoIstanza(loader, idScheda, -1, 0, false);

			var html = String.Format(Constants.HtmlWrapper, GenerazioneHtmlDomandaConstants.CssModelliDinamici, new ModelloDinamicoHtmlRenderer(scheda).GetHtml());

            return this._htmlToPdf.Converti(Constants.NomeFiledaConvertire, html);

			//return this._fileConverter.Converti( Constants.NomeFiledaConvertire, Encoding.Default.GetBytes(html), Constants.Tipoconversione);
		}

		#endregion
	}
}
