using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Corrispondenti
{
    public class CorrispondenteGiuridico : ICorrispondente  
    {
        CorrispondentiServiceWrapper _wrapper;
        IAnagraficaAmministrazione _corrispondente;

        public CorrispondenteGiuridico(CorrispondentiServiceWrapper wrapper, IAnagraficaAmministrazione corrispondente)
        {
            _corrispondente = corrispondente;
            _wrapper = wrapper;
        }

        public xapirestTypeCorrispondenti SearchCorrispondente()
        {
            if (String.IsNullOrEmpty(_corrispondente.CodiceFiscale) && String.IsNullOrEmpty(_corrispondente.PartitaIva))
                throw new Exception(String.Format("{0} {1} CODICE {2} E' SPROVVISTA DI CODICE FISCALE O PARTITA IVA, INSERIRE UNO DEI DUE DATI, O ENTRAMBI, E RIPROVARE", _corrispondente.TipologiaAnagrafica, _corrispondente.NomeCognome, _corrispondente.Codice));

            var parameters = new NameValueCollection();
            var response = new xapirestTypeCorrispondenti();
            
            if (!String.IsNullOrEmpty(_corrispondente.PartitaIva))
            {
                parameters.Add("PRCORE03_99991006_TipoPersona", "G");
                parameters.Add("PRCORE03_99991006_PartitaIVA", _corrispondente.PartitaIva);
                response = _wrapper.GetCorrispondente(parameters);
            }

            if (!String.IsNullOrEmpty(_corrispondente.CodiceFiscale) && (response.getElencoCorrispondenti_Result == null || response.getElencoCorrispondenti_Result.NumCorrispondenti == "0"))
            {
                parameters.Clear();
                parameters.Add("PRCORE03_99991006_TipoPersona", "G");
                parameters.Add("PRCORE03_99991006_CodiceFiscale", _corrispondente.CodiceFiscale);
                response = _wrapper.GetCorrispondente(parameters);
            }

            return response;

            /*

            if (response.getElencoCorrispondenti_Result == null || response.getElencoCorrispondenti_Result.NumCorrispondenti == "0")
                return 0;

            return Convert.ToInt32(response.getElencoCorrispondenti_Result.SEQ_Corrispondente[0].CodiceSoggetto);
            */
        }

        public xapirestTypeInsertCorrispondenti InsertCorrispondente()
        {
            if (String.IsNullOrEmpty(_corrispondente.CodiceFiscale) && String.IsNullOrEmpty(_corrispondente.PartitaIva))
            {
                throw new Exception(String.Format("{0} {1} CODICE {2} E' SPROVVISTA DI CODICE FISCALE O PARTITA IVA, INSERIRE UNO DEI DUE DATI, O ENTRAMBI, E RIPROVARE", _corrispondente.TipologiaAnagrafica, _corrispondente.NomeCognome, _corrispondente.Codice));
            }

            var parameters = new NameValueCollection();

            parameters.Add("PRCORE03_99991007_TipoPersona", "G");
            parameters.Add("PRCORE03_99991007_Denominazione", _corrispondente.Denominazione);

            string codFiscalePartitaIva = _corrispondente.CodiceFiscalePartitaIva;

            parameters.Add("PRCORE03_99991007_CodiceFiscale", _corrispondente.CodiceFiscalePartitaIva);

            if (!String.IsNullOrEmpty(_corrispondente.PartitaIva))
            {
                parameters.Add("PRCORE03_99991007_PartitaIVA", _corrispondente.PartitaIva);
            }

            if (_corrispondente.ComuneResidenza != null)
            {
                if (_corrispondente.ComuneResidenza.SiglaProvincia == "EE")
                {
                    parameters.Add("PRCORE03_99991007_NazioneResidenza", _corrispondente.Comune);
                }
                else
                {
                    parameters.Add("PRCORE03_99991007_ComuneResidenza", _corrispondente.ComuneResidenza.CodiceIstat.Substring(1));
                    parameters.Add("PRCORE03_99991007_ProvinciaResidenza", _corrispondente.ComuneResidenza.SiglaProvincia);
                }
            }

            return _wrapper.InsertCorrispondente(parameters);
            //return Convert.ToInt32(response.insCorrispondente_Result.CodiceSoggetto);
        }
    }
}
