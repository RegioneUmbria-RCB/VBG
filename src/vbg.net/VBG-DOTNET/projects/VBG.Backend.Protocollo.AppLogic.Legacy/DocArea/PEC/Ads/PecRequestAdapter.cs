using Init.SIGePro.Protocollo.InvioPecAdsService;
using System;
using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.PEC.Ads
{
    public class PecRequestAdapter
    {
        ProtocolloLogs _log;

        public PecRequestAdapter(ProtocolloLogs logs)
        {
            _log = logs;
        }

        public ParametriIngressoPG Adatta(ProtocollazioneRet responseProtocollo, IEnumerable<IAnagraficaAmministrazione> destinatari, string utente)
        {
            try
            {
                var destinatariConPec = destinatari.Where(x => !String.IsNullOrEmpty(x.Pec));
                if (destinatariConPec.Count() == 0)
                {
                    throw new Exception("NON E' PRESENTE NESSUN DESTINATARIO CON UNA PEC VALIDA");
                }

                var destinatariPec = String.Join("###", destinatariConPec.Select(x => x.Pec));

                var param = new ParametriIngressoPG
                {
                    anno = Convert.ToInt32(responseProtocollo.lngAnnoPG),
                    numero = Convert.ToInt32(responseProtocollo.lngNumPG),
                    listaDestinatari = destinatariPec,
                    utente_creazione = utente,
                    tipoRegistro = ""
                };

                return param;
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE DURANTE LA CREAZIONE DELLA RICHIESTA PER LA PEC DEL PROTOCOLLO NUMERO {responseProtocollo.lngNumPG}, ANNO {responseProtocollo.lngAnnoPG}, ERRORE: {ex.Message}", ex);
            }
        }
    }
}
