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
    public class CreaCopieIride : ICreaCopie
    {
        CreaCopieInfo _info;

        public CreaCopieIride(CreaCopieInfo info)
        {
            this._info = info;
        }

        public DocumentoOut GeneraCopia()
        {
            try
            {

                this._info.ProtocolloLogs.Debug("Inizio funzionalità CreaCopie Iride");
                var documentoOut = new DocumentoOut();

                if (!String.IsNullOrEmpty(this._info.IdProtocolloSorgente))
                {
                    documentoOut = _info.ProtocolloIrideService.LeggiDocumento(Convert.ToInt32(this._info.IdProtocolloSorgente), this._info.Operatore, this._info.Ruolo);
                }
                else
                {
                    documentoOut = _info.ProtocolloIrideService.LeggiProtocollo(Convert.ToInt16(this._info.AnnoProtocolloSorgente), Convert.ToInt32(this._info.NumeroProtocolloSorgente), this._info.Operatore, this._info.Ruolo);
                }

                if (documentoOut.IdDocumento == 0)
                {
                    throw new Exception($"DOCUMENTO SORGENTE NON TROVATO, MESSAGGIO: {documentoOut.Messaggio}, ERRORE: {documentoOut.Errore}");
                }

                var creaCopie = new CreaCopieService(this._info.Vert.Url, this._info.ProxyAddress, this._info.ProtocolloLogs, this._info.ProtocolloSerializer);
                var creaCopieOut = creaCopie.CreaCopie(uo: this._info.Uo,
                                                        ruolo: this._info.Ruolo,
                                                        idDocumento: documentoOut.IdDocumento,
                                                        annoProtocollo: documentoOut.AnnoProtocollo.ToString(),
                                                        numeroProtocollo: documentoOut.NumeroProtocollo.ToString(),
                                                        operatoreIride: this._info.Operatore,
                                                        codiceEnte: this._info.Vert.CodiceAmministrazione);

                if ((creaCopieOut.CopieCreate == null) || (creaCopieOut.CopieCreate.Length != 1))
                {
                    throw new Exception($"MESSAGGIO: {creaCopieOut.Messaggio}, ERRORE: {creaCopieOut.Errore}");
                }

                var retVal = _info.ProtocolloIrideService.LeggiDocumento(creaCopieOut.CopieCreate[0].IdDocumentoCopia, "", "");
                return retVal;
                
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE DURANTE LA GENERAZIONE DELLA COPIA DEL DOCUMENTO SORGENTE ID: {this._info.IdProtocolloSorgente}, PROTOCOLLO NUMERO: {this._info.NumeroProtocolloSorgente}, ANNO: {this._info.AnnoProtocolloSorgente}, ERRORE: {ex.Message}", ex);
            }
        }
    }
}
