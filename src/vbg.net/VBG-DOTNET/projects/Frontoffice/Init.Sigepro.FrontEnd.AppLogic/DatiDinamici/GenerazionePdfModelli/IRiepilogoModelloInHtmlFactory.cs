// -----------------------------------------------------------------------
// <copyright file="IriepilogoModelloInHtml.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli
{
    public interface IRiepilogoModelloInHtmlFactory
    {
        IRiepilogoModelloInHtml FromDomanda(ISchedeDinamicheDomandaAlRiepilogoService reader, int idModello, int indiceMolteplicita = -1);
        IRiepilogoModelloInHtml FromIdDomandaOnline(int idDomanda, int idModello, int indiceMolteplicita = -1);
    }
}