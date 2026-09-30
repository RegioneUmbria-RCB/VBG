using AreaRiservataCore.Shared.Localizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.CoreServices.Autenticazione;
using Microsoft.AspNetCore.Mvc;
using System.ComponentModel.DataAnnotations;
using System.Text.Json;

namespace AreaRiservataCore.api
{
    [Microsoft.AspNetCore.Mvc.Route("api/[controller]")]
    [ApiController]
    public class StradarioController : ControllerBase
    {
        private readonly IStradarioRepository _stradarioRepository;
        private readonly AliasSoftwareProvider _aliasSoftwareProvider;

        public StradarioController(IStradarioRepository stradarioRepository, AliasSoftwareProvider aliasSoftwareProvider)
        {
            this._stradarioRepository = stradarioRepository;
            this._aliasSoftwareProvider = aliasSoftwareProvider;
        }

        public class AutocompleteRequestWrapper
        {
            public required AutocompleteStradarioResult d { get; set; }
        }


        public class AutocompleteRequest
        {
            [Required]
            public string IdComune { get; set; } = string.Empty;
            public string? CodiceComune { get; set; }
            public string? ComuneLocalizzazione { get; set; }
            [Required]
            public string Match { get; set; } = string.Empty;
        }


        [HttpPost("autocomplete")]
        public async Task<IActionResult> AutocompleteAsync([FromBody] AutocompleteRequest request)
        {
            const int maxCount = 30;
            this._aliasSoftwareProvider.AliasComune = request.IdComune;

            var listaIndirizzi = await this._stradarioRepository.GetByMatchParzialeAsyncIncludiDisabilitateAsync(request.CodiceComune ?? "", request.ComuneLocalizzazione ?? "", request.Match);

            var rVal = new AutocompleteStradarioResult
            {
                ItemCount = listaIndirizzi.Count,
                Items = [.. listaIndirizzi.Take(maxCount).Select(s => new AutocompleteStradarioResultItem
                {
                    Codice = s.CodiceStradario,
                    Descrizione = s.NomeVia,
                    CodViario = s.CodViario
                })]
            };


            return new JsonResult(new AutocompleteRequestWrapper { d = rVal }, new JsonSerializerOptions { PropertyNamingPolicy = null });
        }
    }
}
