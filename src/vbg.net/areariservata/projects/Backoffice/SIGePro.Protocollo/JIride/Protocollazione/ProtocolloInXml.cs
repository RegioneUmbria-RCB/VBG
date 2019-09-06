using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Protocollo.JIride.Protocollazione
{
    [XmlRoot(Namespace = "", ElementName = "ProtoIn", IsNullable = false)]
    public class ProtocolloInXml
    {
        public string Data { get; set; }

        public string DataProt { get; set; }

        public string NumProt { get; set; }

        public string Classifica { get; set; }

        public string TipoDocumento { get; set; }

        public string Oggetto { get; set; }

        public string OggettoBilingue { get; set; }

        public string Origine { get; set; }

        public string MittenteInterno { get; set; }

        [XmlArrayItemAttribute("MittenteDestinatario", IsNullable = false)]
        public MittenteDestinatarioInXml[] MittentiDestinatari { get; set; }

        public string AggiornaAnagrafiche { get; set; }

        public string InCaricoA { get; set; }

        public string AnnoPratica { get; set; }

        public string NumeroPratica { get; set; }

        public string DataDocumento { get; set; }

        public string NumeroDocumento { get; set; }

        public string NumeroAllegati { get; set; }

        public string DataEvid { get; set; }

        public string OggettoStandard { get; set; }

        public string Utente { get; set; }

        public string Ruolo { get; set; }

        [XmlArrayItemAttribute("Allegato", IsNullable = false)]
        public AllegatoInXml[] Allegati { get; set; }
    }

    public class MittenteDestinatarioInXml
    {
        public string CodiceFiscale { get; set; }
        
        public string CognomeNome { get; set; }

        public string Nome { get; set; }

        public string Indirizzo { get; set; }

        public string Localita { get; set; }

        public string CodiceComuneResidenza { get; set; }

        public string DataNascita { get; set; }

        public string CodiceComuneNascita { get; set; }

        public string Nazionalita { get; set; }

        public string DataInvio_DataProt { get; set; }

        public string Spese_NProt { get; set; }

        public string Mezzo { get; set; }

        public string DataRicevimento { get; set; }

        public string TipoSogg { get; set; }

        public string TipoPersona { get; set; }

        [XmlArrayItemAttribute("Recapito", IsNullable = false)]
        public RecapitoInXml[] Recapiti;
    }

    public class RecapitoInXml
    {
        public string TipoRecapito { get; set; }

        public string ValoreRecapito { get; set; }
    }

    public class AllegatoInXml
    {
        public string TipoFile { get; set; }

        public string ContentType { get; set; }

        public byte[] Image { get; set; }

        public string Commento { get; set; }

        public string IdAllegatoPrincipale { get; set; }

        public string Schema { get; set; }

        public string NomeAllegato { get; set; }

        public string TipoAllegato { get; set; }

        public string URI { get; set; }

        public string Hash { get; set; }
    }
}


    
    
