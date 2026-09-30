using Init.SIGePro.Manager.Authentication;
using log4net;
using PersonalLib2.Data;
using SIGePro.Manager.Verticalizzazioni;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.GestioneConsole.UrlAccesso
{
    public class UrlAccessoConsoleService : IUrlAccessoConsoleService
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly AuthenticationInfo _authInfo;
        private readonly ILog _log = LogManager.GetLogger(typeof(UrlAccessoConsoleService));

        public UrlAccessoConsoleService(IVerticalizzazioniFactory verticalizzazioniFactory, AuthenticationInfo authInfo)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._authInfo = authInfo ?? throw new ArgumentNullException(nameof(authInfo));
        }

        public ConfigurazioneUrlConsole GetUrlAccessoConsole(string software)
        {
            var verticalizzazioneAreaRiservata = this._verticalizzazioniFactory.Create<VerticalizzazioneAreaRiservata>(this._authInfo.Alias, software);

            var defaultConfig = new UrlConsole
            {
                CrossLoginUrl = verticalizzazioneAreaRiservata.AidaSmartCrossLoginUrl,
                UrlIstanzeInSospeso = verticalizzazioneAreaRiservata.AidaSmartUrlIstanzeInSospeso,
                UrlNuovaDomanda = verticalizzazioneAreaRiservata.AidaSmartUrlNuovaDomanda
            };

            // Se la nuova verticalizzazione non è attiva in nessun comune vado in fallback sul
            // vecchio comportamento dell'area riservata (verticalizzazione AREA_RISERVATA)
            // Questo permette di mantenere la retrocompatibilità.
            // Se almeno un comune è configurato allora utilizzo per tutti la nuova logica
            var isVericalizzazioneAttiva = this.CheckVerticalizzazioneAttivaInAlmenoUnComune(software);

            if (!isVericalizzazioneAttiva)
            {
                return new ConfigurazioneUrlConsole
                {
                    Default = defaultConfig,
                    UrlLocalizzatiAttivi = false
                };
            }

            // Leggo la lista dei comuni che hanno record nella verticalizzazione, questo mi servirà ad 
            // effettuare la chiamata al ws delle verticalizzazioni utilizzando solamente i comuni attivi
            var comuniConfigurati = this.GetComuniConVerticalizzazioneConfigurata(software);

            var urlLocalizzati = comuniConfigurati.Select(comune => new
            {
                CodiceComune = comune,
                Verticalizzazione = this._verticalizzazioniFactory.Create<VerticalizzazioneUrlServiziConsole>(this._authInfo.Alias, software, comune)
            })
                .Where(cfg => cfg.Verticalizzazione.Attiva)
                .Select(cfg => new UrlConsolePerComune
                {
                    CodiceComune = cfg.CodiceComune,
                    CrossLoginUrl = cfg.Verticalizzazione.CrossLoginUrl,
                    UrlIstanzeInSospeso = cfg.Verticalizzazione.UrlIstanzeInSospeso,
                    UrlNuovaDomanda = cfg.Verticalizzazione.UrlNuovaDomanda
                });

            return new ConfigurazioneUrlConsole
            {
                Default = defaultConfig,
                UrlLocalizzatiAttivi = true,
                UrlLocalizzati = urlLocalizzati.ToArray()
            };
        }

        /// <summary>
        /// Restituisce true se la verticalizzazione è attiva per almeno uno dei comuni associati nel software specificato o in TT
        /// </summary>
        /// <param name="software"></param>
        /// <returns></returns>
        private bool CheckVerticalizzazioneAttivaInAlmenoUnComune(string software)
        {
            using (var db = this._authInfo.CreateDatabase())
            {
                var sql = $"SELECT Count(*) FROM  verticalizzazioni WHERE idcomune={db.Specifics.QueryParameterName("idcomune")} AND software in ('TT', {db.Specifics.QueryParameterName("software")}) and modulo='AR_URL_SERVIZI_CONSOLE' AND attivo=1";

                return 0 < db.ExecuteScalar(sql, 0, mp =>
                {
                    mp.AddParameter("idcomune", this._authInfo.IdComune);
                    mp.AddParameter("software", software);
                });
            }
        }

        private IEnumerable<string> GetComuniConVerticalizzazioneConfigurata(string software)
        {
            using (var db = this._authInfo.CreateDatabase())
            {
                // so che la query non è correttissima ma mi serve leggere rapidamente la lista dei comuni che *potrebbero*
                // essere attivi. Dovrò comunque fare una chiamata al ws delle verticalizzazioni e in quella sede posso 
                // scremare i comuni effettivamente attivi e quelli non attivi
                var sql = $@"SELECT DISTINCT 
                                codicecomune 
                            FROM  
                                verticalizzazioniparametri 
                            WHERE 
                                idcomune={db.Specifics.QueryParameterName("idcomune")} AND 
                                modulo='AR_URL_SERVIZI_CONSOLE' AND 
                                software in ('TT', {db.Specifics.QueryParameterName("software")})";

                return db.ExecuteReader(sql,
                    mp =>
                    {
                        mp.AddParameter("idcomune", this._authInfo.IdComune);
                        mp.AddParameter("software", software);
                    },
                    dr => dr.GetString("codicecomune"));
            }
        }
    }
}
