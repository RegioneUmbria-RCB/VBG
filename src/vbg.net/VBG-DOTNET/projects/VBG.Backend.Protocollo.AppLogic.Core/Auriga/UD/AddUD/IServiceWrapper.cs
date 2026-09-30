using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Core.Auriga.UD.AddUD
{
    public interface IServiceWrapper
    {
        ResponseInfo Protocolla(DatiProtocolloIn protoIn);
    }
}
