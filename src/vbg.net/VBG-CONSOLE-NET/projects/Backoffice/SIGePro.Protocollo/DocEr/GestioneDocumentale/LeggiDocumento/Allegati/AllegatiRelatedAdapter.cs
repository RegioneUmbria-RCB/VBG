using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.LeggiDocumento.Allegati
{
    public class AllegatiRelatedAdapter
    {
        GestioneDocumentaleService _wrapper;
        string _unitaDocumentale;

        public AllegatiRelatedAdapter(GestioneDocumentaleService wrapper, string unitaDocumentale)
        {
            _wrapper = wrapper;
            _unitaDocumentale = unitaDocumentale;
        }

        public List<AllOut> Adatta()
        {
            var response = _wrapper.GetRelatedDocuments(_unitaDocumentale);

            if (response == null)
                return null;

            return response.Select(x => x.ToAllOut(_wrapper)).ToList();
        }
    }
}
