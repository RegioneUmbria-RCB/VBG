using ApiCatalogoSsuDEMO.Models;
using Microsoft.AspNetCore.Mvc;

namespace ApiCatalogoSsuDEMO.Controllers
{

    [ApiController]
    [Route("api/v1/{codiceEnte}/eventi-della-vita")]
    [Tags("EventiVita")]
    public class EventiVitaController : ControllerBase
    {
        [HttpGet]
        [ProducesResponseType(typeof(List<EventoDellaVita>), 200)]
        [ProducesResponseType(typeof(ProblemDetails), 400)]
        [ProducesResponseType(typeof(ProblemDetails), 401)]
        [ProducesResponseType(typeof(ProblemDetails), 404)]
        public IActionResult GetEventiDellaVita(string codiceEnte)
        {
            var eventi = new List<EventoDellaVita>
            {
                new() { Id = 1, Descrizione = "TRASFERIMENTO" },
                new() { Id = 2, Descrizione = "MOVIMENTAZIONI" },
                new() { Id = 3, Descrizione = "APERTURA" }
            };

            return this.Ok(eventi);
        }
    }




}
