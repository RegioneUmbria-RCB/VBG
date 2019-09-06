using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.DocEr.Pec
{
    public class DocumentoPecAdapter
    {
        string _id; 
        Dictionary<string, string> _metadati;
        GestioneDocumentaleService _wrapper;

        public string Uri { get { return ""; } }

        public DocumentoPecAdapter(string id, Dictionary<string, string> metadati, GestioneDocumentaleService wrapper)
        {
            _id = id;
            _metadati = metadati;
            _wrapper = wrapper;
        }

        public AclType[] GetAcl()
        {
            var aclDocPrincipale = _wrapper.GetAclDocument(_id);
            var dicAclDocPrincipale = aclDocPrincipale.ToDictionary(x => x.key, y => y.value);
            return dicAclDocPrincipale.Select(x => new AclType { attore = x.Key, valore = x.Value }).ToArray();
        }

        public ParametroType[] GetMetadati()
        {
            return _metadati.Where(m => !String.IsNullOrEmpty(m.Value))
                            .Select(x => new ParametroType
                            {
                                nome = x.Key,
                                valore = x.Value
                            }).ToArray();
        }
    }
}
