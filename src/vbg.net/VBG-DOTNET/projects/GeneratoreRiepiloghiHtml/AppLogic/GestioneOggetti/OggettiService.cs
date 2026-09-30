
using WsOggetti;

namespace GeneratoreRiepiloghiHtml.AppLogic.GestioneOggetti
{
    public class OggettiService : IOggettiService
    {
        private readonly OggettiServiceCreator _serviceCreator;

        public OggettiService(OggettiServiceCreator serviceCreator)
        {
            this._serviceCreator = serviceCreator;
        }

        public async Task<BinaryFile> GetByIdAsync(int codiceOggetto)
        {
            return await this._serviceCreator.CallAsync(async (ws) =>
            {
                var req = new OggettiFindRequest
                {
                    token = ws.Token,
                    id = codiceOggetto.ToString()
                };

                var res = await ws.Service.OggettiFindAsync(req);

                if ((res?.OggettiFindResponse) == null)
                {
                    throw new Exception($"Impossibile caricare il file con id {codiceOggetto}");
                }

                return BinaryFile.FromFileData(res.OggettiFindResponse.fileName, res.OggettiFindResponse.mimeType, res.OggettiFindResponse.binaryData);
            });
        }
    }
}
