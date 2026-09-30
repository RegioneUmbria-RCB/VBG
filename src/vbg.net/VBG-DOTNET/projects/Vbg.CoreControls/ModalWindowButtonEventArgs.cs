namespace Vbg.CoreControls
{

    public class ModalWindowButtonEventArgs
    {
        public bool IsCanceled { get; private set; } = false;

        internal ModalWindowButtonEventArgs()
        {

        }

        public void Cancel()
        {
            this.IsCanceled = true;
        }
    }
}
