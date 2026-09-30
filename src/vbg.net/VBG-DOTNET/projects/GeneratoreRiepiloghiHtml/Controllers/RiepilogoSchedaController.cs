using GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghi.RiepiloghiSchede;
using Microsoft.AspNetCore.Mvc;
using VBG.DatiDinamici.Interfaces;

namespace GeneratoreRiepiloghiHtml.Controllers
{

    [ApiController]
    [Route("api/riepilogo-singola-scheda/")]
    public class RiepilogoSchedaController : ControllerBase
    {


        private readonly GeneratoreRiepilogoSingolaSchedaService _generatoreRiepilogo;
        private readonly ILogger<RiepilogoSchedaController> _logger;

        public RiepilogoSchedaController(GeneratoreRiepilogoSingolaSchedaService generatoreRiepilogo, ILogger<RiepilogoSchedaController> logger)
        {
            this._generatoreRiepilogo = generatoreRiepilogo;
            this._logger = logger;
        }

        [HttpPost]
        [ApiConventionMethod(typeof(DefaultApiConventions), nameof(DefaultApiConventions.Post))]
        [Route("{codiceIstanza}/{idScheda}")]
        public async Task<FileResult> PostAsync([FromRoute] int codiceIstanza, [FromRoute] int idScheda, [FromBody] Dictionary<int, ValoreCampoModel[]>? valoriCampi)
        {
            using var scope = this._logger.BeginScope("Richiesta di generazione del riepilogo per la scheda {idScheda} della pratica {codiceIstanza}", idScheda, codiceIstanza);
            try
            {
                var valoriDictionary = valoriCampi?.ToDictionary(k => k.Key, v => v.Value.AsEnumerable<IValoreCampo>().ToList()) ?? new();

                var riepilogo = await this._generatoreRiepilogo.GeneraRiepilogoSchedaAsync(codiceIstanza, idScheda, valoriDictionary);

                return this.File(riepilogo.FileContent, riepilogo.MimeType, riepilogo.FileName);
            }
            catch (Exception ex)
            {
                this._logger.LogError("Errore nella generazione del riepilogo: {@ex}", ex);

                throw;
            }
        }

        [HttpGet]
        [ApiConventionMethod(typeof(DefaultApiConventions), nameof(DefaultApiConventions.Post))]
        [Route("{codiceIstanza}/{idScheda}")]
        public async Task<FileResult> GetAsync([FromRoute] int codiceIstanza, [FromRoute] int idScheda)
        {
            using var scope = this._logger.BeginScope("Richiesta di generazione del riepilogo per la scheda {idScheda} della pratica {codiceIstanza}", idScheda, codiceIstanza);
            try
            {
                var riepilogo = await this._generatoreRiepilogo.GeneraRiepilogoSchedaAsync(codiceIstanza, idScheda);

                return this.File(riepilogo.FileContent, riepilogo.MimeType, riepilogo.FileName);
            }
            catch (Exception ex)
            {
                this._logger.LogError("Errore nella generazione del riepilogo: {@ex}", ex);

                throw;
            }
        }

    }
}
