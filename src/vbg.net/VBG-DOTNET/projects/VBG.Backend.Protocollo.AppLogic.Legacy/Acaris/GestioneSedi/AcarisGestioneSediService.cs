using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Logic.GestioneAnagrafiche.GestioneSedi;
using SIGePro.Data.Data;
using System.Configuration;
using VBG.Backend.Protocollo.Verticalizazioni.Legacy;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.GestioneSedi
{
    public class AcarisGestioneSediService
    {
        private readonly AuthenticationInfo _authInfo;
        private readonly AnagrafeIndirizziRepository _repository;
        private readonly VerticalizzazioneProtocolloAcaris _vert;

        public AcarisGestioneSediService(AuthenticationInfo authInfo, string software, string codiceComune)
        {
            this._authInfo = authInfo;
            this._repository = new AnagrafeIndirizziRepository(this._authInfo.CreateDatabase(), this._authInfo.IdComune);
            this._vert = new VerticalizzazioneProtocolloAcaris(this._authInfo.Alias, software, codiceComune);
        }

        public string RegistraNuovaSede(int codiceAnagrafe, string partitaIva, string codiceFiscale, string indirizzo, string citta)
        {
            var sede = this._repository.Insert(new AnagrafeIndirizzi
            {
                Citta = citta,
                CodiceFiscale = codiceFiscale,
                CodiceAnagrafe = codiceAnagrafe,
                IdComune = this._authInfo.IdComune,
                IdentificativoSede = this.CalcolaIdentificativoSede(),
                Indirizzo = indirizzo,
                PartitaIVA = partitaIva,
            });

            return sede.IdentificativoSede;
        }

        private string CalcolaIdentificativoSede()
        {
            var min = this._vert.CodiceDossierMin;
            var max = this._vert.CodiceDossierMax;
            var pad = this._vert.CodiceDossierLength;

            if (!min.HasValue)
            {
                throw new ConfigurationErrorsException("Impossibile calcolare un nuovo identificativo sede, non è stato configurato in verticalizzazione il valore minimo da cui partire");
            }

            if (!max.HasValue)
            {
                throw new ConfigurationErrorsException("Impossibile calcolare un nuovo identificativo sede, non è stato configurato in verticalizzazione il valore massimo");
            }

            var strMin = min.Value.ToString().PadLeft(8, '0');

            using (var db = this._authInfo.CreateDatabase())
            {
                string sql = $@"select 
                                  max(identificativo_sede) as identificativo_sede
                                from 
                                  anagrafe_indirizzi 
                                where 
                                  idcomune = {db.Specifics.QueryParameterName("idComune")} and 
                                  identificativo_sede >= {db.Specifics.QueryParameterName("min")} and 
                                  identificativo_sede <= {db.Specifics.QueryParameterName("max")}";

                var ultimo_identificativo = db.ExecuteScalar<int?>(sql, 0,
                   mp =>
                   {
                       mp.AddParameter("idComune", this._authInfo.IdComune);
                       mp.AddParameter("min", min.Value);
                       mp.AddParameter("max", max.Value);
                   });

                if (!ultimo_identificativo.HasValue)
                {
                    return strMin;
                }

                if (ultimo_identificativo.Value == max.Value)
                {
                    throw new ConfigurationErrorsException($"Impossibile calcolare un nuovo identificativo sede, è stato raggiunto il limite massimo di identificativi riservati");
                }


                return (ultimo_identificativo.Value + 1).ToString().PadLeft(8, '0');
            }
        }
    }
}
