using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Sicraweb.Services;
using Init.SIGePro.Protocollo.Sicraweb.Protocollazione.Segnatura;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.Sicraweb.Protocollazione.Allegati
{
    public class AllegatiAdapter
    {
        ProtocolloService _wrapper;
        List<ProtocolloAllegati> _allegati;

        public AllegatiAdapter(ProtocolloService wrapper, List<ProtocolloAllegati> allegati)
        {
            _wrapper = wrapper;
            _allegati = allegati;
        }

        public Descrizione Adatta()
        {
            var descrizione = new Descrizione();

            if (_allegati.Count == 0)
                return null;
            
            var primoDocumento = _allegati.First();

            var idPrimoDocumento = _wrapper.InserisciDocumento(primoDocumento);
            var documento = new Documento
            {
                nome = primoDocumento.NOMEFILE,
                id = idPrimoDocumento
            };

            descrizione.Documento = documento;

            if (_allegati.Count > 1)
                descrizione.Allegati = _allegati.Skip(1).Select(x => x.GetDocumento(_wrapper)).ToArray();
            
            return descrizione;
        }
    }
}
