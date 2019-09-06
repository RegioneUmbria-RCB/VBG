using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Iride2.Services;
using Init.SIGePro.Protocollo.Iride.Configuration;
using Init.SIGePro.Protocollo.Iride2.Proxies;

namespace Init.SIGePro.Protocollo.Iride2.CreaCopie
{
    public class CreaCopieJIride : ICreaCopie
    {
        CreaCopieInfo _info;

        public CreaCopieJIride(CreaCopieInfo info)
        {
            this._info = info;
        }

        public DocumentoOut GeneraCopia()
        {
            try
            {
                this._info.ProtocolloLogs.InfoFormat("INIZIO FUNZIONALITÀ CREACOPIE J-IRIDE, PROTOCOLLO NUMERO: {0}, ANNO: {1}, ID DOCUMENTO SORGENTE: {2}", this._info.NumeroProtocolloSorgente, this._info.AnnoProtocolloSorgente, this._info.IdProtocolloSorgente);
                var documentoOutSorgente = new DocumentoOut();

                if (!String.IsNullOrEmpty(this._info.IdProtocolloSorgente))
                {
                    _info.ProtocolloLogs.InfoFormat("CHIAMATA A LEGGIDOCUMENTO, IDDOCUMENTO (SORGENTE): {0}, OPERATORE: {1}, RUOLO: {2}", this._info.IdProtocolloSorgente, this._info.Operatore, this._info.Ruolo);
                    documentoOutSorgente = _info.ProtocolloIrideService.LeggiDocumento(Convert.ToInt32(this._info.IdProtocolloSorgente), this._info.Operatore, this._info.Ruolo);
                }
                else
                {
                    _info.ProtocolloLogs.InfoFormat("CHIAMATA A LEGGIPROTOCOLLO, PROTOCOLLO NUMERO: {0}, ANNO: {1}, OPERATORE: {2}, RUOLO: {3}", this._info.NumeroProtocolloSorgente, this._info.AnnoProtocolloSorgente, this._info.Operatore, this._info.Ruolo);
                    documentoOutSorgente = _info.ProtocolloIrideService.LeggiProtocollo(Convert.ToInt16(this._info.AnnoProtocolloSorgente), Convert.ToInt32(this._info.NumeroProtocolloSorgente), this._info.Operatore, this._info.Ruolo);
                }

                if (documentoOutSorgente.IdDocumento == 0)
                {
                    throw new Exception($"DOCUMENTO SORGENTE NON TROVATO, MESSAGGIO: {documentoOutSorgente.Messaggio}, ERRORE: {documentoOutSorgente.Errore}");
                }

                var protoInCopia = new ProtocolloIn
                {
                    //NumProt = _info.NumeroProtocolloSorgente,
                    //DataProt = _info.DataProtocolloSorgente.ToString("dd/MM/yyyy"),
                    AggiornaAnagrafiche = _info.Vert.AggiornaAnagrafiche,
                    Classifica = documentoOutSorgente.Classifica,
                    InCaricoA = documentoOutSorgente.InCaricoA,
                    MittenteInterno = documentoOutSorgente.MittenteInterno,
                    MittentiDestinatari = documentoOutSorgente.MittentiDestinatari.Select(x => new MittenteDestinatarioIn
                    {
                        CodiceFiscale = x.PartitaIVA,
                        CognomeNome = x.CognomeNome
                    }).ToArray(),
                    Origine = documentoOutSorgente.Origine,
                    Oggetto = documentoOutSorgente.Oggetto,
                    TipoDocumento = documentoOutSorgente.TipoDocumento,
                    Allegati = documentoOutSorgente.Allegati.Select(x => new AllegatoIn
                    {
                        Commento = x.Commento,
                        ContentType = x.ContentType,
                        Image = x.Image,
                        NomeAllegato = x.NomeAllegato,
                        TipoAllegato = x.TipoAllegato,
                        TipoFile = x.TipoFile
                    }).ToArray(),
                };

                _info.ProtocolloSerializer.Serialize(ProtocolloLogsConstants.InserisciDocumentoCopiaRequest, protoInCopia);
                _info.ProtocolloLogs.Info("CHIAMATA A INSERISCI DOCUMENTO");
                var protoOutCollegato = _info.ProtocolloIrideService.InserisciDocumento(protoInCopia);
                if (!String.IsNullOrEmpty(protoOutCollegato.Errore))
                {
                    throw new Exception($"ERRORE IN FASE DI INSERIMENTO DEL DOCUMENTO {protoOutCollegato.Errore}");
                }

                var collegaDocumentoIn = new CollegaDocumentoIn
                {
                    IdDocCollegante = documentoOutSorgente.IdDocumento,
                    IdDocCollegato = protoOutCollegato.IdDocumento,
                    TipoCollegamento = "COLL_PROT"
                };

                var collegaDocumentoXml = _info.ProtocolloSerializer.Serialize(ProtocolloLogsConstants.CollegaDocumentoRequest, collegaDocumentoIn);
                _info.ProtocolloLogs.InfoFormat("CHIAMATA A COLLEGADOCUMENTO, XML: {0}", collegaDocumentoXml);
                var esitoXml = _info.ProtocolloIrideService.CollegaDocumento(collegaDocumentoXml);
                _info.ProtocolloLogs.InfoFormat("RISPOSTA A CHIAMATA A COLLEGADOCUMENTO, XML: {0}", esitoXml);
                var collegaDocumentoOut = _info.ProtocolloSerializer.Deserialize<EsitoOperazione>(esitoXml);

                if (!collegaDocumentoOut.Esito)
                {
                    throw new Exception($"ERRORE IN FASE DI COLLEGAMENTO DEL DOCUMENTO {collegaDocumentoIn.IdDocCollegato}, {collegaDocumentoOut.Errore}");
                }

                _info.ProtocolloLogs.InfoFormat("CHIAMATA A LEGGI DOCUMENTO DEL NUOVO DOCUMENTO COLLEGATO CON ID: {0}", protoOutCollegato.IdDocumento);
                var retVal = _info.ProtocolloIrideService.LeggiDocumento(protoOutCollegato.IdDocumento, _info.Operatore, _info.Ruolo);
                _info.ProtocolloLogs.InfoFormat("CHIAMATA A LEGGI DOCUMENTO COLLEGATO, ID {0}, AVVENUTA CORRETTAMENTE", protoOutCollegato.IdDocumento);
                
                return retVal;
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE DURANTE LA GENERAZIONE DELLA COPIA DEL DOCUMENTO SORGENTE ID: {this._info.IdProtocolloSorgente}, PROTOCOLLO NUMERO: {this._info.NumeroProtocolloSorgente}, ANNO: {this._info.AnnoProtocolloSorgente}, ERRORE: {ex.Message}", ex);
            }
        }
    }
}
