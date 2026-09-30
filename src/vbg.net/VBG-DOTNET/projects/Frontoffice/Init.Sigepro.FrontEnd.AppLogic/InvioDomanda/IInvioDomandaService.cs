using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.InvioDomanda
{
    public class ValidazioneIstanzaResult
    {
        public bool IsSuccess { get; }
        public IEnumerable<string>? ErrorDetail { get; }

        protected ValidazioneIstanzaResult(bool isSuccess, IEnumerable<string>? errorDetail)
        {
            if (isSuccess && errorDetail != null)
            {
                throw new InvalidOperationException($"{nameof(errorDetail)} non può essere valorizzato se {nameof(isSuccess)} è true");
            }

            if (!isSuccess && errorDetail == null)
            {
                throw new InvalidOperationException($"{nameof(errorDetail)} non può essere null se {nameof(isSuccess)} è false");
            }

            this.IsSuccess = isSuccess;
            this.ErrorDetail = errorDetail;
        }

        public static ValidazioneIstanzaResult Success() => new ValidazioneIstanzaResult(true, null);
        public static ValidazioneIstanzaResult Failure(IEnumerable<string> errorDetails) => new ValidazioneIstanzaResult(false, errorDetails);
    }


    public interface IInvioDomandaService
    {
        Task<ValidazioneIstanzaResult> ValidaDomandaAsync(int idDomanda);
        Task<InvioIstanzaResult> InviaDomandaAsync(int idDomanda, ParametriInvioDomanda parametriInvio);
    }
}
