using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.PaDoc.Protocollazione.DocumentiAllegati
{
    public class AllegatiAdapter
    {
        private static class Constants
        {
            public const string SequenceNumber = "1";
            public const string Resource = "files";
        }

        public class Allegati
        {
            public metadigitStru Stru { get; private set; }
            public metadigitDoc[] Doc { get; private set; }

            public Allegati(metadigitStru stru = null, metadigitDoc[] doc = null)
            {
                Stru = stru;
                Doc = doc;
            }
        }

        List<ProtocolloAllegati> _allegati;

        public AllegatiAdapter(List<ProtocolloAllegati> allegati)
        {
            _allegati = allegati;
        }

        public Allegati Adatta(string nomenclatura, string baseUrl, string idComuneAlias, string software)
        {
            if (_allegati.Count == 0)
                return new Allegati();

            var stru = new metadigitStru
            {
                sequence_number = Constants.SequenceNumber,
                nomenclature = nomenclatura,
                element = new metadigitStruElement
                {
                    resource = Constants.Resource,
                    start = new metadigitStruElementStart { sequence_number = "1" },
                    stop = new metadigitStruElementStop { sequence_number = _allegati.Count.ToString() }
                }
            };

            var doc = _allegati.Select((x, i) => x.GetMetadigitDoc((i + 1).ToString(), baseUrl, idComuneAlias, software)).ToArray();

            return new Allegati(stru, doc);
        }
    }
}
