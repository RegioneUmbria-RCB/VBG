using ProtocolloInsielService2;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel2.Protocollazione.MittentiDestinatari
{
    public static class DestinatarioIOPExtensions
    {
        public static DestinatarioIOPInsProto GetDestinatarioIOPFromAnagrafe(this ProtocolloAnagrafe anagrafica)
        {
            var nominativo = anagrafica.GetNomeCompleto();

            if (!String.IsNullOrEmpty(anagrafica.PecProtocollazione))
                nominativo = String.Format("{0} ({1})", anagrafica.NOMINATIVO.Replace("  ", " "), anagrafica.PecProtocollazione);

            if (anagrafica.TIPOANAGRAFE == "F")
                nominativo = String.Format("{0} {1} ({2})", anagrafica.NOMINATIVO.Replace("  ", " "), anagrafica.NOME.Replace("  ", " "), anagrafica.PecProtocollazione);

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
                localita = anag.ComuneResidenza != null ? anag.ComuneResidenza.DenominazioneComune : null,
                provincia = anag.ComuneResidenza != null ? anag.ComuneResidenza.SiglaProvincia : null,
                piva = !String.IsNullOrEmpty(anag.PARTITAIVA) ? (anag.PARTITAIVA.Length == 11 ? anag.PARTITAIVA : null) : null
            };
        }

        public static DestinatarioIOPInsProto GetDestinatarioIOPFromAmministrazione(this ProtocolloAmministrazioni amm)
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

        private static DatiAnagrafica GetDatiAmministrazione(ProtocolloAmministrazioni amm, string descrizione)
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
