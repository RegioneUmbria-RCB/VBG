using VBG.Backend.Protocollo.AppLogic.Core.ProtoInf.Allegati;
using VBG.Backend.Protocollo.AppLogic.Core.ProtoInf.Assegnatari;
using VBG.Backend.Protocollo.AppLogic.Core.ProtoInf.MittenteDestinatario;
using VBG.Backend.Protocollo.AppLogic.Core.ProtoInf.Protocollazione;

namespace VBG.Backend.Protocollo.AppLogic.Core.ProtoInf
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
