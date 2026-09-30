// -----------------------------------------------------------------------
// <copyright file="NaturaEndoIncompatibileSpecification.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.IncompatibilitaNatura
{
    using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneEndoprocedimenti;
    using Init.Sigepro.FrontEnd.Infrastructure;
    using log4net;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class NaturaEndoCompatibileSpecification : ISpecification<Endoprocedimento>
    {
        private readonly Endoprocedimento _endoPrincipale;
        private readonly ILog _log = LogManager.GetLogger(typeof(NaturaEndoCompatibileSpecification));

        public NaturaEndoCompatibileSpecification(Endoprocedimento endoPrincipale)
        {
            this._endoPrincipale = endoPrincipale;
        }

        public bool IsSatisfiedBy(Endoprocedimento endo)
        {
            if (this._endoPrincipale == null)
                return true;

            if (this._endoPrincipale.Natura == null || endo.Natura == null)
                return true;

            this._log.Debug($"Verifica della compatibilità tra l'endo principale {this._endoPrincipale.Descrizione}: cod natura={this._endoPrincipale.Natura.Codice}, " +
                        $"descrizione={this._endoPrincipale.Natura.Descrizione}, binariodipendenze={this._endoPrincipale.BinarioDipendenze} " +
                        $"e l'endo {endo.Descrizione}, cod natura={endo.Natura.Codice}, natura={endo.Natura.Descrizione}");

            if ((endo.Natura.Codice & this._endoPrincipale.BinarioDipendenze) != endo.Natura.Codice)
            {
                this._log.DebugFormat(
                        "L'endoprocedimento principale {0}(BD={1}) non è compatibile con l'endo {2} (BD={3}))",
                        this._endoPrincipale.Descrizione,
                        this._endoPrincipale.BinarioDipendenze,
                        endo.Descrizione, endo.BinarioDipendenze);

                return false;
            }

            this._log.Debug("Gli endo sono compatibili");

            return true;
        }
    }
}
