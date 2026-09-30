using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Vbg.CoreControls
{
    using System.Timers;

    /// <summary>
    /// Delays the entered value by the defined interval.
    /// </summary>
    public class ValueDelayer : IDisposable
    {
        /// <summary>
        /// Internal timer used to delay the value.
        /// </summary>
        private Timer timer;

        /// <summary>
        /// Holds the last updated value.
        /// </summary>
        private string value;

        /// <summary>
        /// Event raised after the interval has passed and with new updated value.
        /// </summary>
        public event EventHandler<string> Delayed;

        /// <summary>
        /// Default constructor.
        /// </summary>
        /// <param name="interval">Interval by which the value will be delayed.</param>
        public ValueDelayer(int interval)
        {
            timer = new Timer(interval);
            timer.Elapsed += OnElapsed;
            timer.AutoReset = false;
        }

        private void OnElapsed(object source, ElapsedEventArgs e)
        {
            Delayed?.Invoke(this, value);
        }

        /// <summary>
        /// Updates the internal value.
        /// </summary>
        /// <param name="value">New value.</param>
        public void Update(string value)
        {
            timer.Stop();

            this.value = value;

            timer.Start();
        }

        /// <summary>
        /// Releases all subscribed events.
        /// </summary>
        public void Dispose()
        {
            if (timer != null)
            {
                timer.Stop();
                timer.Elapsed -= OnElapsed;
                timer = null;
            }
        }
    }
}
