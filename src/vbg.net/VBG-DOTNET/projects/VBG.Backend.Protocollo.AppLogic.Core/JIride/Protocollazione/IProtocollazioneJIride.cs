namespace VBG.Backend.Protocollo.AppLogic.Core.JIride.Protocollazione
{
    public interface IProtocollazioneJIride
    {
        ProtocolloOutXml InserisciProtocollo(ProtocolloInXml protocolloIn);
        ProtocolloOutXml InserisciDocumento(ProtocolloInXml protocolloIn);
        string LeggiAnagraficaPerCodiceFiscale(string codiceFiscale, string operatore, string ruolo);
    }
}
