using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.ManagedData;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Xml;
using System.Xml.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG
{
    public class FVGFileSystemWebServiceProxy : IFVGWebServiceProxy
    {
        private static class Constants
        {
            public const string PersistencePath = "c:\\temp\\fvg-managed-data";
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
            var managedDataPath = Path.Combine(Constants.PersistencePath, $"{codiceIstanza}-managed-data.xml");

            if (!File.Exists(managedDataPath))
            {
                throw new Exception($"Managed data non trovato nel percorso {managedDataPath}");
            }
            // var regex = new Regex("xmlns=\".*\"", RegexOptions.Compiled);

            return new ManagedDataDeserializer().Parse(File.ReadAllBytes(managedDataPath));
        }

        

        public void SalvaFilePdf(long codiceIstanza, string idModulo, byte[] pdfDomanda)
        {
            var pdfFilePath = Path.Combine(Constants.PersistencePath, $"{codiceIstanza}-{idModulo}.pdf");

            File.WriteAllBytes(pdfFilePath, pdfDomanda);
        }

        public void SalvaFileXml(long codiceIstanza, string idModulo, byte[] statoXmlSerializzato)
        {
            var xmlFilePath = Path.Combine(Constants.PersistencePath, $"{codiceIstanza}-{idModulo}.xml");

            File.WriteAllBytes(xmlFilePath, statoXmlSerializzato);
        }
    }
}
