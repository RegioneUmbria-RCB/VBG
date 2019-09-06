using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.Adrier
{
    [XmlRoot(Namespace = "", ElementName = "RISPOSTA", IsNullable = false)]
    public class RispostaListaImprese
    {
        [XmlElement(ElementName = "HEADER", Order = 1)]
        public HeaderListaImprese Header { get; set; }

        [XmlElement(ElementName = "DATI", Order = 2)]
        public DatiListaImprese DatiListaImpresa { get; set; }

        public class HeaderListaImprese
        {
            [XmlElement(ElementName = "ESECUTORE", Order = 1)]
            public string Esecutore { get; set; }

            [XmlElement(ElementName = "SERVIZIO", Order = 2)]
            public string Servizio { get; set; }

            [XmlElement(ElementName = "ESITO", Order = 3)]
            public string Esito { get; set; }
        }

        public class DatiListaImprese
        {
            [System.Xml.Serialization.XmlElementAttribute("ERRORE", typeof(Errore))]
            [System.Xml.Serialization.XmlElementAttribute("LISTA_IMPRESE", typeof(ListaImprese))]
            public object Item { get; set; }
        }

        public class ListaImprese
        {
            [System.Xml.Serialization.XmlAttributeAttribute(AttributeName = "totale")]
            public string Totale { get; set; }

            [XmlElement(ElementName = "ESTREMI_IMPRESA", Order = 1)]
            public EstremiImpresaLista[] EstremiImpresa { get; set; }
        }

        public class EstremiImpresaLista
        {
            [System.Xml.Serialization.XmlAttributeAttribute(AttributeName = "elemento")]
            public string Elemento { get; set; }

            [XmlElement(ElementName = "DENOMINAZIONE", Order = 1)]
            public string Denominazione { get; set; }

            [XmlElement(ElementName = "CODICE_FISCALE", Order = 2)]
            public string CodiceFiscale { get; set; }

            [XmlElement(ElementName = "PARTITA_IVA", Order = 3)]
            public string PartitaIva { get; set; }

            [XmlElement(ElementName = "FORMA_GIURIDICA", Order = 4)]
            public FormaGiuridicaListaImpresa FormaGiuridica { get; set; }

            [XmlElement(ElementName = "DATI_ISCRIZIONE_RI", Order = 5)]
            public DatiIscrizioneRiListaImpresa IscrizioneRi { get; set; }

            [XmlElement(ElementName = "DATI_ISCRIZIONE_REA", Order = 6)]
            public DatiIscrizioneReaListaImpresa IscrizioneRea { get; set; }
        }

        public class FormaGiuridicaListaImpresa
        {
            [XmlElement(ElementName = "C_FORMA_GIURIDICA", Order = 1)]
            public string Codice { get; set; }

            [XmlElement(ElementName = "DESCRIZIONE", Order = 2)]
            public string Descrizione { get; set; }
        }

        public class DatiIscrizioneRiListaImpresa
        {
            [XmlElement(ElementName = "DATA", Order = 1)]
            public string Data { get; set; }
        }

        public class DatiIscrizioneReaListaImpresa
        {
            [XmlElement(ElementName = "NREA", Order = 1)]
            public string NRea { get; set; }

            [XmlElement(ElementName = "CCIAA", Order = 2)]
            public string Cciaa { get; set; }

            [XmlElement(ElementName = "FLAG_SEDE", Order = 3)]
            public string FlagSede { get; set; }

            [XmlElement(ElementName = "DATA", Order = 4)]
            public string Data { get; set; }

            [XmlElement(ElementName = "C_FONTE", Order = 5)]
            public string CodiceFonte { get; set; }
        }

        public class Errore
        {
            [XmlElement(ElementName = "TIPO", Order = 1)]
            public string Tipo { get; set; }

            [XmlElement(ElementName = "MSG_ERR", Order = 2)]
            public string MessaggioErrore { get; set; }
        }
    }
}
