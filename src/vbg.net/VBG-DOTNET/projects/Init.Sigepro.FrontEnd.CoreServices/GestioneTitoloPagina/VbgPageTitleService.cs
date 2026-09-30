using Microsoft.AspNetCore.Components;

namespace Init.Sigepro.FrontEnd.CoreServices.GestioneTitoloPagina
{
    public class VbgPageTitleService
    {
        public event Action<RenderFragment>? OnTitleFragmentChanged;
        public event Action<string>? OnTitleChanged;
        public event Action<bool>? OnVisibilityChanged;

        public void SetTitle(string newTitle)
        {
            this.OnTitleChanged?.Invoke(newTitle);
        }

        public void SetTitle(RenderFragment? newTitle)
        {
            this.OnTitleFragmentChanged?.Invoke(newTitle);
        }

        public void HideTitle()
        {
            this.OnVisibilityChanged?.Invoke(false);
        }

        public void ShowTitle()
        {
            this.OnVisibilityChanged?.Invoke(true);
        }

        public void Toggle(bool visible)
        {
            if (visible)
            {
                this.ShowTitle();
            }
            else
            {
                this.HideTitle();
            }

        }
    }
}
