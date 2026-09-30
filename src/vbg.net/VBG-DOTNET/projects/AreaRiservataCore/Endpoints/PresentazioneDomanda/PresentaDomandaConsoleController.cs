using Microsoft.AspNetCore.Mvc;

namespace AreaRiservataCore.Endpoints.PresentazioneDomanda
{
    [Route("presenta-domanda-console")]
    public class PresentaDomandaConsoleController : Controller
    {
        [HttpGet("{alias}/{software}/{codiceIntervento}")]
        public IActionResult Index(string alias, string software, int codiceIntervento)
        {
            return this.Redirect($"~/{alias}/{software}/console-nuova-domanda/?SelezionaIntervento={codiceIntervento}");
        }
    }
}
