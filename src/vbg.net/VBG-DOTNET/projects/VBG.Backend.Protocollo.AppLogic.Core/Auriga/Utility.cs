using System.Reflection;
using System.Runtime.Serialization;
using System.Web;
using VBG.Backend.Protocollo.AppLogic.Core.Auriga.SharedInfo;

namespace VBG.Backend.Protocollo.AppLogic.Core.Auriga
{
    public static class Utility
    {
        public static LivelloGerarchiaType[] GetLivelloGerarchiaDaClassifica(string classifica)
        {
            if (!String.IsNullOrEmpty(classifica))
            {
                var retVal = new List<LivelloGerarchiaType>();
                var c = classifica.Split('.');

                for (var i = 0; i < c.Length; i++)
                {
                    retVal.Add(new LivelloGerarchiaType
                    {
                        Nro = (i + 1).ToString(),
                        Codice = c[i]
                    });
                }

                return retVal.ToArray();
            }
            return null;
        }

        public static string HtmlEncodeContent(string xml)
        {
            if (!String.IsNullOrEmpty(xml))
            {
                xml = HttpUtility.HtmlEncode(xml);
                xml = xml.Replace("&lt;", "<");
                xml = xml.Replace("&quot;", "\"");
                xml = xml.Replace("&gt;", ">");
            }

            return xml;
        }
        public static string ToSerializedString(this Enum value)
        {
            var member = value.GetType()
                .GetMember(value.ToString())
                .First();

            var attribute = member.GetCustomAttribute<EnumMemberAttribute>();

            return attribute?.Value ?? value.ToString();
        }
    }
}
