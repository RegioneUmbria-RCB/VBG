using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.ConversionePDF
{
    public interface IHtmlToPdfFileConverter
    {
        BinaryFile Converti(string nomeFile, string html);
        BinaryFile TrasformaEConverti(string nomeFile, string xml, string xsl);
    }
}
