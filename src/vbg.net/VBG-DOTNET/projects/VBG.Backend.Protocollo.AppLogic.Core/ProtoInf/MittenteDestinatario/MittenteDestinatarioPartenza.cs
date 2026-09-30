using System.Xml.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.ProtoInf.MittenteDestinatario
{
    public class MittenteDestinatarioPartenza : IMittenteDestinatario
    {
        string _uo;
        IEnumerable<IAnagraficaAmministrazione> _destinatari;
        ProtocolloSerializer _serializer;

        public MittenteDestinatarioPartenza(string uo, IEnumerable<IAnagraficaAmministrazione> destinatari, ProtocolloSerializer serializer)
        {
            this._uo = uo;
            this._destinatari = destinatari;
            this._serializer = serializer;
        }

        public string GetDestinatario()
        {
            var destinatario = new DestinatarioPartenzaXML
            {
                Dimensione = new DestinatarioPartenzaXML.Dim
                {
                    NumeroColonne = 7,
                    NumeroRighe = _destinatari.Count()
                },
                Righe = this._destinatari.Select((x, i) => new DestinatarioPartenzaXML.Riga
                {
                    Index = i,
                    Denominazione = x.NomeCognome,
                    DescrizionePersonaDestinataria = "-",
                    CodiceFiscale = String.IsNullOrEmpty(x.CodiceFiscalePartitaIva) ? "-" : x.CodiceFiscalePartitaIva,
                    Email = String.IsNullOrEmpty(x.Pec) ? (String.IsNullOrEmpty(x.Email) ? "-" : x.Email) : x.Pec,
                    IndirizzoPostale = $"{x.Indirizzo ?? ""} - {x.Localita ?? ""}",
                    UnitaOrganizzativa = "-",
                    Flusso = "P"
                }).ToArray()
            };

            var xml = this._serializer.Serialize("DestinatarioXML.xml", destinatario, Shared.Validation.ProtocolloValidation.TipiValidazione.PROTOCOLLOXML_PROTOINF);

            var doc = XDocument.Parse(xml);

            foreach (var element in doc.Descendants())
            {
                if (element.Name.LocalName.StartsWith("_RIGA_IDX"))
                {
                    var indice = element.Attribute("Index");
                    element.Name = $"_RIGA_{indice.Value}";
                    element.Attribute("Index").Remove();
                }
            }

            return doc.ToString();
        }

        public string GetMittente()
        {
            return this._uo;
        }
    }
}
