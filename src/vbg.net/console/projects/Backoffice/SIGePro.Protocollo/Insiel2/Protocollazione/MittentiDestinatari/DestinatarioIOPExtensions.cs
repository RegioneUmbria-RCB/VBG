using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielService2;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.Insiel2.Protocollazione.MittentiDestinatari
{
    public static class DestinatarioIOPExtensions
    {
        public static DestinatarioIOPInsProto GetDestinatarioIOPFromAnagrafe(this ProtocolloAnagrafe anagrafica)
        {
            var nominativo = anagrafica.GetNomeCompleto();

            if (!String.IsNullOrEmpty(anagrafica.Pec))
                nominativo = String.Format("{0} ({1})", anagrafica.NOMINATIVO.Replace("  ", " ") , anagrafica.Pec);
            
            if(anagrafica.TIPOANAGRAFE == "F")
                nominativo = String.Format("{0} {1} ({2})", anagrafica.NOMINATIVO.Replace("  ", " "), anagrafica.NOME.Replace("  ", " "), anagrafica.Pec);

            return new DestinatarioIOPInsProto
            {
                descrizione = nominativo,
                dati_anagrafica = GetDatiAnagraficiFromAnagrafe(anagrafica),
                inserisci = true,
                inserisciSpecified = true
            };
        }

        private static DatiAnagrafica GetDatiAnagraficiFromAnagrafe(ProtocolloAnagrafe anag)
        {
            var nominativo = anag.NOMINATIVO;

            return new DatiAnagrafica()
            {
                cap = anag.CAP,
                codfis = !String.IsNullOrEmpty(anag.CODICEFISCALE) ? (anag.CODICEFISCALE.Length == 16 ? anag.CODICEFISCALE : null) : null,
                cognome = anag.TIPOANAGRAFE == "F" ? nominativo.Replace("  ", " ") : null,
                nome = anag.TIPOANAGRAFE == "F" ? anag.NOME.Replace("  ", " ") : null,
                denominaz = anag.TIPOANAGRAFE == "G" ? nominativo.Replace("  ", " ") : null,
                indirizzo = anag.INDIRIZZO,
                localita = anag.ComuneResidenza != null ? anag.ComuneResidenza.COMUNE : null,
                provincia = anag.ComuneResidenza != null ? anag.ComuneResidenza.SIGLAPROVINCIA : null,
                piva = !String.IsNullOrEmpty(anag.PARTITAIVA) ? (anag.PARTITAIVA.Length == 11 ? anag.PARTITAIVA : null) : null
            };
        }

        public static DestinatarioIOPInsProto GetDestinatarioIOPFromAmministrazione(this Amministrazioni amm)
        {
            var descrizione = amm.AMMINISTRAZIONE.Replace("  ", " ");

            return new DestinatarioIOPInsProto()
            {
                dati_anagrafica = GetDatiAmministrazione(amm, descrizione),
                descrizione = descrizione,
                inserisci = true,
                inserisciSpecified = true,
            };
        }

        private static DatiAnagrafica GetDatiAmministrazione(Amministrazioni amm, string descrizione)
        {
            return new DatiAnagrafica
            {
                cap = amm.CAP,
                denominaz = descrizione,
                indirizzo = amm.INDIRIZZO,
                localita = amm.CITTA,
                piva = !String.IsNullOrEmpty(amm.PARTITAIVA) ? (amm.PARTITAIVA.Length == 11 ? amm.PARTITAIVA : null) : null
            };
        }

    }
}
