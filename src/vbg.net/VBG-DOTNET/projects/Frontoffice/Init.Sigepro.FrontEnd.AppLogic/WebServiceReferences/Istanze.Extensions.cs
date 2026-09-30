using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using System;
using System.IO;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService
{
    public static class IstanzeExtensions
    {
        public static LivelloAccessoVisura GetLivelloAccesso(this Istanze istanza, string codiceFiscale)
        {
            if (istanza.Richiedente != null && istanza.Richiedente.HaCodiceFiscale(codiceFiscale))
            {
                return LivelloAccessoVisura.Completo;
            }

            if (istanza.Professionista != null && istanza.Professionista.HaCodiceFiscale(codiceFiscale))
            {
                return LivelloAccessoVisura.Completo;
            }

            if (istanza.AziendaRichiedente != null && istanza.AziendaRichiedente.HaCodiceFiscale(codiceFiscale))
            {
                return LivelloAccessoVisura.Completo;
            }

            var livelloAccesso = istanza.Richiedenti
                                    .Where(x => x.Richiedente.CODICEFISCALE?.ToUpperInvariant() == codiceFiscale.ToUpperInvariant())
                                    .Max(x => x.TipoSoggetto.FlagLivelliVisuraPratica.GetValueOrDefault(0));

            return LivelloAccessoVisura.DaValoreFlag(livelloAccesso);
        }

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
