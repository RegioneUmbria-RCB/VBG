using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;



namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Fascicolazione
{
    public class DettaglioFascicoloRequestAdapter
    {
        public DettaglioFascicoloRequestAdapter()
        {

        }

        public DettaglioFascicoloRequest Adatta(long progDoc, string progMovi)
        {
            return new DettaglioFascicoloRequest
            {
                Fascicolo = new RegistrazioneFascicolo
                {
                    Id = new IdRegistrazioneFascicolo()
                    {
                        ProgressivoDocumento = progDoc.ToString(),
                        ProgressivoMovimento = progMovi
                    }
                }
            };
        }
    }
}
