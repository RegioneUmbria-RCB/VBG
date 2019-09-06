using Init.SIGePro.Manager.DTO;
using Init.SIGePro.Manager.Verticalizzazioni;
using Init.SIGePro.Sit.Data;
using Init.SIGePro.Sit.Jesi;
using Init.SIGePro.Sit.Manager;
using Init.SIGePro.Sit.ValidazioneFormale;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit
{
    public class SIT_JESI : SitBaseV2
    {
        VerticalizzazioneSitJesi _vert;

        public SIT_JESI() : base(new NullValidazioneFormaleService())
        {

        }

        public override string[] GetListaCampiGestiti()
        {
            return new[]{
                SitIntegrationService.NomiCampiSit.CodiceCivico,		
				SitIntegrationService.NomiCampiSit.CodiceVia,
				SitIntegrationService.NomiCampiSit.Civico,
                SitIntegrationService.NomiCampiSit.Esponente,
                SitIntegrationService.NomiCampiSit.Sezione,
                SitIntegrationService.NomiCampiSit.Foglio,
                SitIntegrationService.NomiCampiSit.Particella,
				SitIntegrationService.NomiCampiSit.Sub
			};
        }

        public override void SetupVerticalizzazione()
        {
            _vert = new VerticalizzazioneSitJesi(this.Alias, this.Software);

            if (!_vert.Attiva)
            {
                throw new ConfigurationErrorsException("La verticalizzazione SIT_JESI non è attiva");
            }
        }

        public override RetSit ElencoCivici()
        {
            var codVia = this.DataSit.CodVia;

            if (String.IsNullOrEmpty(codVia))
            {
                return RetSit.Errore(MessageCode.ElencoCivici, "Dati non sufficienti per enumerare i civici. Selezionare almeno una via", false);
            }
            
            var adapter = new RequestAdapter();

            var parametri = new Dictionary<string, string>();
            parametri.Add("S", codVia);
            
            var request = adapter.Adatta(AliasEnum.Civici_Jesi_S, this._vert.Password, parametri);

            var srv = new ServiceWrapper<ResponseNumerazioneCivicaJSON>(this._vert.UrlWsBase);
            var response = srv.GetDati(request);

            return new RetSit
            {
                ReturnValue = response.Esito,
                MessageCode = "",
                Message = response.MessaggioErrore,
                DataCollection = response.Dati.Where(x => !String.IsNullOrEmpty(x.NumeroCivico)).Select(x => x.NumeroCivico).Distinct().OrderBy(x => Convert.ToInt32(x)).ToList(),
                DataMap = new Dictionary<string, string>()
            };
        }

        public override RetSit CivicoValidazione()
        {
            var codVia = this.DataSit.CodVia;
            var civico = this.DataSit.Civico;

            if (String.IsNullOrEmpty(this.DataSit.Civico))
            {
                return new RetSit(true);
            }

            var adapter = new RequestAdapter();

            var parametri = new Dictionary<string, string>();
            parametri.Add("S", codVia);
            parametri.Add("C", civico);

            var request = adapter.Adatta(AliasEnum.Civici_Jesi_SC, this._vert.Password, parametri);
            var srv = new ServiceWrapper<ResponseNumerazioneCivicaJSON>(this._vert.UrlWsBase);
            var response = srv.GetDati(request);

            if (!response.Esito)
            {
                return RetSit.Errore(MessageCode.CivicoValidazione, response.MessaggioErrore, false);
            }

            return new RetSit(true);
        }

        public override RetSit ElencoEsponenti()
        {
            var codVia = this.DataSit.CodVia;
            var civico = this.DataSit.Civico;

            if (String.IsNullOrEmpty(codVia) || String.IsNullOrEmpty(civico))
            {
                return RetSit.Errore(MessageCode.ElencoEsponenti, "Dati non sufficienti per recuperare gli esponenti. Selezionare una via e un civico", false);
            }

            var adapter = new RequestAdapter();

            var parametri = new Dictionary<string, string>();
            parametri.Add("S", codVia);
            parametri.Add("C", civico);

            var request = adapter.Adatta(AliasEnum.Civici_Jesi_SC, this._vert.Password, parametri);

            var srv = new ServiceWrapper<ResponseNumerazioneCivicaJSON>(this._vert.UrlWsBase);
            var response = srv.GetDati(request);

            //return new RetSit
            //{
            //    ReturnValue = response.Esito,
            //    MessageCode = "",
            //    Message = response.MessaggioErrore,
            //    DataCollection = response.Dati.Where(x => !String.IsNullOrEmpty(x.Lettera) && x.Lettera != ".").Select(x => x.Lettera).Distinct().OrderBy(x => x).ToList(),
            //    DataMap = new Dictionary<string, string>()
            //};

            return new RetSit
            {
                ReturnValue = response.Esito,
                MessageCode = "",
                Message = response.MessaggioErrore,
                DataCollection = response.Dati.Where(x => !String.IsNullOrEmpty(x.Lettera)).Select(x => x.Lettera).Distinct().OrderBy(x => x).ToList(),
                DataMap = new Dictionary<string, string>()
            };
        }

        public override RetSit EsponenteValidazione()
        {
            var codVia = this.DataSit.CodVia;
            var civico = this.DataSit.Civico;
            var esponente = this.DataSit.Esponente;

            if (String.IsNullOrEmpty(esponente))
            {
                return new RetSit(true);
            }

            //if (esponente == ".")
            //{
            //    return RetSit.Errore(MessageCode.EsponenteValidazione, "Dati non trovati", false);
            //}

            if (String.IsNullOrEmpty(codVia) || String.IsNullOrEmpty(civico))
            {
                return RetSit.Errore(MessageCode.EsponenteValidazione, "Dati non sufficienti per validare l'esponente. Selezionare una via e un civico", false);
            }

            var adapter = new RequestAdapter();

            var parametri = new Dictionary<string, string>();
            parametri.Add("S", codVia);
            parametri.Add("C", civico);

            var request = adapter.Adatta(AliasEnum.Civici_Jesi_SC, this._vert.Password, parametri);

            var srv = new ServiceWrapper<ResponseNumerazioneCivicaJSON>(this._vert.UrlWsBase);
            var response = srv.GetDati(request);

            if (!response.Esito)
            {
                return RetSit.Errore(MessageCode.EsponenteValidazione, response.MessaggioErrore, false);
            }

            var elencoEsponenti = response.Dati.Where(x => !String.IsNullOrEmpty(x.Lettera) && x.Lettera == esponente);

            if (elencoEsponenti.Count() == 0)
            {
                return RetSit.Errore(MessageCode.EsponenteValidazione, "Dati non trovati", false);
            }

            return new RetSit(true);
        }

        public override RetSit ElencoFogli()
        {
            var codVia = this.DataSit.CodVia;
            var civico = this.DataSit.Civico;

            if (String.IsNullOrEmpty(codVia) || String.IsNullOrEmpty(civico))
            {
                return RetSit.Errore(MessageCode.ElencoFogli, "Dati non sufficienti per recuperare i fogli. Selezionare una via e un civico", false);
            }

            var tipoCatasto = this.DataSit.TipoCatasto;

            var adapter = new RequestAdapter();

            var parametri = new Dictionary<string, string>();

            parametri.Add("S", codVia);
            parametri.Add("C", civico);

            var request = adapter.Adatta(AliasEnum.Civici_Cat_Jesi, this._vert.Password, parametri);

            var srv = new ServiceWrapper<ResponseCatastoUrbanoJSON>(this._vert.UrlWsBase);
            var response = srv.GetDati(request);

            return new RetSit
            {
                ReturnValue = response.Esito,
                MessageCode = "",
                Message = response.MessaggioErrore,
                DataCollection = response.Dati.Where(x => !String.IsNullOrEmpty(x.Foglio)).Select(x => x.Foglio).Distinct().OrderBy(x => x).ToList(),
                DataMap = new Dictionary<string, string>()
            };
        }

        public override RetSit FoglioValidazione()
        {
            var codVia = this.DataSit.CodVia;
            var civico = this.DataSit.Civico;
            var foglio = this.DataSit.Foglio;

            if (String.IsNullOrEmpty(foglio))
            {
                return new RetSit(true);
            }

            if (String.IsNullOrEmpty(codVia) || String.IsNullOrEmpty(civico) || String.IsNullOrEmpty(foglio))
            {
                return RetSit.Errore(MessageCode.EsponenteValidazione, "Dati non sufficienti per validare il foglio. Selezionare una via, un civico e un foglio", false);
            }

            var adapter = new RequestAdapter();

            var parametri = new Dictionary<string, string>();
            parametri.Add("S", codVia);
            parametri.Add("C", civico);

            var request = adapter.Adatta(AliasEnum.Civici_Cat_Jesi, this._vert.Password, parametri);

            var srv = new ServiceWrapper<ResponseCatastoUrbanoJSON>(this._vert.UrlWsBase);
            var response = srv.GetDati(request);

            if (!response.Esito)
            {
                return RetSit.Errore(MessageCode.EsponenteValidazione, response.MessaggioErrore, false);
            }

            var elencoEsponenti = response.Dati.Where(x => !String.IsNullOrEmpty(x.Foglio) && x.Foglio == foglio);

            if (elencoEsponenti.Count() == 0)
            {
                return RetSit.Errore(MessageCode.EsponenteValidazione, "Dati non trovati", false);
            }

            return new RetSit(true);
        }

        private RetSit ElencoParticelleTerreni()
        {
            var foglio = this.DataSit.Foglio;

            if (String.IsNullOrEmpty(foglio))
            {
                return RetSit.Errore(MessageCode.ElencoFogli, "Dati non sufficienti per recuperare le particelle. Selezionare un foglio", false);
            }

            var adapter = new RequestAdapter();

            var parametri = new Dictionary<string, string>();
            parametri.Add("F", foglio);

            var request = adapter.Adatta(AliasEnum.Cat_Terr_Jesi_F, this._vert.Password, parametri);

            var srv = new ServiceWrapper<ResponseCatastoTerreniParticelle>(this._vert.UrlWsBase);
            var response = srv.GetDati(request);

            return new RetSit
            {
                ReturnValue = response.Esito,
                MessageCode = "",
                Message = response.MessaggioErrore,
                DataCollection = response.Dati.Where(x => !String.IsNullOrEmpty(x.NumeroParticella)).Select(x => x.NumeroParticella).Distinct().OrderBy(x => x).ToList(),
                DataMap = new Dictionary<string, string>()
            };
        }

        public RetSit ElencoParticelleCatastoUrbano()
        {
            var foglio = this.DataSit.Foglio;
            var codVia = this.DataSit.CodVia;
            var civico = this.DataSit.Civico;

            if (String.IsNullOrEmpty(foglio) || String.IsNullOrEmpty(codVia) || String.IsNullOrEmpty(civico))
            {
                return RetSit.Errore(MessageCode.ElencoFogli, "Dati non sufficienti per recuperare le particelle. Selezionare un foglio", false);
            }

            var adapter = new RequestAdapter();

            var parametri = new Dictionary<string, string>();

            parametri.Add("S", codVia);
            parametri.Add("C", civico);

            var request = adapter.Adatta(AliasEnum.Civici_Cat_Jesi, this._vert.Password, parametri);

            var srv = new ServiceWrapper<ResponseCatastoUrbanoJSON>(this._vert.UrlWsBase);
            var response = srv.GetDati(request);

            return new RetSit
            {
                ReturnValue = response.Esito,
                MessageCode = "",
                Message = response.MessaggioErrore,
                DataCollection = response.Dati.Where(x => x.Foglio == foglio && !String.IsNullOrEmpty(x.NumeroParticella)).Select(x => x.NumeroParticella).Distinct().OrderBy(x => x).ToList(),
                DataMap = new Dictionary<string, string>()
            };
        }

        public override RetSit ElencoParticelle()
        {
            if (this.DataSit.TipoCatasto == "F")
            {
                return ElencoParticelleCatastoUrbano();
            }

            return ElencoParticelleTerreni();
        }

        private RetSit ParticellaValidazioneCatastoUrbano()
        {
            var foglio = this.DataSit.Foglio;
            var codVia = this.DataSit.CodVia;
            var civico = this.DataSit.Civico;
            var particella = this.DataSit.Particella;

            if (String.IsNullOrEmpty(particella))
            {
                return new RetSit(true);
            }

            if (String.IsNullOrEmpty(foglio) || String.IsNullOrEmpty(codVia) || String.IsNullOrEmpty(civico))
            {
                return RetSit.Errore(MessageCode.ParticellaValidazione, "Dati non sufficienti per validare il numero della particella. Selezionare, la via, il civico e il foglio", false);
            }

            var adapter = new RequestAdapter();

            var parametri = new Dictionary<string, string>();
            parametri.Add("S", codVia);
            parametri.Add("C", civico);

            var request = adapter.Adatta(AliasEnum.Civici_Cat_Jesi, this._vert.Password, parametri);

            var srv = new ServiceWrapper<ResponseCatastoUrbanoJSON>(this._vert.UrlWsBase);
            var response = srv.GetDati(request);

            var particelleList = response.Dati.Where(x => x.Foglio == foglio && x.NumeroParticella == particella);
            if (particelleList.Count() == 0)
            {
                response.Esito = false;
                response.MessaggioErrore = "Dati non trovati";
            }

            if (!response.Esito)
            {
                return RetSit.Errore(MessageCode.ParticellaValidazione, response.MessaggioErrore, false);
            }

            return new RetSit(true);
        }

        private RetSit ParticellaValidazioneTerreni()
        {
            var foglio = this.DataSit.Foglio;
            var particella = this.DataSit.Particella;

            if (String.IsNullOrEmpty(particella))
            {
                return new RetSit(true);
            }

            if (String.IsNullOrEmpty(foglio))
            {
                return RetSit.Errore(MessageCode.ParticellaValidazione, "Dati non sufficienti per validare il numero della particella. Selezionare un foglio", false);
            }

            var adapter = new RequestAdapter();

            var parametri = new Dictionary<string, string>();
            parametri.Add("F", foglio);
            parametri.Add("N", particella);
            
            var request = adapter.Adatta(AliasEnum.Cat_Terr_Jesi_FN, this._vert.Password, parametri);

            var srv = new ServiceWrapper<ResponseCatastoTerreniParticelle>(this._vert.UrlWsBase);
            var response = srv.GetDati(request);

            if (!response.Esito)
            {
                return RetSit.Errore(MessageCode.ParticellaValidazione, response.MessaggioErrore, false);
            }

            return new RetSit(true);
        }

        public override RetSit ParticellaValidazione()
        {
            if (this.DataSit.TipoCatasto == "F")
            {
                return ParticellaValidazioneCatastoUrbano();
            }

            return ParticellaValidazioneTerreni();
        }

        public override RetSit ElencoSub()
        {
            var foglio = this.DataSit.Foglio;
            var particella = this.DataSit.Particella;

            if(String.IsNullOrEmpty(foglio) && String.IsNullOrEmpty(particella))
            {
                return RetSit.Errore(MessageCode.ElencoFogli, "Foglio e particella non valorizzati", false);
            }

            var adapter = new RequestAdapter();

            var parametri = new Dictionary<string, string>();
            parametri.Add("F", foglio);
            parametri.Add("N", particella);

            var request = adapter.Adatta(AliasEnum.Cat_Urbano_Jesi_FN, this._vert.Password, parametri);

            var srv = new ServiceWrapper<ResponseDettaglioCatastoUrbanoJSON>(this._vert.UrlWsBase);
            var response = srv.GetDati(request);

            return new RetSit
            {
                ReturnValue = response.Esito,
                MessageCode = "",
                Message = response.MessaggioErrore,
                DataCollection = response.Dati.Where(x => !String.IsNullOrEmpty(x.Subalterno)).Select(x => x.Subalterno).Distinct().OrderBy(x => x).ToList(),
                DataMap = new Dictionary<string, string>()
            };
        }

        public override RetSit SubValidazione()
        {
            var foglio = this.DataSit.Foglio;
            var particella = this.DataSit.Particella;
            var subalterno = this.DataSit.Sub;

            if (String.IsNullOrEmpty(subalterno))
            {
                return new RetSit(true);
            }

            if (String.IsNullOrEmpty(foglio) || String.IsNullOrEmpty(particella) || String.IsNullOrEmpty(subalterno))
            {
                return RetSit.Errore(MessageCode.SubValidazione, "Dati non sufficienti per validare il numero della particella. Selezionare un foglio e un numero di particella", false);
            }

            var adapter = new RequestAdapter();

            var parametri = new Dictionary<string, string>();
            parametri.Add("F", foglio);
            parametri.Add("N", particella);
            parametri.Add("S", subalterno);

            var request = adapter.Adatta(AliasEnum.Cat_Urbano_Jesi_FNS, this._vert.Password, parametri);

            var srv = new ServiceWrapper<ResponseDettaglioCatastoUrbanoJSON>(this._vert.UrlWsBase);
            var response = srv.GetDati(request);

            if (!response.Esito)
            {
                return RetSit.Errore(MessageCode.SubValidazione, response.MessaggioErrore, false);
            }

            return new RetSit(true);
        }

        public override RetSit ElencoSezioni()
        {
            return RetSit.Errore(MessageCode.ElencoSezioni, "Sezione non gestita dall'ente", false);
        }

        public override BaseDto<SitFeatures.TipoVisualizzazione, string>[] GetVisualizzazioniFrontoffice()
        {
            return new[] {
                new BaseDto<SitFeatures.TipoVisualizzazione, string>( SitFeatures.TipoVisualizzazione.PuntoDaIndirizzo, this._vert.UrlPuntoDaIndirizzo)
            };
        }

        //public override BaseDto<SitFeatures.TipoVisualizzazione, string>[] GetVisualizzazioniBackoffice()
        //{
        //    return new[] {
        //        new BaseDto<SitFeatures.TipoVisualizzazione, string>( SitFeatures.TipoVisualizzazione.PuntoDaIndirizzo, this._urlZoomDaPuntoBo)
        //    };
        //}
    }
}
