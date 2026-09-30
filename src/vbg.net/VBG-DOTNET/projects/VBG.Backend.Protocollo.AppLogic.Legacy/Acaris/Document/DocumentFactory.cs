using Init.SIGePro.Protocollo.AcarisDocumentServicePort;
using System;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Allegati;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Document.CSIPiemonte.Arrivo;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Document.CSIPiemonte.Partenza;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Entity;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Document
{
    public class DocumentFactory
    {
        private static class Constants
        {
            public const string Installazione = "CSIPIEMONTE";
        }
        private readonly IProtocolloSerializer _serializer;
        private readonly IdFolder _idFolder;
        private readonly IDocumentResolver _resolver;
        private readonly ObjectIdType _classificazione;

        public DocumentFactory(IProtocolloSerializer serializer, IDocumentResolver resolver, IdFolder idFolder)
        {
            this._resolver = resolver;
            this._idFolder = idFolder;
            this._serializer = serializer;
        }

        public DocumentFactory(IProtocolloSerializer serializer, IDocumentResolver resolver, ObjectIdType classificazione)
        {
            this._resolver = resolver;
            this._classificazione = classificazione;
            this._serializer = serializer;
        }

        //se dovessero presentarsi altri clienti che usano questo protocollo, va fatto refactory
        public IDocument GetDocumento(AllegatoAcaris allegato, bool principale)
        {
            var firmatoDigitalmente = allegato.IsFirmato;

            var implementazione = this.Implementazione(principale, firmatoDigitalmente, this._resolver.Flusso);

            switch (implementazione)
            {
                case "CSIPIEMONTE_A__P__F_":
                    return new DocumentPrincipaleArrivoFirmato(this._serializer, this._resolver, this._idFolder, allegato);
                case "CSIPIEMONTE_A__P_":
                    return new DocumentPrincipaleArrivoNonFirmato(this._serializer, this._resolver, this._idFolder, allegato);
                case "CSIPIEMONTE_A__F_":
                    return new DocumentSecondarioArrivoFirmato(this._serializer,this._resolver, this._classificazione, allegato);
                case "CSIPIEMONTE_A_":
                    return new DocumentSecondarioArrivoNonFirmato(this._serializer, this._resolver, this._classificazione, allegato);
                case "CSIPIEMONTE_P__P__F_":
                    return new DocumentPrincipalePartenzaFirmato(this._serializer, this._resolver, this._idFolder, allegato);
                case "CSIPIEMONTE_P__P_":
                    return new DocumentPrincipalePartenzaNonFirmato(this._serializer, this._resolver, this._idFolder, allegato);
                case "CSIPIEMONTE_P__F_":
                    return new DocumentSecondarioPartenzaFirmato(this._serializer, this._resolver, this._classificazione, allegato);
                case "CSIPIEMONTE_P_":
                    return new DocumentSecondarioPartenzaNonFirmato(this._serializer, this._resolver, this._classificazione, allegato);
                default:
                    throw new Exception($"Implementazione {implementazione} non ancora sviluppata");
            }
        }

        public string Implementazione(bool principale, bool firmato, string flusso)
        {
            var tipoDocumento = Constants.Installazione;
            tipoDocumento += $"_{flusso}_";
            tipoDocumento += (principale) ? "_P_" : "";
            tipoDocumento += (firmato) ? "_F_" : "";

            return tipoDocumento;
        }
    }
}
