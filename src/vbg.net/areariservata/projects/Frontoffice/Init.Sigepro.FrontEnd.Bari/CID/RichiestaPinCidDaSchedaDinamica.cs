// -----------------------------------------------------------------------
// <copyright file="RichiestaPinCidDaSchedaDinamica.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.CID
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using Init.Sigepro.FrontEnd.Bari.CID.ServiceProxy;
    using Init.Sigepro.FrontEnd.Bari.CID.DTOs;
using Init.Sigepro.FrontEnd.Bari.Core.Config;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class RichiestaPinCidDaSchedaDinamica
    {
        private class CidConfigService : ICidConfigService
        {
            Uri _url;
            string _username;
            string _password;

            public CidConfigService(string url, string username, string password)
            {
                this._url = new Uri(url);
                this._username = username;
                this._password = password;
            }

            public DatiConfigurazioneDto GetConfigurazioneCid()
            {
                return new DatiConfigurazioneDto(this._url, this._username, this._password);
            }
        }

        private class TributiConfigService : ITributiConfigService
        {

            public ParametriServizioTributi Read()
            {
                return new ParametriServizioTributi();
            }
        }

        BariCidService _cidService;

        public RichiestaPinCidDaSchedaDinamica(string url, string username, string password)
        {
            this._cidService = new BariCidService(new CidConfigService(url, username, password), new TributiConfigService());
        }

        public DatiCidDto LeggiDatiCid(string nomeOperatore, string emailOperatore, string codiceFiscaleOperatore, string codiceFiscaleRichiedente)
        {
            var operatore = new DatiOperatoreDto(nomeOperatore, emailOperatore, codiceFiscaleOperatore);
            var richiesta = new DatiRichiestaDto(String.Empty, codiceFiscaleRichiedente);

            return this._cidService.GetDatiCid(operatore, richiesta);
        }
    }
}
