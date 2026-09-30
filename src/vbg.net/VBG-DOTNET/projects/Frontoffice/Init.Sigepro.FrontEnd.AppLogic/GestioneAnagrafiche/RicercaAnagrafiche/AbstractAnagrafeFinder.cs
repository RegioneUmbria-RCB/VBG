// -----------------------------------------------------------------------
// <copyright file="AbstractAnagrafeFinder.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche.RicercaAnagrafiche
{
    using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
    using System.Threading.Tasks;

    internal abstract class AbstractAnagrafeFinder
    {
        internal abstract AnagraficaDomanda Find(TipoPersonaEnum tipoPersona, string codiceFiscalePartitaIva);
        internal abstract Task<AnagraficaDomanda> FindAsync(TipoPersonaEnum tipoPersona, string codiceFiscalePartitaIva);
    }
}
