using Init.Sigepro.FrontEnd.AppLogic.Utils;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Web;

namespace Init.Sigepro.FrontEnd
{
    /// <summary>
    /// Summary description for Version
    /// </summary>
    public class Version : IHttpHandler
    {
        [Inject]
        public IAreaRiservataRuntimeEnvironment _runtimeEnvironment { get; set; }

        public void ProcessRequest(HttpContext context)
        {
            FoKernelContainer.Inject(this);


            var nameFilterDelegate = new Func<AssemblyName, Boolean>((x) =>
            {
                return !x.Name.StartsWith("System") &&
                        !x.Name.StartsWith("mscorlib") &&
                        !x.Name.StartsWith("Microsoft") &&
                        !x.Name.StartsWith("Ninject") &&
                        x.Name != "AutoMapper" &&
                        x.Name != "log4net" &&
                        x.Name != "WebActivatorEx";
            });

            var asmNamesList = new List<string>();
            var currentAssembly = Assembly.GetExecutingAssembly();
            context.Response.Write("<html><body style='font-family: arial;'>");
            context.Response.Write("<h1>Init.Sigepro.FrontEnd</h1>");
            context.Response.Write($"<h2>Runtime environment: {this._runtimeEnvironment.EnvironmentType.ToString()}</h2>");


            asmNamesList.Add(string.Format("<b>{0} v{1}</b>", currentAssembly.GetName().Name, currentAssembly.GetName().Version));
            foreach (var asmName in currentAssembly.GetReferencedAssemblies().Where(nameFilterDelegate))
            {
                var asm = Assembly.Load(asmName);
                var config = asm.GetCustomAttribute<AssemblyConfigurationAttribute>()?.Configuration;
                var description = asm.GetCustomAttribute<AssemblyDescriptionAttribute>()?.Description;
                var informalVersion = asm.GetCustomAttribute<AssemblyInformationalVersionAttribute>()?.InformationalVersion ?? "N.D.";
                asmNamesList.Add($"{asmName.Name} v{asmName.Version} ({config} - {informalVersion} - {description})");
            }

            foreach (var name in asmNamesList.OrderBy(x => x).Select(x => x))
                context.Response.Write(String.Format("{0}<br />", name));

            context.Response.Write("<h1>Server variables</h1>");
            foreach (var key in context.Request.ServerVariables.AllKeys)
            {
                context.Response.Write(String.Format("<b>{0}=</b>{1}<br/>", key, context.Request.ServerVariables[key]));
            }

            context.Response.ContentType = "text/html";
            context.Response.Write("</body></html>");
        }

        public bool IsReusable
        {
            get
            {
                return false;
            }
        }
    }
}