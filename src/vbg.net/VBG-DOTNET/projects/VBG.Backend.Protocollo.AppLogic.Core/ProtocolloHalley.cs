using Init.SIGePro.Data;
using VBG.Backend.Protocollo.Verticalizzazioni.Core;
using System;
using System.IO;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Core.Halley.Adapters;
using VBG.Backend.Protocollo.AppLogic.Core.Halley.Builders;
using VBG.Backend.Protocollo.AppLogic.Core.Halley.Configurations;
using VBG.Backend.Protocollo.AppLogic.Core.Halley.Factories;
using VBG.Backend.Protocollo.AppLogic.Core.Halley.ImpresaInUnGiorno;
using VBG.Backend.Protocollo.AppLogic.Core.Halley.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Core.Halley.Services;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Factories;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using SIGePro.Manager.VerticalizzazioniBase;
using VBG.Shared.Infrastructure.ServiceModel;

namespace VBG.Backend.Protocollo.AppLogic.Core
{
    public class PROTOCOLLO_HALLEY : ProtocolloBase
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly IBindingFactory _bindingFactory;

        public PROTOCOLLO_HALLEY(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._bindingFactory = bindingFactory;
        }

        public override ListaTipiClassificaType GetClassifiche()
        {
            var vertParams = new HalleyVerticalizzazioneParametriAdapter(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloHalley>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

            if (!vertParams.UsaWsClassifiche)
                return base.GetClassifiche();
            else
            {
                try
                {
                    var serviceClassifiche = new HalleyDizionarioService(_protocolloLogs, vertParams.UrlWsDizionario, ProxyAddress, this._bindingFactory);
                    var token = serviceClassifiche.Login(vertParams.Codiceente, vertParams.Username, vertParams.Password);
                    var response = serviceClassifiche.GetClassifiche(vertParams.Username, token);

                    var adapter = new HalleyClassificheOutputAdapter(response);

                    return adapter.ListaClassifiche;
                }
                catch (Exception ex)
                {
                    throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE IL RECUPERO DELLE CLASSIFICHE", ex);
                }
            }
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {
            var vertParams = new HalleyVerticalizzazioneParametriAdapter(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloHalley>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);

            //gli allegati passati tramite protoIn sono diventati immutabili per cui, quando c'è la necessità di modificare tale lista
            //va introdotta una variabile interna e utilizzata quella
            var allegati = protoIn.RecuperaAllegati().ToList();

            DateTime? dataRicevimento = DateTime.Now;
            string domicilioElettronico = "";

            if (this.DatiProtocollo.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA)
            {
                dataRicevimento = this.DatiProtocollo.Istanza.DATA.Value;
                domicilioElettronico = this.DatiProtocollo.Istanza.DOMICILIO_ELETTRONICO;

                if (vertParams.GeneraSuapXml && !String.IsNullOrEmpty(vertParams.RadiceAlberoEdilizia))
                {
                    try
                    {
                        var suapSueFactory = SuapSueGeneratorFactory.Create(this.DatiProtocollo, allegati, vertParams.RadiceAlberoEdilizia, _protocolloLogs, _protocolloSerializer);
                        var isGenerato = suapSueFactory.Genera();

                        if (isGenerato)
                        {
                            var path = Path.Combine(_protocolloLogs.Folder, suapSueFactory.NomeFile);

                            allegati.Add(new ProtocolloAllegati
                            {
                                NOMEFILE = suapSueFactory.NomeFile,
                                Descrizione = suapSueFactory.NomeFile,
                                MimeType = "text/xml",
                                OGGETTO = File.ReadAllBytes(path)
                            });
                        }
                    }
                    catch (Exception ex)
                    {
                        _protocolloLogs.WarnFormat("ERRORE GENERATO DURANTE LA CREAZIONE DEL FILE SUAP.XML/SUE.XML, ERRORE: {0}", ex.Message);
                    }
                }
            }

            var conf = new HalleySegnaturaParamConfiguration(vertParams, Operatore, protoIn, domicilioElettronico, dataRicevimento.Value);

            var protoSrv = new HalleyProtocollazioneService(vertParams.Url, _protocolloLogs, _protocolloSerializer, this._bindingFactory, ProxyAddress);
            string token = protoSrv.Login(vertParams.Codiceente, vertParams.Username, vertParams.Password);

            IFascicoloHalleyBuilder datiFascicolo = null;

            if (!vertParams.DisabilitaFascicolazione)
                datiFascicolo = HalleyFascicoloFactory.Create(this.DatiProtocollo, vertParams, this._bindingFactory, token, _protocolloLogs, ProxyAddress);

            var segnaturaBuilder = new HalleySegnaturaBuilder(datiProto, conf, _protocolloLogs, _protocolloSerializer, allegati, datiFascicolo);

            if (vertParams.InviaSegnatura && allegati.Count == 0)
            {
                this._protocolloLogs.Debug("Il protocollo non ha allegati, verrà generata la segnatura come richiesto in verticalizzazione");
                segnaturaBuilder.CreaSegnaturaFittizia();
            }

            if (vertParams.InviaAllMovAvvio && allegati.Count == 0 && this.DatiProtocollo.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA)
                protoSrv.InserisciAllegatiDaMovimentoAvvio(this.DatiProtocollo.Istanza, this.DatiProtocollo.Db, DatiProtocollo.IdComune, allegati);

            protoSrv.InserisciAllegati(allegati, token, vertParams.Username);
            var segnatura = segnaturaBuilder.SerializzaSegnatura();

            var response = protoSrv.Protocollazione(vertParams.Username, token, segnatura, vertParams.InviaCf);

            var adapter = new HalleyProtocolloInsertOutputAdapter(response, _protocolloLogs);
            return adapter.Adatta();
        }
    }
}

