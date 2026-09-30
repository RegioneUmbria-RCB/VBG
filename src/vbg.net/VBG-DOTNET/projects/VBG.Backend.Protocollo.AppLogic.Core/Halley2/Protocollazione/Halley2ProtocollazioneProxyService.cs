using VBG.Backend.Protocollo.AppLogic.Core.Halley2.Client;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Halley2.Protocollazione
{
    public class Halley2ProtocollazioneProxyService
    {
        private IProtocolloSerializer _serializer;
        private IProtocollazioneResolver _protocollazioneResolver;
        private ProtocolloHalley2Client _protocolloHalley2Client;

        public Halley2ProtocollazioneProxyService(IProtocolloSerializer serializer,IProtocollazioneResolver protocollazioneResolver)
        {
            this._serializer = serializer;
            this._protocollazioneResolver = protocollazioneResolver;
            this._protocolloHalley2Client = new ProtocolloHalley2Client(protocollazioneResolver.ProtocollazioneUrl);
        }

        public ProtocolloHalley2Response Protocolla(ProtocolloHalley2Request request)
        {
            try
            {
                using (var ws = this._protocolloHalley2Client.CreaWebService())
                {

                    var response = ws.NuovoProtocollo(
                        this._protocollazioneResolver.UserName,
                        this._protocollazioneResolver.Password,
                        request.Segnatura,
                        request
                            .Allegati?
                            .Where(x => x.Principale)?
                            .Select(x => x.File)?
                            .FirstOrDefault(),
                        request
                            .Allegati?
                            .Where(x => !x.Principale)?
                            .Select(x => x.File)?
                            .ToArray());

                    return new ProtocolloHalley2Response(response);
                }
            }
            catch (Exception ex) 
            {
                throw ex;
            }
        }
    }
}
