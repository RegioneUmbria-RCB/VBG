namespace GeneratoreRiepiloghiHtml.AppLogic.GestioneInterventi
{
    public class InterventiService : IInterventiService
    {
        private readonly InterventiServiceCreator _serviceCreator;

        public InterventiService(InterventiServiceCreator serviceCreator)
        {
            this._serviceCreator = serviceCreator;
        }


        public async Task<int?> GetCodiceOggettoDelModelloDiRiepilogoAsync(int idIntervento)
        {
            return await this._serviceCreator.CallAsync(async (ws) =>
            {
                var allegati = await ws.Service.GetDocumentiDaCodiceInterventoAsync(ws.Token, idIntervento, WsInterventi.AmbitoRicerca.AreaRiservata);

                var riepilogo = allegati?
                    .FirstOrDefault(x => x.RiepilogoDomanda);

                return riepilogo?.CodiceOggettoModello;
            });
        }

        public async Task<int?> GetCodiceOggettoCertificatoDiInvioDaIdInterventoAsync(int idIntervento)
        {
            return await this._serviceCreator.CallAsync(async (ws) =>
            {
                return await ws.Service.GetIdCertificatoDiInvioDomandaDaIdInterventoAsync(ws.Token, idIntervento);
            });
        }
    }
}
