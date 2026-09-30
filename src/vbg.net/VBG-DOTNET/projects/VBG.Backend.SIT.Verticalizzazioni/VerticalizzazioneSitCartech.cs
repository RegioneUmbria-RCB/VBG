using SIGePro.Manager.VerticalizzazioniBase;

namespace VBG.Backend.SIT.Verticalizzazioni
{
    /// <summary>
    /// Se abilitato viene richiesto nelle istanza il codice Edificio, il codice poligono ed il codice punto utilizzato dal SIT per interrogare SIGePro tramite una vista. E' stato abilitatoper NovaMilanese.
    /// </summary>
    public partial class VerticalizzazioneSitCartech : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "SIT_CARTECH";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;
        public VerticalizzazioneSitCartech()
        {

        }

        public VerticalizzazioneSitCartech(string idComuneAlias, string software) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software) { }



    }
}