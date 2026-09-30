using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.Autenticazione;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.GestioneDocumentale;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.GestioneDocumentale.Fascicolazione;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.Fascicolazione.CreaFascicolo;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using System;
using System.Linq;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.Fascicolazione
{
    public class FascicolazioneNessuno : IFascicolazione
    {
        VerticalizzazioniConfiguration _vert;
        GestioneDocumentaleService _docWrapper;
        FascicolazioneService _fascWrapper;
        Fascicolo _datiFascicolo;
        int _idProtocollo;
        IAuthenticationService _auth;


        public FascicolazioneNessuno(int idProtocollo, VerticalizzazioniConfiguration vert, GestioneDocumentaleService docWrapper, FascicolazioneService fascWrapper, Fascicolo datiFascicolo, IAuthenticationService auth)
        {
            _auth = auth;
            _idProtocollo = idProtocollo;
            _vert = vert;
            _docWrapper = docWrapper;
            _fascWrapper = fascWrapper;
            _datiFascicolo = datiFascicolo;
        }

        public DatiFascicoloResponseType Fascicola(FascicolazioneRequestAdapter requestAdapter)
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

            return new DatiFascicoloResponseType
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

        private void CreaFascicolo()
        {
            var adapter = new CreaFascicoloMetadataAdapter(_datiFascicolo, _vert.CodiceEnte, _vert.CodiceAoo);
            var metadati = adapter.Adatta();
            var response = _fascWrapper.CreaFascicolo(metadati);

            _datiFascicolo.AnnoFascicolo = Convert.ToInt32(response.esito_fascicolo[0].ANNO_FASCICOLO);
            _datiFascicolo.Classifica = response.esito_fascicolo[0].CLASSIFICA;
            _datiFascicolo.NumeroFascicolo = response.esito_fascicolo[0].PROGR_FASCICOLO;
            _datiFascicolo.Oggetto = response.esito_fascicolo[0].DES_FASCICOLO;

            var fascMetadati = new FascicoloMetadataAdapter(_datiFascicolo, _vert.CodiceEnte, _vert.CodiceAoo);

            var ruoli = _auth.GetRuoli(_docWrapper);

            if (_vert.CompatibilitaFasc)
            {
                var metadatifasc = fascMetadati.Adatta();
                _fascWrapper.UpdateAclFascicolo(ruoli, metadati, _auth.Username);
            }
            else
            {
                _docWrapper.SetAclFascicolo(_datiFascicolo, ruoli, _vert.CodiceEnte, _vert.CodiceAoo, _auth.Username);
            }
        }
    }
}
