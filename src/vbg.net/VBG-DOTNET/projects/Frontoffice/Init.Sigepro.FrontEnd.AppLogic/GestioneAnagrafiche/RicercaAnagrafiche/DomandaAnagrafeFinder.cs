// -----------------------------------------------------------------------
// <copyright file="DomandaAnagrafeFinder.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche.RicercaAnagrafiche
{
    using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
    using System.Threading.Tasks;

    internal class DomandaAnagrafeFinder : AbstractAnagrafeFinder
    {
        private readonly IAnagraficheReadInterface _anagraficheReadInterface;

        public DomandaAnagrafeFinder(IAnagraficheReadInterface anagraficheReadInterface)
        {
            this._anagraficheReadInterface = anagraficheReadInterface;
        }


        internal override AnagraficaDomanda Find(TipoPersonaEnum tipoPersona, string codiceFiscalePartitaIva)
        {
            var anagraficaEsistente = this._anagraficheReadInterface.FindByRiferimentiSoggetto(tipoPersona, codiceFiscalePartitaIva);

            if (anagraficaEsistente != null)
                return anagraficaEsistente.DuplicaRimuovendoIlTipoSoggetto();

            return null;
        }

        internal override Task<AnagraficaDomanda> FindAsync(TipoPersonaEnum tipoPersona, string codiceFiscalePartitaIva)
        {
            return Task.FromResult(this.Find(tipoPersona, codiceFiscalePartitaIva));
        }
    }
}
