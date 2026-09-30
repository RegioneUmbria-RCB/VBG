using System;
using System.IO;
using System.Reflection;
using VBG.DatiDinamici.Scripts;

namespace Init.SIGePro.DatiDinamici.Framework.Scripts
{
    public static class AssemblyExtensions
    {
        public static string GetDirectoryLocation(this Assembly assembly)
        {
            string codeBase = assembly.CodeBase;
            UriBuilder uri = new UriBuilder(codeBase);
            string path = Uri.UnescapeDataString(uri.Path);
            return Path.GetDirectoryName(path);
        }
    }


    public class FrameworkReferencedAssembliesProvider : ReferencedAssembliesProvider
    {
        // Usato solo nei Tests
        public static IScriptCompiler CreateScriptCompiler()
        {
            return new FrameworkReferencedAssembliesProvider().CreateScriptCompilerInternal();
        }
        public FrameworkReferencedAssembliesProvider()
            : base(/*HttpContext.Current.Server.MapPath("~/bin")*/Assembly.GetExecutingAssembly().GetDirectoryLocation())

        {
        }

        private IScriptCompiler CreateScriptCompilerInternal()
        {
            return new FrameworkScriptCompiler(this);
        }
    }
}
