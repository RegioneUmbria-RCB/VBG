namespace Init.Sigepro.FrontEnd.AppLogic.Utils
{
    internal enum RuntimePlatformEnum
    {
        NetFramework,
        NetCore
    }

    internal static class RuntimePlatform
    {
        public static RuntimePlatformEnum Current =>

#if NET9_0_OR_GREATER
            RuntimePlatformEnum.NetCore;
#else
    RuntimePlatformEnum.NetFramework;
#endif

    }
}
