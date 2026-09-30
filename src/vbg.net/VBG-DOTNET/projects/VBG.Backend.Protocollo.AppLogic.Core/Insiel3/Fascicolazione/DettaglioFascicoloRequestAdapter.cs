using ProtocolloInsielService3;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Fascicolazione
{
    public class DettaglioFascicoloRequestAdapter
    {
        public DettaglioFascicoloRequestAdapter()
        {

        }

        public DettagliPraticaRequest Adatta(long progDoc, string progMovi)
        {
            return new DettagliPraticaRequest
            {
                pratica = new PraticaRequest
                {
                    Item = new IdProtocollo
                    {
                        progDoc = progDoc,
                        progMovi = progMovi
                    }
                }
            };
        }
    }
}
