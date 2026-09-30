using Microsoft.AspNetCore.Mvc;

namespace AreaRiservataCore.Endpoints.PresentazioneDomanda
{
    [Route("presenta-domanda-locale")]
    public class PresentaDomandaLocaleController : Controller
    {
        [HttpGet("{alias}/{software}/{codiceIntervento}")]
        public IActionResult Index(string alias, string software, int codiceIntervento)
        {
            return this.Redirect($"~/{alias}/{software}/inserimento-istanza/nuova-domanda/?SelezionaIntervento={codiceIntervento}");
        }
    }
}
