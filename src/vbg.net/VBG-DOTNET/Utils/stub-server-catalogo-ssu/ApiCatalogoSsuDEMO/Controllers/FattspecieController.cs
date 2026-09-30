using ApiCatalogoSsuDEMO.Models;
using Microsoft.AspNetCore.Mvc;

namespace ApiCatalogoSsuDEMO.Controllers
{

    [ApiController]
    [Route("api/v1/{codiceEnte}/fattispecie")]
    [Tags("Fattispecie")]
    public class FattspecieController : ControllerBase
    {
        [HttpGet("primarie")]
        [ProducesResponseType(typeof(List<Fattispecie>), 200)]
        public IActionResult GetFattispeciePrimarie(
            string codiceEnte,
            [FromQuery] int procedimento)
        {
            var primarie = Database.FattispeciePrimarieByProcedimento[procedimento]
                                    .Select(id => Database.Fattispecie[id])
                                    .ToList();

            return this.Ok(primarie);
        }
        // MODIFICATO
        [HttpGet("{idFattispecie}/procedimento")]
        [ProducesResponseType(typeof(Procedimento), 200)]
        public IActionResult GetProcedimentoByFattispecieId(
            string codiceEnte,
            int idFattispecie)
        {
            return this.Ok(Database.GetProcedimentiById([Database.ProcedimentiByFattispecie[idFattispecie]]).FirstOrDefault());
        }

        [HttpGet("secondarie")]
        [ProducesResponseType(typeof(List<ElementoListaFattispecieSecondarie>), 200)]
        public IActionResult GetFattispecieSecondarie(
            string codiceEnte,
            [FromQuery] int[] primarie)
        {
            var lista = new List<ElementoListaFattispecieSecondarie>();

            foreach (var primariaId in primarie)
            {
                var primaria = Database.Fattispecie[primariaId];
                var secondarie = Database.FattispecieSecondarieByIdFattispeciePrimaria[primariaId]
                                    .Select(id => Database.Fattispecie[id])
                                    .ToList();

                lista.Add(new ElementoListaFattispecieSecondarie
                {
                    Primaria = new EntityBase
                    {
                        Id = primaria.Id,
                        Descrizione = primaria.Descrizione
                    },
                    Secondarie = secondarie.ToList()
                });
            }

            return this.Ok(lista);
        }
    }
}
