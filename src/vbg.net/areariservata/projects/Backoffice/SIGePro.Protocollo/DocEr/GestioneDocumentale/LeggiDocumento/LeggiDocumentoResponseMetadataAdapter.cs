using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloDocErGestioneDocumentaleService;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione;
using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.LeggiDocumento.Allegati;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.LeggiDocumento
{
    public class LeggiDocumentoResponseMetadataAdapter
    {
        LeggiDocumentoInfo _info;

        //ProtocolloSerializer _serializer;
        //ProtocolloLogs _logs;
        //GestioneDocumentaleService _wrapper;
        //string _unitaDocumentale;
        //bool _estraiEml;
        //string[] _escludiFilesDaEml;

        public LeggiDocumentoResponseMetadataAdapter(LeggiDocumentoInfo info)
        {
            this._info = info;

            //this._wrapper = wrapper;
            //this._serializer = serializer;
            //this._logs = logs;
            //this._unitaDocumentale = unitaDocumentale;
            //this._estraiEml = estraiEml;
            //this._escludiFilesDaEml = escludiFilesDaEml;
        }

        public DatiProtocolloLetto Adatta()
        {
            var response = this._info.Wrapper.LeggiDocumento(this._info.UnitaDocumentale);

            if (this._info.Logs.IsDebugEnabled)
            {
                this._info.Serializer.Serialize(ProtocolloLogsConstants.LeggiProtocolloResponseFileName, response);
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
            finally
            { 
            
            }

            try
            {
                destinatari = this._info.Serializer.Deserialize<Destinatari>(dic[LeggiDocumentoConstants.Destinatari].Replace("\\n", ""));
            }
            catch (System.Exception ex)
            {
                this._info.Logs.ErrorFormat("DESTINATARI NON DEFINITI, VALORE ELEMENTO DESTINATARI: {0}, ERRORE: {1}", dic[LeggiDocumentoConstants.Destinatari], ex.Message);
            }
            finally
            { 
            
            }

            var factory = MittentiDestinatariFactory.Create(dic[LeggiDocumentoConstants.Flusso], mittenti.Items, destinatari.Items);

            var allegatiAdapter = new AllegatiAdapter(this._info, dic);
            var allegati = allegatiAdapter.Adatta();

            var codiceClassifica = String.IsNullOrEmpty(dic[LeggiDocumentoConstants.CodiceTitolario]) ? dic[LeggiDocumentoConstants.CodiceClassifica] : dic[LeggiDocumentoConstants.CodiceTitolario];
            var numeroFascicolo = String.IsNullOrEmpty(dic[LeggiDocumentoConstants.NumeroFascicolo]) ? dic[LeggiDocumentoConstants.ProgressivoFascicolo] : dic[LeggiDocumentoConstants.NumeroFascicolo];

            var res = new DatiProtocolloLetto
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

            return res;
        }
    }
}
