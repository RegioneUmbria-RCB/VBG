using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.PaDoc.Pec
{
    public class MetadatiPecAdapter
    {
        IDatiProtocollo _datiProto;
        string _numeroProtocollo;
        string _annoProtocollo;
        string _owner;
        string _idComune;
        string _codiceMovimento;
        ProtocolloSerializer _serializer;

        public MetadatiPecAdapter(IDatiProtocollo datiProto, string numeroProtocollo, string annoProtocollo, string owner, string idComune, string codiceMovimento, ProtocolloSerializer serializer)
        {
            _datiProto = datiProto;
            _numeroProtocollo = numeroProtocollo;
            _annoProtocollo = annoProtocollo;
            _owner = owner;
            _idComune = idComune;
            _codiceMovimento = codiceMovimento;
            _serializer = serializer;
        }

        private pecRecipientsRecipient[] GetRecipients()
        {
            var recAmm = _datiProto.AmministrazioniEsterne.Select(x => x.PEC).Distinct().Select(x => new pecRecipientsRecipient { type = "to", Value = x });
            var recAnag = _datiProto.AnagraficheProtocollo.Select(x => x.Pec).Distinct().Select(x => new pecRecipientsRecipient { type = "to", Value = x });

            return recAmm.Union(recAnag).ToArray();
        }

        public string Adatta(ProtocolloSerializer serializer)
        {
            var pec = new send_pec
            {
                external_action_uri = new send_pecExternal_action_uri { update = "", error = "" },
                pec = new pec
                {
                    subject = _datiProto.ProtoIn.Oggetto,
                    text = _datiProto.ProtoIn.Corpo,
                    recipients = GetRecipients()
                },
                registration = new registration
                {
                    type = "U",
                    number = _numeroProtocollo,
                    year = _annoProtocollo,
                    owners = new registrationOwners[] { new registrationOwners { owner = _owner } },
                    external_id = String.Concat(_idComune, "-", _codiceMovimento)
                }
            };

            var res = _serializer.Serialize(ProtocolloLogsConstants.SegnaturaPecRequestFileName, pec);
            return res;
        }


    }
}
