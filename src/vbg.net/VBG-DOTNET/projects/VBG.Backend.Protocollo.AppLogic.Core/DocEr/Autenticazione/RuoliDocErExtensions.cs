using Init.SIGePro.Data;
using PersonalLib2.Data;
using Init.SIGePro.Manager;
using VBG.Backend.Protocollo.AppLogic.Core.DocEr.GestioneDocumentale;
using System.Collections.Generic;
using System;
using VBG.Backend.Protocollo.AppLogic.Shared.Managers;

namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.Autenticazione
{
    public static class RuoliDocErExtensions
    {
        public static KeyValuePair<string, string>? ToRuoliDocEr(this ResponsabiliRuoli responsabiliRuoli, DataBase db, GestioneDocumentaleService gestDocWrapper, string software, string codiceComune)
        {
            string codiceDocEr = "";

            var mgrP = new RuoliProtocolloMgr(db);
            var ruoloP = mgrP.GetById(responsabiliRuoli.IDCOMUNE, Convert.ToInt32(responsabiliRuoli.IDRUOLO), software, codiceComune);

            if (ruoloP != null)
                codiceDocEr = ruoloP.RuoloExt;

            var mgr = new RuoliMgr(db);
            var ruolo = mgr.GetById(responsabiliRuoli.IDCOMUNE, Convert.ToInt32(responsabiliRuoli.IDRUOLO));

            if (ruolo == null)
                return null;

            if(String.IsNullOrEmpty(codiceDocEr))
                codiceDocEr = ruolo.COD_DOCER;

            if (String.IsNullOrEmpty(codiceDocEr))
                return null;

            bool readOnly = ruolo.READONLY == "1";

            if (!IsGroupEnabled(codiceDocEr, gestDocWrapper))
                return null;

            var metadatiAdapter = new MetadatiAclDocumentAdapter();
            return metadatiAdapter.Adatta(codiceDocEr, readOnly);
        }

        private static bool IsGroupEnabled(string ruolo, GestioneDocumentaleService gestDocWrapper)
        {
            var response = gestDocWrapper.GetGroup(ruolo);
            return MetadatiGetGroupResponseAdapter.IsGroupEnabled(response);
        }

        public static KeyValuePair<string, string>? ToIstanzeRuoliDocEr(this IstanzeRuoli istanzeRuoli, DataBase db, GestioneDocumentaleService gestDocWrapper, string software, string codiceComune)
        {
            var mgrP = new RuoliProtocolloMgr(db);
            var ruoloP = mgrP.GetById(istanzeRuoli.IDCOMUNE, Convert.ToInt32(istanzeRuoli.IDRUOLO), software, codiceComune);
            string codiceDocEr = "";

            if (ruoloP != null)
                codiceDocEr = ruoloP.RuoloExt;

            var mgr = new RuoliMgr(db);
            var ruolo = mgr.GetById(istanzeRuoli.IDCOMUNE, Convert.ToInt32(istanzeRuoli.IDRUOLO));

            if (ruolo == null)
                return null;

            if (String.IsNullOrEmpty(codiceDocEr))
                codiceDocEr = ruolo.COD_DOCER;

            if (String.IsNullOrEmpty(codiceDocEr))
                return null;

            bool readOnly = ruolo.READONLY == "1";

            if (!IsGroupEnabled(codiceDocEr, gestDocWrapper))
                return null;

            var metadatiAdapter = new MetadatiAclDocumentAdapter();

            return metadatiAdapter.Adatta(codiceDocEr, readOnly);
        }
    }
}
