using GeneratoreRiepiloghiHtml.AppLogic.GenerazioneCertificatoInvio;
using Microsoft.AspNetCore.Mvc;

namespace GeneratoreRiepiloghiHtml.Controllers
{
    [Route("api/certificato-invio-istanza/")]
    [ApiController]
    public class CertificatoDiInvioController : ControllerBase
    {
        private readonly CertificatoDiInvioService _certificatoDiInvioService;
        private readonly ILogger<CertificatoDiInvioController> _logger;

        public CertificatoDiInvioController(CertificatoDiInvioService certificatoDiInvioService, ILogger<CertificatoDiInvioController> logger)
        {
            this._certificatoDiInvioService = certificatoDiInvioService;
            this._logger = logger;
        }

        [HttpGet]
        [ApiConventionMethod(typeof(DefaultApiConventions), nameof(DefaultApiConventions.Post))]
        [Route("{codiceIstanza}")]
        public async Task<FileResult> GetAsync([FromRoute] int codiceIstanza)
        {
            using var scope = this._logger.BeginScope("Richiesta di generazione del riepilogo della pratica {codiceIstanza}", codiceIstanza);
            try
            {
                var riepilogo = await this._certificatoDiInvioService.GeneraCertificatoDiInvioAsync(codiceIstanza);

                return this.File(riepilogo.FileContent, riepilogo.MimeType, riepilogo.FileName);
            }
            catch (Exception ex)
            {
                this._logger.LogError("Errore nella generazione del riepilogodella pratica {codiceIstanza}: {ex}", codiceIstanza, ex);

                throw;
            }
        }
    }
}
