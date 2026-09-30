using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.Datagraph.LeggiProtocollo
{
    public class AllegatiAdapter
    {
        public AllegatiAdapter()
        {

        }

        public List<AllegatoResponseType> Adatta(Descrizione descrizione)
        {
            var allegati = new List<AllegatoResponseType>();
            if (descrizione != null)
            {
                if (descrizione.Documento != null)
                {
                    allegati.Add(new AllegatoResponseType
                    {
                        IDBase = descrizione.Documento.id.ToString(),
                        Commento = descrizione.Documento.nome,
                        Serial = descrizione.Documento.DescrizioneDocumento
                    });
                }

                if (descrizione.Allegati != null && descrizione.Allegati.Documento != null && descrizione.Allegati.Documento.Count() > 0)
                {
                    var allegatiSecondari = descrizione.Allegati.Documento.Select(x => new AllegatoResponseType
                    {
                        IDBase = x.id.ToString(),
                        Commento = x.nome,
                        Serial = x.DescrizioneDocumento
                    }).ToArray();

                    allegati.AddRange(allegatiSecondari);
                }
            }

            return allegati;
        }
    }
}
