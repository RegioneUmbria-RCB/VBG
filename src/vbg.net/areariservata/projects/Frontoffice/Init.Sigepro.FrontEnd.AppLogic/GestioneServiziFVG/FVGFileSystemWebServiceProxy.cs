using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Xml;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG
{
    public class FVGFileSystemWebServiceProxy : IFVGWebServiceProxy
    {
        private static class Constants
        {
            public const string PersistencePath = "c:\\temp\\";
        }

        public FVGFileSystemWebServiceProxy()
        {
        }

        public byte[] CaricaFileXml(long codiceIstanza, string idModulo)
        {
            var xmlFilePath = Path.Combine(Constants.PersistencePath, $"{codiceIstanza.ToString()}-{idModulo}.xml");

            if (!File.Exists(xmlFilePath))
            {
                return null;
            }

            return File.ReadAllBytes(xmlFilePath);
        }

        public XmlDocument GetManagedDataDaCodiceIstanza(long codiceIstanza)
        {
            var managedData = Path.Combine(Constants.PersistencePath, $"{codiceIstanza.ToString()}-managed-data.xml");

            if (!File.Exists(managedData))
            {
                throw new Exception($"Managed data non trovato nel percorso {managedData}");
            }

            var doc = new XmlDocument();
            using (XmlTextReader tr = new XmlTextReader(managedData))
            {
                tr.Namespaces = false;
                doc.Load(tr);
            }
            
            return doc;
        }

        public void SalvaFilePdf(long codiceIstanza, string idModulo, byte[] pdfDomanda)
        {
            var pdfFilePath = Path.Combine(Constants.PersistencePath, $"{codiceIstanza.ToString()}-{idModulo}.pdf");

            File.WriteAllBytes(pdfFilePath, pdfDomanda);
        }

        public void SalvaFileXml(long codiceIstanza, string idModulo, byte[] statoXmlSerializzato)
        {
            var xmlFilePath = Path.Combine(Constants.PersistencePath, $"{codiceIstanza.ToString()}-{idModulo}.xml");

            File.WriteAllBytes(xmlFilePath, statoXmlSerializzato);
        }
    }
}
