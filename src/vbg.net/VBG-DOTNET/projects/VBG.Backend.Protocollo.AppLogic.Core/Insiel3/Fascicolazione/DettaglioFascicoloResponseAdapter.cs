using ProtocolloInsielService3;
using System.Text.RegularExpressions;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Fascicolazione
{
    public class DettaglioFascicoloResponseAdapter
    {
        public DettaglioFascicoloResponseAdapter()
        {

        }

        public DatiProtocolloFascicolatoResponseType Adatta(Dettagli response)
        {
            string classifica = "";

            if (!String.IsNullOrEmpty(response.codiceRegistro))
            {
                classifica = Regex.Replace(response.codiceRegistro.Trim(), @"\s+", ".");
            }

            return new DatiProtocolloFascicolatoResponseType
            {
                AnnoFascicolo = response.anno,
                DataFascicolo = response.data.ToString("dd/MM/yyyy"),
                Fascicolato = EnumFascicolatoType.si,
                NumeroFascicolo = response.numero,
                Oggetto = response.oggetto,
                NoteFascicolo = response.note,
                Classifica = classifica
            };
        }
    }
}
