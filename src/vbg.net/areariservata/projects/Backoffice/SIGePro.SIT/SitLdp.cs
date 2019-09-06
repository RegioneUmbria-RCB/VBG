using Init.SIGePro.Manager.Verticalizzazioni;
using Init.SIGePro.Sit.Data;
using Init.SIGePro.Sit.Manager;
using Init.SIGePro.Sit.SitLdp;
using Init.SIGePro.Sit.Utils;
using Init.SIGePro.Sit.ValidazioneFormale;
using log4net;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit
{
    public class SIT_LDP : SitBaseV2
    {
        private static class Constants
        {
            public const string CiviciDefaultAddress = "https://ws.ldpgis.it/siena/civici.php";
            public const string CatastoDefaultAddress = "https://ws.ldpgis.it/siena/catasto.php";
        }

        ILog _log = LogManager.GetLogger(typeof(SIT_LDP));
        LdpCiviciClient _civiciClient = null;
        LdpCatastoClient _catastoClient = null;
        VerticalizzazioneSitLdp _vert = null;

        public SIT_LDP() : base(new ValidazioneFormaleTramiteCodiceCivicoService())
        {

        }

        public override void SetupVerticalizzazione()
        {
            this._vert = new VerticalizzazioneSitLdp(this.Alias, this.Software);

            if (!this._vert.Attiva)
            {
                throw new ConfigurationErrorsException("La verticalizzazione SIT_LDP non è attiva");
            }

            var credentials = new BasicSoapAuthenticationCredentials(this._vert.Username, this._vert.Password);

            this._civiciClient = new LdpCiviciClient(this._vert.UrlServizioCivici, credentials);
            this._catastoClient = new LdpCatastoClient(this._vert.UrlServizioCatasto, credentials);
        }

        #region Lettura liste e validazione civici ed esponenti
        public override Data.RetSit ElencoCivici()
        {
            var codViario = this.DataSit.CodVia;
            var elenco = this._civiciClient.GetCiviciByToponimo(codViario);

            return new Data.RetSit(true, elenco);
        }

        public override Data.RetSit ElencoEsponenti()
        {
            var codViario = this.DataSit.CodVia;
            var civico = this.DataSit.Civico;
            var elenco = this._civiciClient.GetEsponentiByToponimoECivco(codViario, civico);

            return new Data.RetSit(true, elenco);
        }

        public override Data.RetSit CivicoValidazione()
        {
            return EsponenteValidazione();
        }

        public override Data.RetSit EsponenteValidazione()
        {
            this.DataSit.Sezione = String.Empty;
            this.DataSit.Foglio = String.Empty;
            this.DataSit.Particella = String.Empty;
            this.DataSit.Sub = String.Empty;
            this.DataSit.AccessoTipo = "";
            this.DataSit.AccessoNumero = "";
            this.DataSit.AccessoDescrizione = "";

            var codViario = this.DataSit.CodVia;
            var civico = this.DataSit.Civico;
            var esponente = this.DataSit.Esponente;
            var esito = this._civiciClient.IsValidCivico(codViario, civico, esponente);

            if (String.IsNullOrEmpty(civico))
            {
                this.DataSit.Esponente = "";
                return new Data.RetSit(true);
            }

            if (String.IsNullOrEmpty(codViario))
            {
                return new Data.RetSit
                {
                    ReturnValue = false,
                    Message = "E' obbligatorio specificare un indirizzo"
                };
            }

            if (!esito)
            {
                return Data.RetSit.Errore(MessageCode.EsponenteValidazione, "Civico / esponente non trovato", false);
            }

            // Se abilitato, recupero dei dati relativi ai passi carrai.
            if (this._vert.AbilitaPassiCarrai == "1")
            {
                var passiCarrai = this._civiciClient.GetAccessiPassiCarraiByCivico(codViario, civico, esponente);

                if (passiCarrai.Count() == 1)
                {
                    this.DataSit.AccessoTipo = passiCarrai.First().p_r;
                    this.DataSit.AccessoNumero = passiCarrai.First().id.ToString();
                    this.DataSit.AccessoDescrizione = passiCarrai.First().tipo;
                }
            }

            // Il civico esiste, leggo le particelle collegate e se ne trovo solamente una precompilo i campi
            var particelle = this._civiciClient.GetParticelleByToponimoCivicoEsponente(codViario, civico, esponente);
            var numParticelle = particelle.Count();

            // Se esiste solo una particella collegata al civico la riporto direttamente (vd codVia=300, civico=5)
            if (numParticelle == 1)
            {
                var p = particelle.First();

                this.DataSit.Sezione = p.Sezione ?? String.Empty;
                this.DataSit.Foglio = p.Foglio ?? String.Empty;
                this.DataSit.Particella = p.Particella ?? String.Empty;
            }

            // Se esiste più di una particella particella collegata al civico cerco di vedere se almeno esiste un solo foglio. 
            // In tal caso riporto solo quello (vd codVia=430, civico=5)
            var distinctFoglio = particelle.Select(x => new
            {
                Sezione = x.Sezione,
                Foglio = x.Foglio
            })
                                           .Distinct();

            if (distinctFoglio.Count() == 1)
            {
                var p = distinctFoglio.First();

                this.DataSit.Sezione = p.Sezione ?? String.Empty;
                this.DataSit.Foglio = p.Foglio ?? String.Empty;
            }

            ImpostaCodCivicoSeValido();

            return new Data.RetSit(true);
        }

        public override RetSit ElencoAccessoTipo()
        {
            var codViario = this.DataSit.CodVia;
            var civico = this.DataSit.Civico;
            var esponente = this.DataSit.Esponente;

            var elenco = this._civiciClient.GetAccessoTipo(codViario, civico, esponente);

            return new RetSit(true, elenco);
        }

        public override RetSit AccessoTipoValidazione()
        {
            var codViario = this.DataSit.CodVia;
            var civico = this.DataSit.Civico;
            var esponente = this.DataSit.Esponente;
            var accessoTipo = this.DataSit.AccessoTipo;
            var esito = this._civiciClient.IsValidCivico(codViario, civico, esponente);

            if (String.IsNullOrEmpty(accessoTipo))
            {
                return new Data.RetSit(true);
            }

            if (String.IsNullOrEmpty(civico))
            {
                return new Data.RetSit(true);
            }

            if (String.IsNullOrEmpty(codViario))
            {
                return new Data.RetSit
                {
                    ReturnValue = false,
                    Message = "E' obbligatorio specificare un indirizzo"
                };
            }

            if (!esito)
            {
                return Data.RetSit.Errore(MessageCode.AccessoTipoValidazione, "I dati immessi non permettono di identificare univocamente un civico", false);
            }

            var passiCarraiTipo = this._civiciClient.GetAccessoNumeroByTipo(codViario, civico, esponente, accessoTipo);

            if (passiCarraiTipo.Count() == 0)
            {
                return Data.RetSit.Errore(MessageCode.AccessoTipoValidazione, "Accesso passo carraio non trovato", false);
            }

            if (passiCarraiTipo.Count() == 1)
            {
                this.DataSit.AccessoNumero = passiCarraiTipo.First().id.ToString();
                this.DataSit.AccessoDescrizione = passiCarraiTipo.First().tipo;
            }

            return new Data.RetSit(true);
        }

        public override RetSit AccessoNumeroValidazione()
        {
            var codViario = this.DataSit.CodVia;
            var civico = this.DataSit.Civico;
            var esponente = this.DataSit.Esponente;
            var accessoTipo = this.DataSit.AccessoTipo;
            var accessoNumero = this.DataSit.AccessoNumero;
            var esito = this._civiciClient.IsValidCivico(codViario, civico, esponente);

            if (String.IsNullOrEmpty(accessoTipo))
            {
                return new Data.RetSit(true);
            }

            if (String.IsNullOrEmpty(accessoNumero))
            {
                return new Data.RetSit(true);
            }

            if (String.IsNullOrEmpty(civico))
            {
                return new Data.RetSit(true);
            }

            if (String.IsNullOrEmpty(codViario))
            {
                return new Data.RetSit
                {
                    ReturnValue = false,
                    Message = "E' obbligatorio specificare un indirizzo"
                };
            }

            if (!esito)
            {
                return Data.RetSit.Errore(MessageCode.AccessoNumeroValidazione, "I dati immessi non permettono di identificare univocamente un civico", false);
            }

            var passiCarraiNumero = this._civiciClient.GetAccessoNumeroByTipoeNumero(codViario, civico, esponente, accessoTipo, accessoNumero);

            if (passiCarraiNumero.Count() == 0)
            {
                return Data.RetSit.Errore(MessageCode.AccessoNumeroValidazione, "Numero accesso passo carraio non trovato", false);
            }

            if (passiCarraiNumero.Count() == 1)
            {
                this.DataSit.AccessoDescrizione = passiCarraiNumero.First().tipo;
            }

            return new Data.RetSit(true);
        }

        public override RetSit AccessoDescrizioneValidazione()
        {
            var codViario = this.DataSit.CodVia;
            var civico = this.DataSit.Civico;
            var esponente = this.DataSit.Esponente;
            var accessoTipo = this.DataSit.AccessoTipo;
            var accessoNumero = this.DataSit.AccessoNumero;
            var accessoDescrizione = this.DataSit.AccessoDescrizione;

            var esito = this._civiciClient.IsValidCivico(codViario, civico, esponente);

            if (String.IsNullOrEmpty(accessoTipo))
            {
                return new Data.RetSit(true);
            }

            if (String.IsNullOrEmpty(accessoNumero))
            {
                return new Data.RetSit(true);
            }

            if (String.IsNullOrEmpty(accessoDescrizione))
            {
                return new Data.RetSit(true);
            }

            if (String.IsNullOrEmpty(civico))
            {
                return new Data.RetSit(true);
            }

            if (String.IsNullOrEmpty(codViario))
            {
                return new Data.RetSit
                {
                    ReturnValue = false,
                    Message = "E' obbligatorio specificare un indirizzo"
                };
            }

            if (!esito)
            {
                return Data.RetSit.Errore(MessageCode.AccessoDescrizioneValidazione, "I dati immessi non permettono di identificare univocamente un civico", false);
            }

            var isValidDescrizioneAccesso = this._civiciClient.IsValidAccessoDescrizione(codViario, civico, esponente, accessoTipo, accessoNumero, accessoDescrizione);

            if (!isValidDescrizioneAccesso)
            {
                return Data.RetSit.Errore(MessageCode.AccessoDescrizioneValidazione, "Descrizione accesso passo carraio non valida", false);
            }

            return new Data.RetSit(true);
        }

        #endregion

        #region Lettura liste e validazione civici ed esponenti
        public override Data.RetSit ElencoSezioni()
        {
            ILdpCatastoClient client = this._catastoClient;

            if (this.DatiCatastaliPresenti())
            {
                var codViario = this.DataSit.CodVia;
                var civico = this.DataSit.Civico;
                var esponente = this.DataSit.Esponente;

                client = new LdpCatastoByCiviciClient(codViario, civico, esponente, this._civiciClient);
            }

            return new Data.RetSit(true, client.GetListaSezioni());
        }

        public override Data.RetSit SezioneValidazione()
        {
            var fogliEsistenti = ElencoFogli();

            if (fogliEsistenti.DataCollection.Count > 0)
            {
                return new Data.RetSit(true);
            }

            return Data.RetSit.Errore(MessageCode.SezioneValidazione, "La sezione immessa non è stata trovata", false);
        }

        public override Data.RetSit ElencoFogli()
        {
            ILdpCatastoClient client = this._catastoClient;
            var sezione = this.DataSit.Sezione;

            if (this.DatiCatastaliPresenti())
            {
                var codViario = this.DataSit.CodVia;
                var civico = this.DataSit.Civico;
                var esponente = this.DataSit.Esponente;

                client = new LdpCatastoByCiviciClient(codViario, civico, esponente, this._civiciClient);
            }

            return new Data.RetSit(true, client.GetListaFogli(sezione));
        }

        public override Data.RetSit FoglioValidazione()
        {
            this.DataSit.Particella = String.Empty;
            this.DataSit.Sub = String.Empty;

            if (String.IsNullOrEmpty(this.DataSit.Foglio))
            {
                return new Data.RetSit(true);
            }

            var particelleEsistenti = ElencoParticelle();

            if (particelleEsistenti.DataCollection.Count > 0)
            {
                return new Data.RetSit(true);
            }

            return Data.RetSit.Errore(MessageCode.FoglioValidazione, "Il foglio immesso non è stata trovato", false);
        }

        public override Data.RetSit ElencoParticelle()
        {
            ILdpCatastoClient client = this._catastoClient;
            var sezione = this.DataSit.Sezione;
            var foglio = this.DataSit.Foglio;

            if (this.DatiCatastaliPresenti())
            {
                var codViario = this.DataSit.CodVia;
                var civico = this.DataSit.Civico;
                var esponente = this.DataSit.Esponente;

                client = new LdpCatastoByCiviciClient(codViario, civico, esponente, this._civiciClient);
            }

            return new Data.RetSit(true, client.GetListaParticelle(sezione, foglio));
        }

        public override Data.RetSit ParticellaValidazione()
        {
            this.DataSit.Sub = String.Empty;

            if (String.IsNullOrEmpty(this.DataSit.Particella))
            {
                return new Data.RetSit(true);
            }

            var subEsistenti = ElencoSub();

            if (subEsistenti.DataCollection.Count > 0)
            {
                return new Data.RetSit(true);
            }

            return Data.RetSit.Errore(MessageCode.ParticellaValidazione, "La particella immessa non è stata trovata", false);
        }

        public override Data.RetSit ElencoSub()
        {
            var sezione = this.DataSit.Sezione;
            var foglio = this.DataSit.Foglio;
            var particella = this.DataSit.Particella;

            return new Data.RetSit(true, this._catastoClient.GetListaSubalterni(sezione, foglio, particella));
        }

        public override Data.RetSit SubValidazione()
        {
            var sezione = this.DataSit.Sezione;
            var foglio = this.DataSit.Foglio;
            var particella = this.DataSit.Particella;
            var sub = this.DataSit.Sub;

            var result = this._catastoClient.IsValidSubalterno(sezione, foglio, particella, sub);

            if (result)
            {
                ImpostaCodCivicoSeValido();
            }

            return new Data.RetSit(true);
        }
        #endregion

        public override Data.DettagliVia[] GetListaVie(Data.FiltroRicercaListaVie filtro, string[] codiciComuni)
        {
            return this._civiciClient.GetListaVie()
                            .Select(x => new Data.DettagliVia
                            {
                                CodiceViario = x.codice,
                                Denominazione = x.denominazione,
                                Toponimo = x.tipo
                            })
                            .ToArray();
        }


        private bool DatiCatastaliPresenti()
        {
            var codViario = this.DataSit.CodVia;
            var civico = this.DataSit.Civico;
            var esponente = this.DataSit.Esponente;

            if (String.IsNullOrEmpty(codViario) || String.IsNullOrEmpty(civico))
            {
                return false;
            }

            return this._civiciClient.IsValidCivico(codViario, civico, esponente);
        }

        private void ImpostaCodCivicoSeValido()
        {
            this.DataSit.CodCivico = String.Empty;

            var valori = new[]{
                // this.DataSit.CodVia,
                // this.DataSit.Civico,
                // this.DataSit.Esponente,
                // this.DataSit.Sezione,
                this.DataSit.Foglio,
                this.DataSit.Particella,
                this.DataSit.Sub
            };

            if (valori.Where(x => String.IsNullOrEmpty(x)).Count() > 0)
            {
                return;
            }

            this.DataSit.CodCivico = String.Join(":", valori);
        }

        public override string[] GetListaCampiGestiti()
        {
            if (this._vert.AbilitaPassiCarrai == "1")
            {
                return new[]{
                    //SitIntegrationService.NomiCampiSit.CodiceCivico,
                    SitIntegrationService.NomiCampiSit.Civico,
                    SitIntegrationService.NomiCampiSit.Esponente,
                    SitIntegrationService.NomiCampiSit.Sezione,
                    SitIntegrationService.NomiCampiSit.Foglio,
                    SitIntegrationService.NomiCampiSit.Particella,
                    SitIntegrationService.NomiCampiSit.Sub,
                    SitIntegrationService.NomiCampiSit.AccessoTipo,
                    SitIntegrationService.NomiCampiSit.AccessoNumero,
                    SitIntegrationService.NomiCampiSit.AccessoDescrizione
                };
            }

            return new[]{
                    //SitIntegrationService.NomiCampiSit.CodiceCivico,
                    SitIntegrationService.NomiCampiSit.Civico,
                    SitIntegrationService.NomiCampiSit.Esponente,
                    SitIntegrationService.NomiCampiSit.Sezione,
                    SitIntegrationService.NomiCampiSit.Foglio,
                    SitIntegrationService.NomiCampiSit.Particella,
                    SitIntegrationService.NomiCampiSit.Sub
            };
        }
    }
}
