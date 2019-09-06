using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Linq;
using System.Xml.Serialization;

namespace Init.SIGePro.Protocollo.ProtoInf.Protocollazione
{
    public class ProtocolloXMLAdapter
    {
        public ProtocolloXMLAdapter()
        {

        }

        public string Adatta(RequestInfo info)
        {
            var protocolloXml = new ProtocolloXML
            {
                Ente = info.ParametriRegola.CodiceEnte,
                Password = info.ParametriRegola.Password,
                TipoProtocollo = info.Metadati.Flusso,
                User = info.ParametriRegola.Username,
                CodAmm = info.ParametriRegola.CodiceAmministrazione,
                CodAoo = info.ParametriRegola.CodiceAoo,
                Oggetto = info.Metadati.ProtoIn.Oggetto,
                Proprietario = info.DatiConfProtocollo.Ruolo.Split(';')[0] ?? "",
                Protocollatore = info.DatiConfProtocollo.Uo,
                DataCarico = DateTime.Now.ToString("yyyyMMdd"),
                OraCarico = DateTime.Now.ToString("HHmmss"),
                Riservato = "N",
                DatiSensibili = "",
                RepNum = info.ParametriRegola.IgnoraClassifica ? "" : info.DatiConfProtocollo.Classifica,
                Fascicoli = "",
                Posta = "C",
                TipoCon = "",
                TiPosta = info.DatiConfProtocollo.TipoDocumento,
                Note = "",
                NumeroRiferimento = "",
                DataRiferimento = "",
                NumeroEmergenza = "",
                AnnoEmergenza = "",
                DataEmergenza = "",
                OrigineProtocollo = info.Metadati.ProtoIn.TipoSmistamento == "-" ? "" : info.Metadati.ProtoIn.TipoSmistamento
            };

            var xml = info.Serializer.Serialize("ProtocolloXML.xml", protocolloXml, Validation.ProtocolloValidation.TipiValidazione.PROTOCOLLOXML_PROTOINF);
            var doc = XDocument.Parse(xml);
            var retVal = doc.ToString().Replace("<ProtocolloXML>", "").Replace("</ProtocolloXML>", "");
            return retVal;
        }
    }
}
