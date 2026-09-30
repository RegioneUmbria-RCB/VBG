using VBG.Backend.Protocollo.AppLogic.Core.ApSystems.Protocollazione.Corrispondenti.Get;
using VBG.Backend.Protocollo.AppLogic.Core.ApSystems.Protocollazione.Corrispondenti.Insert;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;


namespace VBG.Backend.Protocollo.AppLogic.Core.ApSystems.Protocollazione
{
    public class ProtocollazioneFactory
    {
        public static IProtocollazioneApSystems Create(string flusso, IEnumerable<IAnagraficaAmministrazione> anagrafiche, CorrispondentiGetServiceWrapper corrispondentiGetSrv, CorrispondentiInsertServiceWrapper corrispondentiInsertSrv, string username, string uo, VerticalizzazioniWrapper.TipoProtocollazione tipoProtocollazione)
        {
            if (flusso == ProtocolloConstants.COD_ARRIVO)
                return new ProtocollazioneArrivo(anagrafiche, corrispondentiGetSrv, corrispondentiInsertSrv, username, uo);
            else if (flusso == ProtocolloConstants.COD_PARTENZA)
                return new ProtocollazionePartenza(anagrafiche, corrispondentiGetSrv, corrispondentiInsertSrv, username, uo, tipoProtocollazione);
            else if (flusso == ProtocolloConstants.COD_INTERNO)
            {
                if(anagrafiche.Count() == 0)
                    throw new Exception("DESTINATARIO DI UN PROTOCOLLO INTERNO NON PRESENTE");
                var amm = (AmministrazioneService)anagrafiche.First();

                return new ProtocollazioneInterna(uo, amm.Uo, corrispondentiGetSrv);
            }
            else
                throw new Exception(String.Format("FLUSSO {0} NON GESTITO", flusso));
        }
    }
}
