using Init.SIGePro.Protocollo.Archiflow;
using Init.SIGePro.Protocollo.Archiflow.Protocollazione;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.ProtocolloArchiFlowServiceReference;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo
{
    public class PROTOCOLLO_ARCHIFLOW : ProtocolloBase
    {
        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {
            var vert = new VerticalizzazioniWrapper(new VerticalizzazioneProtocolloArchiflow(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune),_protocolloLogs);
            var credenziali = new Login
            {
                Username = vert.Username,
                Password = vert.Password,
                CodEnte = vert.CodiceEnte
            };

            var wrapper = new ProtocollazioneServiceWrapper(vert.Url, credenziali, _protocolloLogs, _protocolloSerializer);

            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);

            string protoMittente = "";
            if (this.DatiProtocollo.Istanza != null && !String.IsNullOrEmpty(this.DatiProtocollo.Istanza.FKIDPROTOCOLLO))
                protoMittente = this.DatiProtocollo.Istanza.FKIDPROTOCOLLO;

            var protoFactory = ProtocollazioneFactory.Create(datiProto, this.Anagrafiche, wrapper, vert, protoMittente);
            var response = protoFactory.Protocolla();

            if(protoIn.Allegati.Count > 0)
            {
                wrapper.InserimentoDocumentoPrincipale(protoFactory.GuidCardProtocollo, protoIn.Allegati.First());

                if (protoIn.Allegati.Count > 1)
                { 
                    var request = protoIn.Allegati.Skip(1).Select(x => 
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
