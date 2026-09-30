using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Configuration;
using Init.SIGePro.Manager.IOC;
using Init.SIGePro.Manager.WsAnagrafeService;
using Init.Utils;
using log4net;
using PersonalLib2.Data;
using System;
using System.ServiceModel;

namespace Init.SIGePro.Manager.Logic.GestioneAnagrafiche
{
    public class AnagraficheDomandaService
    {
        private readonly DataBase _database;
        private readonly IBindingFactory _bindingFactory;
        private readonly ILog _log = LogManager.GetLogger(typeof(AnagraficheDomandaService));

        public AnagraficheDomandaService(DataBase database)
        {
            this._database = database;
            this._bindingFactory = StaticKernelContainer.GetService<IBindingFactory>();
        }

        public void AggiungiSoggettoCollegatoADomanda(AuthenticationInfo authInfo, AggiungiSoggettoCollegatoADomandaCommand cmd)
        {
            try
            {
                var idAnagrafica = -1;

                // L'anagrafica va creata o aggiornata, utilizzo il servizio java per le operazioni sull'anagrafica
                var endpoint = new EndpointAddress(ParametriConfigurazione.Get.WsAnagrafeServiceUrl);
                var binding = this._bindingFactory.CreateAndConfigure("anagrafeHttpBinding");

                using (var ws = new WsAnagrafeService.AnagrafeClient(binding, endpoint))
                {
                    var req = new InserimentoAnagrafeRequest
                    {
                        token = authInfo.Token,
                        datiAnagrafici = new AnagrafeType
                        {
                            cognome = cmd.Cognome,
                            nome = cmd.Nome,
                            comuneNascita = new ComuneType
                            {
                                Item = cmd.ComuneNascita,
                                ItemElementName = ItemChoiceType.comune
                            },
                            codiceFiscale = cmd.CodiceFiscale,
                            residenza = new LocalizzazioneType
                            {
                                cap = cmd.Cap,
                                civico = cmd.Civico,
                                comune = new ComuneType
                                {
                                    Item = cmd.Comune,
                                    ItemElementName = ItemChoiceType.comune
                                },
                                indirizzo = cmd.Indirizzo,
                                localita = cmd.Localita,
                                provincia = cmd.Provincia
                            },
                            telefono = cmd.Telefono,
                            fax = cmd.Fax,
                            email = cmd.Email
                        }
                    };

                    var esitoInserimento = ws.InserimentoAnagrafe(req);

                    if (esitoInserimento.errori != null)
                        throw new InvalidOperationException("Errore durante la creazione dell'anagrafica identificata dal codice fiscale " + cmd.CodiceFiscale + ": " + esitoInserimento.errori.descrizione);

                    idAnagrafica = Convert.ToInt32(esitoInserimento.riferimentiAnagrafe.codiceanagrafe);
                }

                // Creo la riga nei soggetti collegati
                var mgr = new IstanzeRichiedentiMgr(this._database);

                mgr.Insert(new Data.IstanzeRichiedenti
                {
                    IDCOMUNE = authInfo.IdComune,
                    CODICEISTANZA = cmd.CodiceIstanza.ToString(),
                    CODICERICHIEDENTE = idAnagrafica.ToString(),
                    CODICETIPOSOGGETTO = cmd.IdTipoSoggetto.ToString()
                });
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante l'aggiunta del soggetto collegato alla domanda: {0}\r\nDati della richiesta: {1}", ex.ToString(), StreamUtils.SerializeClass(cmd));

                throw;
            }
        }
    }
}
