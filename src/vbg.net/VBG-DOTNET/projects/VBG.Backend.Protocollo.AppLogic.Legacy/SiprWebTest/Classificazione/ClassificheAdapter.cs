using System;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.SiprWebTest.Classificazione
{
    public class ClassificheAdapter
    {
        public ClassificheAdapter()
        {
            
        }

        public ListaTipiClassificaType Adatta(ClassificheReader reader)
        {
            var classifiche = reader.Read();

            return new ListaTipiClassificaType
            {
                Classifica = classifiche.Select(x => new ListaTipiClassificaClassifica
                {
                    Codice = x.CodiceClassificazione,
                    Descrizione = String.Format("[{0}] {1}", x.CodiceClassificazione, x.Classificazione)
                }).ToArray()
            };
        }
    }
}
