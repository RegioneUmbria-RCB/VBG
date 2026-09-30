using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using System;
using System.Collections.Generic;
using VBG.Backend.Protocollo.AppLogic.Core.DocEr.GestioneDocumentale;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;

namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.Autenticazione
{
    public class RuoliDocEr
    {
        public static Dictionary<string, string> GetRuoli(GestioneDocumentaleService gestDocWrapper, ResolveDatiProtocollazioneService datiProtoSrv, string utente, string codiceComune, string software)
        {
            
            var res = new Dictionary<string, string>();

            var mgr = new ResponsabiliRuoliMgr(datiProtoSrv.Db);
            var responsabiliRuoli = mgr.GetList(new ResponsabiliRuoli
            {
                IDCOMUNE = datiProtoSrv.IdComune,
                CODICERESPONSABILE = datiProtoSrv.CodiceResponsabileUtenteLoggato.Value.ToString()
            });

            foreach (var item in responsabiliRuoli)
            {
                var ruolo = item.ToRuoliDocEr(datiProtoSrv.Db, gestDocWrapper, software, codiceComune);

                if (ruolo == null)
                    continue;

                if (!res.ContainsKey(ruolo.Value.Key))
                    res.Add(ruolo.Value.Key, ruolo.Value.Value);
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
                    var ruolo = item.ToIstanzeRuoliDocEr(datiProtoSrv.Db, gestDocWrapper, software, codiceComune);

                    if (ruolo == null)
                        continue;

                    if (!res.ContainsKey(ruolo.Value.Key))
                        res.Add(ruolo.Value.Key, ruolo.Value.Value);
                }            
            }

            var key = utente;
            var value = RuoliDocErConstants.FULL_ACCESS;

            res.Add(key, value);
            return res;
        }
    }
}
