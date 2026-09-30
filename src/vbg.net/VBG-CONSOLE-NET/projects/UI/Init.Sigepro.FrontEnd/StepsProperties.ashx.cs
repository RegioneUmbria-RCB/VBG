using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Web;

namespace Init.Sigepro.FrontEnd
{
    /// <summary>
    /// Summary description for StepsProperties
    /// </summary>
    public class StepsProperties : IHttpHandler
    {
        public class PaginaStep
        {
            public string Name { get; set; }
            public string FullName { get; set; }
            public IEnumerable<PropertyInfo> Properties { get; set; }
        }

        public void ProcessRequest(HttpContext context)
        {
            context.Response.ContentType = "text/html";

            var assembly = Assembly.GetExecutingAssembly();
            var type = typeof(IstanzeStepPage);

            var types = assembly.GetTypes().Where(t => type.IsAssignableFrom(t));
            var list = new List<PaginaStep>();

            foreach(var t in types)
            {
                var props = t.GetProperties(BindingFlags.Public | BindingFlags.Instance | BindingFlags.DeclaredOnly)
                             .Where(x => !x.Name.StartsWith("_") && x.Name != "Master" && !x.Name.EndsWith("Service"));

                if(props.Count() == 0)
                {
                    continue;
                }

                list.Add(new PaginaStep
                {
                    Name = t.Name,
                    FullName = t.FullName,
                    Properties = props
                });

            }

            var sortedList = list.OrderBy(x => x.Name);

            context.Response.Write("<ul>");
            foreach (var t in sortedList)
            { 
                context.Response.Write($"<li><a href='#{t.FullName}'>{t.Name}</a></li>");
            }
            context.Response.Write("</ul>");


            foreach (var t in sortedList)
            {

                context.Response.Write($"<h1 id='{t.FullName}'>{t.Name}</h1><h2>{t.FullName}</h2>");
                context.Response.Write("<ul>");

                foreach (var p in t.Properties)
                {
                    context.Response.Write($"<li>{p.Name} <b>[{p.PropertyType}]</b></li>");
                }

                context.Response.Write("</ul>");

                context.Response.Write("<br />");
            }



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