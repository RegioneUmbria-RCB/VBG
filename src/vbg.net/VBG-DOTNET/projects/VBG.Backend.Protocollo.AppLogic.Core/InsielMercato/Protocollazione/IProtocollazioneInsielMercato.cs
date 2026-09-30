using ProtocolloInsielMercatoService;
using System;

namespace VBG.Backend.Protocollo.AppLogic.Core.InsielMercato.Protocollazione
{
    public interface IProtocollazioneInsielMercato
    {
        direction1 Flusso { get; }
        sender[] GetMittenti();
        recipient[] GetDestinatari();
        document[] GetAllegati();
        string Registro { get; }
        string CodiceUfficioOperante { get; }
        DateTime? DataSpedizione { get; }
    }
}
