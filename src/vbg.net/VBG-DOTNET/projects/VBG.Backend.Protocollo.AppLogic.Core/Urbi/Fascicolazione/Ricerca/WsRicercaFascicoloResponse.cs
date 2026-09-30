using System.Collections.Generic;
using System.Xml.Serialization;
using VBG.Backend.Protocollo.AppLogic.Shared.Utils;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Fascicolazione.Ricerca
{
    [XmlRoot(ElementName = "Fascicolo")]
    public class FascicoloWs
    {

        private string _dataInizio;
        private string _dataFine;


        [XmlElement(ElementName = "idFascicolo")]
        public int IdFascicolo { get; set; }

        [XmlElement(ElementName = "codiceStato")]
        public int CodiceStato { get; set; }

        [XmlElement(ElementName = "descrizioneStato")]
        public string DescrizioneStato { get; set; }

        [XmlElement(ElementName = "oggetto")]
        public string Oggetto { get; set; }

        [XmlElement(ElementName = "anno")]
        public int Anno { get; set; }

        [XmlElement(ElementName = "progressivoAnno")]
        public int ProgressivoAnno { get; set; }

        [XmlElement(ElementName = "dataInizio")]
        public string DataInizio
        {
            get
            {
                return _dataInizio;
            }
            set
            {
                _dataInizio = Utility.FormattaValoriDaDeserializzare(value);
            }
        }


        [XmlElement(ElementName = "dataFine")]
        public string DataFine
        {
            get
            {
                return _dataFine;
            }
            set
            {
                _dataFine = Utility.FormattaValoriDaDeserializzare(value);
            }
        }

        [XmlElement(ElementName = "numeroFascicolo")]
        public int NumeroFascicolo { get; set; }

        [XmlElement(ElementName = "numeroSottoFascicolo")]
        public int NumeroSottoFascicolo { get; set; }

        [XmlElement(ElementName = "descrizioneFascicolo")]
        public string DescrizioneFascicolo { get; set; }

        [XmlElement(ElementName = "codiceRicercaClassificazione")]
        public string CodiceRicercaClassificazione { get; set; }

        [XmlElement(ElementName = "etichettaClassificazioneEstesa")]
        public string EtichettaClassificazioneEstesa { get; set; }

        [XmlElement(ElementName = "etichettaClassificazioneBreve")]
        public string EtichettaClassificazioneBreve { get; set; }

        [XmlElement(ElementName = "descrizioneClassificazioneBreve")]
        public string DescrizioneClassificazioneBreve { get; set; }

        [XmlElement(ElementName = "tipoChiusura")]
        public int TipoChiusura { get; set; }

        [XmlElement(ElementName = "codiceFormadiAggregazione")]
        public int CodiceFormadiAggregazione { get; set; }

        [XmlElement(ElementName = "descrizioneFormaDiAggregazione")]
        public string DescrizioneFormaDiAggregazione { get; set; }

        [XmlElement(ElementName = "codiceFascicoloRicerca")]
        public int CodiceFascicoloRicerca { get; set; }

        [XmlElement(ElementName = "descrizioneBreveFascicicolo")]
        public string DescrizioneBreveFascicicolo { get; set; }

        [XmlElement(ElementName = "descrizioneEstesaFascicicolo")]
        public string DescrizioneEstesaFascicicolo { get; set; }
    }

    [XmlRoot(ElementName = "SEQ_Fascicolo")]
    public class SeqFascicolo
    {

        [XmlElement(ElementName = "Fascicolo")]
        public List<FascicoloWs> Fascicolo { get; set; }
    }

    [XmlRoot(ElementName = "getInterrogazioneFascicolo_Result")]
    public class GetInterrogazioneFascicoloResult
    {

        [XmlElement(ElementName = "numResult")]
        public int NumResult { get; set; }

        [XmlElement(ElementName = "SEQ_Fascicolo")]
        public SeqFascicolo SeqFascicolo { get; set; }

        [XmlElement(ElementName = "MESSAGE")]
        public string Message { get; set; }

        [XmlElement(ElementName = "ERRORCODE")]
        public string ErrorCode { get; set; }
    }

    [XmlRoot(ElementName = "xapirest")]
    public class WsRicercaFascicoloResponse
    {

        [XmlElement(ElementName = "getInterrogazioneFascicolo_Result")]
        public GetInterrogazioneFascicoloResult GetInterrogazioneFascicoloResult { get; set; }
    }
}