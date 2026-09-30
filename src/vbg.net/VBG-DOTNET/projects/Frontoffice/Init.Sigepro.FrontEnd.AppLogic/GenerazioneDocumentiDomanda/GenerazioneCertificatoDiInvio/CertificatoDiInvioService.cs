// -----------------------------------------------------------------------
// <copyright file="CertificatoDiInvioService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio
{
    using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.AllegaCertificatoDiInvio;
    using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
    using log4net;
    using System;
    using System.Threading.Tasks;

    public class CertificatoDiInvioService
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(CertificatoDiInvioService));

        private readonly GeneratoreCertificatoDiInvio _generatoreCertificatoDiInvio;
        private readonly IAllegaCertificatoDiInvioService _allegaCertificatoDiInvioService;
        private readonly ICertificatoDiInvioFinder _certificatoDiInvioFinder;

        public CertificatoDiInvioService(GeneratoreCertificatoDiInvio generatoreCertificatoDiInvio,
                                        IAllegaCertificatoDiInvioService allegaCertificatoDiInvioService, ICertificatoDiInvioFinder certificatoDiInvioFinder)
        {
            this._generatoreCertificatoDiInvio = generatoreCertificatoDiInvio;
            this._allegaCertificatoDiInvioService = allegaCertificatoDiInvioService;
            this._certificatoDiInvioFinder = certificatoDiInvioFinder;
        }
#if NET48
        public BinaryFile GeneraCertificatoDiInvio(int idDomandaBackoffice, bool allegaCertificatoAPratica = true)
        {
            try
            {
                var fileCertificato = this._generatoreCertificatoDiInvio.GeneraCertificatoDiInvio(idDomandaBackoffice);

                if (allegaCertificatoAPratica)
                {
                    this._allegaCertificatoDiInvioService.AllegaSeNonEsiste(idDomandaBackoffice, fileCertificato);
                }

                return fileCertificato;
            }
            catch (Exception ex)
            {
                this._log.Error($"Generazione del certificato di invio fallita per la domanda con id domanda backoffice {idDomandaBackoffice}): {ex.ToString()}");

                throw;
            }
            finally
            {
                this._log.Debug("Fine generazione del certificato di invio");
            }
        }
#endif
        public async Task<BinaryFile> GeneraCertificatoDiInvioAsync(int idDomandaBackoffice, bool allegaCertificatoAPratica = true)
        {
            try
            {
                var fileCertificato = await this._generatoreCertificatoDiInvio.GeneraCertificatoDiInvioAsync(idDomandaBackoffice);

                if (allegaCertificatoAPratica)
                {
                    this._allegaCertificatoDiInvioService.AllegaSeNonEsiste(idDomandaBackoffice, fileCertificato);
                }

                return fileCertificato;
            }
            catch (Exception ex)
            {
                this._log.Error($"Generazione del certificato di invio fallita per la domanda con id domanda backoffice {idDomandaBackoffice}): {ex.ToString()}");

                throw;
            }
            finally
            {
                this._log.Debug("Fine generazione del certificato di invio");
            }
        }

        public int? GetCodiceOggettoCertificatoDiInvioDaIdDomandaBackoffice(int idDomandaBackoffice)
        {
            try
            {
                this._log.DebugFormat("Inizio lettura del certificato di invio allegato alla domanda backoffice con id {0}", idDomandaBackoffice);

                return this._certificatoDiInvioFinder.GetCodiceOggettoCertificatoDiInvio(idDomandaBackoffice);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la lettura del certificato di invio allegato alla domanda backoffice con id {0}: {1}", idDomandaBackoffice, ex.ToString());

                throw;
            }
            finally
            {
                this._log.Debug("Fine della lettura del certificato di invio");
            }
        }
    }
}
