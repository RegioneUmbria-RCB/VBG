using VBG.Shared.Infrastructure.ServiceModel;
using SIGePro.Manager.VerticalizzazioniBase;
using VBG.Backend.Protocollo.AppLogic.Core.Auriga;
using VBG.Backend.Protocollo.AppLogic.Core.Auriga.Folder.Exceptions;
using VBG.Backend.Protocollo.AppLogic.Core.Auriga.Folder.GetMetadataFolder.Response;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Core
{
    public class PROTOCOLLO_AURIGA : ProtocolloBase
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly IBindingFactory _bindingFactory;

        public PROTOCOLLO_AURIGA(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._bindingFactory = bindingFactory;
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {

            //risoluzione della verticalizzazione
            var par = new ParametriRegoleInfoAdapter(this.DatiProtocollo.Token, this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune, this._verticalizzazioniFactory).Adatta();
            var proxyReqInfo = new ProxyRequestInfoAdapter(par).Adatta();

            //protocollazione
            var protocolloResponse = new Auriga.UD.AddUD.ServiceWrapperFactory(par, base._protocolloSerializer, base._protocolloLogs, proxyReqInfo, this._bindingFactory).Protocolla(protoIn);

            if (protocolloResponse != null)
            {
                var r = protocolloResponse.ToDatiProtocolloRes();
                r.DataProtocollo = DateTime.Now.Date.ToString("dd/MM/yyyy");

                return r;
            }

            return null;
        }

        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            //risoluzione della verticalizzazione
            var par = new ParametriRegoleInfoAdapter(this.DatiProtocollo.Token, this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune, this._verticalizzazioniFactory).Adatta();
            var proxyReqInfo = new ProxyRequestInfoAdapter(par).Adatta();

            var leggiprotocolloresponse = new Auriga.UD.GetMetadataUd.GetMetadataUdServiceWrapper(par, base._protocolloSerializer, base._protocolloLogs, proxyReqInfo, this._bindingFactory).LeggiProtocollo(leggiProtocolloRequest);

            if (leggiprotocolloresponse?.Any() ?? false)
            {
                return leggiprotocolloresponse
                    .Select(r => r.ToDatiProtocolloLetto())
                    .ToList();
            }

            return null;
        }

        public override DatiFascicoloResponseType Fascicola(Fascicolo fascicolo)
        {
            //risoluzione della verticalizzazione
            var par = new ParametriRegoleInfoAdapter(this.DatiProtocollo.Token, this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune, this._verticalizzazioniFactory).Adatta();
            var proxyReqInfo = new ProxyRequestInfoAdapter(par).Adatta();

            var fascicoloresponse = new Auriga.Folder.NewFolder.ResponseInfo();

            //tento la fascicolazione ma il fascicolo potrebbe anche esistere
            if (String.IsNullOrEmpty(fascicolo.NumeroFascicolo))
            {

                try
                {
                    fascicoloresponse = new Auriga.Folder.NewFolder.NewFolderServiceWrapper(par, base._protocolloSerializer, base._protocolloLogs, proxyReqInfo, this._bindingFactory).Fascicola(fascicolo);
                }
                catch (FolderExistException ex)
                {
                    var leggiFascicoloResponse = new Auriga.Folder.GetMetadataFolder.GetMetadataFolderServiceWrapper(par, base._protocolloSerializer, base._protocolloLogs, proxyReqInfo, this._bindingFactory).LeggiFascicoloDaPath(ex.Libreria, ex.PathNome);

                    fascicolo.AnnoFascicolo = leggiFascicoloResponse.ServiceResponse.Apertura.DataOra.Year;
                    fascicolo.DataFascicolo = leggiFascicoloResponse.ServiceResponse.Apertura.DataOra.ToString("dd/MM/yyyy");
                    fascicolo.NumeroFascicolo = ((FascDiTitolarioType)leggiFascicoloResponse.ServiceResponse.Item).NroFascicolo;
                }
                catch (Exception ex)
                {
                    this._protocolloLogs.ErrorFormat("Errore durante la fascicolazione del protocollo {0} con anno {1} e numero {2}. Errore: {3}", base.IdProtocollo, base.AnnoProtocollo, base.NumProtocollo, ex.ToString());
                    throw;
                }
            }

            if (!string.IsNullOrEmpty(fascicoloresponse?.IdFolder))
            {
                //aggiorno il protocollo
                var updProtFascicoloResponse = new Auriga.UD.UppUD.UppUDServiceWrapper(par, base._protocolloSerializer, base._protocolloLogs, proxyReqInfo, this._bindingFactory).FascicolaProtocollo(base.IdProtocollo, fascicoloresponse.IdFolder);

                //rilettura dei dati del fascicolo
                var leggiFascicoloResponse = new Auriga.Folder.GetMetadataFolder.GetMetadataFolderServiceWrapper(par, base._protocolloSerializer, base._protocolloLogs, proxyReqInfo, this._bindingFactory).LeggiFascicoloDaID(fascicoloresponse.IdFolder);

                //ritorno i dati del fascicolo al sistema di protocollazione
                return new DatiFascicoloResponseType
                {
                    AnnoFascicolo = leggiFascicoloResponse.ServiceResponse.Apertura.DataOra.Year.ToString(),
                    DataFascicolo = leggiFascicoloResponse.ServiceResponse.Apertura.DataOra.ToString("dd/MM/yyyy"),
                    NumeroFascicolo = ((FascDiTitolarioType)leggiFascicoloResponse.ServiceResponse.Item).NroFascicolo,
                    Errore = string.IsNullOrEmpty(updProtFascicoloResponse.WsError) ? null : new ErroreProtocolloType
                    {
                        Descrizione = updProtFascicoloResponse.WsError
                    },
                    Warning = updProtFascicoloResponse.WarningMessage
                };
            }


            if (!fascicolo.AnnoFascicolo.HasValue)
            {
                throw new InvalidOperationException("Non è possibile associare il protocollo ad un fascicolo esistente senza aver specificato l'anno di apertura del fascicolo");
            }

            var idProtocollo = base.IdProtocollo;
            var numFascicolo = fascicolo.NumeroFascicolo;
            var classifica = fascicolo.Classifica;
            var annoFascicolo = fascicolo.AnnoFascicolo.Value.ToString();

            //aggiorno il protocollo
            var fascicoloProtocollo = new Auriga.UD.UppUD.UppUDServiceWrapper(par, base._protocolloSerializer, base._protocolloLogs, proxyReqInfo, this._bindingFactory).FascicolaProtocollo(idProtocollo, numFascicolo, annoFascicolo, classifica);

            //ritorno i dati del fascicolo al sistema di protocollazione
            return new DatiFascicoloResponseType
            {
                AnnoFascicolo = fascicolo.AnnoFascicolo.Value.ToString(),
                DataFascicolo = fascicolo.DataFascicolo,
                NumeroFascicolo = fascicolo.NumeroFascicolo,
                Errore = string.IsNullOrEmpty(fascicoloProtocollo.WsError) ? null : new ErroreProtocolloType
                {
                    Descrizione = fascicoloProtocollo.WsError
                },
                Warning = fascicoloProtocollo.WarningMessage
            };
        }

        public override DatiProtocolloFascicolatoResponseType IsFascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            var par = new ParametriRegoleInfoAdapter(this.DatiProtocollo.Token, this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune, this._verticalizzazioniFactory).Adatta();
            var proxyReqInfo = new ProxyRequestInfoAdapter(par).Adatta();

            var leggiprotocolloresponse = new Auriga.UD.GetMetadataUd.GetMetadataUdServiceWrapper(par, base._protocolloSerializer, base._protocolloLogs, proxyReqInfo, this._bindingFactory).LeggiProtocollo(new LeggiProtocolloRequest()
            {
                IdProtocollo = idProtocollo,
                AnnoProtocollo = annoProtocollo,
                NumeroProtocollo = numeroProtocollo
            });

            if (leggiprotocolloresponse != null)
            {
                if (leggiprotocolloresponse.Count() > 1)
                {
                    throw new InvalidOperationException("I parametri utilizzati restituiscono più di un protocollo");
                }

                var protocollo = leggiprotocolloresponse.FirstOrDefault();

                return new DatiProtocolloFascicolatoResponseType
                {
                    Fascicolato = (protocollo.IsFascicolato) ? EnumFascicolatoType.si : EnumFascicolatoType.no,
                    AnnoFascicolo = protocollo.AnnoFascicolo,
                    NumeroFascicolo = protocollo.NumeroFascicolo,
                    Classifica = protocollo.Classifica,
                    Oggetto = protocollo.OggettoFasc,
                };
            }

            return new DatiProtocolloFascicolatoResponseType { Fascicolato = EnumFascicolatoType.no };
        }

        public override AllegatoResponseType LeggiAllegato()
        {
            var par = new ParametriRegoleInfoAdapter(this.DatiProtocollo.Token, this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune, this._verticalizzazioniFactory).Adatta();
            var proxyReqInfo = new ProxyRequestInfoAdapter(par).Adatta();

            Auriga.UD.ExtractOne.ResponseInfo allegatoResponse;

            if (String.IsNullOrEmpty(this.IdAllegato))
            {
                allegatoResponse = new Auriga.UD.ExtractOne.ExtractOneServiceWrapper(par, base._protocolloSerializer, base._protocolloLogs, proxyReqInfo, this._bindingFactory).EstraiPrimario(this.IdProtocollo, this.AnnoProtocollo, this.NumProtocollo);

            }
            else
            {
                allegatoResponse = new Auriga.UD.ExtractOne.ExtractOneServiceWrapper(par, base._protocolloSerializer, base._protocolloLogs, proxyReqInfo, this._bindingFactory).EstraiAllegato(this.IdProtocollo, this.AnnoProtocollo, this.NumProtocollo, this.IdAllegato);
            }

            var retVal = allegatoResponse.ToAllOut();
            return retVal;
        }
    }
}