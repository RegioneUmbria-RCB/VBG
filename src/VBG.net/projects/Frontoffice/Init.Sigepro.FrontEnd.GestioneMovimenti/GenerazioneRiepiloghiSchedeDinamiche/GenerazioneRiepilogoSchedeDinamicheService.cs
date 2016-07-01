using System;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestioneSostituzioneSegnapostoRiepilogo;
using Init.Sigepro.FrontEnd.AppLogic.Services.ConversioneDomanda;
using Init.Sigepro.FrontEnd.GestioneMovimenti.DatiDinamici;
using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
using Init.SIGePro.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.StrutturaModelli;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.GenerazioneRiepiloghiSchedeDinamiche
{
	public class GenerazioneRiepilogoSchedeDinamicheService : IGenerazioneRiepilogoSchedeDinamicheService
	{
		IAliasResolver _aliasResolver;
		ICommandSender _commandSender;
		IDatiDinamiciRepository _datiDinamiciRepository;
		ITokenApplicazioneService _tokenService;
		FileConverterService _fileConverter;
		MovimentiIstanzeManager _istanzeManager;
		IStrutturaModelloReader _strutturaModelloReader;

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
															MovimentiIstanzeManager istanzeManager,
															IStrutturaModelloReader strutturaModelloReader)
		{
			this._aliasResolver = aliasResolver;
			this._commandSender = commandSender;
			this._datiDinamiciRepository = datiDinamiciRepository;
			this._tokenService = tokenService;
			this._fileConverter = fileConverter;
			this._istanzeManager = istanzeManager;
			this._strutturaModelloReader = strutturaModelloReader;
		}

		#region IGenerazioneRiepilogoSchedeDinamicheService Members

		public AppLogic.GestioneOggetti.BinaryFile GeneraRiepilogoScheda(ReadInterface.DatiMovimentoDaEffettuare movimento, int idScheda)
		{
			var generatoreScheda = new GeneratoreHtmlSchedeDinamiche(this._tokenService, this._strutturaModelloReader);

			var codiceScheda = movimento.MovimentoDiOrigine.DatiIstanza.CodiceIstanza;

			var dap = new Dyn2DataAccessProvider(this._datiDinamiciRepository, this._aliasResolver.AliasComune, idScheda, this._tokenService, movimento, this._commandSender, this._istanzeManager, codiceScheda);
			var loader = new ModelloDinamicoLoader(dap, this._aliasResolver.AliasComune, true);
			var scheda = new ModelloDinamicoIstanza(loader, idScheda, -1, 0, false);

			var html = String.Format(Constants.HtmlWrapper, GenerazioneHtmlDomandaConstants.CssModelliDinamici, generatoreScheda.GeneraHtmlScheda(scheda));

			return this._fileConverter.Converti( Constants.NomeFiledaConvertire, Encoding.Default.GetBytes(html), Constants.Tipoconversione);
		}

		#endregion
	}
}
