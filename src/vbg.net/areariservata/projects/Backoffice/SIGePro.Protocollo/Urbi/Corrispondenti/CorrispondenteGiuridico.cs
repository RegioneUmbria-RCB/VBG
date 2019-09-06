using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Urbi.Corrispondenti
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

        public int SearchCorrispondente()
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

            if (response.getElencoCorrispondenti_Result == null || response.getElencoCorrispondenti_Result.NumCorrispondenti == "0")
                return 0;

            return Convert.ToInt32(response.getElencoCorrispondenti_Result.SEQ_Corrispondente[0].CodiceSoggetto);
        }

        public int InsertCorrispondente()
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
                parameters.Add("PRCORE03_99991007_ComuneResidenza", _corrispondente.ComuneResidenza.CODICEISTAT.Substring(1));
                parameters.Add("PRCORE03_99991007_ProvinciaResidenza", _corrispondente.ComuneResidenza.SIGLAPROVINCIA);
            }

            var response = _wrapper.InsertCorrispondente(parameters);
            return Convert.ToInt32(response.insCorrispondente_Result.CodiceSoggetto);
        }
    }
}
