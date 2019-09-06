using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale;
using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.LeggiDocumento;
using Init.SIGePro.Protocollo.DocEr.Pec.Persone;
using Init.SIGePro.Protocollo.ProtocolloDocErGestioneDocumentaleService;

namespace Init.SIGePro.Protocollo.DocEr.Pec
{
    public class DatiPec : IDatiPec
    {
        Dictionary<string, string> _metadati;
        string _idDocumento;
        ProtocolloSerializer _serializer;
        GestioneDocumentaleService _wrapper;

        public DatiPec(string idDocumento, GestioneDocumentaleService wrapper, ProtocolloSerializer serializer)
        {
            _serializer = serializer;
            _idDocumento = idDocumento;
            _wrapper = wrapper;

            var responseLeggiDocumento = wrapper.LeggiDocumento(idDocumento);
            _metadati = responseLeggiDocumento.ToDictionary(x => x.key, y => y.value);
        }

        public string Oggetto
        {
            get { return _metadati[LeggiDocumentoConstants.Oggetto]; }
        }

        public FlussoType Flusso
        {
            get { return FlussoPecAdapter.Adatta(); }
        }

        public IntestazioneTypeDestinatari GetDestinatari()
        {
            var destinatariLetti = _metadati[LeggiDocumentoConstants.Destinatari];
            var destinatariDocEr = (Destinatari)_serializer.Deserialize(destinatariLetti, typeof(Destinatari));
            var destinatari = destinatariDocEr.Items.Select(x =>
            {
                var factory = PersonaFisicaGiuridicaFactory.Create(x);
                return factory.GetDestinatario();
            });

            return new IntestazioneTypeDestinatari { Destinatario = destinatari.ToArray() };
        }

        public DocumentoType GetDocumentoPrincipale()
        {
            var doc = new DocumentoPecAdapter(_idDocumento, _metadati, _wrapper);

            return new DocumentoType
            {
                id = _idDocumento,
                uri = doc.Uri,
                Metadati = doc.GetMetadati(),
                Acl = doc.GetAcl()
            };
        }

        public DocumentoType[] GetRelated()
        {
            var related = _wrapper.GetRelatedDocuments(_idDocumento);
            if (related != null)
            {
                return related.Select(x =>
                {
                    var responseDoc = _wrapper.LeggiDocumento(x);
                    var metadati = responseDoc.ToDictionary(k => k.key, v => v.value);

                    var doc = new DocumentoPecAdapter(x, metadati, _wrapper);

                    return new DocumentoType
                    {
                        id = x,
                        Metadati = doc.GetMetadati(),
                        uri = doc.Uri,
                        Acl = doc.GetAcl()
                    };
                }).ToArray();
            }
            return null;
        }


        public DocumentoType[] GetAnnessi()
        {
            return new DocumentoType[] { };
        }

        public DocumentoType[] GetAnnotazioni()
        {
            return new DocumentoType[] { };
        }
    }
}
