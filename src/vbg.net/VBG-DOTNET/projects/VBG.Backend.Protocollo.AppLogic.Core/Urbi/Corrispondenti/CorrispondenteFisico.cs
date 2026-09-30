using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Corrispondenti
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

        public xapirestTypeCorrispondenti SearchCorrispondente()
        {
            if (String.IsNullOrEmpty(_corrispondente.CodiceFiscale))
                throw new Exception(String.Format("ANAGRAFICA {0} CODICE {1} E' SPROVVISTA DI CODICE FISCALE, INSERIRE IL DATO E RIPROVARE", _corrispondente.NomeCognome, _corrispondente.Codice));

            var parameters = new NameValueCollection();
            parameters.Add("PRCORE03_99991006_CodiceFiscale", _corrispondente.CodiceFiscale);
            parameters.Add("PRCORE03_99991006_TipoPersona", "F");

            if (parameters.Count == 0)
                return new xapirestTypeCorrispondenti();

            return _wrapper.GetCorrispondente(parameters);

            /*

            if (response.getElencoCorrispondenti_Result.NumCorrispondenti == "0")
                return 0;

            return Convert.ToInt32(response.getElencoCorrispondenti_Result.SEQ_Corrispondente[0].CodiceSoggetto);

            */
        }

        public xapirestTypeInsertCorrispondenti InsertCorrispondente()
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
