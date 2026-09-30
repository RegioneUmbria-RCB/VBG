using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.EProt.Titolario
{
    public class TitolarioResponseAdapter
    {
        public static ListaTipiClassificaType Adatta(TitolarioListType response)
        {
            var tipiDoc = response.titolo.OrderBy(y => y.codice).Select(x => new ListaTipiClassificaClassifica
            {
                Codice = x.id,
                Descrizione = String.Format("{0} - {1}", x.codice, x.descrizione)
            });

            return new ListaTipiClassificaType { Classifica = tipiDoc.ToArray() };
        }
    }
}
