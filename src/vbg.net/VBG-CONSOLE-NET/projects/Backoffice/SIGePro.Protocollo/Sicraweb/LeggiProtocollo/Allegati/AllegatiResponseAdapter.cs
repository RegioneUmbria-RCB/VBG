using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Sicraweb.LeggiProtocollo.Segnatura;
using Init.SIGePro.Data;
using System.IO;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.Sicraweb.LeggiProtocollo.Allegati
{
    public class AllegatiResponseAdapter
    {
        Descrizione _descrizioneResponse;

        public AllegatiResponseAdapter(Descrizione descrizioneResponse)
        {
            _descrizioneResponse = descrizioneResponse;    
        }

        public AllOut[] Adatta(OggettiMgr oggetti)
        { 
            var allegati = new List<AllOut>();

            if (_descrizioneResponse.Documento != null)
                allegati.Add(new AllOut
                {
                    IDBase = _descrizioneResponse.Documento.id.ToString(),
                    Serial = _descrizioneResponse.Documento.nome,
                    Commento = Path.GetFileNameWithoutExtension(_descrizioneResponse.Documento.nome),
                    ContentType = oggetti.GetContentType(_descrizioneResponse.Documento.nome),
                    TipoFile = String.IsNullOrEmpty(Path.GetExtension(_descrizioneResponse.Documento.nome)) ? "" : Path.GetExtension(_descrizioneResponse.Documento.nome).Substring(1)
                });

            if (_descrizioneResponse.Allegati != null && _descrizioneResponse.Allegati.Length > 0)
            {
                var ct = new ContentTypes();

                _descrizioneResponse.Allegati.ToList().ForEach(x => allegati.Add(new AllOut
                {
                    IDBase = x.id.ToString(),
                    Serial = x.nome,
                    Commento = Path.GetFileNameWithoutExtension(x.nome),
                    ContentType = oggetti.GetContentType(x.nome),
                    TipoFile = String.IsNullOrEmpty(Path.GetExtension(x.nome)) ? "" : Path.GetExtension(x.nome).Substring(1)
                }));
            }

            return allegati.ToArray();
        }
    }
}
