using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.Kibernetes.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.Kibernetes.Protocollazione;

namespace Init.SIGePro.Protocollo
{
    public class PROTOCOLLO_KIBERNETES : ProtocolloBase
    {
        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {
            var vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloKibernetes(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var datiAnagrafici = DatiProtocolloInsertFactory.Create(protoIn);

            var protoService = new ProtocollazioneService(vert.Url, vert.Username, vert.Password, _protocolloLogs, _protocolloSerializer);
            var proto = ProtocollazioneFactory.Create(vert, this.Anagrafiche, DatiProtocolloInsertFactory.Create(protoIn), this.Operatore);

            var response = protoService.Protocolla(proto);

            if(protoIn.Allegati.Count > 0)
                protoService.AggiungiAllegato(protoIn.Allegati.First(), response.Numero, response.Anno, vert, true);

            if (protoIn.Allegati.Count > 1)
                protoIn.Allegati.Skip(1).ToList().ForEach(x => protoService.AggiungiAllegato(x, response.Numero, response.Anno, vert, false));

            var retVal = new DatiProtocolloRes
            {
                AnnoProtocollo = response.Anno.ToString(),
                NumeroProtocollo = response.Numero.ToString(),
                DataProtocollo = DateTime.Now.ToString("dd/MM/yyyy")
            };

            if (!String.IsNullOrEmpty(_protocolloLogs.Warnings.WarningMessage))
                retVal.Warning = _protocolloLogs.Warnings.WarningMessage;

            return retVal;
        }

        public override DatiProtocolloLetto LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            return new DatiProtocolloLetto
            {
                AnnoProtocollo = annoProtocollo,
                NumeroProtocollo = numeroProtocollo,
                DataProtocollo = DataProtocollo.HasValue ? DataProtocollo.Value.ToString("dd/MM/yyyy") : ""
            };
        }

        public override void AggiungiAllegati(string idProtocollo, string numeroProtocollo, DateTime? dataProtocollo, IEnumerable<SIGePro.Data.ProtocolloAllegati> allegati)
        {
            var vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloKibernetes(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var protoService = new ProtocollazioneService(vert.Url, vert.Username, vert.Password, _protocolloLogs, _protocolloSerializer);

            allegati.Skip(1).ToList().ForEach(x => protoService.AggiungiAllegato(x, Convert.ToInt16(numeroProtocollo), Convert.ToInt16(dataProtocollo.Value.ToString("yyyy")), vert, false));

            if (!String.IsNullOrEmpty(_protocolloLogs.Warnings.WarningMessage))
                throw new Exception(_protocolloLogs.Warnings.WarningMessage);
        }
    }
}
