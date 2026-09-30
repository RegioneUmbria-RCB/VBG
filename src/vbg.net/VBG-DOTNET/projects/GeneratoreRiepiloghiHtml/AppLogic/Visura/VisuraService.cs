using VisuraVbg;

namespace GeneratoreRiepiloghiHtml.AppLogic.Visura
{
    public class VisuraService : IVisuraService
    {
        private readonly VisuraServiceCreator _serviceCreator;

        public VisuraService(VisuraServiceCreator serviceCreator)
        {
            this._serviceCreator = serviceCreator;
        }

        public async Task<Istanze> GetDettaglioPraticaAsync(int codiceIstanza)
        {
            return await this._serviceCreator.CallAsync(async ws =>
            {
                var istanza = await ws.Service.GetDettaglioPraticaAsync(ws.Token, codiceIstanza);

                if (istanza is null)
                {
                    throw new Exception($"Non è stato possibile recuperare i dettagli della pratica con codice {codiceIstanza}");
                }

                return istanza;
            });
        }
    }
}
