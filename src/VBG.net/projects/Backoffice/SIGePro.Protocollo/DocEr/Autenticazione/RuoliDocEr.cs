using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale;
using Init.SIGePro.Protocollo.ProtocolloDocErGestioneDocumentaleService;
using Init.SIGePro.Protocollo.ProtocolloServices;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.DocEr.Autenticazione
{
    public class RuoliDocEr
    {
        public static KeyValuePair[] GetRuoli(GestioneDocumentaleService gestDocWrapper, ResolveDatiProtocollazioneService datiProtoSrv, string utente)
        {
            
            var res = new List<KeyValuePair>();

            var mgr = new ResponsabiliRuoliMgr(datiProtoSrv.Db);
            var responsabiliRuoli = mgr.GetList(new ResponsabiliRuoli
            {
                IDCOMUNE = datiProtoSrv.IdComune,
                CODICERESPONSABILE = datiProtoSrv.CodiceResponsabile.Value.ToString()
            });

            foreach (var item in responsabiliRuoli)
            {
                var ruolo = item.ToRuoliDocEr(datiProtoSrv.Db, gestDocWrapper);

                if (ruolo == null)
                    continue;

                if (!res.Contains(ruolo))
                    res.Add(ruolo);
            }

            if(!String.IsNullOrEmpty(datiProtoSrv.CodiceIstanza))
            {
                var mgrIstRuoli = new IstanzeRuoliMgr(datiProtoSrv.Db);
                var istanzeRuoli = mgrIstRuoli.GetList(new IstanzeRuoli
                {
                    IDCOMUNE = datiProtoSrv.Istanza.IDCOMUNE,
                    CODICEISTANZA = datiProtoSrv.CodiceIstanza
                });

                foreach (var item in istanzeRuoli)
                {
                    var ruolo = item.ToIstanzeRuoliDocEr(datiProtoSrv.Db, gestDocWrapper);

                    if (ruolo == null)
                        continue;

                    if (!res.Contains(ruolo))
                        res.Add(ruolo);
                }            
            }

            res.Add(new KeyValuePair
            {
                key = utente,
                value = RuoliDocErConstants.FULL_ACCESS
            });

            return res.ToArray();
        }
    }
}
