using VBG.Shared.Infrastructure.ServiceModel;
using ProtocolloIride2Service;
using VBG.Backend.Protocollo.AppLogic.Core.Iride2.Services;


namespace VBG.Backend.Protocollo.AppLogic.Core.Iride2.CreaCopie
{
    public class CreaCopieIride : ICreaCopie
    {
        CreaCopieInfo _info;
        IBindingFactory _bindingFactory;

        public CreaCopieIride(CreaCopieInfo info, IBindingFactory bindingFactory)
        {
            this._info = info;
            this._bindingFactory = bindingFactory;
        }

        public DocumentoOut GeneraCopia()
        {
            try
            {

                this._info.ProtocolloLogs.Debug("Inizio funzionalità CreaCopie Iride");
                var documentoOut = new DocumentoOut();

                if (!String.IsNullOrEmpty(this._info.IdProtocolloSorgente))
                {
                    documentoOut = _info.ProtocolloIrideService.LeggiDocumento(Convert.ToInt32(this._info.IdProtocolloSorgente)); //TODO qui c'erano this._info.Operatore, this._info.Ruolo... vedi prossimo
                }
                else
                {
                    documentoOut = _info.ProtocolloIrideService.LeggiProtocollo(Convert.ToInt16(this._info.AnnoProtocolloSorgente), Convert.ToInt32(this._info.NumeroProtocolloSorgente)); //TODO anche qui c'erano this._info.Operatore, this._info.Ruolo... vedi prossimo
                }

                if (documentoOut.IdDocumento == 0)
                {
                    throw new Exception($"DOCUMENTO SORGENTE NON TROVATO, MESSAGGIO: {documentoOut.Messaggio}, ERRORE: {documentoOut.Errore}");
                }

                var creaCopie = new CreaCopieService(this._info.Vert.Url, this._info.ProxyAddress, this._info.ProtocolloLogs, this._info.ProtocolloSerializer, _bindingFactory);
                var creaCopieOut = creaCopie.CreaCopie(uo: this._info.Uo,
                                                        ruolo: this._info.Ruolo,
                                                        idDocumento: documentoOut.IdDocumento,
                                                        annoProtocollo: documentoOut.AnnoProtocollo.ToString(),
                                                        numeroProtocollo: documentoOut.NumeroProtocollo.ToString(),
                                                        operatoreIride: this._info.Operatore,
                                                        codiceEnte: this._info.Vert.CodiceAmministrazione);

                if ((creaCopieOut.CopieCreate == null) || (creaCopieOut.CopieCreate.Count != 1))
                {
                    throw new Exception($"MESSAGGIO: {creaCopieOut.Messaggio}, ERRORE: {creaCopieOut.Errore}");
                }

                var retVal = _info.ProtocolloIrideService.LeggiDocumento(creaCopieOut.CopieCreate[0].IdDocumentoCopia); //TODO qui invece erano  "", ""   quindi da capire se posso passare anche qui this._info.Operatore, this._info.Ruolo altrimenti va modificato il metodo LeggiDocumento
                return retVal;
                
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE DURANTE LA GENERAZIONE DELLA COPIA DEL DOCUMENTO SORGENTE ID: {this._info.IdProtocolloSorgente}, PROTOCOLLO NUMERO: {this._info.NumeroProtocolloSorgente}, ANNO: {this._info.AnnoProtocolloSorgente}, ERRORE: {ex.Message}", ex);
            }
        }
    }
}
