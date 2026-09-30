namespace VBG.Pagamenti.NodoPagamenti
{
    public interface IEstremiPosizioneDebitoriaClient
    {
        string IdPosizioneDebitoria { get; }
        string RiferimentoClient { get; }
        string IUV { get; }
        string CodiceComune { get; }

        RiferimentoPosizioneDebitoriaType ToRiferimentoPosizioneDebitoriaType();
    }

    public interface IEstremiPosizioneDebitoriaServer
    {
        string IdPosizioneDebitoria { get; }
        string RiferimentoClient { get; }
        string UuidNodoPagamenti { get; }
        string IUV { get; }
        string CodiceComune { get; }

    }
}
