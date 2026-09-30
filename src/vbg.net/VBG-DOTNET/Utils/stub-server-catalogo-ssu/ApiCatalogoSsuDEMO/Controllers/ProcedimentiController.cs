using ApiCatalogoSsuDEMO.Models;
using Microsoft.AspNetCore.Mvc;

namespace ApiCatalogoSsuDEMO.Controllers
{
    // ==================== Controllers/ProcedimentiController.cs ====================

    [ApiController]
    [Route("api/v1/{codiceEnte}/procedimenti")]
    [Tags("Procedimenti")]
    public class ProcedimentiController : ControllerBase
    {
        [HttpGet]
        [ProducesResponseType(typeof(PagedItemsOfProcedimento), 200)]
        public IActionResult GetProcedimenti(
            string codiceEnte,
            [FromQuery] string? evento = null,
            [FromQuery] string? tipologia = null,
            [FromQuery] string? q = null,
            [FromQuery] int? page = null)
        {
            var allProcedimenti = Database.GetProcedimentiById([1644, 1436]);

            var filtered = allProcedimenti.AsEnumerable();

            if (!string.IsNullOrEmpty(q))
                filtered = filtered.Where(p =>
                    p.Descrizione.Contains(q, StringComparison.OrdinalIgnoreCase) ||
                    p.DescrizioneEstesa.Contains(q, StringComparison.OrdinalIgnoreCase));

            var items = filtered.ToList();
            var pageSize = 10;
            var currentPage = page ?? 1;
            var totalCount = items.Count;
            var totalPages = (int)Math.Ceiling(totalCount / (double)pageSize);

            var pagedItems = items
                .Skip((currentPage - 1) * pageSize)
                .Take(pageSize)
                .ToList();

            return this.Ok(new PagedItemsOfProcedimento
            {
                Items = pagedItems,
                TotalCount = totalCount,
                Page = currentPage,
                PageSize = pageSize,
                TotalPages = totalPages
            });
        }
    }
}
