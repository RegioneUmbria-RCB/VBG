using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale;
using Init.SIGePro.Protocollo.DocEr.Verticalizzazioni;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.Fascicolazione;
using Init.SIGePro.Protocollo.DocEr.Fascicolazione.CreaFascicolo;
using Init.SIGePro.Protocollo.ProtocolloDocErGestioneDocumentaleService;
using Init.SIGePro.Protocollo.DocEr.Autenticazione;

namespace Init.SIGePro.Protocollo.DocEr.Fascicolazione
{
    public class FascicolazioneIstanza : IFascicolazione
    {
        VerticalizzazioniConfiguration _vert;
        GestioneDocumentaleService _docWrapper;
        FascicolazioneService _fascWrapper;
        Fascicolo _datiFascicolo;
        IEnumerable<Movimenti> _movimentiProtocollati;
        int _idProtocollo;
        IAuthenticationService _auth;

        public FascicolazioneIstanza(int idProtocollo, VerticalizzazioniConfiguration vert, GestioneDocumentaleService docWrapper, FascicolazioneService fascWrapper, Fascicolo datiFascicolo, IEnumerable<Movimenti> movimentiProtocollati, IAuthenticationService auth)
        {
            _auth = auth;
            _idProtocollo = idProtocollo;
            _vert = vert;
            _docWrapper = docWrapper;
            _fascWrapper = fascWrapper;
            _datiFascicolo = datiFascicolo;
            
            _movimentiProtocollati = movimentiProtocollati;
        }

        public DatiFascicolo Fascicola(FascicolazioneRequestAdapter requestAdapter)
        {
            
            if (String.IsNullOrEmpty(_datiFascicolo.NumeroFascicolo))
                CreaFascicolo();
            else
            {
                bool isFascicolato = this.IsFascicolato();
                if (!isFascicolato)
                    CreaFascicolo();
            }

            var segnatura = requestAdapter.Adatta(_datiFascicolo);

            _fascWrapper.Fascicola(_idProtocollo, segnatura.SegnaturaSerializzata);

            FascicolaMovimentiProtocollati(requestAdapter);

            return new DatiFascicolo
            {
                AnnoFascicolo = _datiFascicolo.AnnoFascicolo.ToString(),
                DataFascicolo = _datiFascicolo.DataFascicolo,
                NumeroFascicolo = _datiFascicolo.NumeroFascicolo
            };
        }

        private bool IsFascicolato()
        {
            var adapter = new GestioneDocumentaleFascicoloMetadataAdapter(_datiFascicolo, _vert.CodiceEnte, _vert.CodiceAoo);
            var metadati = adapter.Adatta();
            var response = _docWrapper.SearchFascicoli(metadati);

            bool res = (response != null && response.Length > 0);
            if (res)
            {
                var dic = response[0].metadata.ToDictionary(x => x.key, y => y.value);
                _datiFascicolo.AnnoFascicolo = Convert.ToInt32(dic[FascicolazioneMetadataConstants.ANNO_FASCICOLO]);
                _datiFascicolo.Classifica = dic[FascicolazioneMetadataConstants.CLASSIFICA];
                _datiFascicolo.NumeroFascicolo = dic[FascicolazioneMetadataConstants.PROGRESSIVO_FASCICOLO];
                _datiFascicolo.Oggetto = dic[FascicolazioneMetadataConstants.DES_FASCICOLO];
            }
            return (response != null && response.Length > 0);
        }

        private void SetAclFascicolo()
        { 
            
        }

        private void CreaFascicolo()
        {
            var adapter = new CreaFascicoloMetadataAdapter(_datiFascicolo, _vert.CodiceEnte, _vert.CodiceAoo);
            var metadati = adapter.Adatta();
            var response = _fascWrapper.CreaFascicolo(metadati);

            _datiFascicolo.AnnoFascicolo = Convert.ToInt32(response.esito_fascicolo[0].ANNO_FASCICOLO);
            _datiFascicolo.Classifica = response.esito_fascicolo[0].CLASSIFICA;
            _datiFascicolo.NumeroFascicolo = response.esito_fascicolo[0].PROGR_FASCICOLO;
            _datiFascicolo.Oggetto = response.esito_fascicolo[0].DES_FASCICOLO;

            _docWrapper.SetAclFascicolo(_datiFascicolo, _auth, _vert.CodiceEnte, _vert.CodiceAoo);
        }

        private void FascicolaMovimentiProtocollati(FascicolazioneRequestAdapter requestAdapter)
        {
            foreach (var mov in _movimentiProtocollati)
            {
                if (String.IsNullOrEmpty(mov.FKIDPROTOCOLLO))
                    continue;

                var fascMov = new FascicolazioneMovimento(Convert.ToInt32(mov.FKIDPROTOCOLLO), _datiFascicolo, _fascWrapper);
                fascMov.Fascicola(requestAdapter);
            }
        }
    }
}
