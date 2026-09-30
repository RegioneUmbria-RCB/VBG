using System;
using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.Services;
using VBG.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.Proxy;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.Protocollazione
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
