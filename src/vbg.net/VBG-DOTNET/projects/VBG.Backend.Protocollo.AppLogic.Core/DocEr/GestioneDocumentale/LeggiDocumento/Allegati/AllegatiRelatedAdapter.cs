
using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.GestioneDocumentale.LeggiDocumento.Allegati
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

        public List<AllegatoResponseType> Adatta()
        {
            var response = _wrapper.GetRelatedDocuments(_unitaDocumentale);

            if (response == null)
                return null;

            return response.Select(x => x.ToAllOut(_wrapper)).ToList();
        }
    }
}
