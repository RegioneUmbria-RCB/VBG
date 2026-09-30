using ApiCatalogoSsuDEMO.Models;
using Microsoft.AspNetCore.Mvc;

namespace ApiCatalogoSsuDEMO.Controllers
{

    [ApiController]
    [Route("api/v1/{codiceEnte}/tipi-soggetto")]
    [Tags("TipiSoggetto")]
    public class TipiSoggettoController : ControllerBase
    {
        [HttpGet]
        [ProducesResponseType(typeof(List<TipoSoggetto>), 200)]
        public IActionResult GetTipiSoggetto(
            string codiceEnte,
            [FromQuery] int[] procedimenti)
        {
            var tipi = new List<TipoSoggetto>
            {
                new()
                {
                    Id = 1,
                    Descrizione = "Richiedente",
                    DescrizioneEstesa = "Soggetto che presenta la richiesta",
                    Obbligatorio = true,
                    TipoPersona = TipoPersonaEnum.Fisica,
                    RichiedeAnagraficaCollegata = false,
                    OccorrenzeMax = 1,
                    Ruolo = FlagRuoloSoggettoEnum.Richiedente,
                },
                new()
                {
                    Id = 4,
                    Descrizione = "Legale rappresentante della società",
                    DescrizioneEstesa = "Soggetto che presenta la richiesta",
                    Obbligatorio = false,
                    TipoPersona = TipoPersonaEnum.Fisica,
                    RichiedeAnagraficaCollegata = true,
                    OccorrenzeMax = 1,
                    Ruolo = FlagRuoloSoggettoEnum.Richiedente,
                },
                new()
                {
                    Id = 2,
                    Descrizione = "Tecnico progettista",
                    DescrizioneEstesa = "Professionista abilitato che redige il progetto",
                    Obbligatorio = false,
                    TipoPersona = TipoPersonaEnum.Fisica,
                    RichiedeAnagraficaCollegata = false,
                    OccorrenzeMax=1
                    , Ruolo = FlagRuoloSoggettoEnum.Tecnico,
                },
                new()
                {
                    Id = 3,
                    Descrizione = "Impresa esecutrice",
                    DescrizioneEstesa = "Impresa che eseguirà i lavori",
                    Obbligatorio = false,
                    TipoPersona = TipoPersonaEnum.Giuridica,
                    RichiedeAnagraficaCollegata = false,
                    OccorrenzeMax = 1
                },
                new()
                {
                    Id = 5,
                    Descrizione = "Azienda richiedente",
                    DescrizioneEstesa = "Azienda per conto di cui viene presentata la domanda",
                    Obbligatorio = false,
                    TipoPersona = TipoPersonaEnum.Giuridica,
                    RichiedeAnagraficaCollegata = false,
                    OccorrenzeMax = 1,
                    Ruolo = FlagRuoloSoggettoEnum.Azienda,
                },
                new()
                {
                    Id = 6,
                    Descrizione = "Altro soggetto",
                    DescrizioneEstesa = "Altro soggetto della domanda",
                    Obbligatorio = false,
                    TipoPersona = TipoPersonaEnum.Fisica,
                    RichiedeAnagraficaCollegata = false,
                    OccorrenzeMax = 3,
                    Ruolo = FlagRuoloSoggettoEnum.AltroSoggetto,
                }
            };

            return this.Ok(tipi);
        }
    }
}
