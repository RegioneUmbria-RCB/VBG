namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti
{
    public class SubEndoprocedimentoSelezionato
	{
		public readonly int Id;
		public readonly int? IdPadre;

		public SubEndoprocedimentoSelezionato(int id)
		{
			this.Id = id;
			this.IdPadre = null;
		}

		public SubEndoprocedimentoSelezionato(int id, int idPadre)
		{
			this.Id = id;
			this.IdPadre = idPadre;
		}
	}
}
