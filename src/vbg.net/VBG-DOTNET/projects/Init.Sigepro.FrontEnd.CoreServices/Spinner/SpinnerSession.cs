//namespace Init.Sigepro.FrontEnd.CoreServices.Spinner
//{
//    public class SpinnerSession : IAsyncDisposable, IDisposable
//    {
//        private readonly SpinnerService _spinnerService;

//        public static async Task<SpinnerSession> Start(SpinnerService spinnerService)
//        {
//            var session = new SpinnerSession(spinnerService);

//            await session.Start();

//            return session;
//        }

//        private SpinnerSession(SpinnerService spinnerService)
//        {
//            this._spinnerService = spinnerService;
//        }

//        private async Task Start()
//        {
//            await this._spinnerService.ShowAsync();
//        }


//        public async ValueTask DisposeAsync()
//        {
//            await this._spinnerService.HideAsync();

//            GC.SuppressFinalize(this);
//        }

//        public void Dispose()
//        {
//            this._spinnerService.Hide();
//        }
//    }
//}
