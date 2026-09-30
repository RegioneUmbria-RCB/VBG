
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using System.Data;
using System.ComponentModel;
using Init.SIGePro.Authentication;

using PersonalLib2.Sql;
using Init.Utils.Sorting;
using Init.SIGePro.Utils;
using System.Configuration;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class FoArConfigurazioneMgr
    {
		public FoArConfigurazione LeggiDati(string idComune, string software)
		{
			FoArConfigurazione cfgTt = GetById(idComune, "TT");
			FoArConfigurazione cfgSw = GetById(idComune, software);

			// TODO: migliorare la ligica di sincronizzazione dati con tt

			// Non ho trovato nei dati di configurazione del software ne quelli per TT
			// Devo andare in errore perchè non saprei come rileggere lo stato iniziale dell'istanza
			if (cfgSw == null && cfgTt == null)
			{
				string errMsg = "Dati di configurazione per l'area riservata non trovati ne per il software " + software + " ne per TT. Verificare che la tabella FO_ARCONFIGURAZIONE contenga dei records";

				Logger.LogEvent(db, idComune, "FoArConfigurazione", errMsg, "FO_AR_CFG");

				throw new ConfigurationErrorsException(errMsg);
			}


			if (cfgSw == null)
				return cfgTt;
/*
			if (String.IsNullOrEmpty(cfgSw.IntestazioneDettaglioVisura))
				cfgSw.IntestazioneDettaglioVisura = cfgTt.IntestazioneDettaglioVisura;

			if (String.IsNullOrEmpty(cfgSw.MsgCertInvioFirma))
				cfgSw.MsgCertInvioFirma = cfgTt.MsgCertInvioFirma;

			if (String.IsNullOrEmpty(cfgSw.MsgCertInvioSottoscrizione))
				cfgSw.MsgCertInvioSottoscrizione = cfgTt.MsgCertInvioSottoscrizione;

			if (String.IsNullOrEmpty(cfgSw.MsgInvioFallito))
				cfgSw.MsgInvioFallito = cfgTt.MsgInvioFallito;

			if (String.IsNullOrEmpty(cfgSw.StatoInizialeIstanza))
				cfgSw.StatoInizialeIstanza = cfgTt.StatoInizialeIstanza;
*/

			return cfgSw;
		}
	}
}
				