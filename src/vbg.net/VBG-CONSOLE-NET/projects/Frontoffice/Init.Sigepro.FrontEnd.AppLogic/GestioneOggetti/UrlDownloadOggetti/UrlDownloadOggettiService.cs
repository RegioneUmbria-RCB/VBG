using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneConversioneFiles;
using Init.Sigepro.FrontEnd.Infrastructure.DatesAndTimes;
using System;
using System.Globalization;
using System.Linq;
using System.Security.Cryptography;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti
{

    public class UrlDownloadOggettiService : IUrlDownloadOggettiService
    {
        private static class Constants
        {
            public const string UrlDownload = "~/oggetti/download.ashx";
            // public const string UrlDownloadPdf = "~/oggetti/download-pdf.ashx";
            public const string UrlDownloadMultiformato = "~/oggetti/download-multiformato.ashx";
            public const string UrlDownloadCompilabile = "~/oggetti/download-compilabile.ashx";
            public const string UrlDownloadPdfCompilabile = "~/oggetti/download-pdf-compilabile.ashx";
            public const string DateTimeFormat = "yyyyMMddHHmm";
        }

        private readonly ICurrentDateTimeProvider _dateTimeProvider;
        private readonly ITokenResolver _tokenResolver;
        private readonly IAliasSoftwareResolver _aliasResolver;
        private readonly IDownloadOggettiSecretKeyService _secretKeyService;
        private readonly int _finestraMinuti = 20;

        public UrlDownloadOggettiService(ICurrentDateTimeProvider dateTimeProvider, ITokenResolver tokenResolver, IDownloadOggettiSecretKeyService secretKeyService, IAliasSoftwareResolver aliasResolver)
        {
            this._dateTimeProvider = dateTimeProvider;
            this._tokenResolver = tokenResolver;
            this._secretKeyService = secretKeyService;
            this._aliasResolver = aliasResolver;
        }

        public CodiceOggettoDownload GetCodiceOggettoDaEncryptedString(string encryptedString)
        {
            var parts = encryptedString.Split('.');

            if (parts.Length != 4)
            {
                throw new FileChecksumValidationException($"La stringa criptata ha un formato non corretto: {encryptedString}");
            }

            if (!Int32.TryParse(parts[1], out var id))
            {
                throw new FileChecksumValidationException($"La stringa criptata non contiene un id valido: {encryptedString}");
            }

            if (!DateTime.TryParseExact(parts[2], Constants.DateTimeFormat, null, DateTimeStyles.AssumeLocal, out var date))
            {
                throw new FileChecksumValidationException($"La stringa criptata non contiene una data valida: {encryptedString}");
            }

            var dateDelta = this._dateTimeProvider.DateTime - date;

            if (dateDelta.TotalMinutes > this._finestraMinuti)
            {
                throw new FileChecksumValidationException($"La stringa criptata è scaduta: {encryptedString}");
            }

            var dateStr = parts[2];
            var alias = parts[0];
            var chk = parts[3];

            var computed = this.Checksum(alias, id, dateStr);

            if (computed != chk)
            {
                throw new FileChecksumValidationException($"Il checksum della stringa criptata non è valido: {encryptedString}");
            }

            return new CodiceOggettoDownload(alias, id);
        }


        public string GetUrlDownloadPdfCompilabile(int codiceOggetto, int idDomanda)
        {
            return $"{Constants.UrlDownloadPdfCompilabile}?id={this.GetParametroQuerystring(codiceOggetto)}&idPresentazione={idDomanda}";
        }

        public string GetUrlDownloadCompilato(int codiceOggetto, int idDomanda, FormatoConversioneEnum formato, int? idAllegatoDomandaMd5 = null)
        {
            var idAllegato = idAllegatoDomandaMd5.HasValue ?
                                $"&all={idAllegatoDomandaMd5.Value}" :
                                "";

            return $"{Constants.UrlDownloadCompilabile}?id={this.GetParametroQuerystring(codiceOggetto)}&idPresentazione={idDomanda}&f={formato}{idAllegato}";
        }

        public string GetUrlDownload(int codiceOggetto)
        {
            return $"{Constants.UrlDownload}?id={this.GetParametroQuerystring(codiceOggetto)}";
        }

        public string GetUrlDownloadConvertito(int codiceOggetto, FormatoConversioneEnum formato)
        {
            return $"{Constants.UrlDownloadMultiformato}?id={this.GetParametroQuerystring(codiceOggetto)}&f={formato.ToString()}";
        }

        private string GetParametroQuerystring(int codiceOggetto)
        {
            var dateString = this._dateTimeProvider.DateTime.ToString(Constants.DateTimeFormat);
            var alias = this._aliasResolver.AliasComune;

            return $"{alias}.{codiceOggetto}.{dateString}.{this.Checksum(alias, codiceOggetto, dateString)}";
        }

        private string Checksum(string alias, int codiceOggetto, string dateString)
        {
            var token = this._tokenResolver.Token;

            if (String.IsNullOrEmpty(token))
            {
                token = "ANONIMO";
            }

            var secret = UTF8Encoding.UTF8.GetBytes(this._secretKeyService.Secret);
            var qs = $"{alias}.{codiceOggetto}.{dateString}.{this._tokenResolver.Token}";
            var qsBytes = UTF8Encoding.UTF8.GetBytes(qs);
            var hash = new HMACSHA256(secret).ComputeHash(qsBytes);

            return String.Join("", hash.Select(x => x.ToString("x2")));
        }
    }
}
