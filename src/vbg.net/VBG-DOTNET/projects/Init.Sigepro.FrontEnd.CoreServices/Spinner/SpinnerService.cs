//namespace Init.Sigepro.FrontEnd.CoreServices.Spinner
//{
//    public class SpinnerService
//    {
//        //public event Action? OnShow;
//        //public event Action? OnHide;

//        public delegate Task OnSpinnerDelegate();

//        public event OnSpinnerDelegate? OnShowAsync;
//        public event OnSpinnerDelegate? OnHideAsync;

//        public int SpinnerCount { get; private set; } = 0;

//        public async Task ShowAsync()
//        {
//            if (OnShowAsync is not null)
//            {
//                await OnShowAsync?.Invoke();
//                this.SpinnerCount++;
//            }
//        }

//        public void Hide()
//        {
//            this.SpinnerCount--;

//            if (this.SpinnerCount <= 0)
//            {
//                this.SpinnerCount = 0;
//                if (OnHideAsync is not null)
//                {
//                    OnHideAsync?.Invoke();
//                }
//            }
//        }

//        public async Task HideAsync()
//        {
//            if (this.SpinnerCount == 0)
//            {
//                return;
//            }

//            this.SpinnerCount--;

//            if (this.SpinnerCount <= 0)
//            {
//                this.SpinnerCount = 0;
//                await OnHideAsync?.Invoke();
//                //OnHide?.Invoke();
//            }
//        }

//        public async Task<SpinnerSession> StartSession()
//        {
//            return await SpinnerSession.Start(this);
//        }
//    }
//}
