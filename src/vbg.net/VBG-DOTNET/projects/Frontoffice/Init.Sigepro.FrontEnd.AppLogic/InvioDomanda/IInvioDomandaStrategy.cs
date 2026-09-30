// -----------------------------------------------------------------------
// <copyright file="IInvioDomandaStrategy.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.InvioDomanda
{
    using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
    using System.Threading.Tasks;

    public interface IInvioDomandaStrategy
    {
        InvioIstanzaResult Send(DomandaOnline domanda, string pecDestinatario);
        ValueTask<InvioIstanzaResult> SendAsync(DomandaOnline domanda, string pecDestinatario, SportelloStcDestinatario? sportelloDestinatario = null, bool verificaEsistenzaPraticaNelBackoffice = true);
    }
}
