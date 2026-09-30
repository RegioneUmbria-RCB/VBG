using log4net;
using log4net.Repository.Hierarchy;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Logs
{
    internal static class Common
    {
        public static log4net.Core.Level ResolveLogLevel(string level)
        {
            var hierarchy = (Hierarchy)LogManager.GetRepository();

            return hierarchy.LevelMap[level.ToUpperInvariant()]
                   ?? log4net.Core.Level.Info;
        }
    }
}
