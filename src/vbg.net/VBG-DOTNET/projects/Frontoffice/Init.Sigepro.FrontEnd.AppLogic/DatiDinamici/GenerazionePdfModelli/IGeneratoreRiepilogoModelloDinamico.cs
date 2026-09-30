// -----------------------------------------------------------------------
// <copyright file="IGeneratoreRiepilogoModelloDinamico.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli
{
    using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;


    public interface IGeneratoreRiepilogoModelloDinamico
    {
        BinaryFile GeneraRiepilogo(int idModello, string nomeRiepilogo, int indiceMolteplicita);
    }
}
