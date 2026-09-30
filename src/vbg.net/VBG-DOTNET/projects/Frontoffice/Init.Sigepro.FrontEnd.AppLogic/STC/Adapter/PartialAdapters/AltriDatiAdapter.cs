using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.GestioneMetadatiToken.Client;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters
{
    internal class AltriDatiAdapter : IStcPartialAdapter
    {
        private static class Constants
        {
            public static class NomiTagsAltriDati
            {
                public const string CodiceAutorizzazione = "Autorizzazioni.Codice";
                public const string NumeroAutorizzazione = "Autorizzazioni.Numero";
                public const string DataAutorizzazione = "Autorizzazioni.Data";
                //public const string CodiceEnteAutorizzazione = "Autorizzazioni.Ente.Codice";
                public const string DescrizioneEnteAutorizzazione = "Autorizzazioni.Ente.Descrizione";
                public const string NumeroPresenzeCalcolateAutorizzazione = "Autorizzazioni.NumeroPresenze.Calcolate";
                public const string NumeroPresenzeDichiarateAutorizzazione = "Autorizzazioni.NumeroPresenze.Dichiarate";
                //public const string DatiUtenzaTaresBari = "Bari.Tares.DatiUtenza";
                public const string Eventi = "AREARISERVATA_EVENTI";
                public const string IdentificativoSuap = "IDENTIFICATIVO_SUAP";
                public const string IdDomandaCollegata = "#CERCA_PRATICA_COLLEGATA_PADRE#";
                public const string MetadatoPraticaDiTest = "ISTANZA_METADATO_PRATICA_TEST";
            }
        }

        private readonly ParametriHelper _parametriHelper = new ParametriHelper();
        private readonly ICodiceAccreditamentoHelper _codiceAccreditamentoHelper;
        private readonly IAuthenticationDataResolver _authenticationDataResolver;
        private readonly IMetadatiTokenUtenteService _metadatiTokenService;

        public AltriDatiAdapter(ICodiceAccreditamentoHelper codiceAccreditamentoHelper, IAuthenticationDataResolver authenticationDataResolver, IMetadatiTokenUtenteService metadatiTokenService)
        {
            this._codiceAccreditamentoHelper = codiceAccreditamentoHelper;
            this._authenticationDataResolver = authenticationDataResolver;
            this._metadatiTokenService = metadatiTokenService;
        }

        public void Adapt(GestionePresentazioneDomanda.IDomandaOnlineReadInterface _readInterface, StcService.DettaglioPraticaType _dettaglioPratica)
        {
            var listaAltriDati = new List<ParametroType>();

            if (_dettaglioPratica.altriDati != null)
                listaAltriDati.AddRange(_dettaglioPratica.altriDati);

            // METADATI DEL TOKEN
            var metadati = this._metadatiTokenService.GetMetadatiTokenUtente();

            foreach (var metadato in metadati)
            {
                listaAltriDati.Add(this._parametriHelper.CreaParametroType(metadato.Nome, metadato.Valore));
            }

            // EVENTI DELL'ISTANZA
            // Gli eventi dell'istanza generati nel FO vengono aggiunti nella sezione AltriDati

            if (_readInterface.AltriDati.Eventi.Any())
            {
                var parametroEventi = new ParametroType
                {
                    nome = Constants.NomiTagsAltriDati.Eventi,
                    valore = _readInterface.AltriDati.Eventi
                                                     .Select(evento => new ValoreParametroType
                                                     {
                                                         codice = evento.Codice,
                                                         descrizione = evento.Descrizione
                                                     })
                                                        .ToArray()
                };

                listaAltriDati.Add(parametroEventi);
            }

            // CODICE ACCREDITAMENTO
            // Il codice accreditamento dello sportello viene aggiunto nella sezione "altriDati"
            var codiceAccreditamento = this._codiceAccreditamentoHelper.GetCodiceAccreditamento();

            if (!String.IsNullOrEmpty(codiceAccreditamento))
            {
                listaAltriDati.Add(this._parametriHelper.CreaParametroType(Constants.NomiTagsAltriDati.IdentificativoSuap, codiceAccreditamento));
            }

            if (_readInterface.AltriDati.IdDomandaCollegata.HasValue)
            {
                listaAltriDati.Add(this._parametriHelper.CreaParametroType(Constants.NomiTagsAltriDati.IdDomandaCollegata, _readInterface.AltriDati.IdDomandaCollegata.Value.ToString()));
            }

            // Dati autorizzazioni
            if (_readInterface.AutorizzazioniMercati.Autorizzazione != null)
            {
                var aut = _readInterface.AutorizzazioniMercati.Autorizzazione;

                listaAltriDati.AddRange(
                    new ParametroType[]{
                        this._parametriHelper.CreaParametroType( Constants.NomiTagsAltriDati.CodiceAutorizzazione, aut.Id.ToString()),
                        this._parametriHelper.CreaParametroType(Constants.NomiTagsAltriDati.NumeroAutorizzazione, aut.Numero),
                        this._parametriHelper.CreaParametroType(Constants.NomiTagsAltriDati.DataAutorizzazione, aut.Data),
                        this._parametriHelper.CreaParametroType(Constants.NomiTagsAltriDati.DescrizioneEnteAutorizzazione, aut.EnteRilascio.Descrizione),
                        this._parametriHelper.CreaParametroType(Constants.NomiTagsAltriDati.NumeroPresenzeCalcolateAutorizzazione, aut.NumeroPresenzeCalcolato),
                        this._parametriHelper.CreaParametroType(Constants.NomiTagsAltriDati.NumeroPresenzeDichiarateAutorizzazione, aut.NumeroPresenzeDichiarato)
                });
            }

            // Se l'utente è un utente tester viene inserito anche il metadato ISTANZA_METADATO_PRATICA_TEST
            if (this._authenticationDataResolver.DatiAutenticazione?.DatiUtente?.UtenteTester ?? false)
            {
                listaAltriDati.Add(this._parametriHelper.CreaParametroType(Constants.NomiTagsAltriDati.MetadatoPraticaDiTest, "1"));
            }

            _dettaglioPratica.altriDati = listaAltriDati.ToArray();
        }
    }
}
