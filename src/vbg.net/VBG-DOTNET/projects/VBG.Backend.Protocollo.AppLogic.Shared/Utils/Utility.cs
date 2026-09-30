using RestSharp;
using System.Collections.Specialized;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Utils
{
    public class Utility
    {
        /// <summary>
        /// Questa funzionalità si è resa necessaria in quanto il web service restituisce delle stringhe che iniziano e finiscono per \n
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public static string FormattaValoriDaDeserializzare(string value)
        {
            if (String.IsNullOrEmpty(value))
                return null;

            return value.Replace("\n", "");
        }

        public static string NameValueCollectionToString(NameValueCollection values)
        {
            return String.Join("|", values.AllKeys.Select(key => String.Format("{0} = {1}", key, values[key])));
        }

        public static string NameValueCollectionToString(List<Parameter> parameters)
        {
            return String.Join("|", parameters.Select(param => String.Format("{0} = {1}", param.Name, param.Value)));
        }
    }
}
