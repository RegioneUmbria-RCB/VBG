using ApiCatalogoSsuDEMO.Models;
using Microsoft.AspNetCore.Mvc;

namespace ApiCatalogoSsuDEMO.Controllers
{

    [ApiController]
    [Route("api/v1/{codiceEnte}/oneri")]
    [Tags("Oneri")]
    public class OneriController : ControllerBase
    {
        [HttpGet]
        [ProducesResponseType(typeof(List<ElementoListaOneriProcedimento>), 200)]
        public IActionResult GetOneri(
            string codiceEnte,
            [FromQuery] int[] procedimenti)
        {
            var lista = new List<ElementoListaOneriProcedimento>();

            foreach (var procId in procedimenti)
            {
                lista.Add(new ElementoListaOneriProcedimento
                {
                    Procedimento = Database.GetProcedimentiById([procId]).Select(x => new EntityBase() { Id = x.Id, Descrizione = x.Descrizione }).First(),
                    //Procedimento = new EntityBase
                    //{
                    //    Id = procId,
                    //    Descrizione = $"Procedimento {procId}"
                    //},
                    Oneri = new List<OnereProcedimento>
                    {
                        new()
                        {
                            Id = 1,
                            Descrizione = "Diritti di segreteria",
                            DescrizioneEstesa = "Diritti fissi di segreteria per l'istruttoria",
                            Importo = 50.00,
                            PermetteneNonDovuto = false,
                            PermetteModificaImporto = false,
                            ModalitaPagamento = new List<ModalitaPagamentoOnere>
                            {
                                new() { Id = 1, Descrizione = "PagoPA" },
                                new() { Id = 2, Descrizione = "Bonifico bancario" }
                            }
                        },
                        new()
                        {
                            Id = 19,
                            Descrizione = "Oneri di urbanizzazione",
                            DescrizioneEstesa = "Oneri variabili in base alla superficie",
                            Importo = null,
                            PermetteneNonDovuto = true,
                            PermetteModificaImporto = true,
                            CampoDinamicoImporto = "superficie_mq",
                            ModalitaPagamento = new List<ModalitaPagamentoOnere>
                            {
                                new() { Id = 1, Descrizione = "PagoPA" }
                            }
                        }
                    }
                });
            }

            return this.Ok(lista);
        }
    }
}
