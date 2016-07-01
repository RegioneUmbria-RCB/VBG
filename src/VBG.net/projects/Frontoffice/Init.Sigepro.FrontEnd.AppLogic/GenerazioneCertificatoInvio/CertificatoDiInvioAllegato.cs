using System;
using System.Threading;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Services.ConversioneDomanda;
using log4net;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneCertificatoInvio
{
	public class CertificatoDiInvioAllegato
	{
		private static class Constants
		{
			public const string NomeAllegatoRiepilogo = "Certificato di invio";
			public const string NomeFileRiepilogo = "CertificatoInvio.pdf";
			public const string MimeFileRiepilogo = "application/pdf";
		}

		ILog _log = LogManager.GetLogger(typeof(CertificatoDiInvioAllegato));

		IDettaglioPraticaRepository _visuraRepository;
		IAliasSoftwareResolver _aliasSoftwareResolver;
		FileConverterService _fileConverterService;
		IAllegatiIstanzaRepository _allegatiIstanzaRepository;
		IOggettiService _oggettiService;

		public CertificatoDiInvioAllegato(IAliasSoftwareResolver aliasSoftwareResolver, IDettaglioPraticaRepository visuraRepository, FileConverterService fileConverterService, IAllegatiIstanzaRepository allegatiIstanzaRepository, IOggettiService oggettiService)
		{
			this._visuraRepository = visuraRepository;
			this._aliasSoftwareResolver = aliasSoftwareResolver;
			this._fileConverterService = fileConverterService;
			this._allegatiIstanzaRepository = allegatiIstanzaRepository;
			this._oggettiService = oggettiService;
		}


		public void AllegaSeNonEsiste(string idDomandaBackoffice, CertificatoDiInvioHtml htmlCertificatoAllegato)
		{
			if( !CertificatoAllegatoEsiste( idDomandaBackoffice ) )
				AllegaCertificato( idDomandaBackoffice , htmlCertificatoAllegato );
		}

		private void AllegaCertificato(string idDomandaBackoffice, CertificatoDiInvioHtml htmlCertificatoAllegato)
		{
			// Allego l'oggetto all'istanza
			// Il caricamento dell'allegato viene effettuato in un altro thread
			var fileCertificatoPdf = new CertificatoDiInvioPdf( _fileConverterService).DaHtml(htmlCertificatoAllegato);

			try
			{
				_log.Debug("Inizio invocazione a AgiungiCertificatoAIstanzaThread.esegui");

				var codiceOggetto = _oggettiService.InserisciOggetto(Constants.NomeFileRiepilogo, Constants.MimeFileRiepilogo, fileCertificatoPdf.FileContent);

				_allegatiIstanzaRepository.AggiungiAllegatoIstanza(_aliasSoftwareResolver.AliasComune, Convert.ToInt32(idDomandaBackoffice), Constants.NomeAllegatoRiepilogo, codiceOggetto);

				_log.Debug("Invocazione a AgiungiCertificatoAIstanzaThread.esegui completata");
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore durante la chiamata a AllegaCertificato: {0}", ex.ToString());
			}
		}

		private bool CertificatoAllegatoEsiste(string idDomandaBackoffice)
		{
			return GetIdFileCertificato(idDomandaBackoffice).HasValue;
		}

		public BinaryFile GetByIdDomanda(string idDomandaBackoffice)
		{
			var idCertificato = GetIdFileCertificato(idDomandaBackoffice);

			if (idCertificato.GetValueOrDefault(-1) > 0)
				return _oggettiService.GetById(idCertificato.Value);

			return null;
		}

		public int? GetIdFileCertificato(string idDomandaBackoffice)
		{
			var iIdPratica = -1;

			if (!int.TryParse(idDomandaBackoffice, out iIdPratica))
				return -1;

			var istanza = _visuraRepository.GetById(_aliasSoftwareResolver.AliasComune, iIdPratica, false);

			if (istanza == null)
				return -1;

			foreach (var documento in istanza.DocumentiIstanza)
			{
				if (documento.DOCUMENTO == Constants.NomeAllegatoRiepilogo && !String.IsNullOrEmpty(documento.CODICEOGGETTO))
					return Convert.ToInt32(documento.CODICEOGGETTO);
			}

			return null;
		}


		#region thread per allegare il certificato alla domanda
		/// <summary>
		/// Classe che rappresenta il thread che verrà utilizzato 
		/// per il salvataggio del certificato di invio al comune
		/// </summary>
		protected class AgiungiCertificatoAIstanzaThread
		{
			ILog _log = LogManager.GetLogger(typeof(AgiungiCertificatoAIstanzaThread));

			/// <summary>
			/// id comune
			/// </summary>
			string _idComune;

			/// <summary>
			/// Contenuto del riepilogo in encodato in utf8 
			/// </summary>
			byte[] _contenutoPdf;

			/// <summary>
			/// codice istanza a cui verrà aggiunto l'allegato
			/// </summary>
			int _codiceIstanza;

			IAllegatiIstanzaRepository _allegatiIstanzaRepository;
			IOggettiService _oggettiService;

			public AgiungiCertificatoAIstanzaThread(string idComune, int codiceIstanza, byte[] contenutoPdf, IAllegatiIstanzaRepository allegatiIstanzaRepository, IOggettiService oggettiService)
			{
				_idComune = idComune;
				_codiceIstanza = codiceIstanza;
				_contenutoPdf = contenutoPdf;
				_allegatiIstanzaRepository = allegatiIstanzaRepository;
				_oggettiService = oggettiService;
			}



			public void Esegui()
			{
			}
		}
		#endregion

	}
}
