using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Microsis.Classifiche
{
    public class ClassificheResponseAdapter
    {
        public static ListaTipiClassificaType Adatta(Lista_Titolario response)
        {
            return new ListaTipiClassificaType
            {
                Classifica = response.Items.Select(x => new ListaTipiClassificaClassifica 
                { 
                    Codice = x.Codice, 
                    Descrizione = x.Classe 
                }).ToArray()
            };
        }
    }
}
