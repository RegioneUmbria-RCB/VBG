using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Insiel3.Services;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.ProtocolloInsielService3;
using Init.SIGePro.Protocollo.Insiel3.LeggiProtocollo.MittentiDestinatari;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.ProtocolloEnumerators;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.Insiel3.LeggiProtocollo
{
    public class LeggiProtocolloOutputAdapter
    {
        DettagliProtocollo _response;
        ProtocolloLogs _logs;

        public LeggiProtocolloOutputAdapter(DettagliProtocollo response, ProtocolloLogs logs)
        {
            this._response = response;
            this._logs = logs;
        }


        public DatiProtocolloLetto Adatta(bool usaLivelliClassifica)
        {
            var mittentiDestinatari = MittentiDestinatariFactory.Create(_response);

            string codiceClassifica = "";
            if (_response.classifiche.Length > 0)
            {
                var classificaAdapter = new ClassificaAdapter();
                codiceClassifica = classificaAdapter.EstraiClassifica(usaLivelliClassifica, _response.classifiche[_response.classifiche.Length - 1]);
            }

            string descrizioneClassifica = _response.classifiche.Length == 0 ? "" : $"[{codiceClassifica}] {_response.classifiche[_response.classifiche.Length - 1].descrizione}";
            string idProtocollo = String.Format("{0};{1}", _response.infoGenerali.protoProgDoc, _response.infoGenerali.protoProgMovi);

            var retVal = new DatiProtocolloLetto
            {
                NumeroProtocollo = _response.infoGenerali.protoNumProt.Value.ToString(),
                DataProtocollo = _response.infoGenerali.protoDataOraAgg.Value.ToString("dd/MM/yyyy"),
                AnnoProtocollo = _response.infoGenerali.protoAnnoProt.Value.ToString(),
                IdProtocollo = idProtocollo,
                TipoDocumento = _response.infoGenerali.docCodTipoDoc,
                TipoDocumento_Descrizione = _response.infoGenerali.tipoDocDescTipoDoc,
                Oggetto = _response.infoGenerali.docDescOgge,
                InCaricoA = mittentiDestinatari.InCaricoA,
                InCaricoA_Descrizione = mittentiDestinatari.InCaricoADescrizione,
                Classifica = codiceClassifica,
                Classifica_Descrizione = descrizioneClassifica,
                Annullato = _response.infoGenerali.protoStato.GetValueOrDefault(0) == 1 ? ProtocolloEnum.IsAnnullato.si.ToString() : ProtocolloEnum.IsAnnullato.no.ToString(),
                Origine = _response.infoGenerali.protoApProt,
                MittentiDestinatari = mittentiDestinatari.GetMittenteDestinatario(),
                Allegati = _response.documenti.Select(x => new AllOut
                {
                    IDBase = x.idDoc.ToString(),
                    Serial = x.nome,
                    TipoFile = x.tipoDoc,
                    Commento = x.nome
                }).ToArray(),
            };

            if (this._response.pratiche != null && this._response.pratiche.Count() > 0)
            {
                retVal.AnnoNumeroPratica = $"{this._response.pratiche.First().anno}/{this._response.pratiche.First().numero}";
                retVal.NumeroPratica = this._response.pratiche.First().numero;
            }

            return retVal;
        }
    }
}