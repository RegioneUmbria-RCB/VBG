using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Protocollo.Prisma.LeggiProtocollo
{
    [XmlRoot(Namespace = "", ElementName = "PROTOCOLLO", IsNullable = false)]
    public class LeggiProtocolloOutXML
    {
        [XmlElement("DOC")]
        public DocOutXml Doc { get; set; }

        [XmlElement("FILE_PRINCIPALE")]
        public FilePrincipaleOutXml FilePrincipale { get; set; }

        [XmlElement("ALLEGATI")]
        public AllegatiOutXml Allegati { get; set; }

        [XmlElement("SMISTAMENTI")]
        public SmistamentiOutXml Smistamenti { get; set; }

        [XmlElement("RAPPORTI")]
        public RapportiOutXml Rapporti { get; set; }
    }

    public class DocOutXml
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
        public string _data { get; set; }

        [XmlIgnore]
        public DateTime? Data
        {
            get
            {
                if (!String.IsNullOrEmpty(this._data))
                {
                    return DateTime.Parse(this._data);
                }

                return null;
            }
        }

        [XmlElement("OGGETTO")]
        public string Oggetto { get; set; }

        [XmlElement("CLASS_COD")]
        public string ClassificaCod { get; set; }

        [XmlElement("CLASS_DAL")]
        public string ClassificaDal { get; set; }

        [XmlElement("FASCICOLO_ANNO")]
        public string FascicoloAnno { get; set; }

        [XmlElement("FASCICOLO_NUMERO")]
        public string FascicoloNumero { get; set; }

        [XmlElement("STATO_PR")]
        public string StatoProtocollo { get; set; }

        [XmlElement("TIPO_DOCUMENTO")]
        public string TipoDocumento { get; set; }

        [XmlElement("UNITA_PROTOCOLLANTE")]
        public string UnitaProtocollante { get; set; }

        [XmlElement("MODALITA")]
        public string Modalita { get; set; }
    }

    public class FilePrincipaleOutXml
    {
        [XmlElement("FILE")]
        public FileOutXml File { get; set; }
    }

    public class FileOutXml
    {
        [XmlElement("ID_OGGETTO_FILE")]
        public string IdOggettoFile { get; set; }

        [XmlElement("ID_DOCUMENTO")]
        public string IdDocumento { get; set; }

        [XmlElement("FILENAME")]
        public string FileName { get; set; }
    }

    public class AllegatiOutXml
    {
        [XmlElementAttribute("ALLEGATO", IsNullable = false)]
        public AllegatoOutXml[] Allegato { get; set; }
    }

    public class AllegatoOutXml
    {
        [XmlElement("ID_DOCUMENTO")]
        public string IdDocumento { get; set; }

        [XmlElement("DESC_TIPO_ALLEGATO")]
        public string DescTipoAllegato { get; set; }

        [XmlElement("TIPO_ALLEGATO")]
        public string TipoAllegato { get; set; }

        [XmlElement("DESCRIZIONE")]
        public string Descrizione { get; set; }

        [XmlElement("IDRIF")]
        public string IdRif { get; set; }

        [XmlElement("NUMERO_PAG")]
        public string NumeroPag { get; set; }

        [XmlElement("QUANTITA")]
        public string Quantita { get; set; }

        [XmlElement("RISERVATO")]
        public string Riservato { get; set; }

        [XmlElement("TITOLO_DOCUMENTO")]
        public string TitoloDocumento { get; set; }

        [XmlElement("FILE_ALLEGATI")]
        public FileAllegatiOutXml FileAllegati { get; set; }
    }

    public class FileAllegatiOutXml
    {
        [XmlElementAttribute("FILE", IsNullable = false)]
        public FileOutXml[] File { get; set; }
    }

    public class SmistamentiOutXml
    {
        [XmlElementAttribute("SMISTAMENTO", IsNullable = false)]
        public SmistamentoOutXml[] Smistamento { get; set; }
    }

    public class SmistamentoOutXml
    {
        [XmlElement("ID_DOCUMENTO")]
        public string IdDocumento { get; set; }

        [XmlElement("DES_UFFICIO_SMISTAMENTO")]
        public string DescrizioneUfficioSmistamento { get; set; }

        [XmlElement("DES_UFFICIO_TRASMISSIONE")]
        public string DescrizioneUfficioTrasmissione { get; set; }

        [XmlElement("IDRIF")]
        public string IdRif { get; set; }

        [XmlElement("SMISTAMENTO_DAL")]
        public string SmistamentoDal { get; set; }

        [XmlElement("STATO_SMISTAMENTO")]
        public string StatoSmistamento { get; set; }

        [XmlElement("TIPO_SMISTAMENTO")]
        public string TipoSmistamento { get; set; }

        [XmlElement("UFFICIO_SMISTAMENTO")]
        public string UfficioSmistamento { get; set; }

        [XmlElement("UFFICIO_TRASMISSIONE")]
        public string UfficioTrasmissione { get; set; }

        [XmlElement("UTENTE_TRASMISSIONE")]
        public string UtenteTrasmissione { get; set; }
    }

    public class RapportiOutXml
    {
        [XmlElementAttribute("RAPPORTO", IsNullable = false)]
        public RapportoOutXml[] Rapporto { get; set; }
    }

    public class RapportoOutXml
    {
        [XmlElement("ID_DOCUMENTO")]
        public string IdDocumento { get; set; }

        [XmlElement("COGNOME_NOME")]
        public string CognomeNome { get; set; }

        [XmlElement("CODICE_FISCALE")]
        public string CodiceFiscale { get; set; }

        [XmlElement("EMAIL")]
        public string Email { get; set; }

        [XmlElement("DENOMINAZIONE")]
        public string Denominazione { get; set; }

        [XmlElement("IDRIF")]
        public string IdRif { get; set; }

        [XmlElement("CONOSCENZA")]
        public string Conoscenza { get; set; }
    }
}
