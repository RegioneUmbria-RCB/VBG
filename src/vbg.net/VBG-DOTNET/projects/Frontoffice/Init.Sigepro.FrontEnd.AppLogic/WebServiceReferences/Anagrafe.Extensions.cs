
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService
{
    public static class AnagrafeExtensions
    {
        public static bool HaCodiceFiscale(this Anagrafe anagrafe, string codiceFiscale)
        {
            var cfUcase = codiceFiscale.ToUpperInvariant();

            if (anagrafe == null)
            {
                return false;
            }

            if (!String.IsNullOrEmpty(anagrafe.CODICEFISCALE) && anagrafe.CODICEFISCALE.ToUpperInvariant() == cfUcase)
            {
                return true;
            }

            if (!String.IsNullOrEmpty(anagrafe.PARTITAIVA) && anagrafe.PARTITAIVA.ToUpperInvariant() == cfUcase)
            {
                return true;
            }

            return false;
        }

        public static AnagraficaDomanda ToAnagraficaDomanda(this Anagrafe anagrafe)
        {

            var codiceAnagrafe = Convert.ToInt32(anagrafe.CODICEANAGRAFE);

            var anagraficaDomanda = AnagraficaDomanda.New(codiceAnagrafe);

            anagraficaDomanda.Codicefiscale = anagrafe.CODICEFISCALE ?? "";

            anagraficaDomanda.DataCostituzione = anagrafe.DATANOMINATIVO;

            anagraficaDomanda.IdCittadinanza = String.IsNullOrEmpty(anagrafe.CODICECITTADINANZA) ? (int?)null : Convert.ToInt32(anagrafe.CODICECITTADINANZA);

            anagraficaDomanda.IdFormagiuridica = String.IsNullOrEmpty(anagrafe.FORMAGIURIDICA) ? (int?)null : Convert.ToInt32(anagrafe.FORMAGIURIDICA);

            anagraficaDomanda.IdTitolo = String.IsNullOrEmpty(anagrafe.TITOLO) ? (int?)null : Convert.ToInt32(anagrafe.TITOLO);

            anagraficaDomanda.Nome = anagrafe.NOME;

            anagraficaDomanda.Nominativo = anagrafe.NOMINATIVO;

            anagraficaDomanda.Note = anagrafe.NOTE;

            anagraficaDomanda.PartitaIva = anagrafe.PARTITAIVA ?? "";

            anagraficaDomanda.TipoPersona = (anagrafe.TIPOANAGRAFE == "F") ? TipoPersonaEnum.Fisica : TipoPersonaEnum.Giuridica;

            ImpostaIndirizzoResidenza(anagrafe, anagraficaDomanda);

            ImpostaIndirizzoCorrispondenza(anagrafe, anagraficaDomanda);

            ImpostaDatiIscrizioneRegTrib(anagrafe, anagraficaDomanda);

            ImpostaDatiIscrizioneRea(anagrafe, anagraficaDomanda);

            ImpostaDatiIscrizioneCCIAA(anagrafe, anagraficaDomanda);

            ImpostaDatiIscrizioneAlboProfessionale(anagrafe, anagraficaDomanda);

            ImpostaDatiIscrizioneINAIL(anagrafe, anagraficaDomanda);

            ImpostaContatti(anagrafe, anagraficaDomanda);

            ImpostaDatiIscrizioneINPS(anagrafe, anagraficaDomanda);

            ImpostaDatiNascita(anagrafe, anagraficaDomanda);

            ImpostaSesso(anagrafe, anagraficaDomanda);

            return anagraficaDomanda;
        }

        #region Metodi privati
        private static void ImpostaIndirizzoResidenza(Anagrafe anagrafe, AnagraficaDomanda anagraficaDomanda)
        {
            anagraficaDomanda.IndirizzoResidenza = new IndirizzoAnagraficaDomanda(anagrafe.INDIRIZZO, anagrafe.CITTA, anagrafe.CAP, anagrafe.PROVINCIA, anagrafe.COMUNERESIDENZA);
        }

        private static void ImpostaIndirizzoCorrispondenza(Anagrafe anagrafe, AnagraficaDomanda anagraficaDomanda)
        {
            anagraficaDomanda.IndirizzoCorrispondenza = new IndirizzoAnagraficaDomanda(anagrafe.INDIRIZZOCORRISPONDENZA, anagrafe.CITTACORRISPONDENZA, anagrafe.CAPCORRISPONDENZA, anagrafe.PROVINCIACORRISPONDENZA, anagrafe.COMUNECORRISPONDENZA);
        }

        private static void ImpostaDatiIscrizioneRegTrib(Anagrafe anagrafe, AnagraficaDomanda anagraficaDomanda)
        {
            anagraficaDomanda.DatiIscrizioneRegTrib = new DatiIscrizioneRegTrib(anagrafe.REGTRIB, anagrafe.DATAREGTRIB, anagrafe.CODCOMREGTRIB);
        }

        private static void ImpostaDatiIscrizioneRea(Anagrafe anagrafe, AnagraficaDomanda anagraficaDomanda)
        {
            anagraficaDomanda.DatiIscrizioneRea = new DatiIscrizioneReaAnagrafica(anagrafe.PROVINCIAREA, anagrafe.NUMISCRREA, anagrafe.DATAISCRREA);
        }

        private static void ImpostaDatiIscrizioneCCIAA(Anagrafe anagrafe, AnagraficaDomanda anagraficaDomanda)
        {
            anagraficaDomanda.DatiIscrizioneCciaa = new DatiIscrizioneCciaa(anagrafe.REGDITTE, anagrafe.DATAREGDITTE, anagrafe.CODCOMREGDITTE);
        }

        private static void ImpostaDatiIscrizioneAlboProfessionale(Anagrafe anagrafe, AnagraficaDomanda anagraficaDomanda)
        {
            var idAlbo = String.IsNullOrEmpty(anagrafe.CODICEELENCOPRO) ? (int?)null : Convert.ToInt32(anagrafe.CODICEELENCOPRO);
            var descrAlbo = (anagrafe.ElencoProfessionale == null) ? null : anagrafe.ElencoProfessionale.EpDescrizione;
            anagraficaDomanda.DatiIscrizioneAlboProfessionale = new DatiIscrizioneAlboProfessionale(idAlbo, descrAlbo, anagrafe.NUMEROELENCOPRO, anagrafe.PROVINCIAELENCOPRO);
        }

        private static void ImpostaDatiIscrizioneINAIL(Anagrafe anagrafe, AnagraficaDomanda anagraficaDomanda)
        {
            var descrizioneInailSede = (anagrafe.SedeInail != null) ? anagrafe.SedeInail.Descrizione : null;
            anagraficaDomanda.DatiIscrizioneInail = new DatiIscrizioneInailType(anagrafe.InailMatricola, anagrafe.InailCodiceSede, descrizioneInailSede);
        }

        private static void ImpostaContatti(Anagrafe anagrafe, AnagraficaDomanda anagraficaDomanda)
        {
            anagraficaDomanda.Contatti = new DatiContattoAnagrafica(anagrafe.TELEFONO, anagrafe.TELEFONOCELLULARE, anagrafe.FAX, anagrafe.EMAIL, anagrafe.Pec);
        }

        private static void ImpostaDatiIscrizioneINPS(Anagrafe anagrafe, AnagraficaDomanda anagraficaDomanda)
        {
            var descrizioneInpsSede = (anagrafe.SedeInps != null) ? anagrafe.SedeInps.Descrizione : null;
            anagraficaDomanda.DatiIscrizioneInps = new DatiIscrizioneInpsType(anagrafe.InpsMatricola, anagrafe.InpsCodiceSede, descrizioneInpsSede);
        }

        private static void ImpostaDatiNascita(Anagrafe anagrafe, AnagraficaDomanda anagraficaDomanda)
        {
            var provinciaNascita = (anagrafe.ComuneNascita != null) ? anagrafe.ComuneNascita.SIGLAPROVINCIA : null;
            anagraficaDomanda.DatiNascita = new DatiNascitaAnagrafica(anagrafe.CODCOMNASCITA, provinciaNascita, anagrafe.DATANASCITA);
        }

        private static void ImpostaSesso(Anagrafe anagrafe, AnagraficaDomanda anagraficaDomanda)
        {
            if (!String.IsNullOrEmpty(anagrafe.SESSO))
            {
                anagraficaDomanda.Sesso = (anagrafe.SESSO.ToUpper() == "M") ? SessoEnum.Maschio : SessoEnum.Femmina;
            }
        }
        #endregion
    }
}
