using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.JProtocollo2.Proxy;

namespace Init.SIGePro.Protocollo.JProtocollo2.LeggiProtocollo
{
    public class LeggiProtocolloRequestAdapter
    {
        string _numero;
        string _anno;
        string _username;

        public LeggiProtocolloRequestAdapter(string numero, string anno, string username)
        {
            _numero = numero;
            _anno = anno;
            _username = username;
        }

        public leggiProtocolloRichiestaLeggiProtocollo Adatta()
        {
            return new leggiProtocolloRichiestaLeggiProtocollo
            {
                username = _username,
                riferimento = new riferimento
                {
                    anno = _anno,
                    numero = _numero
                },
                allegati = false
            };
        }
    }
}
