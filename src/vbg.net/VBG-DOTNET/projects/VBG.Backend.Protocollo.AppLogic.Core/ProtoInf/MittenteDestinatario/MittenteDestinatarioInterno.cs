using System.Xml.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.ProtoInf.MittenteDestinatario
{
    public class MittenteDestinatarioInterno : IMittenteDestinatario
    {
        private readonly ProtocolloAmministrazioni _mittente;
        private readonly ProtocolloSerializer _serializer;
        private readonly ProtocolloAmministrazioni _Destinatario;
        private readonly string _uoMittente;
        private readonly string _indirizzoMittente;

        public MittenteDestinatarioInterno(ProtocolloAmministrazioni strutturaDefault, ProtocolloAmministrazioni mittente, ProtocolloSerializer serializer)
        {
            this._mittente = strutturaDefault;
            this._serializer = serializer;
            this._Destinatario = strutturaDefault;
            this._uoMittente = $"{mittente.PROT_UO}";
            if (!String.IsNullOrEmpty(mittente.PROT_RUOLO))
            {
                this._uoMittente = $"{mittente.PROT_UO} - {mittente.PROT_RUOLO}";
            }

            this._indirizzoMittente = $"{mittente.INDIRIZZO} - {mittente.CITTA}";
        }

        public string GetDestinatario()
        {
            var destinatario = $"{this._Destinatario.INDIRIZZO ?? ""} - {this._Destinatario.CITTA ?? ""}";

            var destinatarioXml = new MittenteDestinatarioInternoXML
            {
                Dimensione = new MittenteDestinatarioInternoXML.Dim { NumeroColonne = 6 },
                Denominazione = this._Destinatario.AMMINISTRAZIONE,
                DescrizionePersonaDestinataria = "-",
                CodiceFiscale = this._Destinatario.PARTITAIVA ?? "-",
                Email = this._Destinatario.PEC ?? "-",
                IndirizzoPostale = destinatario,
                UnitaOrganizzativa = this._Destinatario.UFFICIO ?? "-"
            };

            var xml = this._serializer.Serialize("DestinatarioXML.xml", destinatarioXml, Shared.Validation.ProtocolloValidation.TipiValidazione.PROTOCOLLOXML_PROTOINF);
            var doc = XDocument.Parse(xml);
            return doc.ToString();
        }

        public string GetMittente()
        {
            var mittenteXml = new MittenteDestinatarioInternoXML
            {
                Dimensione = new MittenteDestinatarioInternoXML.Dim { NumeroColonne = 6 },
                Denominazione = this._mittente.AMMINISTRAZIONE,
                DescrizionePersonaDestinataria = "-",
                CodiceFiscale = String.IsNullOrEmpty(this._mittente.PARTITAIVA) ? "-" : this._mittente.PARTITAIVA,
                Email = String.IsNullOrEmpty(this._mittente.PEC) ? (String.IsNullOrEmpty(this._mittente.EMAIL) ? "-" : this._mittente.EMAIL) : this._mittente.PEC,
                IndirizzoPostale = this._indirizzoMittente,
                UnitaOrganizzativa = String.IsNullOrEmpty(this._uoMittente) ? "-" : this._uoMittente
            };

            var xml = this._serializer.Serialize("MittenteXML.xml", mittenteXml, Shared.Validation.ProtocolloValidation.TipiValidazione.PROTOCOLLOXML_PROTOINF);
            var doc = XDocument.Parse(xml);
            return doc.ToString();
        }

    }
}
