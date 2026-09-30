using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public static class QuerystringArgumentsListExtensions
    {
        // Questo schianta se non c'è un httpcontext (web api). Occhio!
        private static NameValueCollection QueryString => HttpContext.Current?.Request.QueryString;

        public static IQuerystringArgumentsList AddAlias(this IQuerystringArgumentsList qs)
        {
            qs.Add(new QsAliasComune(QueryString));

            return qs;
        }

        public static IQuerystringArgumentsList AddSoftware(this IQuerystringArgumentsList qs)
        {
            qs.Add(new QsSoftware(QueryString));

            return qs;
        }

        public static IQuerystringArgumentsList AddIdPresentazione(this IQuerystringArgumentsList qs)
        {
            qs.Add(new QsIdDomandaOnline(QueryString));

            return qs;
        }

        public static IQuerystringArgumentsList AddStepId(this IQuerystringArgumentsList qs)
        {
            qs.Add(new QsStepId(QueryString));

            return qs;
        }
    }
}