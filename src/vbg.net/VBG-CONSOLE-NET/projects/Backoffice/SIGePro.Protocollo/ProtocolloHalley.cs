using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.Validation;
using Init.SIGePro.Data;
using System.IO;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.Halley.Adapters;
using Init.SIGePro.Protocollo.Halley.Services;
using Init.SIGePro.Protocollo.Halley.Configurations;
using Init.SIGePro.Protocollo.Halley.Builders;
using Init.SIGePro.Protocollo.Halley.Factories;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.Halley.ImpresaInUnGiorno;
using Init.SIGePro.Protocollo.Halley.Interfaces;

namespace Init.SIGePro.Protocollo
{
    public class PROTOCOLLO_HALLEY : ProtocolloBase
    {
        public PROTOCOLLO_HALLEY()
        {

        }

        public override ListaTipiClassifica GetClassifiche()
        {
            var vertParams = new HalleyVerticalizzazioneParametriAdapter(_protocolloLogs, new VerticalizzazioneProtocolloHalley(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            if (!vertParams.UsaWsClassifiche)
                return base.GetClassifiche();
            else
            {
                try
                {
                    var serviceClassifiche = new HalleyDizionarioService(_protocolloLogs, vertParams.UrlWsDizionario, ProxyAddress);
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

        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {
            var vertParams = new HalleyVerticalizzazioneParametriAdapter(_protocolloLogs, new VerticalizzazioneProtocolloHalley(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);

            DateTime? dataRicevimento = DateTime.Now;
            string domicilioElettronico = "";

            if (this.DatiProtocollo.TipoAmbito == ProtocolloEnumerators.ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA)
            {
                dataRicevimento = this.DatiProtocollo.Istanza.DATA.Value;
                domicilioElettronico = this.DatiProtocollo.Istanza.DOMICILIO_ELETTRONICO;

                if (vertParams.GeneraSuapXml && !String.IsNullOrEmpty(vertParams.RadiceAlberoEdilizia))
                {
                    try
                    {
                        var suapSueFactory = SuapSueGeneratorFactory.Create(this.DatiProtocollo, protoIn.Allegati, vertParams.RadiceAlberoEdilizia, _protocolloLogs, _protocolloSerializer);
                        var isGenerato = suapSueFactory.Genera();

                        if (isGenerato)
                        {
                            var path = Path.Combine(_protocolloLogs.Folder, suapSueFactory.NomeFile);

                            protoIn.Allegati.Add(new ProtocolloAllegati
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

            var protoSrv = new HalleyProtocollazioneService(vertParams.Url, _protocolloLogs, _protocolloSerializer, ProxyAddress);
            string token = protoSrv.Login(vertParams.Codiceente, vertParams.Username, vertParams.Password);

            IFascicoloHalleyBuilder datiFascicolo = null;
            
            if(!vertParams.DisabilitaFascicolazione)
                datiFascicolo = HalleyFascicoloFactory.Create(this.DatiProtocollo, vertParams, token, _protocolloLogs, ProxyAddress);

            var segnaturaBuilder = new HalleySegnaturaBuilder(datiProto, conf, _protocolloLogs, _protocolloSerializer, protoIn.Allegati, datiFascicolo);

            if (vertParams.InviaSegnatura && protoIn.Allegati.Count == 0)
                segnaturaBuilder.CreaSegnaturaFittizia();

            if (vertParams.InviaAllMovAvvio && protoIn.Allegati.Count == 0 && this.DatiProtocollo.TipoAmbito == ProtocolloEnumerators.ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA)
                protoSrv.InserisciAllegatiDaMovimentoAvvio(this.DatiProtocollo.Istanza, this.DatiProtocollo.Db, DatiProtocollo.IdComune, protoIn.Allegati);

            protoSrv.InserisciAllegati(protoIn.Allegati, token, vertParams.Username);
            var segnatura = segnaturaBuilder.SerializzaSegnatura();

            var response = protoSrv.Protocollazione(vertParams.Username, token, segnatura, vertParams.InviaCf);

            var adapter = new HalleyProtocolloInsertOutputAdapter(response, _protocolloLogs);
            return adapter.Adatta();
        }
    }
}

