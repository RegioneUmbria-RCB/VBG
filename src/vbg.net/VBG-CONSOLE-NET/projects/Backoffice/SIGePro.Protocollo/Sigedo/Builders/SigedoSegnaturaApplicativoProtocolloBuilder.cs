using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Sigedo.Configurations;
using System.Net;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.Sigedo.Services;

namespace Init.SIGePro.Protocollo.Sigedo.Builders
{
    public class SigedoSegnaturaApplicativoProtocolloBuilder
    {
        public readonly List<KeyValuePair<string, string>> DatiApplicativoProtocollo;

        private SigedoSegnaturaParamConfiguration _configuration;
        private ProtocolloLogs _logs;
        private ProtocolloSerializer _serializer;

        public SigedoSegnaturaApplicativoProtocolloBuilder(ProtocolloLogs logs, ProtocolloSerializer serializer, SigedoSegnaturaParamConfiguration configuration)
        {
            _configuration = configuration;
            _logs = logs;
            _serializer = serializer;
            DatiApplicativoProtocollo = CreaDatiApplicativoProtocollo();
        }

        private List<KeyValuePair<string, string>> CreaDatiApplicativoProtocollo()
        {
            var list = new List<KeyValuePair<string, string>>();

            if(!String.IsNullOrEmpty(_configuration.UoSmistamento))
                list.Add(new KeyValuePair<string, string>("uo", _configuration.UoSmistamento));

            list.Add(new KeyValuePair<string, string>("tipoSmistamento", _configuration.TipoSmistamento));
            list.Add(new KeyValuePair<string, string>("utente", _configuration.Operatore));

            if (_configuration.AltriDestinatariInterni != null &&_configuration.AltriDestinatariInterni.Count > 0)
            {
                foreach (var amm in _configuration.AltriDestinatariInterni)
                {
                    if (String.IsNullOrEmpty(amm.ModalitaTrasmissione))
                        _logs.WarnFormat("L'UNITA' ORGANIZZATIVA {0} RELATIVA ALL'AMMINISTRAZIONE {1} NON E' STATA INSERITA TRA GLI SMISTAMENTI IN QUANTO NON E' VALORIZZATA LA MODALITA' DI TRASMISSIONE", amm.PROT_UO, amm.ModalitaTrasmissione);
                    else
                        list.Add(new KeyValuePair<string, string>("smistamento", String.Format("{0}@@{1}", amm.PROT_UO, amm.ModalitaTrasmissione)));
                }
            }

            return list;
        }
    }
}
