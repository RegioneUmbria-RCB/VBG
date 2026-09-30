//using Init.SIGePro.Manager.Authentication.WsSigeproSecurity;
//using Init.SIGePro.Manager.Utils;
//using Init.SIGePro.Manager.Tmp;
//using Init.SIGePro.Manager.Authentication.ServiceReferences;
//using System;

//namespace Init.SIGePro.Manager.Authentication.Legacy
//{
//    public class LoginSSOService
//    {
//        //private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
//        private readonly IAuthenticationManager _authenticationManager;

//        //public LoginSSOService(IVerticalizzazioniFactory verticalizzazioniFactory, IAuthenticationManager authenticationManager)
//        public LoginSSOService(IAuthenticationManager authenticationManager)

//        {
//            //this._verticalizzazioniFactory = verticalizzazioniFactory;
//            this._authenticationManager = authenticationManager;
//        }

//        [Obsolete("Il metodo è obsoleto e verrà rimosso a breve")]
//        public string LoginSSO(string alias, Anagrafe anagrafica)
//        {
//            if (string.IsNullOrEmpty(alias))
//                throw new ArgumentNullException(nameof(alias));

//            if (anagrafica == null)
//                throw new ArgumentNullException(nameof(anagrafica));

//            if (string.IsNullOrEmpty(anagrafica.CODICEFISCALE) && string.IsNullOrEmpty(anagrafica.PARTITAIVA))
//                throw new ArgumentException("E' stata passata un'anagrafica senza Codice Fiscale e Partita IVA. Uno di questi due parametri è obbligatorio per eseguire l'autenticazione");

//            // verifico che il codice fiscale sia scritto in maiuscolo
//            if (!String.IsNullOrEmpty(anagrafica.CODICEFISCALE))
//                anagrafica.CODICEFISCALE = anagrafica.CODICEFISCALE.ToUpper();

//            if (!String.IsNullOrEmpty(anagrafica.PARTITAIVA))
//                anagrafica.PARTITAIVA = anagrafica.PARTITAIVA.ToUpper();

//            var ccnReq = new GetDbConnectionInfoRequest
//            {
//                alias = alias,
//                ambiente = AmbienteType.DOTNET
//            };

//            var sigeproSecurityProxy = new SigeproSecurityProxy();
//            var cnnInfo = sigeproSecurityProxy.GetDbConnectionInfo(ccnReq);

//            using (var db = cnnInfo.CreateDatabase())
//            {
//                string idComune = cnnInfo.idComune;

//                // Verifico che la verticalizzaizone sia attiva
//                //var verticalizzazioneSSO = this._verticalizzazioniFactory.Create<VerticalizzazioneAutenticazioneSso>(alias, "TT");

//                //if (!verticalizzazioneSSO.Attiva)
//                //    throw new Exception("Non è possibile utilizzare questo metodo se non è attiva la modalità di autenticazione SSO");

//                anagrafica.IDCOMUNE = idComune;

//                var anagrafeMgr = new AnagrafeMgr(db);

//                anagrafeMgr.EscludiControlliSuAnagraficheDisabilitate = true;
//                anagrafeMgr.RicercaSoloCF_PIVA = true;

//                var filtro = new Anagrafe
//                {
//                    IDCOMUNE = anagrafica.IDCOMUNE,
//                    CODICEFISCALE = anagrafica.CODICEFISCALE,
//                    PARTITAIVA = anagrafica.PARTITAIVA,
//                    NOMINATIVO = anagrafica.NOMINATIVO,
//                    NOME = anagrafica.NOME,
//                    FLAG_DISABILITATO = "0",
//                };

//                filtro = anagrafeMgr.Extract(filtro);

//                if (string.IsNullOrEmpty(filtro.CODICEANAGRAFE))
//                {
//                    //l'anagrafica non esiste e deve essere inserita
//                    anagrafica.PASSWORD = RandomPassword.Generate(6);
//                    filtro = anagrafeMgr.Insert(anagrafica);
//                }
//                else
//                {
//                    //l'anagrafica è stata trovata, potrebbe non avere una password che va eventualmente impostata
//                    if (string.IsNullOrEmpty(filtro.PASSWORD))
//                    {
//                        filtro.PASSWORD = RandomPassword.Generate(6);
//                        filtro = anagrafeMgr.Update(filtro);
//                    }
//                }

//                string codUtente = String.IsNullOrEmpty(filtro.CODICEFISCALE) ? filtro.PARTITAIVA : filtro.CODICEFISCALE;

//                return this._authenticationManager.Login(alias, codUtente, filtro.PASSWORD, ContextType.Anagrafe).Token;
//            }
//        }
//    }
//}
