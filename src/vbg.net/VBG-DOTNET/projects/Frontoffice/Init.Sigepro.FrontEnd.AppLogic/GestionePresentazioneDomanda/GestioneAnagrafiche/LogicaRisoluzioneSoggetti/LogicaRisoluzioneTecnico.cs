using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.LogicaRisoluzioneSoggetti
{
    public class LogicaRisoluzioneTecnico : ILogicaRisoluzioneTecnico
    {
        IUserCredentialsStorage _userCredentialsStorage;

        public LogicaRisoluzioneTecnico(IUserCredentialsStorage userCredentialsStorage)
        {
            this._userCredentialsStorage = userCredentialsStorage;
        }

        public AnagraficaDomanda Risolvi(IEnumerable<AnagraficaDomanda> anagrafichedomanda)
        {
            if (anagrafichedomanda == null)
            {
                return null;
            }

            var codiceFiscale = "-";

            // Se si sta stampando un anteprima modello non esiste un utente loggato
            if (this._userCredentialsStorage.Get() != null)
            {
                var datiUtente = this._userCredentialsStorage.Get().DatiUtente;
                codiceFiscale = datiUtente.Codicefiscale;

                if (String.IsNullOrEmpty(codiceFiscale))
                {
                    codiceFiscale = datiUtente.Partitaiva;
                }

                codiceFiscale = codiceFiscale.ToUpperInvariant();
            }

            var anagrafiche = anagrafichedomanda.Where(x => x.TipoSoggetto.Ruolo == RuoloTipoSoggettoDomandaEnum.Tecnico);

            if (anagrafiche.Count() == 0)
            {
                return null;
            }

            if (anagrafiche.Count() == 1)
            {
                return anagrafiche.FirstOrDefault();
            }

            var utenteLoggato = anagrafiche.Where(x => x.Codicefiscale.ToUpperInvariant() == codiceFiscale).FirstOrDefault();

            if (utenteLoggato == null)
            {
                return anagrafiche.FirstOrDefault();
            }

            return utenteLoggato;
        }
    }
}
