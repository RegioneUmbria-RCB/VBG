using ApiCatalogoSsuDEMO.Models;
using Microsoft.AspNetCore.Mvc;

namespace ApiCatalogoSsuDEMO.Controllers
{
    // ==================== Controllers/TipologieController.cs ====================

    [ApiController]
    [Route("api/v1/{codiceEnte}/tipologie")]
    [Tags("Tipologie")]
    public class TipologieController : ControllerBase
    {
        [HttpGet]
        [ProducesResponseType(typeof(PagedItemsOfTipologia), 200)]
        public IActionResult GetTipologie(
            string codiceEnte,
            [FromQuery] string? q = null,
            [FromQuery] int? page = null)
        {
            var allTipologie = new List<Tipologia>
            {
                new() { Id = 1, Descrizione = "ESERCIZIO-MEDIA-STRUTTURA-VENDITA- NON-ALIMENTARE" },
                new() { Id = 2, Descrizione = "PIN" },
                new() { Id = 3, Descrizione = "ATTIVITA-OGGETTI-PREZIOSI" },
                new() { Id = 4, Descrizione = "COMMERCIALIZZAZIONE-ARMI" },
                new() { Id = 5, Descrizione = "GPL-VENDITA" },
                new() { Id = 6, Descrizione = "VENDITA-GAS-INFIAMMABILI" },
                new() { Id = 7, Descrizione = "VENDITA-PRODOTTI-FITOSANITARI" },
                new() { Id = 8, Descrizione = "MANGIMI-DI-MINERALE-MINERALE" },
                new() { Id = 9, Descrizione = "MEZZI-PUBBLICITARI-DOMANDA-COMUNE" },

            };

            var filtered = string.IsNullOrEmpty(q)
                ? allTipologie
                : allTipologie.Where(t => t.Descrizione.Contains(q, StringComparison.OrdinalIgnoreCase)).ToList();

            var pageSize = 10;
            var currentPage = page ?? 1;
            var totalCount = filtered.Count;
            var totalPages = (int)Math.Ceiling(totalCount / (double)pageSize);

            var items = filtered
                .Skip((currentPage - 1) * pageSize)
                .Take(pageSize)
                .ToList();

            return this.Ok(new PagedItemsOfTipologia
            {
                Items = items,
                TotalCount = totalCount,
                Page = currentPage,
                PageSize = pageSize,
                TotalPages = totalPages
            });
        }
    }
}
