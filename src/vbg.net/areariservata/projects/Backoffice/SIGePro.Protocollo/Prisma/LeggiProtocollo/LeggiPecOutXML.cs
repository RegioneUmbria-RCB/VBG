using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Protocollo.Prisma.LeggiProtocollo
{
    [XmlRoot(Namespace = "", ElementName = "PROTOCOLLO", IsNullable = false)]
    public class LeggiPecOutXML
    {
        [XmlElement("DATI")]
        public DatiPecOutXML Dati { get; set; }

        [XmlElement("MEMO_INVIATI")]
        public MemoInviatiOutXml MemoInviati { get; set; }

        public FileOutXml[] GetFiles()
        {
            if (this.MemoInviati != null && this.MemoInviati.Memo != null && this.MemoInviati.Memo.FileAllegati != null && this.MemoInviati.Memo.FileAllegati.File != null && this.MemoInviati.Memo.FileAllegati.File.Count() > 0)
            {
                return this.MemoInviati.Memo.FileAllegati.File;
            }

            return null;
        }
    }

    public class DatiPecOutXML
    {
        [XmlElement("ID_DOCUMENTO")]
        public string IdDocumento { get; set; }

        [XmlElement("IDRIF")]
        public string IdRif { get; set; }

        [XmlElement("ANNO")]
        public string Anno { get; set; }

        [XmlElement("NUMERO")]
        public string Numero { get; set; }

        [XmlElement("TIPO_REGISTRO")]
        public string TipoRegistro { get; set; }

        [XmlElement("DESCRIZIONE_TIPO_REGISTRO")]
        public string DescrizioneTipoRegistro { get; set; }

        [XmlElement("DATA")]
        public string Data { get; set; }

        [XmlElement("OGGETTO")]
        public string Oggetto { get; set; }

        [XmlElement("CLASS_COD")]
        public string CodiceClassifica { get; set; }

        [XmlElement("CLASS_DAL")]
        public string DataClassificaDal { get; set; }

        [XmlElement("FASCICOLO_ANNO")]
        public string AnnoFascicolo { get; set; }

        [XmlElement("FASCICOLO_NUMERO")]
        public string NumeroFascicolo { get; set; }

        [XmlElement("UNITA_PROTOCOLLANTE")]
        public string UnitaProtocollante { get; set; }

        [XmlElement("UTENTE_PROTOCOLLANTE")]
        public string UtenteProtocollante { get; set; }

        [XmlElement("MODALITA")]
        public string Flusso { get; set; }

        [XmlElement("MEMO_INVIATI")]
        public MemoInviatiOutXml MemoInviati { get; set; }
    }

    public class MemoInviatiOutXml
    {
        [XmlElement("MEMO")]
        public MemoOutXml Memo { get; set; }
    }

    public class MemoOutXml
    {
        [XmlElement("ID_DOCUMENTO")]
        public string IdDocumento { get; set; }

        [XmlElement("DATA_SPEDIZIONE")]
        public string DataSpedizione { get; set; }

        [XmlElement("DESTINATARI")]
        public string Destinatari { get; set; }

        [XmlElement("FILE_ALLEGATI")]
        public FileAllegatiOutXml FileAllegati { get; set; }
    }
}
