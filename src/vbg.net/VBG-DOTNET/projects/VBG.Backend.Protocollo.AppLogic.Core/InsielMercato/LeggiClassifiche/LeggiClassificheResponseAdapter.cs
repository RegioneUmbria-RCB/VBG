using ProtocolloInsielMercatoService;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.InsielMercato.LeggiClassifiche
{
    public class LeggiClassificheResponseAdapter
    {
        public readonly ListaTipiClassificaType Classifiche;

        public LeggiClassificheResponseAdapter(filing[] filing)
        {
            Classifiche = CreaClassifiche(filing);
        }

        private ListaTipiClassificaType CreaClassifiche(filing[] response)
        {
            try
            {
                var retVal = new ListaTipiClassificaType();
                var list = new List<ListaTipiClassificaClassifica>();

                foreach (var cl in response)
                {
                    if (!cl.disabled && !cl.remove)
                    {
                        var tipiClassifica = new ListaTipiClassificaClassifica { Codice = cl.code };
                        string codiceLivelli = String.Empty;

                        cl.levelList.ToList().ForEach(l => codiceLivelli += String.Concat(l.value.Trim(), "."));

                        tipiClassifica.Descrizione = String.Concat(codiceLivelli.Substring(0, codiceLivelli.Length - 1), " - ", cl.description.Length > 60 ? String.Concat(cl.description.Substring(0, 60), "....") : cl.description, "  (", cl.code, ")");
                        list.Add(tipiClassifica);
                    }
                }

                retVal.Classifica = list.ToArray();
                return retVal;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO SULL'ADATTATORE DI RISPOSTA DEI DATI DI CLASSIFICA", ex);
            }
        }

    }
}
