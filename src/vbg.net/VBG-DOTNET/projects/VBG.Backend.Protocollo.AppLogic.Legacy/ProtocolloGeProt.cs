using Init.SIGePro.Manager;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Legacy.GeProt.Fascicolazione;
using VBG.Backend.Protocollo.AppLogic.Legacy.GeProt.Protocollazione;
using VBG.Backend.Protocollo.AppLogic.Legacy.GeProt.Services;
using VBG.Backend.Protocollo.AppLogic.Legacy.GeProt.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Factories;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Managers;
using VBG.Backend.Protocollo.AppLogic.Shared.Validation;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.Verticalizzazioni.Legacy;

namespace VBG.Backend.Protocollo.AppLogic.Legacy
{
    public class PROTOCOLLO_GEPROT : ProtocolloBase
    {
        IVerticalizzazioniFactory _verticalizzazioniFactory;

        public PROTOCOLLO_GEPROT(IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            _verticalizzazioniFactory = verticalizzazioniFactory;
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {
            VerticalizzazioniConfiguration vert = null;
            ProtocollazioneService service = null;

            try
            {
                vert = new VerticalizzazioniConfiguration(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloGeprot>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune), this._protocolloLogs);
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

                var response = service.Protocolla(segnaturaSerializzata, protoIn.RecuperaAllegati().ToList());

                var adapterOut = new ResponseAdapter(response);

                try
                {
                    var datiProto = DatiProtocolloInsertFactory.Create(protoIn);
                    var datiFascicolo = new DatiFascicolazioneConfiguration(vert, response, protoIn.Classifica, datiProto.Amministrazione.PROT_UO, protoIn.Oggetto, service, this.DatiProtocollo.TipoAmbito);
                    this.FascicolaProtocollo(datiFascicolo);
                }
                catch (Exception ex)
                {
                    _protocolloLogs.WarnFormat("ERRORE NELLA FASCICOLAZIONE, {0}", ex.Message);
                }

                if (protoIn.Flusso == ProtocolloConstants.COD_PARTENZA && vert.InvioPec)
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

                return adapterOut.Adatta(_protocolloLogs);
            }
            finally
            {
                if (service != null)
                {
                    service.Logout();
                }
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
