

using VBG.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.Proxy;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.LeggiProtocollo
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
