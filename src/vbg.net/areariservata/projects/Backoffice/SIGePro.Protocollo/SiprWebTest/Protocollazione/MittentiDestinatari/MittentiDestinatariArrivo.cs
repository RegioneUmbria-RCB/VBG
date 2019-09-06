using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.SiprWebTest.Protocollazione.MittentiDestinatari
{
    public class MittentiDestinatariArrivo : BaseMittentiDestinatari, IMittentiDestinatari
    {
        MittentiDestinatariArrivoValidation _validazione;
        ProtocolloLogs _logs;

        public MittentiDestinatariArrivo(IDatiProtocollo datiProto, ProtocolloLogs logs)
            : base(datiProto)
        {
            _logs = logs;
            _logs.Debug("MITTENTI / DESTINATARI IN ARRIVO");
            _logs.DebugFormat("datiProto is null? {0}", datiProto == null);
            _validazione = new MittentiDestinatariArrivoValidation(datiProto);
        }

        public string Mittente
        {
            get { return GetMittente(); }
        }

        public string[] Destinatari
        {
            get
            {
                _logs.DebugFormat("RECUPERO DESTINATARIO");
                _logs.DebugFormat("VALORE DESTINATARIO: {0}", DatiProto.Amministrazione.PROT_UO);
                return new string[] 
                { 
                    DatiProto.Amministrazione.PROT_UO 
                };
            }
        }

        private string GetMittente()
        {
            string rVal = "";
            _logs.Debug("CREAZIONE DEL MITTENTE");
            _logs.Debug("VALIDAZIONE DEL MITTENTE");
            _validazione.ValidaMittente();

            _logs.DebugFormat("NUMERO ANAGRAFICHE PROTOCOLLO: {0}", DatiProto.AnagraficheProtocollo.Count);
            if (DatiProto.AnagraficheProtocollo.Count > 0)
            {
                var anag = DatiProto.AnagraficheProtocollo[0];
                rVal = String.Format("{0} {1} CF:{2} PIVA:{3}", anag.NOME, anag.NOMINATIVO, anag.CODICEFISCALE, anag.PARTITAIVA).TrimStart();
            }
            else if (DatiProto.AmministrazioniProtocollo.Count > 0)
            {
                var amm = DatiProto.AmministrazioniProtocollo[0];
                string ufficio = !String.IsNullOrEmpty(amm.UFFICIO) ? String.Concat(amm.UFFICIO, " ") : "";
                rVal = String.Format("{0} {1}PIVA:{2}", amm.AMMINISTRAZIONE, ufficio, amm.PARTITAIVA);
            }
            else
                throw new Exception("ERRORE DURANTE LA COMPILAZIONE DEL MITTENTE, NON E' STATO TROVATO ALCUN MITTENTE");

            _logs.DebugFormat("MITTENTE RECUPERATO, VALORE: {0}", rVal);
            return rVal;
        }


        public string[] DestinatariCC
        {
            get { return null; }
        }
    }
}
