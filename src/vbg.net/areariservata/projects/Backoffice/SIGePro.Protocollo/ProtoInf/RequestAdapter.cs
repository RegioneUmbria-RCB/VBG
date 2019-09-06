using Init.SIGePro.Protocollo.ProtocolloProtInfService;
using Init.SIGePro.Protocollo.ProtoInf.Allegati;
using Init.SIGePro.Protocollo.ProtoInf.Assegnatari;
using Init.SIGePro.Protocollo.ProtoInf.MittenteDestinatario;
using Init.SIGePro.Protocollo.ProtoInf.Protocollazione;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ProtoInf
{
    public class RequestAdapter
    {
        RequestInfo _info;

        public RequestAdapter(RequestInfo info)
        {
            this._info = info;

        }

        public string AdattaProtocolloXml()
        {
            var adapter = new ProtocolloXMLAdapter();
            return adapter.Adatta(_info);
        }

        public IMittenteDestinatario GetMittenteDestinatario()
        {
            return MittenteDestinatarioFactory.Create(this._info);
        }



        public string AdattaAssegnatarioXml()
        {
            var adapter = new AssegnatariXMLAdapter();
            return adapter.Adatta(this._info, this._info.Serializer);
        }

        public AllegatiXMLAdapter AdattaAllegatiXml()
        {
            return new AllegatiXMLAdapter();
        }
    }
}
