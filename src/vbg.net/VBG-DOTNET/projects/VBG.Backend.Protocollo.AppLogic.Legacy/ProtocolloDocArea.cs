using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocArea;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Adapters;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Builders;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Configurations;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Factories;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.LeggiProtocollo;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.PEC;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Factories;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.Verticalizzazioni.Legacy;

namespace VBG.Backend.Protocollo.AppLogic.Legacy
{
    public class PROTOCOLLO_DOCAREA : ProtocolloBase
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;

        public PROTOCOLLO_DOCAREA(IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
        }

        public override void InizializzaProtocolloBase(ResolveDatiProtocollazioneService datiProtocolloService)
        {
            base.InizializzaProtocolloBase(datiProtocolloService);
            base._protocolloSerializer = new DocAreaSerializer(this._protocolloLogs, this._protocolloValidation);
        }

        public PROTOCOLLO_DOCAREA()
        {

        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {
            var vertParams = new DocAreaVerticalizzazioneParametriAdapter(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloDocarea>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            DateTime? dataRicevimento = DateTime.Now;
            string domicilioElettronico = "";

            //gli allegati passati tramite protoIn sono diventati immutabili per cui, quando c'è la necessità di modificare tale lista
            //va introdotta una variabile interna e utilizzata quella
            var allegati = protoIn.RecuperaAllegati().ToList();

            if (this.DatiProtocollo.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA)
            {
                dataRicevimento = this.DatiProtocollo.Istanza.DATA.Value;
                domicilioElettronico = this.DatiProtocollo.Istanza.DOMICILIO_ELETTRONICO;
            }

            var conf = new DocAreaSegnaturaParamConfiguration(vertParams, Operatore, protoIn, domicilioElettronico, dataRicevimento.Value, this.Provenienza, this.TipoInserimento);

            var protoSrv = ProtocollazioneServiceWrapperFactory.Create(conf, this.Anagrafiche, _protocolloLogs, _protocolloSerializer);
            string token = protoSrv.Login(vertParams.Codiceente, vertParams.Username, vertParams.Password);

            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);

            var segnaturaBuilder = new DocAreaSegnaturaBuilder(datiProto, conf, _protocolloLogs, _protocolloSerializer, allegati);

            if (vertParams.InviaSegnatura && !allegati.Any())
                segnaturaBuilder.CreaSegnaturaFittizia();

            if (vertParams.InviaAllMovAvvio && !allegati.Any() && this.DatiProtocollo.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA)
                protoSrv.InserisciAllegatiDaMovimentoAvvio(this.DatiProtocollo.Istanza, this.DatiProtocollo.Db, DatiProtocollo.IdComune, allegati);

            protoSrv.InserisciAllegati(allegati, token, vertParams.Username, vertParams.InviaNomeFileAttachment, vertParams.IsAbilitatoWarningVerificaFirma);
            segnaturaBuilder.SerializzaSegnatura();

            var response = protoSrv.Protocollazione(vertParams.Username, token);

            if (vertParams.GestionePec && protoIn.Flusso == "P")
            {
                try
                {
                    var factory = PECFactory.Create(this.Anagrafiche, vertParams.UtenteCreazionePec, vertParams.TipoFornitore, _protocolloLogs, _protocolloSerializer, vertParams.Username, vertParams.Password);
                    if (factory != null)
                    {
                        factory.InviaPec(vertParams.UrlPec, response);
                    }
                }
                catch (Exception ex)
                {
                    _protocolloLogs.WarnFormat("ERRORE GENERATO DURANTE L'INVIO DELLA PEC, ERRORE: {0}", ex.Message);
                }
            }

            return DocAreaProtocolloInsertOutputAdapter.Adatta(response, _protocolloLogs);
        }

        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {

            this._protocolloLogs.Info($"Inizio chiamata a LeggiProtocollo({leggiProtocolloRequest.IdProtocollo}, {leggiProtocolloRequest.AnnoProtocollo}, {leggiProtocolloRequest.NumeroProtocollo})");

            if (string.IsNullOrEmpty(leggiProtocolloRequest.AnnoProtocollo))
            {
                throw new ArgumentException($"'{nameof(leggiProtocolloRequest.AnnoProtocollo)}' non può essere Null o vuoto", nameof(leggiProtocolloRequest.AnnoProtocollo));
            }

            if (string.IsNullOrEmpty(leggiProtocolloRequest.NumeroProtocollo))
            {
                throw new ArgumentException($"'{nameof(leggiProtocolloRequest.NumeroProtocollo)}' non può essere Null o vuoto", nameof(leggiProtocolloRequest.NumeroProtocollo));
            }


            this._protocolloLogs.Info($"Inizio recupero parametri dalla verticalizzazione");

            var vertParams = new DocAreaVerticalizzazioneParametriAdapter(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloDocarea>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            this._protocolloLogs.Info($"Fine recupero parametri dalla verticalizzazione");

            var service = new LeggiProtocolloFactory(vertParams.TipoFornitore, vertParams.UrlLeggiProtocollo, vertParams.Username, vertParams.Password, this._protocolloSerializer).GetService();

            this._protocolloLogs.Info($"Inizio lettura del protocollo");

            var retVal = service.Leggi(Convert.ToInt32(leggiProtocolloRequest.AnnoProtocollo), Convert.ToInt32(leggiProtocolloRequest.NumeroProtocollo), null);

            this._protocolloLogs.Info($"Fine lettura del protocollo");

            this._protocolloLogs.Info($"Fine chiamata a LeggiProtocollo({leggiProtocolloRequest.IdProtocollo}, {leggiProtocolloRequest.AnnoProtocollo}, {leggiProtocolloRequest.NumeroProtocollo})");

            return new List<DatiProtocolloLettoResponseType>() { retVal };
        }

        public override AllegatoResponseType LeggiAllegato()
        {
            this._protocolloLogs.Info($"Inizio chiamata a LeggiAllegato({IdAllegato})");

            this._protocolloLogs.Info($"Inizio recupero parametri dalla verticalizzazione");

            var vertParams = new DocAreaVerticalizzazioneParametriAdapter(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloDocarea>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

            this._protocolloLogs.Info($"Fine recupero parametri dalla verticalizzazione");

            var service = new LeggiAllegatoFactory(vertParams.TipoFornitore, vertParams.UrlDownloadAllegato, vertParams.Username, vertParams.Password, this._protocolloSerializer).GetService();

            this._protocolloLogs.Info($"Inizio download allegato");

            AllegatoResponseType allegato = LeggiAllegatoDaLeggiProtocollo();

            allegato.Image = service.Download(allegato.IDBase);

            this._protocolloLogs.Info($"Fine download allegato");

            this._protocolloLogs.Info($"Inizio chiamata a LeggiAllegato({IdAllegato})");

            return allegato;
        }
    }
}

