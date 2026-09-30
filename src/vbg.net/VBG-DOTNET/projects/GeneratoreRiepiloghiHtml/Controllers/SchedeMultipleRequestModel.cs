namespace GeneratoreRiepiloghiHtml.Controllers
{
    public class SchedeMultipleRequestModel
    {
        public int[] IdSchede { get; set; } = Array.Empty<int>();
        public Dictionary<int, ValoreCampoModel[]>? ValoriCampi { get; set; } = new();
    }
}
