using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.JProtocollo2.Services;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.JProtocollo2.Proxy;

namespace Init.SIGePro.Protocollo.JProtocollo2.Protocollazione
{
    public class DocumentiAdapter
    {
        ProtocolloService _wrapper;
        List<ProtocolloAllegati> _allegati;

        public DocumentiAdapter(ProtocolloService wrapper, List<ProtocolloAllegati> allegati)
        {
            _wrapper = wrapper;
            _allegati = allegati;
        }

        public void Adatta(string numeroProtocollo, string annoProtocollo, string username)
        {
            if (_allegati.Count > 1)
            {
                _allegati.Skip(1).ToList().ForEach(x => _wrapper.InserisciDocumento(new allegaDocumentoRichiestaAllegaDocumento
                {
                    documento = new documento
                    {
                        file = x.OGGETTO,
                        nomeFile = x.NOMEFILE,
                        titolo = x.NOMEFILE
                    },
                    riferimento = new riferimento
                    {
                        anno = annoProtocollo,
                        numero = numeroProtocollo
                    },
                    username = username
                }, x.CODICEOGGETTO));
            }
        }
    }
}
