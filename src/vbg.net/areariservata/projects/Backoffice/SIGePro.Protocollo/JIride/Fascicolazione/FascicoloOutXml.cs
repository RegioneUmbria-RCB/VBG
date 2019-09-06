using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Protocollo.JIride.Fascicolazione
{
    [XmlRoot(Namespace = "", ElementName = "FascicoloOut", IsNullable = false)]
    public class FascicoloOutXml
    {
        [XmlElement("Id")]
        public string _id { get; set; }

        [XmlIgnore]
        public int Id
        {
            get
            {
                if (!String.IsNullOrEmpty(this._id))
                {
                    return int.Parse(this._id);
                }
                return 0;
            }
        }

        [XmlElement("Anno")]
        public string _anno { get; set; }

        [XmlIgnore]
        public int? Anno
        {
            get
            {
                if (!String.IsNullOrEmpty(this._anno))
                {
                    return int.Parse(this._anno);
                }

                return null;
            }
        }


        public string Numero { get; set; }
        public string NumeroSenzaClassifica { get; set; }
        public string Oggetto { get; set; }

        [XmlElement("Data")]
        public string _data { get; set; }

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

        public string Classifica { get; set; }
        public string Classifica_Descrizione { get; set; }
        public string AltriDati { get; set; }
        public string Archiviata { get; set; }

        [XmlElement("AnnoArchiviazione")]
        public string _annoArchiviazione { get; set; }

        [XmlIgnore]
        public int? AnnoArchiviazione
        {
            get
            {
                if (!String.IsNullOrEmpty(_annoArchiviazione))
                {
                    return int.Parse(this._annoArchiviazione);
                }
                return null;
            }
        }

        [XmlElement("NumeroArchiviazione")]
        public string _numeroArchiviazione { get; set; }

        [XmlIgnore]
        public int? NumeroArchiviazione
        {
            get
            {
                if (!String.IsNullOrEmpty(this._numeroArchiviazione))
                {
                    return int.Parse(this._numeroArchiviazione);
                }
                return null;
            }
        }


        public string UtenteDiInserimento { get; set; }
        public string RuoloDiInserimento { get; set; }
        public string DataDiInserimento { get; set; }
        public string DataDiChiusura { get; set; }
        public bool PraticaChiusa { get; set; }
        public string DataDiScarto { get; set; }
        public short PraticaRiservata { get; set; }

        [XmlArrayItemAttribute("DocumentoFascicoloOut", IsNullable = false)]
        public DocumentoFascicoloOutXml[] DocumentiFascicolo;

        public string FormatoData { get; set; }
        public string LivelloDiSicurezza { get; set; }
        public bool PraticaScartabile { get; set; }

        [XmlElement("NumeroDocumentiPratica")]
        public string _numeroDocumentiPratica { get; set; }

        [XmlIgnore]
        public int? NumeroDocumentiPratica
        {
            get
            {
                if (!String.IsNullOrEmpty(this._numeroDocumentiPratica))
                {
                    return int.Parse(this._numeroDocumentiPratica);
                }

                return null;
            }
        }

        [XmlElement("IterAttivo")]
        public string _iterAttivo { get; set; }

        [XmlIgnore]
        public int? IterAttivo
        {
            get
            {
                if (!String.IsNullOrEmpty(this._iterAttivo))
                {
                    return int.Parse(this._iterAttivo);
                }
                return null;
            }
        }

        public string ACL { get; set; }
        public string ErrDescription { get; set; }
        public string DataDiAnnullo { get; set; }
        public bool PraticaAnnullata { get; set; }
        public string AnnullamentoNote { get; set; }
        public string AnnullamentoUtente { get; set; }
        public string Padre { get; set; }
        public string Key { get; set; }
        public string SottoFascicolo { get; set; }
        public bool IsSottofascicolo { get; set; }
        public bool HasSottofascicolo { get; set; }
        public bool HasDocumenti { get; set; }
        public bool HasDocumentiConIter { get; set; }
        public string Messaggio { get; set; }
        public string Errore { get; set; }
        public bool Eterogeneo { get; set; }
    }

    public class DocumentoFascicoloOutXml
    {
        public int IdDocumento { get; set; }
        public string TipoDocumento { get; set; }
        public string DataInserimento { get; set; }
        public string DestinatarioInterno { get; set; }
        public string DataInvioDestinatario { get; set; }
        public bool Copia { get; set; }
        public string AnnoProtocollo { get; set; }
        public string NumeroProtocollo { get; set; }
        public bool IterAttivo { get; set; }
        public string Oggetto { get; set; }
        public string DataAnnullamento { get; set; }
        public string Origine { get; set; }
    }
}
