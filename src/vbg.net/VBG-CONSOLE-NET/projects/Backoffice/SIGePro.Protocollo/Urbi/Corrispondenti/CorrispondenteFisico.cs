using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Urbi.Corrispondenti
{
    public class CorrispondenteFisico : ICorrispondente
    {
        CorrispondentiServiceWrapper _wrapper;
        IAnagraficaAmministrazione _corrispondente;

        public CorrispondenteFisico(CorrispondentiServiceWrapper wrapper, IAnagraficaAmministrazione corrispondente)
        {
            _wrapper = wrapper;
            _corrispondente = corrispondente;
        }

        public int SearchCorrispondente()
        {
            if (String.IsNullOrEmpty(_corrispondente.CodiceFiscale))
                throw new Exception(String.Format("ANAGRAFICA {0} CODICE {1} E' SPROVVISTA DI CODICE FISCALE, INSERIRE IL DATO E RIPROVARE", _corrispondente.NomeCognome, _corrispondente.Codice));

            var parameters = new NameValueCollection();
            parameters.Add("PRCORE03_99991006_CodiceFiscale", _corrispondente.CodiceFiscale);
            parameters.Add("PRCORE03_99991006_TipoPersona", "F");

            if (parameters.Count == 0)
                return 0;

            var response = _wrapper.GetCorrispondente(parameters);

            if (response.getElencoCorrispondenti_Result.NumCorrispondenti == "0")
                return 0;

            return Convert.ToInt32(response.getElencoCorrispondenti_Result.SEQ_Corrispondente[0].CodiceSoggetto);
        }

        public int InsertCorrispondente()
        {
            if (String.IsNullOrEmpty(_corrispondente.CodiceFiscale))
                throw new Exception(String.Format("ANAGRAFICA {0} CODICE {1} E' SPROVVISTA DI CODICE FISCALE, INSERIRE IL DATO E RIPROVARE", _corrispondente.NomeCognome, _corrispondente.Codice));

            var parameters = new NameValueCollection();

            parameters.Add("PRCORE03_99991007_TipoPersona", "F");
            parameters.Add("PRCORE03_99991007_Cognome", _corrispondente.Cognome);
            parameters.Add("PRCORE03_99991007_Nome", _corrispondente.Nome);
            parameters.Add("PRCORE03_99991007_CodiceFiscale", _corrispondente.CodiceFiscale);

            if (!String.IsNullOrEmpty(_corrispondente.Sesso))
                parameters.Add("PRCORE03_99991007_Sesso", _corrispondente.Sesso);

            if (!String.IsNullOrEmpty(_corrispondente.Indirizzo))
                parameters.Add("PRCORE03_99991007_IndirizzoResidenza", _corrispondente.Indirizzo);

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
