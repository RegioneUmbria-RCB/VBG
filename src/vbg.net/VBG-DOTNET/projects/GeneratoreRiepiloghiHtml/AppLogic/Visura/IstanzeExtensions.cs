using System.Text;
using System.Xml.Serialization;
using VBG.DatiDinamici.Interfaces;

namespace VisuraVbg
{
    public partial class Istanze : IClasseContestoModelloDinamico
    {
    }

    public static class IstanzeExtensions
    {
        public static string ToXmlModelloRiepilogo(this Istanze istanza, string forzaNumeroIstanza = "")
        {
            var oldNumeroIstanza = istanza.NUMEROISTANZA;

            if (!String.IsNullOrEmpty(forzaNumeroIstanza))
            {
                istanza.NUMEROISTANZA = forzaNumeroIstanza;
            }

            try
            {
                XmlAttributeOverrides overrides = new XmlAttributeOverrides();
                XmlAttributes attribs = new XmlAttributes();
                attribs.XmlElements.Add(new XmlElementAttribute("IstanzeAllegati"));
                overrides.Add(typeof(IstanzeProcedimenti), "IstanzeAllegati", attribs);


                using (var ms = new MemoryStream())
                {
                    var xs = new XmlSerializer(istanza.GetType(), overrides);
                    xs.Serialize(ms, istanza);

                    string xml = Encoding.UTF8.GetString(ms.ToArray());// StreamUtils.StreamToString(ms);
                    xml = xml.Replace("xmlns=\"http://init.sigepro.it\"", "");

                    return xml;
                }
            }
            finally
            {
                istanza.NUMEROISTANZA = oldNumeroIstanza;
            }
        }
    }
}
