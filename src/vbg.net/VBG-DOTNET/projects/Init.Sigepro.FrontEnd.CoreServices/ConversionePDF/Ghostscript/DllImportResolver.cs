using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using log4net;
using System.Reflection;
using System.Runtime.InteropServices;

namespace Init.Sigepro.FrontEnd.CoreServices.ConversionePDF.Ghostscript
{
    public class DllImportResolver
    {
        private readonly IConfigurazione<ParametriPhantomjs> _config;

        private static bool LoadCallbackRegistered = false;
        private static readonly object Lock = new();
        private readonly ILog _log = LogManager.GetLogger(typeof(DllImportResolver));

        public DllImportResolver(IConfigurazione<ParametriPhantomjs> config)
        {
            this._config = config;

            if (!LoadCallbackRegistered)
            {
                lock (Lock)
                {
                    if (!LoadCallbackRegistered)
                    {
                        LoadCallbackRegistered = true;
                        NativeLibrary.SetDllImportResolver(Assembly.GetExecutingAssembly(), this.DllImportResolverMethod);
                    }
                }
            }
        }

        private IntPtr DllImportResolverMethod(string libraryName, Assembly assembly, DllImportSearchPath? searchPath)
        {
            this._log.Debug($"Caricamento della dll nativa {libraryName}");

            if (libraryName == "gsdll64")
            {
                this._log.Debug($"Risolvo il percorso di gsdll64");

                if (RuntimeInformation.IsOSPlatform(OSPlatform.Linux))
                {
                    this._log.Debug($"Piattaforma Linux");

                    return NativeLibrary.Load("libgs.so", assembly, searchPath);
                }

                if (RuntimeInformation.IsOSPlatform(OSPlatform.Windows))
                {
                    var libName = "gsdll64.dll";
                    var path = Path.Combine(this._config.Parametri.PhantomjsPath, libName);

                    this._log.Debug($"Piattaforma Windows, caricamento della dll dal path {path}, processo a 64 bit: {Environment.Is64BitProcess}");

                    var loadresult = NativeLibrary.Load(path, assembly, searchPath);

                    this._log.Debug($"loadresult={loadresult}");

                    return loadresult;
                }
            }

            // Otherwise, fallback to default import resolver.
            return IntPtr.Zero;
        }
    }
}
