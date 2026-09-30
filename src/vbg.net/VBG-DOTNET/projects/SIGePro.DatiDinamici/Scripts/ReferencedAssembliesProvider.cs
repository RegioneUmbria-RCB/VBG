using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace Init.SIGePro.DatiDinamici.Framework.Scripts
{
    public class ReferencedAssembliesProvider
    {
        private readonly object _l = new object();
        private static string[] _referencedAssemblies;
        // Contiene la lista dei files da non referenziare. Vanno inseriti tutti i nomi files
        // di dll native che possono essere inserite nella cartella bin dell'applicazione
        private static readonly string[] _blacklist = new string[]{
            "gsdll32.dll".ToUpperInvariant(),
            "gsdll64.dll".ToUpperInvariant(),
            "krbcc64.dll".ToUpperInvariant(),
            "krb5_64.dll".ToUpperInvariant(),
            "k5sprt64.dll".ToUpperInvariant(),
            "gssapi64.dll".ToUpperInvariant(),
            "gsdll64.dll".ToUpperInvariant(),
            "comerr64.dll".ToUpperInvariant(),
            "Basic.Reference.Assemblies.dll".ToUpperInvariant(),
            "Basic.Reference.Assemblies.Net100.dll".ToUpperInvariant(),
            "Basic.Reference.Assemblies.NetStandard20.dll".ToUpperInvariant(),
            /*
            "RabbitMQ.Client.dll".ToUpperInvariant(),
            "AutoMapper.dll".ToUpperInvariant(),
            "AutoMapper.Extensions.Microsoft.DependencyInjection.dll".ToUpperInvariant(),
            "BlazorTemplater.dll".ToUpperInvariant(),
            "Castle.Core.dll".ToUpperInvariant(),
            "log4net.dll".ToUpperInvariant(),
            "Markdig.dll".ToUpperInvariant(),
            "Microsoft.AspNetCore.Authorization.dll".ToUpperInvariant(),
            "Microsoft.AspNetCore.Components.Authorization.dll".ToUpperInvariant(),
            "Microsoft.AspNetCore.Components.dll".ToUpperInvariant(),
            "Microsoft.AspNetCore.Components.Forms.dll".ToUpperInvariant(),
            "Microsoft.AspNetCore.Components.Web.dll".ToUpperInvariant(),
            "Microsoft.AspNetCore.Metadata.dll".ToUpperInvariant(),
            "Microsoft.Bcl.AsyncInterfaces.dll".ToUpperInvariant(),
            "Microsoft.CodeAnalysis.CSharp.dll".ToUpperInvariant(),
            "Microsoft.CodeAnalysis.dll".ToUpperInvariant(),
            "Microsoft.EntityFrameworkCore.Abstractions.dll".ToUpperInvariant(),
            "Microsoft.EntityFrameworkCore.dll".ToUpperInvariant(),
            "Microsoft.EntityFrameworkCore.Proxies.dll".ToUpperInvariant(),
            "Microsoft.EntityFrameworkCore.Relational.dll".ToUpperInvariant(),
            "Microsoft.Extensions.Configuration.Binder.dll".ToUpperInvariant(),
            "Microsoft.Extensions.DependencyInjection.Abstractions.dll".ToUpperInvariant(),
            "Microsoft.Extensions.DependencyModel.dll".ToUpperInvariant(),
            "Microsoft.Extensions.Logging.Abstractions.dll".ToUpperInvariant(),
            "Microsoft.Extensions.Options.dll".ToUpperInvariant(),
            "Microsoft.IdentityModel.Logging.dll".ToUpperInvariant(),
            "Microsoft.IdentityModel.Protocols.WsTrust.dll".ToUpperInvariant(),
            "Microsoft.IdentityModel.Tokens.dll".ToUpperInvariant(),
            "Microsoft.IdentityModel.Tokens.Saml.dll".ToUpperInvariant(),
            "Microsoft.IdentityModel.Xml.dll".ToUpperInvariant(),
            "Microsoft.JSInterop.dll".ToUpperInvariant(),
            "MySqlConnector.dll".ToUpperInvariant(),
            "Newtonsoft.Json.dll".ToUpperInvariant(),
            "Oracle.EntityFrameworkCore.dll".ToUpperInvariant(),
            "Oracle.ManagedDataAccess.dll".ToUpperInvariant(),
            "Pomelo.EntityFrameworkCore.MySql.dll".ToUpperInvariant(),
            "Serilog.AspNetCore.dll".ToUpperInvariant(),
            "Serilog.dll".ToUpperInvariant(),
            "Serilog.Extensions.Hosting.dll".ToUpperInvariant(),
            "Serilog.Extensions.Logging.dll".ToUpperInvariant(),
            "Serilog.Formatting.Compact.dll".ToUpperInvariant(),
            "Serilog.Settings.Configuration.dll".ToUpperInvariant(),
            "Serilog.Sinks.Console.dll".ToUpperInvariant(),
            "Serilog.Sinks.Debug.dll".ToUpperInvariant(),
            "Serilog.Sinks.File.dll".ToUpperInvariant(),
            "Serilog.Sinks.Graylog.Core.dll".ToUpperInvariant(),
            "Serilog.Sinks.Graylog.dll".ToUpperInvariant(),
            "System.CodeDom.dll".ToUpperInvariant(),
            "System.Configuration.ConfigurationManager.dll".ToUpperInvariant(),
            "System.Diagnostics.PerformanceCounter.dll".ToUpperInvariant(),
            "System.DirectoryServices.Protocols.dll".ToUpperInvariant(),
            "System.Private.ServiceModel.dll".ToUpperInvariant(),
            "System.Security.Cryptography.ProtectedData.dll".ToUpperInvariant(),
            "System.ServiceModel.Duplex.dll".ToUpperInvariant(),
            "System.ServiceModel.Federation.dll".ToUpperInvariant(),
            "System.ServiceModel.Http.dll".ToUpperInvariant(),
            "System.ServiceModel.NetTcp.dll".ToUpperInvariant(),
            "System.ServiceModel.Primitives.dll".ToUpperInvariant(),
            "System.ServiceModel.Security.dll".ToUpperInvariant(),
            */
        };

        private static readonly string[] _systemAssemblies = new string[]{
            "mscorlib.dll",
            "system.dll",
            "system.Core.dll",
            "system.data.dll",
            "System.Web.dll",
            "System.Xml.dll",
            "System.Web.Services.dll",
            "System.ServiceModel.dll",
            "System.Configuration.dll",
            "System.Data.OracleClient.dll",
            "System.Runtime.Serialization.dll",
            "netstandard.dll"
        };
        private readonly string _scanLocation;

        public ReferencedAssembliesProvider(string scanLocation)
        {
            this._scanLocation = scanLocation;
        }


        public IEnumerable<string> GetAssemblyReferenziati()
        {
            lock (this._l)
            {
                if (_referencedAssemblies == null)
                {
                    var dll = Directory.GetFiles(this._scanLocation, "*.dll")
                                        .Where(x => !_blacklist.Contains(Path.GetFileName(x).ToUpperInvariant()));

                    _referencedAssemblies = _systemAssemblies.Union(dll).ToArray();
                }
            }

            return _referencedAssemblies;
        }
    }
}
