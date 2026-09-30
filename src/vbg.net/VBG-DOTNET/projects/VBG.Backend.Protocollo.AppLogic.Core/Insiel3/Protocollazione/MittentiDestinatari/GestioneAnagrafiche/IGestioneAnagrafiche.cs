using VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;


namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Protocollazione.MittentiDestinatari.GestioneAnagrafiche
{
    public interface IGestioneAnagrafiche
    {
        void Gestisci(IAnagraficaAmministrazione anagrafica, ProtocolloService srv);
        string Nominativo { get; }
    }
}
