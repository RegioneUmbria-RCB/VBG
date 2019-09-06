using System.Xml;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG
{
    public interface IFVGWebServiceProxy
    {
        XmlDocument GetManagedDataDaCodiceIstanza(long codiceIstanza);
        void SalvaFileXml(long codiceIstanza, string idModulo, byte[] statoXmlSerializzato);
        void SalvaFilePdf(long codiceIstanza, string idModulo, byte[] pdfDomanda);
        byte[] CaricaFileXml(long codiceIstanza, string idModulo);
    }
}