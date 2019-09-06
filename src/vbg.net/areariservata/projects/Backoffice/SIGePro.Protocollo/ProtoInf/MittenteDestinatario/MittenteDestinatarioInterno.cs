using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Linq;

namespace Init.SIGePro.Protocollo.ProtoInf.MittenteDestinatario
{
    public class MittenteDestinatarioInterno : IMittenteDestinatario
    {
        Amministrazioni _mittente;
        ProtocolloSerializer _serializer;
        Amministrazioni _Destinatario;
        string _uoMittente;
        string _indirizzoMittente;

        public MittenteDestinatarioInterno(Amministrazioni strutturaDefault, Amministrazioni mittente, ProtocolloSerializer serializer)
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
            string destinatario = $"{this._Destinatario.INDIRIZZO ?? ""} - {this._Destinatario.CITTA ?? ""}";

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

            var xml = _serializer.Serialize("DestinatarioXML.xml", destinatarioXml, Validation.ProtocolloValidation.TipiValidazione.PROTOCOLLOXML_PROTOINF);
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

            var xml = _serializer.Serialize("MittenteXML.xml", mittenteXml, Validation.ProtocolloValidation.TipiValidazione.PROTOCOLLOXML_PROTOINF);
            var doc = XDocument.Parse(xml);
            return doc.ToString();
        }

    }
}
