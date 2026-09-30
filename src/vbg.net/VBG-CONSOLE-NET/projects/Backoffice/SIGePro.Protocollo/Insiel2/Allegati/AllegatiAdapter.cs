using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.ProtocolloFilesInsielService2;
using Init.SIGePro.Protocollo.Insiel2.Services;
using Init.SIGePro.Protocollo.ProtocolloInsielService2;

namespace Init.SIGePro.Protocollo.Insiel2.Allegati
{
    public class AllegatiAdapter
    {
        AllegatiService _wrapper;
        List<ProtocolloAllegati> _allegati;
        string _utente;
        string _password;

        public AllegatiAdapter(AllegatiService wrapper, List<ProtocolloAllegati> allegati, string utente, string password)
        {
            _wrapper = wrapper;
            _allegati = allegati;
            _utente = utente;
            _password = password;
        }

        public documentoInsProto[] Adatta()
        {
            var responseDoc = _allegati.Select(x => _wrapper.Upload(new UploadRequest
            {
                documento = new AttachmentData
                {
                    tipoFile = "",
                    fileName = x.NOMEFILE,
                    binaryData = x.OGGETTO
                }
            }));

            var allegati = responseDoc.Select((x, idx) => new documentoInsProto
            {
                id = x.idDocumento,
                is_primario = idx == 0,
                is_primarioSpecified = true,
                nome = _allegati[idx].NOMEFILE
            });

            return allegati.ToArray();
        }
    }
}
