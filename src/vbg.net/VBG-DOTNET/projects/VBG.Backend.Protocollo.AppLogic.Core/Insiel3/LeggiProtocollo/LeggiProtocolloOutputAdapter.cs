using ProtocolloInsielService3;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel3.LeggiProtocollo.MittentiDestinatari;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel3.LeggiProtocollo
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


        public DatiProtocolloLettoResponseType Adatta(bool usaLivelliClassifica)
        {
            var mittentiDestinatari = MittentiDestinatariFactory.Create(_response);

            string codiceClassifica = "";

            if (_response.classifiche.Length > 0)
            {
                var classificaAdapter = new ClassificaAdapter();
                codiceClassifica = classificaAdapter.EstraiClassifica(usaLivelliClassifica, _response.classifiche[_response.classifiche.Length - 1]);
            }
            else
            {
                if (_response.pratiche.Length > 0)
                {
                    var classificaAdapter = new ClassificaAdapter();
                    codiceClassifica = classificaAdapter.EstraiClassifica(usaLivelliClassifica, _response.pratiche[_response.pratiche.Length - 1].codiceRegistro);
                }
            }

            string descrizioneClassifica = _response.classifiche.Length == 0 ? "" : $"[{codiceClassifica}] {_response.classifiche[_response.classifiche.Length - 1].descrizione}";
            string idProtocollo = String.Format("{0};{1}", _response.infoGenerali.protoProgDoc, _response.infoGenerali.protoProgMovi);

            var retVal = new DatiProtocolloLettoResponseType
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
                Classifica_Descrizione = String.IsNullOrEmpty(descrizioneClassifica) ? codiceClassifica : descrizioneClassifica,
                Annullato = _response.infoGenerali.protoStato.GetValueOrDefault(0) == 1 ? IsAnnullato.si.ToString() : IsAnnullato.no.ToString(),
                Origine = _response.infoGenerali.protoApProt,
                MittentiDestinatari = mittentiDestinatari.GetMittenteDestinatario(),
                Allegati = _response.documenti.Select(x => new AllegatoResponseType
                {
                    IDBase = x.idDoc.ToString(),
                    Serial = x.nome,
                    TipoFile = x.tipoDoc,
                    Commento = x.nome
                }).ToArray(),
            };

            if (this._response.pratiche != null && this._response.pratiche.Count() > 0)
            {
                retVal.AnnoNumeroPratica = $"{codiceClassifica}/{this._response.pratiche.First().anno}/{this._response.pratiche.First().numero}";
                retVal.NumeroPratica = this._response.pratiche.First().numero;
            }

            return retVal;
        }
    }
}