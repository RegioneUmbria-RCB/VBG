using ApiCatalogoSsuDEMO.Models;
using Microsoft.AspNetCore.Mvc;

namespace ApiCatalogoSsuDEMO.Controllers
{

    [ApiController]
    [Route("api/v1/{codiceEnte}/configurazione")]
    [Tags("Configurazione")]
    public class ConfigurazioneController : ControllerBase
    {
        [HttpGet("regimi-amministrativi")]
        [ProducesResponseType(typeof(RegimeAmministrativo), 200)]
        public IActionResult GetRegimiAmministrativi(
            string codiceEnte,
            [FromQuery] int[] procedimenti)
        {
            var regime = new RegimeAmministrativo
            {
                Id = 1,
                Descrizione = "Regime ordinario"
            };

            return this.Ok(regime);
        }

        [HttpGet("regimi-amministrativi/{idRegimeAmministrativo}/riepilogo-domanda")]
        [ProducesResponseType(typeof(RiepilogoDomanda), 200)]
        public IActionResult GetRiepilogoDomanda(
            string codiceEnte,
            string idRegimeAmministrativo)
        {
            var fileHtml = System.IO.File.ReadAllText("riepilogo-domanda.html");
            var riepilogo = new RiepilogoDomanda
            {
                TemplateXsl = fileHtml,
                RichiedeFirma = true
            };

            return this.Ok(riepilogo);
        }

        [HttpGet("ricevuta-domanda/xsl")]
        [ProducesResponseType(typeof(TemplateRicevutaDomandaXsl), 200)]
        public IActionResult GetRicevutaDomandaXsl(
            string codiceEnte)
        {
            var template = new TemplateRicevutaDomandaXsl
            {
                TemplateXsl = System.IO.File.ReadAllText("ricevuta-domanda.html")
            };

            return this.Ok(template);
        }

        [HttpGet("ricevuta-domanda/email")]
        [ProducesResponseType(typeof(TemplateRicevutaDomandaEmail), 200)]
        public IActionResult GetRicevutaDomandaEmail(
            string codiceEnte)
        {
            var template = new TemplateRicevutaDomandaEmail
            {
                OggettoXsl = "Ricevuta domanda - Protocollo #{protocollo}",
                CorpoXsl = System.IO.File.ReadAllText("ricevuta-domanda.html")
            };

            return this.Ok(template);
        }

        [HttpGet("ricevuta-domanda/io")]
        [ProducesResponseType(typeof(TemplateRicevutaDomandaIo), 200)]
        public IActionResult GetRicevutaDomandaIo(
            string codiceEnte)
        {
            var template = new TemplateRicevutaDomandaIo
            {
                Oggetto = "Domanda ricevuta",
                Corpo = @"La tua domanda è stata ricevuta.

Protocollo: #{protocollo}
Data: #{data}

Puoi seguire lo stato della pratica nell'area riservata."
            };

            return this.Ok(template);
        }
    }
}

