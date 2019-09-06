using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Protocollo.Prisma.Fascicolazione
{
    [XmlRoot(Namespace = "", ElementName = "ROOT", IsNullable = false)]
    public class FascicolazioneOutXML
    {
        public enum ResultEnum { OK, KO }

        [XmlElement("RESULT")]
        public string _result { get; set; }

        public ResultEnum Result
        {
            get
            {
                if (String.IsNullOrEmpty(this._result))
                {
                    return ResultEnum.KO;
                }

                ResultEnum retVal = ResultEnum.KO;

                var isParsable = Enum.TryParse(this._result, out retVal);
                return retVal;
            }
        }

        [XmlElement("EXCEPTION")]
        public string Exception { get; set; }

        [XmlElement("MESSAGE")]
        public string Message { get; set; }

        [XmlElement("ERROR_NUMBER")]
        public string ErrorNumber { get; set; }

        [XmlElement("CLASS_COD")]
        public string CodiceClassifica { get; set; }

        [XmlElement("FASCICOLO_NUMERO")]
        public string NumeroFascicolo { get; set; }

        [XmlElement("FASCICOLO_ANNO")]
        public string AnnoFascicolo { get; set; }
    }
}
