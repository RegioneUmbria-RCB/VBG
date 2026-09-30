namespace VBG.Backend.Protocollo.AppLogic.Core.Prisma.Protocollazione
{
    public interface IProtocollazionePrisma
    {
        Mittente[] GetMittente();
        Destinatario[] GetDestinatario();
        string Flusso { get; }
        string Uo { get; }
        string Smistamento { get; }
    }
}
