using VBG.Shared.Infrastructure.ServiceModel;
using ProtocolloArchiFlowServiceReference;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Core.Archiflow;
using VBG.Backend.Protocollo.AppLogic.Core.Archiflow.Protocollazione;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Factories;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.Verticalizzazioni.Core;

namespace VBG.Backend.Protocollo.AppLogic.Core
{
    public class PROTOCOLLO_ARCHIFLOW : ProtocolloBase
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly IBindingFactory _bindingFactory;

        public PROTOCOLLO_ARCHIFLOW(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._bindingFactory = bindingFactory;
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {
            var vert = new VerticalizzazioniWrapper(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloArchiflow>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune), this._protocolloLogs);
            
            var credenziali = new Login
            {
                Username = vert.Username,
                Password = vert.Password,
                CodEnte = vert.CodiceEnte
            };

            var wrapper = new ProtocollazioneServiceWrapper(vert.Url, credenziali, _protocolloLogs, _protocolloSerializer, _bindingFactory);

            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);

            string protoMittente = "";
            if (this.DatiProtocollo.Istanza != null && !String.IsNullOrEmpty(this.DatiProtocollo.Istanza.FKIDPROTOCOLLO))
                protoMittente = this.DatiProtocollo.Istanza.FKIDPROTOCOLLO;

            var protoFactory = ProtocollazioneFactory.Create(datiProto, this.Anagrafiche, wrapper, vert, protoMittente);
            var response = protoFactory.Protocolla();

            if(protoIn.HaAllegati())
            {
                wrapper.InserimentoDocumentoPrincipale(protoFactory.GuidCardProtocollo, protoIn.RecuperaAllegati().First());

                if (protoIn.NumeroAllegatiPresenti > 1)
                { 
                    var request = protoIn.RecuperaAllegati().Skip(1).Select(x => 
                    {
                        _protocolloLogs.InfoFormat("ALLEGATO CODICE: {0}, NOME FILE: {1}", x.CODICEOGGETTO, x.NOMEFILE);
                        return new oAttachmentCard
                        {
                            contenByte = x.OGGETTO,
                            extension = x.Extension,
                            Filename = x.NOMEFILE
                        };
                    });
                    
                    wrapper.InserimentoAllegati(protoFactory.GuidCardProtocollo, request.ToArray());
                }
            }

            response.Warning = _protocolloLogs.Warnings.WarningMessage;

            return response;
        }
    }
}
