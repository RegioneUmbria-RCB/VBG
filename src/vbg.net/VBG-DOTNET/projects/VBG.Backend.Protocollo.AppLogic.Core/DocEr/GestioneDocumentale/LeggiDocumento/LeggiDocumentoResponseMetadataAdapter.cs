using System;
using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Core.DocEr.GestioneDocumentale.LeggiDocumento.Allegati;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.GestioneDocumentale.LeggiDocumento
{
    public class LeggiDocumentoResponseMetadataAdapter
    {
        readonly LeggiDocumentoInfo _info;

        public LeggiDocumentoResponseMetadataAdapter(LeggiDocumentoInfo info)
        {
            this._info = info;
        }

        public DatiProtocolloLettoResponseType Adatta()
        {
            var response = this._info.Wrapper.LeggiDocumento(this._info.UnitaDocumentale);

            if (this._info.Logs.IsDebugEnabled)
            {
                this._info.Serializer.LogAndValidate(ProtocolloLogsConstants.LeggiProtocolloResponseFileName, response);
            }

            var dic = response.ToDictionary(x => x.key, y => y.value);

            var mittenti = new Mittenti();
            var destinatari = new Destinatari();

            try
            {
                mittenti = this._info.Serializer.Deserialize<Mittenti>(dic[LeggiDocumentoConstants.Mittenti].Replace("\\n", ""));
            }
            catch (System.Exception ex)
            {
                this._info.Logs.ErrorFormat("MITTENTI NON DEFINITI, VALORE ELEMENTO MITTENTI: {0}, ERRORE: {1}", dic[LeggiDocumentoConstants.Mittenti], ex.Message);
            }

            try
            {
                destinatari = this._info.Serializer.Deserialize<Destinatari>(dic[LeggiDocumentoConstants.Destinatari].Replace("\\n", ""));
            }
            catch (System.Exception ex)
            {
                this._info.Logs.ErrorFormat("DESTINATARI NON DEFINITI, VALORE ELEMENTO DESTINATARI: {0}, ERRORE: {1}", dic[LeggiDocumentoConstants.Destinatari], ex.Message);
            }

            var factory = MittentiDestinatariFactory.Create(dic[LeggiDocumentoConstants.Flusso], mittenti.Items, destinatari.Items);
            var codiceClassifica = "";
            var numeroFascicolo = "";
            AllegatoResponseType[] allegati = new List<AllegatoResponseType>().ToArray();

            try
            {
                var allegatiAdapter = new AllegatiAdapter(this._info, dic);
                allegati = allegatiAdapter.Adatta();

                codiceClassifica = String.IsNullOrEmpty(dic[LeggiDocumentoConstants.CodiceTitolario]) ? dic[LeggiDocumentoConstants.CodiceClassifica] : dic[LeggiDocumentoConstants.CodiceTitolario];
                numeroFascicolo = String.IsNullOrEmpty(dic[LeggiDocumentoConstants.NumeroFascicolo]) ? dic[LeggiDocumentoConstants.ProgressivoFascicolo] : dic[LeggiDocumentoConstants.NumeroFascicolo];
            }
            catch (System.Exception ex)
            {
                this._info.Logs.ErrorFormat("ERRORE DURANTE LA DESERIALIZZAZIONE DEGLI ALLEGATI: {0}", ex.Message);
            }

            try
            {
                return new DatiProtocolloLettoResponseType
                {
                    NumeroProtocollo = dic[LeggiDocumentoConstants.NumeroProtocollo],
                    DataProtocollo = DateTime.Parse(dic[LeggiDocumentoConstants.DataProtocollo]).ToString("dd/MM/yyyy"),
                    AnnoProtocollo = dic[LeggiDocumentoConstants.AnnoProtocollo],
                    Classifica = codiceClassifica,
                    Classifica_Descrizione = String.Format("{0} - {1}", dic[LeggiDocumentoConstants.CodiceClassifica], dic[LeggiDocumentoConstants.DescrizioneClassifica]),
                    IdProtocollo = dic[LeggiDocumentoConstants.IdProtocollo],
                    Oggetto = dic[LeggiDocumentoConstants.Oggetto],
                    TipoDocumento = dic[LeggiDocumentoConstants.TipoDocumento],
                    TipoDocumento_Descrizione = dic[LeggiDocumentoConstants.TipoDocumentoDescrizione],
                    Origine = factory.Flusso,
                    MittentiDestinatari = factory.GetMittenteDestinatario(),
                    InCaricoA = factory.InCaricoA,
                    InCaricoA_Descrizione = factory.InCaricoADescrizione,
                    Allegati = allegati,
                    AnnoNumeroPratica = String.Format("{0}/{1}", numeroFascicolo, dic[LeggiDocumentoConstants.AnnoFascicolo])
                };
            }
            catch (System.Exception ex)
            {
                this._info.Logs.ErrorFormat("ERRORE DURANTE LA CREAZIONE DELLA STRUTTURA  DatiProtocolloLetto: {0}", ex);
                throw;
            }
        }
    }
}
