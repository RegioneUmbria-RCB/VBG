// -----------------------------------------------------------------------
// <copyright file="ISalvataggioDomandaStrategy.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda
{
    using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
    using System.Threading.Tasks;

    public interface ISalvataggioDomandaStrategy
    {
        DomandaOnline GetById(int idPresentazione);
        Task<DomandaOnline> GetByIdAsync(int idPresentazione);

        void Salva(DomandaOnline domanda, bool aggiornaDataultimaModifica = true);
        ValueTask SalvaAsync(DomandaOnline domanda, bool aggiornaDataultimaModifica = true);

        void Elimina(DomandaOnline domanda);


        byte[] GetAsXml(int idDomanda);
        void ImpostaIdIstanzaOrigine(int idDomanda, int idDomandaOrigine);
    }
}
