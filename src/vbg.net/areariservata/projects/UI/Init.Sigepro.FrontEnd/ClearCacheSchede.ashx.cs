using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd
{
    /// <summary>
    /// Summary description for ClearCacheSchede
    /// </summary>
    public class ClearCacheSchede : IHttpHandler
    {

        public void ProcessRequest(HttpContext context)
        {

            var enumerator = context.Cache.GetEnumerator();

            context.Response.ContentType = "text/plain";

            var schedeInCache = context.Cache.Cast<DictionaryEntry>().Where(x => x.Key.ToString().StartsWith("ModelloDinamicoCache")).ToList();

            foreach(var scheda in schedeInCache)
            {
                context.Response.Write(scheda.Key);
                context.Response.Write("... Eliminato.");
                context.Response.Write("\r\n");

                context.Cache.Remove(scheda.Key.ToString());
            }
            /*
            while (enumerator.MoveNext())
            {
                var item = (DictionaryEntry)enumerator.Current;

                
                context.Response.Write(item.Key);
                context.Response.Write("\r\n");
            }
            */
            
            context.Response.Write("Cache schede riciclata");
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