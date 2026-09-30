using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Xml;
using System.Xml.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.ManagedData
{
    internal class ManagedDataDeserializer
    {
        public ManagedDataDeserializer()
        {
        }

        public XmlDocument Parse(byte[] fileData)
        {
            var txtDoc = RemoveAllNamespaces(Encoding.UTF8.GetString(fileData));

            var doc = new XmlDocument();
            using (var tr = new XmlTextReader(new StringReader(txtDoc)))
            {
                tr.Namespaces = false;
                doc.Load(tr);
            }

            return doc;
        }

        public static string RemoveAllNamespaces(string xmlDocument)
        {
            XElement xmlDocumentWithoutNs = RemoveAllNamespaces(XElement.Parse(xmlDocument));

            return xmlDocumentWithoutNs.ToString();
        }

        public static XElement RemoveAllNamespaces(XElement e)
        {
            return new XElement(e.Name.LocalName,
              (from n in e.Nodes()
               select ((n is XElement) ? RemoveAllNamespaces(n as XElement) : n)),
                  (e.HasAttributes) ?
                    (from a in e.Attributes()
                     where (!a.IsNamespaceDeclaration)
                     select new XAttribute(a.Name.LocalName, a.Value)) : null);
        }
    }
}
