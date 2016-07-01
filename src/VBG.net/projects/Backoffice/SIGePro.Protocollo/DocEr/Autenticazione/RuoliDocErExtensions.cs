using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.ProtocolloDocErGestioneDocumentaleService;
using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale;

namespace Init.SIGePro.Protocollo.DocEr.Autenticazione
{
    public static class RuoliDocErExtensions
    {
        public static KeyValuePair ToRuoliDocEr(this ResponsabiliRuoli responsabiliRuoli, DataBase db, GestioneDocumentaleService gestDocWrapper)
        {
            var mgr = new RuoliMgr(db);
            var ruolo = mgr.GetById(responsabiliRuoli.IDCOMUNE, Convert.ToInt32(responsabiliRuoli.IDRUOLO));

            if (ruolo == null)
                return null;

            if (!IsGroupEnabled(ruolo.COD_DOCER, gestDocWrapper))
                return null;

            var metadatiAdapter = new MetadatiAclDocumentAdapter(ruolo);
            return metadatiAdapter.Adatta();
        }

        private static bool IsGroupEnabled(string ruolo, GestioneDocumentaleService gestDocWrapper)
        {
            var response = gestDocWrapper.GetGroup(ruolo);
            return MetadatiGetGroupResponseAdapter.IsGroupEnabled(response);
        }

        public static KeyValuePair ToIstanzeRuoliDocEr(this IstanzeRuoli istanzeRuoli, DataBase db, GestioneDocumentaleService gestDocWrapper)
        {
            var mgr = new RuoliMgr(db);
            var ruolo = mgr.GetById(istanzeRuoli.IDCOMUNE, Convert.ToInt32(istanzeRuoli.IDRUOLO));

            if (ruolo == null)
                return null;

            if (!IsGroupEnabled(ruolo.COD_DOCER, gestDocWrapper))
                return null;

            var metadatiAdapter = new MetadatiAclDocumentAdapter(ruolo);

            return metadatiAdapter.Adatta();
        }
    }
}
