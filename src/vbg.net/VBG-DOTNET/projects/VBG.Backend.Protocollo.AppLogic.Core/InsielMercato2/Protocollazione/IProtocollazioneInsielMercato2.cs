using ProtocolloInsielMercatoService2;
using System;

namespace VBG.Backend.Protocollo.AppLogic.Core.InsielMercato2.Protocollazione
{
    public interface IProtocollazioneInsielMercato2
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
