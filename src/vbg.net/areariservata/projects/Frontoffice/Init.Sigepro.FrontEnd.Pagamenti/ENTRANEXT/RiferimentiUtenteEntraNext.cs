using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Pagamenti.ENTRANEXT
{
    public class RiferimentiUtenteEntraNext : RiferimentiUtente
    {
        public readonly string RagioneSociale;
        public readonly string Nome;
        public readonly string Cognome;
        public readonly string PartitaIva;
        public readonly string Indirizzo;
        public readonly string Cap;
        public readonly string Comune;
        public readonly string Provincia;
        public readonly string Localita;
        public readonly string NaturaGiuridica;

        public RiferimentiUtenteEntraNext(string email, string identificativoUtente, string userId, string ragioneSociale, string nome, string cognome, string partitaIva, string indirizzo, string comune, string provincia, string cap, string localita, string tipoSoggetto) : base(email, identificativoUtente, userId)
        {
            this.RagioneSociale = ragioneSociale;
            this.Nome = nome;
            this.Cognome = cognome;
            this.PartitaIva = partitaIva;
            this.Indirizzo = indirizzo;
            this.Comune = comune;
            this.Provincia = provincia;
            this.Cap = cap;
            this.Localita = localita;
            this.NaturaGiuridica = tipoSoggetto;
        }
    }
}
