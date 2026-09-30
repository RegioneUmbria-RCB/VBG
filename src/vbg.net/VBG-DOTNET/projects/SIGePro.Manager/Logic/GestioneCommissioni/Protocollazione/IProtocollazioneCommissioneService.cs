namespace Init.SIGePro.Manager.Logic.GestioneCommissioni.Protocollazione
{
    public interface IProtocollazioneCommissioneService
    {
        EsitoProtocollazioneCommissione ProtocollaParereEsterno(int codiceIstanza, IRisolviMittenteProtocolloCommissione mittente, IRisolviOggettoProtocolloCommissione oggetto, IRisolviAllegatiProtocolloCommissione allegati);
    }

    public class NullProtocollazioneCommissioneService : IProtocollazioneCommissioneService
    {
        public EsitoProtocollazioneCommissione ProtocollaParereEsterno(int codiceIstanza, IRisolviMittenteProtocolloCommissione mittente, IRisolviOggettoProtocolloCommissione oggetto, IRisolviAllegatiProtocolloCommissione allegati)
        {
            return EsitoProtocollazioneCommissione.Riuscito("", "", "");
        }
    }
}
