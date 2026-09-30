using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;

namespace Init.Sigepro.FrontEnd.AppLogic.InvioDomanda
{


    public interface IInvioDomandaStrategy
    {
        IInvioIstanzaResult Send(DomandaOnline domanda, string pecDestinatario);
    }
}
