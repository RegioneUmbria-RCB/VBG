// -----------------------------------------------------------------------
// <copyright file="NuovaAnagraficaFinder.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche.RicercaAnagrafiche
{
    using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
    using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
    using System.Threading.Tasks;


    /// <summary>
    /// Finder che crea una nuova anagrafica a partire dai dati del codice fiscale passato
    /// </summary>
    internal class NuovaAnagraficaFinder : AbstractAnagrafeFinder
    {
        private readonly DomandaOnline _domanda;

        public NuovaAnagraficaFinder(DomandaOnline domanda)
        {
            this._domanda = domanda;
        }

        internal override AnagraficaDomanda Find(TipoPersonaEnum tipoPersona, string codiceFiscalePartitaIva)
        {
            return AnagraficaDomanda.DaCodiceFiscaleTipoPersona(tipoPersona, codiceFiscalePartitaIva);
        }

        internal override Task<AnagraficaDomanda> FindAsync(TipoPersonaEnum tipoPersona, string codiceFiscalePartitaIva)
        {
            return Task.FromResult(this.Find(tipoPersona, codiceFiscalePartitaIva));
        }
    }
}
