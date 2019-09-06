using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Services.ConversioneDomanda;
using log4net;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio
{
	public class CertificatoDiInvioAllegato
	{
		private static class Constants
		{
			public const string NomeAllegatoRiepilogo = "Certificato di invio";
		}

		ILog _log = LogManager.GetLogger(typeof(CertificatoDiInvioAllegato));

		IVisuraService _visuraService;
		IAliasSoftwareResolver _aliasSoftwareResolver;
		IAllegatiIstanzaRepository _allegatiIstanzaRepository;
		IOggettiService _oggettiService;

        public CertificatoDiInvioAllegato(IAliasSoftwareResolver aliasSoftwareResolver, IVisuraService visuraService, IAllegatiIstanzaRepository allegatiIstanzaRepository, IOggettiService oggettiService)
		{
			this._visuraService = visuraService;
			this._aliasSoftwareResolver = aliasSoftwareResolver;
			this._allegatiIstanzaRepository = allegatiIstanzaRepository;
			this._oggettiService = oggettiService;
		}


		public void AllegaSeNonEsiste(string idDomandaBackoffice, BinaryFile certificatoDiInvio)
		{
			if( !CertificatoAllegatoEsiste( idDomandaBackoffice ) )
				AllegaCertificato( idDomandaBackoffice , certificatoDiInvio );
		}

        private void AllegaCertificato(string idDomandaBackoffice, BinaryFile certificatoDiInvio)
		{
			try
			{
				_log.Debug("Inizio invocazione a AgiungiCertificatoAIstanzaThread.esegui");

                var codiceOggetto = _oggettiService.InserisciOggetto(certificatoDiInvio.FileName, certificatoDiInvio.MimeType, certificatoDiInvio.FileContent);

				_allegatiIstanzaRepository.AggiungiAllegatoIstanza(Convert.ToInt32(idDomandaBackoffice), Constants.NomeAllegatoRiepilogo, codiceOggetto);

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

			var istanza = _visuraService.GetById(iIdPratica, new VisuraIstanzaFlags { LeggiDatiConfigurazione = false });

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
