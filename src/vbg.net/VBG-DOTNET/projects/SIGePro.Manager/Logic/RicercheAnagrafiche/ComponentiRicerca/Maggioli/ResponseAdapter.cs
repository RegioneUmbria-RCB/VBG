using Init.SIGePro.Data;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.ComponentiRicerca.Maggioli
{
    public class ResponseAdapter
    {
        public IEnumerable<Anagrafe> Adatta(LeggiAnagraficaSikuelRisposta response)
        {
            return response.Anagrafica.Select(x =>
            {
                var indirizzo = $"{x.Toponimo} {x.Via} {x.Civico} {x.Barrato}";

                return new Anagrafe
                {
                    NOMINATIVO = String.IsNullOrEmpty(x.Cognome) ? x.RagioneSociale : x.Cognome,
                    NOME = x.Nome,
                    TIPOANAGRAFE = x.PersonaGiuridica ? "G" : "F",
                    SESSO = x.Sesso,
                    INDIRIZZO = indirizzo.Trim(),
                    CODICEFISCALE = x.CodiceFiscale,
                    CAP = x.CapComuneDiResidenza,
                    DATANASCITA = x.DataDiNascita,
                    COMUNERESIDENZA = x.codCatastoComuneDiResidenza,
                    CODCOMNASCITA = !x.PersonaGiuridica ? x.CodiceFiscale.Substring(11, 4) : "",
                    Pec = x.Pec,
                    EMAIL = x.Email,
                    PARTITAIVA = x.PartitaIva,
                    PROVINCIA = x.SiglaProvinciaDiResidenza
                };
            });
        }
    }
}
