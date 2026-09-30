using Init.SIGePro.Protocollo.AcarisDocumentServicePort;
using System;
using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Allegati;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Client;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Document.CSIPiemonte.Annotazioni;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Entity;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Document.CSIPiemonte.Arrivo
{
    public class DocumentPrincipaleArrivoNonFirmato : DocumentoArchivisticoIRC, IDocument
    {
        private class Constants
        {
            public static string DocumentoFisicoDescrizione = "documento fisico";
        }
        private readonly IDocumentResolver _resolver;
        public InfoRichiestaCreazione Documento { get; internal set; }

        public List<Annotazione> Annotazioni { get; internal set; } = new List<Annotazione>();

        public DocumentPrincipaleArrivoNonFirmato(IProtocolloSerializer serializer, IDocumentResolver resolver, IdFolder idFolder, AllegatoAcaris allegato)
        {
            this._resolver = resolver;

            var mimeType = allegato.MimeType.FromReflectedXmlValue<enumMimeTypeType>();

            this.Annotazioni.Add(new DocumentoArt65().ToAnnotazione());
            this.Annotazioni.Add(new FirmatoElettronicamente(this._resolver.UtenteEsteso).ToAnnotazione());

            if (allegato.Metadati.Any(x => x.Chiave == "CONVERTITO_DA") && allegato.Metadati.Any(x => x.Chiave == "CONVERTITO_IN"))
            {
                this.Annotazioni.Add(new DocumentoConvertito(
                    allegato.Metadati.First(x => x.Chiave == "CONVERTITO_DA").Valore,
                    allegato.Metadati.First(x => x.Chiave == "CONVERTITO_IN").Valore
                    ).ToAnnotazione());
            }

            var managementWSClient = new ManagementWSClient(this._resolver.ManagementWsUrl, this._resolver.AccessToken);

            this.Documento = new DocumentoArchivisticoIRC
            {
                tipoDocumento = enumTipoDocumentoArchivistico.DocumentoSemplice,
                propertiesClassificazione = new ClassificazionePropertiesType
                {
                    copiaCartacea = false,
                    cartaceo = false,
                },
                propertiesDocumento = new DocumentoSemplicePropertiesType
                {
                    analogico = false,
                    applicativoAlimentante = this._resolver.Configurazione.ApplicativoAlimentante,
                    autoreFisico = this._resolver.MittentiPF.ToArray(),
                    autoreGiuridico = this._resolver.MittentiPG.ToArray(),
                    composizione = enumDocPrimarioType.DocumentoSingolo,
                    contentStreamFilename = allegato.NomeFile,
                    contentStreamMimeType = mimeType,
                    contentStreamLength = allegato.ContentLenght,
                    datiPersonali = true,
                    datiRiservati = false,
                    datiSensibili = false,
                    dataCreazione = DateTime.Now,
                    dataDocCronica = DateTime.Now,
                    dataDocCronicaSpecified = true,
                    daConservarePrimaDelSpecified = false,
                    daConservareDopoIlSpecified = false,
                    definitivo = true,
                    destinatarioGiuridico = this._resolver.DestinatariPG.ToArray(),
                    docAutenticato = false,
                    docAutenticatoCopiaAutentica = false,
                    docAutenticatoFirmaAutenticata = false,
                    docConAllegati = this._resolver.NumeroAllegati > 0,
                    idStatoDiEfficacia = new IdStatoDiEfficaciaType { value = this.GetStatoEfficaciaDefault(serializer) },
                    idVitalRecordCode = new IdVitalRecordCodeType { value = new IdVitalRecordCodeMedio(serializer, managementWSClient, this._resolver.RepositoryId).Id },
                    modificabile = false,
                    multiplo = false,
                    objectTypeId = enumArchiveObjectType.DocumentoSemplicePropertiesType,
                    originatore = new string[] { this._resolver.Configurazione.ApplicativoAlimentante },
                    oggetto = this._resolver.OggettoDocumentoPrincipale,
                    origineInterna = false,
                    rappresentazioneDigitale = true,
                    registrato = true,
                    tipoDocFisico = enumTipoDocumentoType.Semplice,
                },
                parentFolderId = new ObjectIdType { value = idFolder.IdAcaris },
                gruppoAllegati = this._resolver.NumeroAllegati > 0 ?
                new GruppoAllegatiPropertiesType
                {
                    numeroAllegati = this._resolver.NumeroAllegati,
                    dataInizio = DateTime.Now
                } : null,
                documentiFisici = new DocumentoFisicoIRC[]
                {
                    new DocumentoFisicoIRC
                    {
                        propertiesDocumentoFisico = new DocumentoFisicoPropertiesType
                        {
                            descrizione = Constants.DocumentoFisicoDescrizione,
                            dataMemorizzazione = DateTime.Now
                        },
                        contenutiFisici = new ContenutoFisicoIRC[]
                        {
                            new ContenutoFisicoIRC
                            {
                                propertiesContenutoFisico = new ContenutoFisicoPropertiesType
                                {
                                    contentStreamLength = allegato.ContentLenght,
                                    numeroVersione = 0,
                                    workingCopy = false,
                                    sbustamento = false,
                                    modificabile = false,
                                    docPrimario = false  //viene sovrascritto da tipo = AcarisDocumentServicePort.enumStreamId.primary,
                                },
                                stream = new acarisContentStreamType
                                {
                                    filename = allegato.NomeFile,
                                    streamMTOM = allegato.Content,
                                    mimeType = mimeType,
                                    mimeTypeSpecified = true,
                                },
                                tipo = enumStreamId.primary,
                                azioniVerificaFirma = new StepErrorAction[]
                                {
                                    new StepErrorAction
                                    {
                                        action = enumStepErrorAction.insert,
                                        step = 0
                                    }
                                }
                            }
                        }
                    }
                }
            };
        }

        private int GetStatoEfficaciaDefault(IProtocolloSerializer serializer)
        {
            var client = new ObjectWSClient(this._resolver.ObjectWSUrl, this._resolver.AccessToken);
            return new IdStatoEfficaciaPerfettoEdEfficaceNonFirmato(serializer, client, this._resolver.RepositoryId, this._resolver.PrincipalId).DbKey;
        }
    }
}
