namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Model
{
    public class MenuItemModel
    {
        public string Titolo { get; set; }
        public string Descrizione { get; set; }
        public string Url { get; set; }
        public string Target { get; set; }
        public IMenuIcon Icona { get; set; }
        public bool MostraInHomePage { get; set; }
    }
}