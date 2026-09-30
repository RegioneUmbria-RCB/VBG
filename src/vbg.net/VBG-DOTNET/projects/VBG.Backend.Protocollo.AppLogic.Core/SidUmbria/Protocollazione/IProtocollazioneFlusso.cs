namespace VBG.Backend.Protocollo.AppLogic.Core.SidUmbria.Protocollazione
{
    public interface IProtocollazioneFlusso
    {
        string Flusso { get; }
        corrispondente[] GetCorrispondenti();
    }
}
