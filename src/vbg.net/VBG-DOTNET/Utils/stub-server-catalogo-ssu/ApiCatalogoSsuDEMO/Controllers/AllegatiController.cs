using ApiCatalogoSsuDEMO.Models;
using Microsoft.AspNetCore.Mvc;

namespace ApiCatalogoSsuDEMO.Controllers
{

    [ApiController]
    [Route("api/v1/{codiceEnte}/allegati")]
    [Tags("Allegati")]
    public class AllegatiController : ControllerBase
    {
        [HttpGet]
        [ProducesResponseType(typeof(List<ElementoListaAllegatiProcedimento>), 200)]
        public IActionResult GetAllegati(
            string codiceEnte,
            [FromQuery] int[] procedimenti)
        {
            var lista = new List<ElementoListaAllegatiProcedimento>();

            foreach (var procId in procedimenti)
            {
                lista.Add(new ElementoListaAllegatiProcedimento
                {
                    Procedimento = Database.GetProcedimentiById([procId]).Select(x => new EntityBase() { Id = x.Id, Descrizione = x.Descrizione}).First(),
                    //Procedimento = new EntityBase
                    //{
                    //    Id = procId,
                    //    Descrizione = $"Procedimento {procId}"
                    //},
                    Allegati = new List<AllegatoProcedimento>
                    {
                        new()
                        {
                            Id = 1 + procId * 100,
                            Descrizione = "Documento identità",
                            DescrizioneEstesa = "Copia documento di identità valido",
                            Obbligatorio = true,
                            RichiedeFirma = true,
                            DomensioneMassimaInKb = 5120,
                            EstensioniAmmesse = new List<string> { "pdf", "p7m" }
                        },
                        new()
                        {
                            Id = 2 + procId * 100,
                            Descrizione = "Planimetria",
                            DescrizioneEstesa = "Planimetria catastale aggiornata",
                            Obbligatorio = true,
                            RichiedeFirma = false,
                            DomensioneMassimaInKb = 10240,
                            EstensioniAmmesse = new List<string> { "pdf", "dwg" }
                        },
                        new()
                        {
                            Id = 3 + procId * 100,
                            Descrizione = "Altro allegato facoltativo",
                            DescrizioneEstesa = $"Altro allegato del procedimento {procId}",
                            Obbligatorio = false,
                            RichiedeFirma = false,
                            DomensioneMassimaInKb = 10240,
                            EstensioniAmmesse = new List<string> { "pdf", "txt" }
                        }
                    }
                });
            }

            return this.Ok(lista);
        }
    }
}
