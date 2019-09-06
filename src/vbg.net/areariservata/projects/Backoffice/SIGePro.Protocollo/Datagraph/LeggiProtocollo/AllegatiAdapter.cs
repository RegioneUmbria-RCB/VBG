using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Datagraph.LeggiProtocollo
{
    public class AllegatiAdapter
    {
        public AllegatiAdapter()
        {

        }

        public List<AllOut> Adatta(Descrizione descrizione)
        {
            var allegati = new List<AllOut>();
            if (descrizione != null)
            {
                if (descrizione.Documento != null)
                {
                    allegati.Add(new AllOut
                    {
                        IDBase = descrizione.Documento.id.ToString(),
                        Commento = descrizione.Documento.nome,
                        Serial = descrizione.Documento.DescrizioneDocumento
                    });
                }

                if (descrizione.Allegati != null && descrizione.Allegati.Documento != null && descrizione.Allegati.Documento.Count() > 0)
                {
                    var allegatiSecondari = descrizione.Allegati.Documento.Select(x => new AllOut
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
