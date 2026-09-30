using Init.SIGePro.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Pal.Protocollazione.Allegati
{
    public class AllegatiAdapter
    {
        IEnumerable<ProtocolloAllegati> _allegati;
        const string Binary = "BINARY";
        string _timeStamp;

        public AllegatiAdapter(IEnumerable<ProtocolloAllegati> allegati, string timeStamp)
        {
            _allegati = allegati.Where(x => x.OGGETTO != null);
            _timeStamp = timeStamp;
        }

        public DocumentoType GetAllegatoPrincipale()
        {
            if (_allegati.Count() == 0)
            {
                return null;
            }

            var allegato = _allegati.First();

            return new DocumentoType
            {
                Nome = allegato.NOMEFILE,
                Riferimento = String.Format("{0}_{1}", allegato.CODICEOGGETTO, _timeStamp),
                Tipo = Binary,
                Oggetto = allegato.OGGETTO
            };

        }

        public AllegatiType GetAllegati()
        {
            if (_allegati.Count() < 2)
            {
                return null;
            }

            var allegati = _allegati.Skip(1).Select(x => new DocumentoType
            {
                Nome = x.NOMEFILE,
                Riferimento = String.Format("{0}_{1}", x.CODICEOGGETTO, _timeStamp),
                Tipo = Binary,
                Oggetto = x.OGGETTO
            });

            return new AllegatiType { Documento = allegati.ToArray() };
        }
    }
}
