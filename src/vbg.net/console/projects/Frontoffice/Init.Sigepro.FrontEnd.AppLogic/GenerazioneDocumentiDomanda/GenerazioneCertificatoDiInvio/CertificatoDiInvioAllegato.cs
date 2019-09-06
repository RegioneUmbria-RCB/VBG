using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Services.ConversioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.STC;
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

        IStcService _stc;

        public CertificatoDiInvioAllegato(IStcService stc)
		{
            this._stc = stc;
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

                this._stc.AllegaCertificatoDiInvio(idDomandaBackoffice, certificatoDiInvio);

                //var codiceOggetto = _oggettiService.InserisciOggetto(certificatoDiInvio.FileName, certificatoDiInvio.MimeType, certificatoDiInvio.FileContent);
				//_allegatiIstanzaRepository.AggiungiAllegatoIstanza(Convert.ToInt32(idDomandaBackoffice), Constants.NomeAllegatoRiepilogo, codiceOggetto);

				_log.Debug("Invocazione a AgiungiCertificatoAIstanzaThread.esegui completata");
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore durante la chiamata a AllegaCertificato: {0}", ex.ToString());
			}
		}

		private bool CertificatoAllegatoEsiste(string idDomandaBackoffice)
		{
            var certificatoDiInvio = GetIdCertificatoDiInvio(idDomandaBackoffice);

            return certificatoDiInvio.HasValue;
		}

		public BinaryFile GetByIdDomanda(string idDomandaBackoffice)
		{
            var certificatoDiInvio = GetIdCertificatoDiInvio(idDomandaBackoffice);
            
            if (!certificatoDiInvio.HasValue)
            {
                return null;
            }

            var response = this._stc.AllegatoBinario(certificatoDiInvio.Value.ToString());

            return new BinaryFile(response.fileName, response.mimeType, response.binaryData);
		}

        public int? GetIdCertificatoDiInvio(string idDomandaBackoffice)
        {
            return this._stc.GetIdCertificatoDiInvio(idDomandaBackoffice);
        }


    }
}
