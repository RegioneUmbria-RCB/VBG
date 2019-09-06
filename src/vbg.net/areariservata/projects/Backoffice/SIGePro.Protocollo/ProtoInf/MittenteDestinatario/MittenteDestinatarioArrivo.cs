using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Linq;

namespace Init.SIGePro.Protocollo.ProtoInf.MittenteDestinatario
{
    public class MittenteDestinatarioArrivo : IMittenteDestinatario
    {
        IAnagraficaAmministrazione _mittente;
        ProtocolloSerializer _serializer;
        Amministrazioni _amministrazione;

        public MittenteDestinatarioArrivo(IAnagraficaAmministrazione mittente, ProtocolloSerializer serializer, Amministrazioni amministrazione)
        {
            this._mittente = mittente;
            this._serializer = serializer;
            this._amministrazione = amministrazione;
        }

        public string GetDestinatario()
        {
            string destinatario = $"{this._amministrazione.INDIRIZZO ?? ""} - {this._amministrazione.CITTA ?? ""}";

            var destinatarioXml = new MittenteDestinatarioArrivoXML
            {
                Dimensione = new MittenteDestinatarioArrivoXML.Dim { NumeroColonne = 6 },
                Denominazione = this._amministrazione.AMMINISTRAZIONE,
                DescrizionePersonaDestinataria = "-",
                CodiceFiscale = this._amministrazione.PARTITAIVA ?? "-",
                Email = this._amministrazione.PEC ?? "-",
                IndirizzoPostale = destinatario ?? "-",
                UnitaOrganizzativa = this._amministrazione.UFFICIO ?? "-"
            };

            var xml = _serializer.Serialize("DestinatarioXML.xml", destinatarioXml, Validation.ProtocolloValidation.TipiValidazione.PROTOCOLLOXML_PROTOINF);
            var doc = XDocument.Parse(xml);
            return doc.ToString();
        }

        public string GetMittente()
        {
            string mittente = $"{this._mittente.Indirizzo ?? ""} - {this._mittente.Localita ?? ""}";

            var mittenteXml = new MittenteDestinatarioArrivoXML
            {
                Dimensione = new MittenteDestinatarioArrivoXML.Dim { NumeroColonne = 6 },
                Denominazione = this._mittente.NomeCognome,
                DescrizionePersonaDestinataria = "-",
                CodiceFiscale = String.IsNullOrEmpty(this._mittente.CodiceFiscalePartitaIva) ? "-" : this._mittente.CodiceFiscalePartitaIva,
                Email = String.IsNullOrEmpty(this._mittente.Pec) ? (String.IsNullOrEmpty(this._mittente.Email) ? "-" : this._mittente.Email) : this._mittente.Pec,
                IndirizzoPostale = mittente,
                UnitaOrganizzativa = "-"
            };

            var xml = _serializer.Serialize("MittenteXML.xml", mittenteXml, Validation.ProtocolloValidation.TipiValidazione.PROTOCOLLOXML_PROTOINF);
            var doc = XDocument.Parse(xml);
            return doc.ToString();
        }
    }
}
