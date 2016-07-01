using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale;
using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.LeggiDocumento;

namespace Init.SIGePro.Protocollo.DocEr.Pec
{
    public class PecManuale : IDatiPec
    {
        string _destinatari;
        string _oggetto;
        ProtocolloSerializer _serializer;

        public PecManuale(string idDocumento, GestioneDocumentaleService wrapper, ProtocolloSerializer serializer)
        {
            _serializer = serializer;

            var responseLeggiDocumento = wrapper.LeggiDocumento(idDocumento);
            var dic = responseLeggiDocumento.ToDictionary(x => x.key, y => y.value);
            
            _destinatari = dic[LeggiDocumentoConstants.Destinatari];
            _oggetto = dic[LeggiDocumentoConstants.Oggetto];
        }

        public string Oggetto
        {
            get { return _oggetto; }
        }

        public FlussoType Flusso
        {
            get { return FlussoPecAdapter.Adatta(); }
        }

        public IntestazioneTypeDestinatari Destinatari
        {
            get { return (IntestazioneTypeDestinatari)_serializer.Deserialize(_destinatari, typeof(IntestazioneTypeDestinatari)); ;}
        }
    }
}
