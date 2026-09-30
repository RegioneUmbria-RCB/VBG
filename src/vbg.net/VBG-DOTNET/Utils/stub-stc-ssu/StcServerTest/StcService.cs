namespace StcServerTest
{
    public class StcService : Stc
    {
        public Task<AggiungiDocumentiResponse> AggiungiDocumentiAsync(AggiungiDocumentiRequest1 request)
        {
            throw new NotImplementedException();
        }

        public Task<AllegatoBinarioResponse1> AllegatoBinarioAsync(AllegatoBinarioRequest1 request)
        {
            throw new NotImplementedException();
        }

        public Task<CancellaAttivitaResponse> CancellaAttivitaAsync(CancellaAttivita request)
        {
            throw new NotImplementedException();
        }

        public async Task<CheckTokenResponse1> CheckTokenAsync(CheckTokenRequest1 request)
        {
            var response = new CheckTokenResponse1();
            response.CheckTokenResponse = new CheckTokenResponse()
            {
                result = true
            };

            return await Task.FromResult(response);
        }

        public Task<DirezioneSportelloResponse1> DirezioneSportelloAsync(DirezioneSportelloRequest1 request)
        {
            throw new NotImplementedException();
        }

        public Task<InserimentoPraticaResponse1> InserimentoPraticaAsync(InserimentoPraticaRequest1 request)
        {
            var pratica = new RiferimentiPraticaType()
            {
                idPratica = Guid.NewGuid().ToString(),
                numeroPratica = DateTime.Now.ToString("yyyyMMdd-mmss"),
                dataPratica = DateTime.Now
            };

            var resp = new InserimentoPraticaResponse1()
            {
                InserimentoPraticaResponse = new InserimentoPraticaResponse()
                {
                    Items = new object[] { pratica }
                }
            };

            return Task.FromResult(resp);
        }

        public Task<LoginResponse1> LoginAsync(LoginRequest1 request)
        {
            var resp = new LoginResponse1()
            {
                LoginResponse = new LoginResponse()
                {
                    result = true,
                    token = Guid.NewGuid().ToString()
                }
            };

            return Task.FromResult(resp);
        }

        public Task<NotificaAttivitaResponse1> NotificaAttivitaAsync(NotificaAttivitaRequest1 request)
        {
            var resp = new NotificaAttivitaResponse1()
            {
                NotificaAttivitaResponse = new NotificaAttivitaResponse()
                {
                    Items = new object[]
                    {
                        new RiferimentiAttivitaType()
                        {
                            idAttivita = request.NotificaAttivitaRequest.datiAttivita.idAttivita,
                            idPratica = request.NotificaAttivitaRequest.datiAttivita.idPratica,
                            idProcedimento = "PROC001"
                        }
                    }
                }
            };


            return Task.FromResult(resp);
        }

        public Task<RichiestaPraticaResponse1> RichiestaPraticaAsync(RichiestaPraticaRequest1 request)
        {
            var res = new RichiestaPraticaResponse1(new RichiestaPraticaResponse
            {
                dettaglioPratica = new DettaglioPraticaVisuraType
                {
                    dettaglioPratica = new DettaglioPraticaType
                    {
                        numeroPratica = request.RichiestaPraticaRequest.rifPratica.numeroPratica,
                        idPratica = request.RichiestaPraticaRequest.rifPratica.idPratica
                    }
                }
            });

            return Task.FromResult(res);
        }

        public Task<RichiestaPraticaCollegataResponse1> RichiestaPraticaCollegataAsync(RichiestaPraticaCollegataRequest1 request)
        {
            throw new NotImplementedException();
        }

        public Task<RichiestaPraticaCollegataDaAttivitaDestinatariaResponse> RichiestaPraticaCollegataDaAttivitaDestinatariaAsync(RichiestaPraticaCollegataDaAttivitaDestinataria request)
        {
            throw new NotImplementedException();
        }

        public Task<RichiestaPraticaCollegataDaAttivitaMittenteResponse> RichiestaPraticaCollegataDaAttivitaMittenteAsync(RichiestaPraticaCollegataDaAttivitaMittente request)
        {
            throw new NotImplementedException();
        }

        public Task<RichiestaPraticheListaResponse1> RichiestaPraticheListaAsync(RichiestaPraticheListaRequest1 request)
        {
            throw new NotImplementedException();
        }

        // Implementa tutti gli altri metodi definiti in Stc
    }

}
