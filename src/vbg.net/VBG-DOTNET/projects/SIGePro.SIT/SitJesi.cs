using Init.SIGePro.Manager.DTO;
using Init.SIGePro.Manager.Verticalizzazioni;
using Init.SIGePro.Sit.Data;
using Init.SIGePro.Sit.Jesi;
using Init.SIGePro.Sit.Manager;
using Init.SIGePro.Sit.ValidazioneFormale;
using log4net;
using SIGePro.SIT.Jesi.Request;
using System;
using System.Diagnostics;
using System.Linq;

namespace Init.SIGePro.Sit
{
    public class SIT_JESI : SitBaseV2
    {
        private VerticalizzazioneSitJesi _vert;
        private AuthenticationRestClient _authClient;
        private readonly ILog _log = LogManager.GetLogger(typeof(SIT_JESI));

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
                SitIntegrationService.NomiCampiSit.Sub,
                SitIntegrationService.NomiCampiSit.Fabbricato,
                SitIntegrationService.NomiCampiSit.TipoCatasto
            };
        }

        public override void SetupVerticalizzazione()
        {
            this._vert = new VerticalizzazioneSitJesi(this.Alias, this.Software);
            this._authClient = new AuthenticationRestClient(this._vert);

            if (!this._vert.Attiva)
            {
                throw new Exception("La verticalizzazione SIT_JESI non è attiva");
            }
        }

        public override RetSit ElencoFabbricati()
        {
            var civico = this.DataSit.Civico;
            var codiceViario = this.DataSit.CodVia;
            var esponente = this.DataSit.Esponente;

            if (String.IsNullOrEmpty(civico) || String.IsNullOrEmpty(codiceViario))
            {
                return RetSit.Errore(MessageCode.ElencoFabbricati, "Dati non sufficienti per enumerare i fabbricati. Selezionare indirizzo e civico", false);
            }

            // se l'utente non specifica un esponente uso il punto
            if (string.IsNullOrEmpty(esponente))
                esponente = ".";

            var adapter = new RequestAdapter();

            var parametri = new RequestSCL
            {
                S = codiceViario,
                C = civico,
                L = esponente
            };

            var request = adapter.Adatta<RequestSCL>(AliasEnum.Ind_Edi, parametri);

            var srv = new ServiceWrapper<ResponseEdificiJSON>(this._vert.UrlWsBase, this._vert.Username, this._vert.Password);

            if (srv.QWS(request, this._authClient))
            {
                var data = srv.GetSuccessResponse();

                return new RetSit(
                    true,
                    data.SelectMany(innerList => innerList)
                        .Where(x => !String.IsNullOrEmpty(x.CodiceEdificio))
                        .Select(x => x.CodiceEdificio)
                        .Distinct()
                        .OrderBy(x => x)
                        .ToList()
                );
            }
            else
            {
                var errors = srv.GetErrorResponse();
                return RetSit.Errore(MessageCode.ElencoFabbricati, string.Join(" -- ", errors.Select(err => err.Message)), true);
            }
        }

        // vecchio metodo
        //public override RetSit ElencoFabbricati()
        //{
        //    var foglio = this.DataSit.Foglio;
        //    var particella = this.DataSit.Particella;

        //    if (String.IsNullOrEmpty(foglio))
        //    {
        //        return RetSit.Errore(MessageCode.ElencoFabbricati, "Dati non sufficienti per enumerare i fabbricati. Selezionare i dati catastali", false);
        //    }

        //    var adapter = new RequestAdapter();

        //    var parametri = new List<Parametro>() { new Parametro() { Chiave = "F", Valore = foglio }, new Parametro() { Chiave = "N", Valore = particella } };

        //    var request = adapter.Adatta(AliasEnum.Cat_Edifici_Jesi, parametri);

        //    var srv = new ServiceWrapper<ResponseEdificiJSON>(this._vert.UrlWsBase, this._vert.Username, this._vert.Password);
        //    var response = srv.QWS(request, this._authClient);

        //    return new RetSit
        //    {
        //        ReturnValue = true,
        //        MessageCode = "",
        //        Message = response.ErrorResponse?.Message,
        //        DataCollection = response.HxSucc.SelectMany(innerList => innerList).Where(x => !String.IsNullOrEmpty(x.CodiceEdificio)).Select(x => x.CodiceEdificio).Distinct().OrderBy(x => x).ToList(),
        //        DataMap = new Dictionary<string, string>()
        //    };
        //}

        public override RetSit ElencoCivici()
        {
            var codVia = this.DataSit.CodVia;

            if (String.IsNullOrEmpty(codVia))
            {
                return RetSit.Errore(MessageCode.ElencoCivici, "Dati non sufficienti per enumerare i civici. Selezionare almeno una via", false);
            }

            var adapter = new RequestAdapter();

            var parametri = new RequestS
            {
                S = codVia
            };

            var request = adapter.Adatta(AliasEnum.Civici_Jesi_S, parametri);

            var sw = new Stopwatch();
            sw.Start();

            try
            {
                var srv = new ServiceWrapper<ResponseNumerazioneCivicaJSON>(this._vert.UrlWsBase, this._vert.Username, this._vert.Password);

                if (srv.QWS(request, this._authClient))
                {



                    var data = srv.GetSuccessResponse();
                    return new RetSit(
                        true,
                        data.SelectMany(innerList => innerList) // Flatten the nested lists
                            .Where(x => x.NumeroCivico.HasValue && String.IsNullOrEmpty(this.DataSit.Civico) ? true : x.NumeroCivico.ToString().StartsWith(this.DataSit.Civico))
                            .OrderBy(x => x.NumeroCivico.Value)
                            .Select(x => x.NumeroCivico.Value.ToString())
                            .Distinct()
                            .ToArray()
                    );

                }
                else
                {
                    var errors = srv.GetErrorResponse();
                    return RetSit.Errore(MessageCode.ElencoCivici, string.Join(" -- ", errors.Select(err => err.Message)), true);
                }

            }
            finally
            {
                sw.Stop();
                Debug.WriteLine($"Interrogazione al SIT eseguita in {sw.ElapsedMilliseconds}ms");
            }
        }

        public override RetSit CivicoValidazione()
        {
            var codVia = this.DataSit.CodVia;
            var civico = this.DataSit.Civico;

            if (String.IsNullOrEmpty(this.DataSit.Civico))
            {
                return new RetSit(true);
            }

            var parametri = new RequestSC
            {
                S = codVia,
                C = civico
            };

            return this.GeneraRispostaValidazione<RequestSC, ResponseNumerazioneCivicaJSON>(
                AliasEnum.Civici_Jesi_SC,
                parametri,
                x => x.NumeroCivico.HasValue && x.NumeroCivico.ToString() == civico,
                MessageCode.CivicoValidazione);
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

            var parametri = new RequestSC
            {
                S = codVia,
                C = civico
            };

            var request = adapter.Adatta(AliasEnum.Civici_Jesi_SC, parametri);

            var srv = new ServiceWrapper<ResponseNumerazioneCivicaJSON>(this._vert.UrlWsBase, this._vert.Username, this._vert.Password);

            if (srv.QWS(request, this._authClient))
            {
                var data = srv.GetSuccessResponse();
                return new RetSit(
                    true,
                    data.SelectMany(innerList => innerList)
                        .Where(x => !String.IsNullOrEmpty(x.Lettera)) //  && x.Lettera != "."
                        .Select(x => x.Lettera)
                        .Distinct()
                        .OrderBy(x => x)
                        .ToList()
                );
            }
            else
            {
                var errors = srv.GetErrorResponse();
                return RetSit.Errore(MessageCode.ElencoEsponenti, string.Join(" -- ", errors.Select(err => err.Message)), true);
            }
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

            var parametri = new RequestSC
            {
                S = codVia,
                C = civico
            };

            return this.GeneraRispostaValidazione<RequestSC, ResponseNumerazioneCivicaJSON>(
                AliasEnum.Civici_Jesi_SC,
                parametri,
                x => !String.IsNullOrEmpty(x.Lettera) && x.Lettera == esponente,
                MessageCode.EsponenteValidazione);
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

            var parametri = new RequestSC
            {
                S = codVia,
                C = civico
            };

            var request = adapter.Adatta(AliasEnum.Civici_Cat_Jesi, parametri);

            var srv = new ServiceWrapper<ResponseCatastoUrbanoJSON>(this._vert.UrlWsBase, this._vert.Username, this._vert.Password);

            if (srv.QWS(request, this._authClient))
            {
                var data = srv.GetSuccessResponse();
                return new RetSit(
                    true,
                    data.SelectMany(innerList => innerList)
                        .Where(x => x.Foglio.HasValue)
                        .Select(x => x.Foglio.ToString())
                        .Distinct()
                        .OrderBy(x => Convert.ToInt32(x))
                        .ToList()
                );
            }
            else
            {
                var errors = srv.GetErrorResponse();
                return RetSit.Errore(MessageCode.ElencoFogli, string.Join(" -- ", errors.Select(err => err.Message)), true);
            }
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
                return RetSit.Errore(MessageCode.FoglioValidazione, "Dati non sufficienti per validare il foglio. Selezionare una via, un civico e un foglio", false);
            }

            var parametri = new RequestSC
            {
                S = codVia,
                C = civico
            };

            return this.GeneraRispostaValidazione<RequestSC, ResponseCatastoUrbanoJSON>(
                AliasEnum.Civici_Cat_Jesi,
                parametri,
                x => x.Foglio.HasValue && x.Foglio.ToString() == foglio,
                MessageCode.FoglioValidazione);
        }

        private RetSit ElencoParticelleTerreni()
        {
            var foglio = this.DataSit.Foglio;

            if (String.IsNullOrEmpty(foglio))
            {
                return RetSit.Errore(MessageCode.ElencoParticelle, "Dati non sufficienti per recuperare le particelle. Selezionare un foglio", false);
            }

            var adapter = new RequestAdapter();

            var parametri = new RequestF
            {
                F = foglio
            };

            var request = adapter.Adatta(AliasEnum.Cat_Terr_Jesi_F, parametri);

            var srv = new ServiceWrapper<ResponseCatastoTerreniParticelle>(this._vert.UrlWsBase, this._vert.Username, this._vert.Password);

            if (srv.QWS(request, this._authClient))
            {
                var data = srv.GetSuccessResponse();
                return new RetSit(
                    true,
                    data.SelectMany(innerList => innerList)
                        .Where(x => !String.IsNullOrEmpty(x.NumeroParticella))
                        .Select(x => x.NumeroParticella)
                        .Distinct()
                        .OrderBy(x => x)
                        .ToList()
                );
            }
            else
            {
                var errors = srv.GetErrorResponse();
                return RetSit.Errore(MessageCode.ElencoParticelle, string.Join(" -- ", errors.Select(err => err.Message)), true);
            }
        }

        public RetSit ElencoParticelleCatastoUrbano()
        {
            var foglio = this.DataSit.Foglio;
            var codVia = this.DataSit.CodVia;
            var civico = this.DataSit.Civico;

            if (String.IsNullOrEmpty(foglio) || String.IsNullOrEmpty(codVia) || String.IsNullOrEmpty(civico))
            {
                return RetSit.Errore(MessageCode.ElencoParticelle, "Dati non sufficienti per recuperare le particelle. Selezionare un foglio", false);
            }

            var adapter = new RequestAdapter();

            var parametri = new RequestSC
            {
                S = codVia,
                C = civico
            };

            var request = adapter.Adatta(AliasEnum.Civici_Cat_Jesi, parametri);

            var srv = new ServiceWrapper<ResponseCatastoUrbanoJSON>(this._vert.UrlWsBase, this._vert.Username, this._vert.Password);

            if (srv.QWS(request, this._authClient))
            {
                var data = srv.GetSuccessResponse();
                return new RetSit(
                    true,
                    data.SelectMany(innerList => innerList)
                        .Where(x => x.Foglio.ToString() == foglio && !String.IsNullOrEmpty(x.NumeroParticella))
                        .Select(x => x.NumeroParticella)
                        .Distinct()
                        .OrderBy(x => x)
                        .ToList()
                );
            }
            else
            {
                var errors = srv.GetErrorResponse();
                return RetSit.Errore(MessageCode.ElencoParticelle, string.Join(" -- ", errors.Select(err => err.Message)), true);
            }
        }

        public override RetSit ElencoParticelle()
        {
            if (this.DataSit.TipoCatasto == "F")
            {
                return this.ElencoParticelleCatastoUrbano();
            }

            return this.ElencoParticelleTerreni();
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

            this._log.DebugFormat("Invocazione di ParticellaValidazioneCatastoUrbano con parametri [S = {0}] [C = {1}]", codVia, civico);

            var parametri = new RequestSC
            {
                S = codVia,
                C = civico
            };

            return this.GeneraRispostaValidazione<RequestSC, ResponseCatastoUrbanoJSON>(
                AliasEnum.Civici_Cat_Jesi,
                parametri,
                x => x.Foglio.ToString() == foglio && x.NumeroParticella == particella,
                MessageCode.ParticellaValidazione);
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

            this._log.DebugFormat("Invocazione di ParticellaValidazioneTerreni con parametri [F = {0}] [N = {1}]", foglio, particella);

            var parametri = new RequestFN
            {
                F = foglio,
                N = particella
            };

            return this.GeneraRispostaValidazione<RequestFN, ResponseCatastoTerreniParticelle>(
                AliasEnum.Cat_Terr_Jesi_FN,
                parametri,
                x => x.Foglio.ToString() == foglio && x.NumeroParticella == particella,
                MessageCode.ParticellaValidazione);
        }

        public override RetSit ParticellaValidazione()
        {
            this._log.DebugFormat("Invocazione di ParticellaValidazione. Tipo catasto: {0}", this.DataSit.TipoCatasto);

            if (this.DataSit.TipoCatasto == "F")
            {
                return this.ParticellaValidazioneCatastoUrbano();
            }

            return this.ParticellaValidazioneTerreni();
        }

        public override RetSit ElencoSub()
        {
            var foglio = this.DataSit.Foglio;
            var particella = this.DataSit.Particella;

            if (String.IsNullOrEmpty(foglio) && String.IsNullOrEmpty(particella))
            {
                return RetSit.Errore(MessageCode.ElencoSub, "Foglio e particella non valorizzati", false);
            }

            var adapter = new RequestAdapter();

            var parametri = new RequestFN
            {
                F = foglio,
                N = particella
            };

            var request = adapter.Adatta(AliasEnum.Cat_Urbano_Jesi_FN, parametri);

            var srv = new ServiceWrapper<ResponseDettaglioCatastoUrbanoJSON>(this._vert.UrlWsBase, this._vert.Username, this._vert.Password);

            if (srv.QWS(request, this._authClient))
            {
                var data = srv.GetSuccessResponse();
                return new RetSit(
                    true,
                    data.SelectMany(innerList => innerList)
                        .Where(x => !String.IsNullOrEmpty(x.Subalterno))
                        .Select(x => x.Subalterno)
                        .Distinct()
                        .OrderBy(x => x)
                        .ToList()
                );
            }
            else
            {
                var errors = srv.GetErrorResponse();
                return RetSit.Errore(MessageCode.ElencoSub, string.Join(" -- ", errors.Select(err => err.Message)), true);
            }
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

            this._log.DebugFormat("Invocazione di SubValidazione con parametri [F = {0}] [N = {1} [S = {2}]]", foglio, particella, subalterno);

            var parametri = new RequestFNS
            {
                F = foglio,
                N = particella,
                S = subalterno
            };

            return this.GeneraRispostaValidazione<RequestFNS, ResponseDettaglioCatastoUrbanoJSON>(
                AliasEnum.Cat_Urbano_Jesi_FNS,
                parametri,
                x => !String.IsNullOrEmpty(x.Subalterno) && x.Subalterno == subalterno,
                MessageCode.SubValidazione);
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

        public override RetSit FabbricatoValidazione()
        {
            return new RetSit(true);
        }

        //public override BaseDto<SitFeatures.TipoVisualizzazione, string>[] GetVisualizzazioniBackoffice()
        //{
        //    return new[] {
        //        new BaseDto<SitFeatures.TipoVisualizzazione, string>( SitFeatures.TipoVisualizzazione.PuntoDaIndirizzo, this._urlZoomDaPuntoBo)
        //    };
        //}

        //private RetSit GeneraRispostaValidazione<T>(AliasEnum metodo, IRequest parametri, Func<T, bool> whereCondition, MessageCode messageCode)
        //{
        //    var adapter = new RequestAdapter();
        //    var request = adapter.Adatta(metodo, parametri);
        //    var srv = new ServiceWrapper<T>(this._vert.UrlWsBase, this._vert.Username, this._vert.Password);

        //    if (srv.QWS(request, this._authClient, true))
        //    {
        //        var data = srv.GetSuccessResponse();

        //        var found = data.SelectMany(innerList => innerList).Where(whereCondition).Any();

        //        if (found)
        //        {
        //            return new RetSit(true);
        //        }

        //        return RetSit.Errore(messageCode, "Dati non trovati", false);
        //    }
        //    else
        //    {
        //        var errors = srv.GetErrorResponse();
        //        return RetSit.Errore(messageCode, string.Join(" -- ", errors.Select(err => err.Message)), true);
        //    }
        //}

        private RetSit GeneraRispostaValidazione<TRequest, TResponse>(AliasEnum metodo, TRequest parametri, Func<TResponse, bool> whereCondition, MessageCode messageCode)
        {
            var adapter = new RequestAdapter();
            var request = adapter.Adatta(metodo, parametri);
            var srv = new ServiceWrapper<TResponse>(this._vert.UrlWsBase, this._vert.Username, this._vert.Password);

            if (srv.QWS(request, this._authClient, true))
            {
                var data = srv.GetSuccessResponse();

                var found = data.SelectMany(innerList => innerList).Where(whereCondition).Any();

                if (found)
                {
                    return new RetSit(true);
                }

                return RetSit.Errore(messageCode, "Dati non trovati", false);
            }
            else
            {
                var errors = srv.GetErrorResponse();
                return RetSit.Errore(messageCode, string.Join(" -- ", errors.Select(err => err.Message)), true);
            }
        }
    }
}
