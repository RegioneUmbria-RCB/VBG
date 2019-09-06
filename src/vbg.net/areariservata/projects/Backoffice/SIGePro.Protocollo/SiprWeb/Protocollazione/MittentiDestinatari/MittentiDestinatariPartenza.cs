using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.SiprWeb.Protocollazione.MittentiDestinatari
{
    public class MittentiDestinatariPartenza : BaseMittentiDestinatari, IMittentiDestinatari
    {
        MittentiDestinatariPartenzaValidation _validazione;
        ProtocolloLogs _logs;
        string _codiceCC;

        public MittentiDestinatariPartenza(IDatiProtocollo datiProto, ProtocolloLogs logs, string codiceCC) : base(datiProto)
        {
            _logs = logs;
            _codiceCC = codiceCC;

            _validazione = new MittentiDestinatariPartenzaValidation(datiProto, logs);
        }

        public string Mittente
        {
            get { return DatiProto.Amministrazione.PROT_UO; }
        }

        public string[] Destinatari
        {
            get { return GetDestinatari(); }
        }

        public string[] DestinatariCC
        {
            get { return GetDestinatariCC(); }
        }

        private string[] GetDestinatari()
        {
            _validazione.ValidaDestinatari();

            List<string> rVal = new List<string>();

            DatiProto.AnagraficheProtocollo.Where(x => x.ModalitaTrasmissione != _codiceCC).ToList()
                                           .ForEach((x => rVal.Add(String.Format("{0} {1} CF:{2} PIVA:{3}", x.NOME, x.NOMINATIVO, x.CODICEFISCALE, x.PARTITAIVA).TrimStart())));

            DatiProto.AmministrazioniProtocollo.Where(x => x.ModalitaTrasmissione != _codiceCC).ToList()
                                               .ForEach(x => rVal.Add(String.Format("{0} {1}PIVA:{2}", x.AMMINISTRAZIONE, !String.IsNullOrEmpty(x.UFFICIO) ? String.Concat(x.UFFICIO, " ") : "", x.PARTITAIVA)));

            return rVal.ToArray();
        }

        private string[] GetDestinatariCC()
        {
            _validazione.ValidaDestinatari();

            List<string> rVal = new List<string>();

            DatiProto.AnagraficheProtocollo.Where(x => x.ModalitaTrasmissione == _codiceCC).ToList()
                                           .ForEach((x => rVal.Add(String.Format("{0} {1} CF:{2} PIVA:{3}", x.NOME, x.NOMINATIVO, x.CODICEFISCALE, x.PARTITAIVA).TrimStart())));

            DatiProto.AmministrazioniProtocollo.Where(x => x.ModalitaTrasmissione == _codiceCC).ToList()
                                               .ForEach(x => rVal.Add(String.Format("{0} {1}PIVA:{2}", x.AMMINISTRAZIONE, !String.IsNullOrEmpty(x.UFFICIO) ? String.Concat(x.UFFICIO, " ") : "", x.PARTITAIVA)));

            return rVal.ToArray();
        }
    }
}
