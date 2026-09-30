namespace Init.Sigepro.FrontEnd.CoreServices.LabelUpdater
{
    public class LabelUpdaterService
    {
        public delegate Task OnShowDelegate(string resourceId, object labelComponent);
        public delegate Task OnHideDelegate();
        public delegate Task OnRefreshLabelDelegate(string newValue, object labelComponent);

        public event OnShowDelegate? OnShowAsync;
        public event OnHideDelegate? OnHideAsync;
        public event OnRefreshLabelDelegate? OnRefreshLabelAsync;

        private int _spinnerCount = 0;

        public async Task ShowAsync(string resourceId, object labelComponent)
        {
            if (this._spinnerCount == 0)
            {
                this._spinnerCount++;

                if (OnShowAsync is not null)
                {
                    await OnShowAsync.Invoke(resourceId, labelComponent);
                }
            }
        }

        public async Task HideAsync()
        {
            this._spinnerCount = 0;

            if (OnHideAsync is not null)
            {
                await OnHideAsync.Invoke();
            }
        }

        public async Task RefreshLabelAsync(string newValue, object labelComponent)
        {
            if (OnRefreshLabelAsync is not null)
            {
                await OnRefreshLabelAsync.Invoke(newValue, labelComponent);
            }
        }
    }
}
