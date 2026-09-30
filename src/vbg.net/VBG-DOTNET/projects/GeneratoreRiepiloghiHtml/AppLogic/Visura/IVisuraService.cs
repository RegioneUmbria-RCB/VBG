using VisuraVbg;

namespace GeneratoreRiepiloghiHtml.AppLogic.Visura
{
    public interface IVisuraService
    {
        Task<Istanze> GetDettaglioPraticaAsync(int codiceIstanza);
    }
}
