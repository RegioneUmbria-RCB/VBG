using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.LeggiDocumento.Allegati
{
    public class AllegatiAdapter
    {
        private static class Constants
        {
            public const string NomeDocumento = "DOCNAME";
            public const string DescrizioneDocumento = "ABSTRACT";
        }

        string _unitaDocumentale;
        GestioneDocumentaleService _wrapper;
        Dictionary<string, string> _dic;

        public AllegatiAdapter(string unitaDocumentale, GestioneDocumentaleService wrapper, Dictionary<string, string> dic)
        {
            _unitaDocumentale = unitaDocumentale;
            _wrapper = wrapper;
            _dic = dic;
        }

        public AllOut[] Adatta()
        {
            var listaAllegati = new List<AllOut>();

            listaAllegati.Add(new AllOut
            {
                IDBase = _unitaDocumentale,
                Serial = _dic[Constants.NomeDocumento],
                Commento = _dic[Constants.DescrizioneDocumento]
            });

            var allegatiRelatedAdapter = new AllegatiRelatedAdapter(_wrapper, _unitaDocumentale);

            var allegati = allegatiRelatedAdapter.Adatta();
            
            if(allegati != null)
                allegati.ForEach(x => listaAllegati.Add(x));

            return listaAllegati.ToArray();
        }
    }
}
