using System.Xml.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.ProtoInf.MittenteDestinatario
{
    public class MittenteDestinatarioArrivo : IMittenteDestinatario
    {
        private readonly IAnagraficaAmministrazione _mittente;
        private readonly ProtocolloSerializer _serializer;
        private readonly ProtocolloAmministrazioni _amministrazione;

        public MittenteDestinatarioArrivo(IAnagraficaAmministrazione mittente, ProtocolloSerializer serializer, ProtocolloAmministrazioni amministrazione)
        {
            this._mittente = mittente;
            this._serializer = serializer;
            this._amministrazione = amministrazione;
        }

        public string GetDestinatario()
        {
            var destinatario = $"{this._amministrazione.INDIRIZZO ?? ""} - {this._amministrazione.CITTA ?? ""}";

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

            var xml = this._serializer.Serialize("DestinatarioXML.xml", destinatarioXml, Shared.Validation.ProtocolloValidation.TipiValidazione.PROTOCOLLOXML_PROTOINF);
            var doc = XDocument.Parse(xml);
            return doc.ToString();
        }

        public string GetMittente()
        {
            var mittente = $"{this._mittente.Indirizzo ?? ""} - {this._mittente.Localita ?? ""}";

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

            var xml = this._serializer.Serialize("MittenteXML.xml", mittenteXml, Shared.Validation.ProtocolloValidation.TipiValidazione.PROTOCOLLOXML_PROTOINF);
            var doc = XDocument.Parse(xml);
            return doc.ToString();
        }
    }
}
