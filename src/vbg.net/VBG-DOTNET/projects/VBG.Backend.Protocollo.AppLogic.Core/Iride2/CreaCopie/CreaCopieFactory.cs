using VBG.Shared.Infrastructure.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;

namespace VBG.Backend.Protocollo.AppLogic.Core.Iride2.CreaCopie
{
    public static class CreaCopieFactory
    {
        public static ICreaCopie Create(CreaCopieInfo info, IBindingFactory bindingFactory)
        {
            info.ProtocolloLogs.InfoFormat("VERSIONE: {0}", info.Vert.Versione.ToString());

            switch(info.Vert.Versione)
            {
                case ProtocolloIrideEnumerators.VersioneEnum.J_IRIDE:
                    return new CreaCopieJIride(info);
                case ProtocolloIrideEnumerators.VersioneEnum.IRIDE:
                    return new CreaCopieIride(info, bindingFactory);

                default:
                    return new CreaCopieIride(info, bindingFactory);
            }
        }
    }
}
