namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Model
{
    public class MenuIconModel : IMenuIcon
    {
        private readonly string _cssClass;

        public MenuIconModel(string cssClass)
        {
            this._cssClass = cssClass;
        }

        public override string ToString()
        {
            return this._cssClass;
        }
    }
}
