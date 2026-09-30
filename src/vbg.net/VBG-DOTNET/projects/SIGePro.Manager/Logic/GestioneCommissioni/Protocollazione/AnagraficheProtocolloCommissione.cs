using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.GestioneCommissioni.Protocollazione
{
    public class AnagraficheProtocolloCommissione
    {
        public class Anagrafica
        {
            public int CodiceAnagrafe { get; set; }
        }

        public class Amministrazione
        {
            public int CodiceAmministrazione { get; set; }
        }

        public IEnumerable<Anagrafica> Anagrafiche { get; } = Enumerable.Empty<Anagrafica>();

        public IEnumerable<Amministrazione> Amministrazioni { get; } = Enumerable.Empty<Amministrazione>();

        public AnagraficheProtocolloCommissione(int codiceAnagrafe, int? codiceAmministrazione)
        {
            this.Anagrafiche = new[] {
                new Anagrafica{ CodiceAnagrafe = codiceAnagrafe }
            };

            if (codiceAmministrazione.HasValue)
            {
                this.Amministrazioni = new[]
                {
                    new Amministrazione{ CodiceAmministrazione = codiceAmministrazione.Value}
                };
            }
        }
    }
}