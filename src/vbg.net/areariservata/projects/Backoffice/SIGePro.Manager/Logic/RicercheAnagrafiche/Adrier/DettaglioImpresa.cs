using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.Adrier
{
    [XmlRoot(Namespace = "", ElementName = "RISPOSTA", IsNullable = false)]
    public class RispostaDettaglioImpresa
    {
        [XmlElement(ElementName = "HEADER", Order = 1)]
        public HeaderDettaglioImpresa Header { get; set; }

        [XmlElement(ElementName = "DATI", Order = 2)]
        public DatiDettaglio DatiDettaglioImpresa { get; set; }

        public class HeaderDettaglioImpresa
        {
            [XmlElement(ElementName = "ESECUTORE", Order = 1)]
            public string Esecutore { get; set; }

            [XmlElement(ElementName = "SERVIZIO", Order = 2)]
            public string Servizio { get; set; }

            [XmlElement(ElementName = "ESITO", Order = 3)]
            public string Esito { get; set; }
        }

        public class DatiDettaglio
        {
            [System.Xml.Serialization.XmlElementAttribute("ERRORE", typeof(ErroreDatiImpresa))]
            [System.Xml.Serialization.XmlElementAttribute("DATI_IMPRESA", typeof(DatiImpresaDettaglio))]
            public object Item { get; set; }
        }

        public class DatiImpresaDettaglio
        {

            [XmlElement(ElementName = "ESTREMI_IMPRESA", Order = 1)]
            public EstremiImpresaDettaglio[] EstremiImpresa { get; set; }

            [XmlElement(ElementName = "CODICE_FORMA_AMMV", Order = 2)]
            public string CodiceFormaAmmV { get; set; }

            [XmlElement(ElementName = "DESC_FORMA_AMMV", Order = 3)]
            public string DescrizioneFormaAmmV { get; set; }

            [XmlElement(ElementName = "DURATA_SOCIETA", Order = 4)]
            public DurataSocieta DurataSocieta { get; set; }

            [XmlElement(ElementName = "CAPITALI", Order = 5)]
            public Capitali Capitali { get; set; }

            [XmlElement(ElementName = "INFORMAZIONI_SEDE", Order = 6)]
            public InformazioniSedeImpresa InformazioniSede { get; set; }
        }

        public class EstremiImpresaDettaglio
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
            public FormaGiuridicaEstremiImpresa FormaGiuridica { get; set; }

            [XmlElement(ElementName = "DATI_ISCRIZIONE_RI", Order = 5)]
            public DatiIscrizioneRiEstremiImpresa IscrizioneRi { get; set; }

            [XmlElement(ElementName = "DATI_ISCRIZIONE_REA", Order = 6)]
            public DatiIscrizioneReaEstremiImpresa IscrizioneRea { get; set; }

        }

        public class FormaGiuridicaEstremiImpresa
        {
            [XmlElement(ElementName = "C_FORMA_GIURIDICA", Order = 1)]
            public string Codice { get; set; }

            [XmlElement(ElementName = "DESCRIZIONE", Order = 2)]
            public string Descrizione { get; set; }
        }

        public class DatiIscrizioneRiEstremiImpresa
        {
            [XmlElement(ElementName = "DATA", Order = 1)]
            public string Data { get; set; }
        }

        public class DatiIscrizioneReaEstremiImpresa
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


        public class DurataSocieta
        {
            [XmlElement(ElementName = "DT_COSTITUZIONE", Order = 1)]
            public string DataCostruzione { get; set; }

            [XmlElement(ElementName = "DT_TERMINE", Order = 2)]
            public string DataTermine { get; set; }

            [XmlElement(ElementName = "SCADENZE_ESERCIZI", Order = 3)]
            public ScadenzeEsercizi ScadenzeEsercizi { get; set; }


        }

        public class ScadenzeEsercizi
        {
            [XmlElement(ElementName = "DT_SUCCESSIVE", Order = 1)]
            public string DateSuccessive { get; set; }
        }

        public class Capitali
        {
            [XmlElement(ElementName = "TOTALE_QUOTE", Order = 1)]
            public TotaleQuote TotaleQuote { get; set; }

            [XmlElement(ElementName = "CAPITALE_SOCIALE", Order = 2)]
            public CapitaleSociale CapitaleSociale { get; set; }
        }

        public class TotaleQuote
        {
            [XmlElement(ElementName = "AMMONTARE", Order = 1)]
            public string Ammontare { get; set; }

            [XmlElement(ElementName = "VALUTA", Order = 2)]
            public string Valuta { get; set; }

        }

        public class CapitaleSociale
        {
            [XmlElement(ElementName = "DELIBERATO", Order = 1)]
            public string Deliberato { get; set; }

            [XmlElement(ElementName = "SOTTOSCRITTO", Order = 2)]
            public string Sottoscritto { get; set; }

            [XmlElement(ElementName = "VERSATO", Order = 3)]
            public string Versato { get; set; }

            [XmlElement(ElementName = "VALUTA", Order = 4)]
            public string Valuta { get; set; }
        }

        public class InformazioniSedeImpresa
        {
            [XmlElement(ElementName = "INDIRIZZO", Order = 1)]
            public IndirizzoImpresa Indirizzo { get; set; }

            [XmlElement(ElementName = "ATTIVITA", Order = 2)]
            public string Attivita { get; set; }

            [XmlElement(ElementName = "INFO_ATTIVITA", Order = 3)]
            public InfoAttivitaImpresa InfoAttivita { get; set; }

            [XmlElement(ElementName = "CODICE_ATECO_UL", Order = 4)]
            public CodiceAtecoUlImpresa CodiceAtecoUl { get; set; }
        }

        public class IndirizzoImpresa
        {
            [XmlElement(ElementName = "PROVINCIA", Order = 1)]
            public string Provincia { get; set; }

            [XmlElement(ElementName = "COMUNE", Order = 2)]
            public string Comune { get; set; }

            [XmlElement(ElementName = "C_COMUNE", Order = 3)]
            public string CodiceComune { get; set; }

            [XmlElement(ElementName = "TOPONIMO", Order = 4)]
            public string Toponimo { get; set; }

            [XmlElement(ElementName = "VIA", Order = 5)]
            public string Via { get; set; }

            [XmlElement(ElementName = "N_CIVICO", Order = 6)]
            public string NumeroCivico { get; set; }

            [XmlElement(ElementName = "CAP", Order = 7)]
            public string Cap { get; set; }

            [XmlElement(ElementName = "FRAZIONE", Order = 8)]
            public string Frazione { get; set; }

            [XmlElement(ElementName = "INDIRIZZO_PEC", Order = 9)]
            public string IndirizzoPec { get; set; }
        }

        public class InfoAttivitaImpresa
        {
            [XmlElement(ElementName = "DT_INIZIO_ATTIVITA", Order = 1)]
            public string DataInizioAttivita { get; set; }
        }

        public class CodiceAtecoUlImpresa
        {
            [XmlElement(ElementName = "ATTIVITA_ISTAT", Order = 1)]
            public AttivitaIstatAtecoUlImpresa[] AttivitaIstat { get; set; }
        }

        public class AttivitaIstatAtecoUlImpresa
        {
            [XmlElement(ElementName = "C_ATTIVITA", Order = 1)]
            public string CodiceAttivita { get; set; }

            [XmlElement(ElementName = "T_CODIFICA", Order = 2)]
            public string Codifica { get; set; }

            [XmlElement(ElementName = "DESC_ATTIVITA", Order = 3)]
            public string DescrizioneAttivita { get; set; }

            [XmlElement(ElementName = "C_IMPORTANZA", Order = 4)]
            public string CodiceImportanza { get; set; }

            [XmlElement(ElementName = "DT_INIZIO_ATTIVITA", Order = 5)]
            public string DataInizioAttivita { get; set; }
        }

        public class ErroreDatiImpresa
        {
            [XmlElement(ElementName = "TIPO", Order = 1)]
            public string Tipo { get; set; }

            [XmlElement(ElementName = "MSG_ERR", Order = 2)]
            public string MessaggioErrore { get; set; }
        }

    }
}
