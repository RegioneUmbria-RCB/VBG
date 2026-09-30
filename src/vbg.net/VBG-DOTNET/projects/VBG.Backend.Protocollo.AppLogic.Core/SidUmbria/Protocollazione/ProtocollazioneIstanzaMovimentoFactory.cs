using VBG.Backend.Protocollo.AppLogic.Shared.Services;

namespace VBG.Backend.Protocollo.AppLogic.Core.SidUmbria.Protocollazione
{
    public class ProtocollazioneIstanzaMovimentoFactory
    {
        public static IProtocollazioneIstanzaMovimento Create(ResolveDatiProtocollazioneService datiIstanzaMovimentoService)
        {
            if (datiIstanzaMovimentoService.Movimento != null)
                return new ProtocollazioneMovimento(datiIstanzaMovimentoService.Movimento);
            else if (datiIstanzaMovimentoService.Istanza != null)
                return new ProtocollazioneIstanza(datiIstanzaMovimentoService.Istanza);
            else
                return new ProtocollazioneDefault();

        }

    }
}
