
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.GestioneMovimentiFrontoffice
{
    public class DocumentoSostituibileMovimentoDto
    {
        public enum OrigineDocumentoEnum
        {
            Intervento = 0,
            Endoprocedimento = 1
        }

        [XmlElement(Order = 0)]
        public OrigineDocumentoEnum Origine { get; set; }

        [XmlElement(Order = 1)]
        public int IdDocumento { get; set; }

        [XmlElement(Order = 2)]
        public int? CodiceOggetto { get; set; }

        [XmlElement(Order = 3)]
        public string Descrizione { get; set; }

        [XmlElement(Order = 4)]
        public string NomeFile { get; set; }

    }
}
