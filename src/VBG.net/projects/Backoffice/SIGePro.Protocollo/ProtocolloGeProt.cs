using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.GeProt.Verticalizzazioni;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Validation;
using Init.SIGePro.Protocollo.GeProt.Fascicolazione;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.GeProt.Services;
using Init.SIGePro.Protocollo.GeProt.Protocollazione;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo
{
    public class PROTOCOLLO_GEPROT : ProtocolloBase
    {
        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {
            VerticalizzazioniConfiguration vert = null;
            ProtocollazioneService service = null;

            try
            {
                vert = new VerticalizzazioniConfiguration(new VerticalizzazioneProtocolloGeprot(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune), _protocolloLogs);
                service = new ProtocollazioneService(_protocolloLogs, _protocolloSerializer, vert.Url);

                service.Login(vert.Operatore, vert.Password);

                string descrizioneTipoDocumento = "";
                if (!String.IsNullOrEmpty(protoIn.TipoDocumento))
                {
                    var tipoDocMgr = new ProtocolloTipiDocumentoMgr(this.DatiProtocollo.Db);
                    var tipoDoc = tipoDocMgr.GetById(this.DatiProtocollo.IdComune, protoIn.TipoDocumento, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune);
                    if (tipoDoc != null)
                        descrizioneTipoDocumento = tipoDoc.Descrizione;
                }

                var segnaturaAdapter = new SegnaturaAdapter(vert, protoIn, _protocolloLogs);

                var segnatura = segnaturaAdapter.Adatta(this.Operatore, descrizioneTipoDocumento, vert.InvioPec);
                string segnaturaSerializzata = _protocolloSerializer.Serialize(ProtocolloLogsConstants.SegnaturaXmlFileName, segnatura, ProtocolloValidation.TipiValidazione.DTD_GEPROT, vert.ProtoDocType, false);

                var response = service.Protocolla(segnaturaSerializzata, protoIn.Allegati);

                var adapterOut = new ResponseAdapter(response);

                try
                {
                    var datiProto = DatiProtocolloInsertFactory.Create(protoIn);
                    var datiFascicolo = new DatiFascicolazioneConfiguration(vert, response, protoIn.Classifica, datiProto.Amministrazione.PROT_UO, protoIn.Oggetto, service, this.DatiProtocollo.TipoAmbito);
                    this.FascicolaProtocollo(datiFascicolo);
                }
                catch(Exception ex)
                {
                    _protocolloLogs.WarnFormat("ERRORE NELLA FASCICOLAZIONE, {0}", ex.Message);
                }

                if(protoIn.Flusso == ProtocolloConstants.COD_PARTENZA && vert.InvioPec)
                {
                    try
                    {
                        var dt = DateTime.Parse(response[4]);
                        var requestPec = new string[] { vert.CodiceAmministrazione, vert.CodiceAoo, dt.ToString("yyyy"), response[3] };
                        service.InviaPec(requestPec);
                    }
                    catch (Exception ex)
                    {
                        _protocolloLogs.WarnFormat("ERRORE GENERATO DURANTE L'INVIO DELLA PEC, {0}", ex.Message);
                    }
                }

                var retVal = adapterOut.Adatta(_protocolloLogs);
                return retVal;
            }
            finally
            {
                if(service != null)
                service.Logout();
            }
        }
        
        public void FascicolaProtocollo(DatiFascicolazioneConfiguration datiFascicolo)
        {
            var movimentiMgr = new MovimentiMgr(DatiProtocollo.Db);
            var movimentiProtocollati = movimentiMgr.GetMovimentiProtocollati(this.DatiProtocollo.IdComune, this.DatiProtocollo.CodiceIstanza);

            var f = FascicolazioneFactory.Create(datiFascicolo, _protocolloLogs, this.DatiProtocollo.Istanza, movimentiProtocollati);
            f.Fascicola();
        }
    }
}
