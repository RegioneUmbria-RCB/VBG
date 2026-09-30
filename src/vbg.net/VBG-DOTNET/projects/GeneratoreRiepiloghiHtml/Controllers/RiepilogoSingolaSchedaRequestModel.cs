using System.ComponentModel.DataAnnotations;
using VisuraVbg;

namespace GeneratoreRiepiloghiHtml.Controllers
{
    public class RiepilogoSingolaSchedaRequestModel
    {
        [Required]
        public int IdScheda { get; set; }
        [Required]
        public Istanze Istanza { get; set; } = default!;

        public Dictionary<int, ValoreCampoModel[]>? ValoriCampi { get; set; } = new();
    }
}
