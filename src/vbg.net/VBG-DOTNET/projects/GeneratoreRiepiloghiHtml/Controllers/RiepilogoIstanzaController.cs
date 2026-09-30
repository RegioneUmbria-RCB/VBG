using GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghi.RiepiloghiIstanze;
using Microsoft.AspNetCore.Mvc;
using System.ComponentModel.DataAnnotations;
using System.Runtime.Serialization;
using VisuraVbg;

namespace GeneratoreRiepiloghiHtml.Controllers
{
    [Route("api/riepilogo-istanza/")]
    [ApiController]
    public class RiepilogoIstanzaController : ControllerBase
    {
        private readonly GeneratoreRiepilogoDomanda _generatoreRiepilogoDomanda;
        private readonly ILogger<RiepilogoIstanzaController> _logger;

        public RiepilogoIstanzaController(GeneratoreRiepilogoDomanda generatoreRiepilogoDomanda, ILogger<RiepilogoIstanzaController> logger)
        {
            this._generatoreRiepilogoDomanda = generatoreRiepilogoDomanda;
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
                var riepilogo = await this._generatoreRiepilogoDomanda.GeneraRiepilogoDomandaAsync(codiceIstanza);

                return this.File(riepilogo.FileContent, riepilogo.MimeType, riepilogo.FileName);
            }
            catch (Exception ex)
            {
                this._logger.LogError("Errore nella generazione del riepilogodella pratica {codiceIstanza}: {ex}", codiceIstanza, ex);

                throw;
            }
        }

        [HttpPost]
        [ApiConventionMethod(typeof(DefaultApiConventions), nameof(DefaultApiConventions.Post))]
        [Route("")]
        public async Task<FileResult> PostAsync([Required] IFormFile file)
        {
            using var scope = this._logger.BeginScope("Richiesta di generazione del riepilogo della pratica xml");
            try
            {
                using var ms = new MemoryStream();

                await file.CopyToAsync(ms);

                ms.Seek(0, SeekOrigin.Begin);

                var istanza = ClassFromXmlString<Istanze>(ms);

                if (istanza is null)
                {
                    throw new ArgumentException($"Impossibile deserializzare i dati passati");
                }

                using var scope2 = this._logger.BeginScope("Richiesta di generazione del riepilogo della pratica {codiceIstanza}", istanza.CODICEISTANZA);

                var riepilogo = await this._generatoreRiepilogoDomanda.GeneraRiepilogoDomandaAsync(istanza);

                return this.File(riepilogo.FileContent, riepilogo.MimeType, riepilogo.FileName);
            }
            catch (Exception ex)
            {
                this._logger.LogError("Errore nella generazione del riepilogo della pratica xml: {ex}", ex);

                throw;
            }
        }

        private static T? ClassFromXmlString<T>(MemoryStream stringStream)
        {
            var deserializer = new DataContractSerializer(typeof(T));
            return (T?)deserializer.ReadObject(stringStream);
        }
    }
}
