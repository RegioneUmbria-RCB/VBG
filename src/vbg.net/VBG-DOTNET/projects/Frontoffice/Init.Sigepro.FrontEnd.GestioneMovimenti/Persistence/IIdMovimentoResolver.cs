// -----------------------------------------------------------------------
// <copyright file="GestioneMovimentiHttpDataContext.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence
{
    public interface IIdMovimentoResolver
    {
        int IdMovimento { get; }
        void SetIdMovimento(int idMovimento);
    }
}
