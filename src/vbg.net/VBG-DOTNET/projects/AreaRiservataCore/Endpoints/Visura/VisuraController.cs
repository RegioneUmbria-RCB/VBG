using Microsoft.AspNetCore.Mvc;

namespace AreaRiservataCore.Endpoints.Visura
{
    [Route("visura")]
    [Route("v")]
    public class VisuraController : Controller
    {
        [HttpGet("{alias}/{software}")]
        public IActionResult Index(string alias, string software)
        {
            return this.Redirect($"~/{alias}/{software}/archiviopratiche");
        }

        [HttpGet("{alias}/{software}/{uuid}")]
        public IActionResult Index(string alias, string software, string uuid)
        {
            return this.Redirect($"~/{alias}/{software}/dettaglioistanzaex/{uuid}/istanzepresentate");
        }
    }
}
