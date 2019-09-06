using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Datagraph.LeggiProtocollo
{
    public class LeggiAllegatoAdapter
    {
        public LeggiAllegatoAdapter()
        {

        }

        public AllOut Adatta(RegistrazioneConAllegatiResponse response, long id)
        {
            for (int i = 0; i < response.Attachments.Count; i++)
            {
                if (i == 0)
                {
                    if (response.Segnatura.Registrazione.Descrizione.Documento.id == id)
                    {
                        byte[] buffOut = new byte[response.Attachments[0].Stream.Length];
                        response.Attachments[0].Stream.Read(buffOut, 0, Convert.ToInt32(response.Attachments[0].Stream.Length));
                        response.Attachments[0].Stream.Close();
                        return new AllOut
                        {
                            IDBase = id.ToString(),
                            Commento = response.Segnatura.Registrazione.Descrizione.Documento.nome,
                            Serial = response.Segnatura.Registrazione.Descrizione.Documento.DescrizioneDocumento,
                            ContentType = response.Attachments[0].ContentType,
                            Image = buffOut
                        };
                    }
                }
                else
                {
                    if (response.Segnatura.Registrazione.Descrizione.Allegati.Documento[i-1].id == id)
                    {
                        byte[] buffOut = new byte[response.Attachments[i].Stream.Length];
                        response.Attachments[i].Stream.Read(buffOut, 0, Convert.ToInt32(response.Attachments[i].Stream.Length));
                        response.Attachments[i].Stream.Close();
                        return new AllOut
                        {
                            IDBase = id.ToString(),
                            Commento = response.Segnatura.Registrazione.Descrizione.Allegati.Documento[i-1].nome,
                            Serial = response.Segnatura.Registrazione.Descrizione.Allegati.Documento[i-1].DescrizioneDocumento,
                            ContentType = response.Attachments[i].ContentType,
                            Image = buffOut
                        };
                    }
                }
            }
            throw new Exception($"L'ALLEGATO CON ID: {id} NON E' STATO TROVATO");
        }
    }
}
