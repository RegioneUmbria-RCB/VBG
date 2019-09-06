using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAllegati;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using log4net;
using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale;

namespace Init.Sigepro.FrontEnd.AppLogic.ViewModels.FirmaDigitale
{
	public class FirmaDocumentoViewModel
	{
		ILog							_log = LogManager.GetLogger(typeof(FirmaDocumentoViewModel));
		IOggettiService					_oggettiService;
		IVerificaFirmaDigitaleService	_firmaDigitaleService;
		AllegatiService					_allegatiService;
		ITokenApplicazioneService		_tokenApplicazioneService;
		IAliasResolver					_aliasResolver;
		IAuthenticationDataResolver		_authenticationDataResolver;

		public FirmaDocumentoViewModel(IAliasResolver aliasResolver, IAuthenticationDataResolver authenticationDataResolver, IOggettiService oggettiService, IVerificaFirmaDigitaleService firmaDigitaleService, AllegatiService allegatiService, ITokenApplicazioneService tokenApplicazioneService)
		{
			this._oggettiService			= oggettiService;
			this._firmaDigitaleService		= firmaDigitaleService;
			this._allegatiService			= allegatiService;
			this._tokenApplicazioneService	= tokenApplicazioneService;
			this._aliasResolver				= aliasResolver;
			this._authenticationDataResolver = authenticationDataResolver;
		}

		public string GetTokenApplicazione()
		{
			return this._tokenApplicazioneService.GetToken(this._aliasResolver.AliasComune);
		}

		public void AggiornaStatoFirma(int idDomanda,int codiceOggetto, string nomeFile)
		{
			// Se la verifica ha successo imposto il flag "FirmatoDigitalmente" dell'allegato della domanda
			// e aggiorna il nome file
			_allegatiService.ModificaNomeFileEFlagFirmaDaCodiceOggetto(idDomanda, codiceOggetto, nomeFile, true);
		}

		public string GetNomeFile(int codiceOggetto)
		{
			return this._oggettiService.GetNomeFile(codiceOggetto);
		}
	}
}
