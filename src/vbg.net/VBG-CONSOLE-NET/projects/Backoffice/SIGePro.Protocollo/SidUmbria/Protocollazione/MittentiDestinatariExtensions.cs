using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloSidUmbriaService;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.SidUmbria.Protocollazione
{
    public static class MittentiDestinatariExtensions
    {
        public static corrispondente ToCorrispondenteAnagraficaArrivo(this ProtocolloAnagrafe anag)
        {
            var cfPiva = anag.CODICEFISCALE;

            if(String.IsNullOrEmpty(cfPiva))
                cfPiva = anag.PARTITAIVA;

            if(String.IsNullOrEmpty(cfPiva))
                throw new Exception(String.Format("CODICE FISCALE / PARTITA IVA DELL'ANAGRAFICA {0} CODICE {1}, NON VALORIZZATO", anag.GetNomeCompleto(), anag.CODICEANAGRAFE));

            var res = new corrispondente
            {
                denominazione = String.Format("{0} - {1}", anag.GetNomeCompleto(), (anag.ComuneResidenza != null ? anag.ComuneResidenza.COMUNE : "NON DEFINITO")),
                id = cfPiva,
                email = anag.Pec
            };

            return res;
        }

        public static corrispondente ToCorrispondenteAnagraficaPartenza(this ProtocolloAnagrafe anag, string destinatarioCC)
        {
            var cfPiva = anag.CODICEFISCALE;

            if (String.IsNullOrEmpty(cfPiva))
                cfPiva = anag.PARTITAIVA;

            if (String.IsNullOrEmpty(cfPiva))
                throw new Exception(String.Format("CODICE FISCALE / PARTITA IVA DELL'ANAGRAFICA {0} CODICE {1}, NON VALORIZZATO", anag.GetNomeCompleto(), anag.CODICEANAGRAFE));

            var res = new corrispondente
            {
                denominazione = String.Format("{0} - {1}", anag.GetNomeCompleto(), (anag.ComuneResidenza != null ? anag.ComuneResidenza.COMUNE : "NON DEFINITO")),
                id = cfPiva,
                email = anag.Pec,
                diretto = anag.ModalitaTrasmissione != destinatarioCC,
                interno = false
            };

            return res;
        }

        public static corrispondente ToCorrispondenteAmministrazioneArrivo(this Amministrazioni amm)
        {
            if (String.IsNullOrEmpty(amm.PARTITAIVA))
                throw new Exception(String.Format("PARTITA IVA DELL'AMMINSITRAZIONE {0} CODICE {1}, NON VALORIZZATO", amm.AMMINISTRAZIONE, amm.CODICEAMMINISTRAZIONE));

            var res = new corrispondente
            {
                denominazione = String.Format("{0} - {1}", amm.AMMINISTRAZIONE, (amm.CITTA != null ? amm.CITTA : "NON DEFINITO")),
                id = amm.PARTITAIVA,
                email = amm.PEC,
                interno = !String.IsNullOrEmpty(amm.PROT_UO)
            };

            return res;
        }

        public static corrispondente ToCorrispondenteAmministrazionePartenza(this Amministrazioni amm, string destinatarioCC)
        {
            if (String.IsNullOrEmpty(amm.PARTITAIVA))
                throw new Exception(String.Format("PARTITA IVA DELL'AMMINSITRAZIONE {0} CODICE {1}, NON VALORIZZATO", amm.AMMINISTRAZIONE, amm.CODICEAMMINISTRAZIONE));

            var res = new corrispondente
            {
                denominazione = String.Format("{0} - {1}", amm.AMMINISTRAZIONE, (amm.CITTA != null ? amm.CITTA : "NON DEFINITO")),
                id = amm.PARTITAIVA,
                email = amm.PEC,
                diretto = amm.ModalitaTrasmissione != destinatarioCC,
                interno = !String.IsNullOrEmpty(amm.PROT_UO)
            };

            return res;
        }

    }
}
